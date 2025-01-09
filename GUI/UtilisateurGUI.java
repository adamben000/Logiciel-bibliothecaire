package GUI;

import LMS.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.event.*;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

public class UtilisateurGUI extends JPanel implements ActionListener {

    // Instance de la base de données
    private Database db = new Database();
    private JLabel nomUtilisateurL;
    private String nomUtilisateur;

    JButton emprunterBouton = new JButton();
    JLabel livreEmprunter = new JLabel();
    JLabel dateDEmprunt = new JLabel();
    JLabel dateDERemise = new JLabel();
    JButton retournerBouton = new JButton();

    // Panel pour ajouter/retourner un livre
    private JPanel panelGauche;

    // Panel pour la recherche de livres
    private JPanel panelCentre;

    // Panel pour les paramètres, la date et le nom d'utilisateur
    private JPanel panelHaut;

    JTable j;
    private TableRowSorter<DefaultTableModel> sorter;

    // Méthode pour rechercher dans la table
    //Inpirer de ce code: https://www.tutorialspoint.com/how-to-implement-the-search-functionality-of-a-jtable-in-java
    private void searchTable(String searchText) {
        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null); // Réinitialiser le filtre si aucune recherche
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Appliquer le filtre
        }
    }

    // Constructeur de l'interface utilisateur
    public UtilisateurGUI(CardLayout cardLayout, JPanel cardPanel, JFrame cadre, String nomUtilisateur) {
        setSize(1003, 600);
        this.nomUtilisateur = nomUtilisateur;

        setLayout(new BorderLayout());

        // Panel du haut ######################################################################
        panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelHaut.setPreferredSize(new Dimension(800, 50));
        add(panelHaut, BorderLayout.NORTH);

        Font headerFont = new Font("Arial", Font.PLAIN, 16);

        // Afficher le nom de l'utilisateur
        nomUtilisateurL = new JLabel("Utilisateur: " + this.nomUtilisateur);
        nomUtilisateurL.setFont(headerFont);

        // Afficher la date du jour
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String dateForma = dateFormat.format(date);
        JLabel dateDuJourL = new JLabel(dateForma);
        dateDuJourL.setFont(headerFont);

        // Ajouter un bouton pour les paramètres avec une icône
        ImageIcon originalIcon = new ImageIcon("GUI/assets/parametres.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        Icon icon = new ImageIcon(scaledImage);
        JButton parametresB = new JButton(icon);
        parametresB.setBorderPainted(false);
        parametresB.setContentAreaFilled(false);
        parametresB.setFocusPainted(false);
        parametresB.setPreferredSize(new Dimension(35, 35));
        parametresB.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Organiser les composants du panel du haut
        JPanel contenantGauche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel contenantCentre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel contenantDroite = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        contenantGauche.add(nomUtilisateurL);
        contenantCentre.add(dateDuJourL);
        contenantDroite.add(parametresB);

        panelHaut.add(contenantGauche, BorderLayout.WEST);
        panelHaut.add(contenantCentre, BorderLayout.CENTER);
        panelHaut.add(contenantDroite, BorderLayout.EAST);

        panelHaut.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Panel de gauche ###################################################################
        panelGauche = new JPanel(new BorderLayout());
        panelGauche.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelGauche.setPreferredSize(new Dimension(400, 550));
        add(panelGauche, BorderLayout.WEST);

        // Ajouter un bouton pour se déconnecter
        JPanel deconnexionPanel = new JPanel();
        deconnexionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton deconnexionB = new JButton("Deconnexion");
        deconnexionPanel.add(deconnexionB);
        panelGauche.add(deconnexionPanel, BorderLayout.SOUTH);

        // Contenu principal pour l'emprunt/retour de livre
        JPanel mainPanelGauche = new JPanel();
        emprunterBouton.setText("Emprunter");
        livreEmprunter.setText("Livre emprunté: ");
        dateDEmprunt.setText("Date d'emprunt: ");
        dateDERemise.setText("Date de remise: ");
        retournerBouton.setText("Retournez votre livre");

        mainPanelGauche.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(10, 10, 10, 10);

        gbc1.gridx = 0;
        gbc1.gridy = 1;
        mainPanelGauche.add(livreEmprunter, gbc1);
        gbc1.gridy = 2;
        mainPanelGauche.add(dateDEmprunt, gbc1);
        gbc1.gridy = 3;
        mainPanelGauche.add(dateDERemise, gbc1);
        gbc1.gridy = 4;
        gbc1.gridwidth = 2;
        mainPanelGauche.add(retournerBouton, gbc1);
        mainPanelGauche.add(emprunterBouton, gbc1);

        panelGauche.add(mainPanelGauche, BorderLayout.CENTER);

        // Ajouter des événements pour les boutons d'emprunt et de retour
        emprunterBouton.addActionListener(e -> {
            // Gestion de l'emprunt de livre
            JPanel empruntPopup = new JPanel();
            JLabel idL = new JLabel("Livre ID:");
            JTextField idF = new JTextField(10);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0; gbc.gridy = 0;
            empruntPopup.add(idL, gbc);
            gbc.gridx = 1; gbc.gridy = 0;
            empruntPopup.add(idF, gbc);

            int result = JOptionPane.showConfirmDialog(
                    cadre,
                    empruntPopup,
                    "ID",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String enteredId = idF.getText();
                String utilisateur = this.nomUtilisateur;
                String livreId = enteredId;

                if (livreId.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Veuillez remplir le champs : Livre ID.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    if (db.aEmprunt(utilisateur)) {
                        JOptionPane.showMessageDialog(this,
                                "Erreur : Vous avez déjà un emprunt actif.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!db.livreExiste(livreId)) {
                        JOptionPane.showMessageDialog(this,
                                "Erreur : Le livre spécifié n'existe pas.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!db.livreDisponible(livreId, 1)) {
                        JOptionPane.showMessageDialog(this,
                                "Erreur : Le livre spécifié n'est pas disponible.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (db.emprunterLivre(utilisateur, livreId)) {
                        actualiserLeTableau();
                        idF.setText("");
                        empruntDetection();

                        JOptionPane.showMessageDialog(this,
                                "Livre emprunté avec succès !",
                                "Succès",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Erreur inattendue lors de l'emprunt.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e1){
                    JOptionPane.showMessageDialog(this,
                            "Erreur système lors de l'emprunt.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }
        });


        // Panel du centre ######################################################################
        panelCentre = new JPanel();
        panelCentre.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(panelCentre, BorderLayout.CENTER);

        // Ajouter le champ de recherche et la table des livres
        JLabel rechercheL = new JLabel("Recherche:");
        JTextField rechercheF = new JTextField(15);

        rechercheF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchTable(rechercheF.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchTable(rechercheF.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchTable(rechercheF.getText());
            }
        });

        JPanel recherchePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        recherchePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        recherchePanel.add(rechercheL);
        recherchePanel.add(rechercheF);

        // Charger les données des livres
        String[][] data = chargerLesDonneesDesLivres();
        String[] columnNames = {"Livres", "Auteur", "Genre", "Disponibles", "ID"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        j = new JTable(model);
        sorter = new TableRowSorter<>(model);
        j.setRowSorter(sorter);

        JScrollPane sp = new JScrollPane(j);
        sp.setPreferredSize(new Dimension(585, 450));

        panelCentre.add(recherchePanel, BorderLayout.NORTH);
        panelCentre.add(sp, BorderLayout.CENTER);

        // Bouton pour déconnexion
        deconnexionB.addActionListener(e -> {
            cadre.setSize(600, 400);
            cadre.setTitle("Connexion-Librairie");
            cadre.setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "Connexion");
        });

        retournerBouton.addActionListener(this);

        parametresB.addActionListener(e -> {
            BoiteDeDialogueParametres dialog = new BoiteDeDialogueParametres((JFrame) SwingUtilities.getWindowAncestor(this), db, nomUtilisateur);
            dialog.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // Gestion des actions (boutons)
        String command = actionEvent.getActionCommand();
        if (command.equals("Retournez votre livre")) {
            String utilisateur = this.nomUtilisateur;
            try {

                int joursRetard = db.enRetard(utilisateur);
                if (db.retournerLivre(utilisateur)) {
                    actualiserLeTableau();

                    if (joursRetard > 0) {
                        JOptionPane.showMessageDialog(this,
                                "Livre retourné en retard de " + joursRetard + " jours",
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                        empruntDetection();
                        actualiserLeTableau();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Livre retourné avec succès !",
                                "Info",
                                JOptionPane.INFORMATION_MESSAGE);
                        empruntDetection();
                        actualiserLeTableau();
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erreur : Vous n'avez emprunter aucun livre.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur système lors du retour du livre.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    // Charger les données des livres à partir du fichier
    private String[][] chargerLesDonneesDesLivres() {
        ArrayList<String[]> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("db/livres.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                String[] bookData = {rowData[0], rowData[1], rowData[2], rowData[4], rowData[5]};
                dataList.add(bookData);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des utilisateurs. Fichier manquant ou inaccessible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        String[][] data = new String[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }
        return data;
    }

    // Détection des emprunts pour mettre à jour l'affichage
    public void empruntDetection() {
        String[] infoEmprunt = db.detecterEmprunt(this.nomUtilisateur);
        if (infoEmprunt.length > 1) {
            livreEmprunter.setText("Livre emprunter: " + infoEmprunt[1]);
            dateDEmprunt.setText("Date d'emprunt: " + infoEmprunt[3]);
            dateDERemise.setText("Date de remise: " + infoEmprunt[4]);
            emprunterBouton.setVisible(false);
            retournerBouton.setVisible(true);
        } else if (infoEmprunt[0].equals("PAS_D_EMPRUNT")) {
            livreEmprunter.setText("Livre emprunter: Aucun livre");
            dateDEmprunt.setText("Date d'emprunt : Aucune");
            dateDERemise.setText("Date de remise: Aucune");
            retournerBouton.setVisible(false);
            emprunterBouton.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Erreur lecture de fichier.",
                    "Erreur:",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Charger les données des livres à partir d'un fichier
    public void actualiserLeTableau() {
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);

        String[][] data1 = chargerLesDonneesDesLivres();
        for (String[] row : data1) {
            model.addRow(row);
        }
    }

    class BoiteDeDialogueParametres extends JDialog {
        private JTextField nouveauNom1;
        private JTextField nouveauNom2;
        private JPasswordField ancienMotDePasse;
        private JPasswordField nouveauMotDePasse1;
        private JPasswordField nouveauMotDePasse2;
        private JButton okButton;
        private JButton cancelButton;
        private JButton changerNomButton;
        private JButton changerMotDePasseButton;
        private Database db;
        private String nomUtilisateur;

        public BoiteDeDialogueParametres(JFrame parent, Database db, String nomUtilisateur) {
            super(parent, "Paramètres", true);
            this.db = db;
            this.nomUtilisateur = nomUtilisateur;
            initialiserLesComposants();
            configurationMiseEnPage();
            configurerLesListeners();
            pack();
            setLocationRelativeTo(parent);
        }

        private void initialiserLesComposants() {
            nouveauNom1 = new JTextField(15);
            nouveauNom2 = new JTextField(15);
            ancienMotDePasse = new JPasswordField(15);
            nouveauMotDePasse1 = new JPasswordField(15);
            nouveauMotDePasse2 = new JPasswordField(15);

            okButton = new JButton("OK");
            cancelButton = new JButton("Cancel");
            changerNomButton = new JButton("Changer");
            changerMotDePasseButton = new JButton("Changer");
        }

        private void configurationMiseEnPage() {
            setLayout(new BorderLayout());

            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel headerLabel = new JLabel("Changer infos");
            headerLabel.setFont(new Font(headerLabel.getFont().getName(), Font.BOLD, 24));
            headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            headerPanel.add(headerLabel);

            JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));

            JPanel panelGauche = new JPanel(new GridBagLayout());
            GridBagConstraints gbcLeft = new GridBagConstraints();
            gbcLeft.insets = new Insets(5, 5, 5, 5);
            gbcLeft.fill = GridBagConstraints.HORIZONTAL;

            gbcLeft.gridx = 0;
            gbcLeft.gridy = 0;
            panelGauche.add(new JLabel("Nouveau Nom:"), gbcLeft);

            gbcLeft.gridy = 1;
            panelGauche.add(nouveauNom1, gbcLeft);

            gbcLeft.gridy = 2;
            panelGauche.add(new JLabel("Réécrire Nouveau Nom:"), gbcLeft);

            gbcLeft.gridy = 3;
            panelGauche.add(nouveauNom2, gbcLeft);

            gbcLeft.gridy = 4;
            panelGauche.add(changerNomButton, gbcLeft);

            JPanel panelDroit = new JPanel(new GridBagLayout());
            GridBagConstraints gbcRight = new GridBagConstraints();
            gbcRight.insets = new Insets(5, 5, 5, 5);
            gbcRight.fill = GridBagConstraints.HORIZONTAL;

            gbcRight.gridx = 0;
            gbcRight.gridy = 0;
            panelDroit.add(new JLabel("Ancien Mot de Passe:"), gbcRight);

            gbcRight.gridy = 1;
            panelDroit.add(ancienMotDePasse, gbcRight);

            gbcRight.gridy = 2;
            panelDroit.add(new JLabel("Nouveau Mot de Passe:"), gbcRight);

            gbcRight.gridy = 3;
            panelDroit.add(nouveauMotDePasse1, gbcRight);

            gbcRight.gridy = 4;
            panelDroit.add(new JLabel("Réécrire Nouveau Mot de Passe:"), gbcRight);

            gbcRight.gridy = 5;
            panelDroit.add(nouveauMotDePasse2, gbcRight);

            gbcRight.gridy = 6;
            panelDroit.add(changerMotDePasseButton, gbcRight);

            mainPanel.add(panelGauche);
            mainPanel.add(panelDroit);

            JPanel panelInferieur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelInferieur.add(okButton);
            panelInferieur.add(cancelButton);

            panelGauche.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelDroit.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            add(headerPanel, BorderLayout.NORTH);
            add(mainPanel, BorderLayout.CENTER);
            add(panelInferieur, BorderLayout.SOUTH);

            setPreferredSize(new Dimension(500, 400));
        }

        private void configurerLesListeners() {
            cancelButton.addActionListener(e -> dispose());

            okButton.addActionListener(e -> dispose());

            changerNomButton.addActionListener(e -> {
                String nouveauNomSaisi1 = nouveauNom1.getText();
                String nouveauNomSaisi2 = nouveauNom2.getText();

                if (nouveauNomSaisi1.equals(nouveauNomSaisi2)) {
                    try {
                        if (nouveauNomSaisi1.length() > 10) {
                            JOptionPane.showMessageDialog(this,
                                    "Le nom d'utilisateur ne peut pas dépasser 10 caractères!",
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                            nouveauNom1.setText("");
                            nouveauNom2.setText("");
                            return;
                        }

                        if (nouveauNomSaisi1.equals(UtilisateurGUI.this.nomUtilisateur)) {
                            JOptionPane.showMessageDialog(this,
                                    "Le nouveau nom d'utilisateur ne peut pas être identique à l'ancien!",
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                            nouveauNom1.setText("");
                            nouveauNom2.setText("");
                            return;
                        }

                        if (nouveauNomSaisi1.equalsIgnoreCase("admin")) {
                            JOptionPane.showMessageDialog(this,
                                    "Le nom d'utilisateur 'admin' n'est pas autorisé!",
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                            nouveauNom1.setText("");
                            nouveauNom2.setText("");
                            return;
                        }

                        if (!db.utilisateurExisteSansMotDePasse(nouveauNomSaisi1)) {
                            if (db.aEmprunt(UtilisateurGUI.this.nomUtilisateur)) {
                                db.mettreAJourEmprunt(UtilisateurGUI.this.nomUtilisateur, nouveauNomSaisi1);
                            }

                            db.changerUtilisateur(UtilisateurGUI.this.nomUtilisateur, nouveauNomSaisi1);

                            UtilisateurGUI.this.nomUtilisateur = nouveauNomSaisi1;
                            nomUtilisateurL.setText("Utilisateur: " + nouveauNomSaisi1);

                            this.nomUtilisateur = nouveauNomSaisi1;

                            empruntDetection();

                            nouveauNom1.setText("");
                            nouveauNom2.setText("");

                            JOptionPane.showMessageDialog(this, "Nom d'utilisateur changé avec succès!");
                        } else {
                            nouveauNom1.setText("");
                            nouveauNom2.setText("");
                            JOptionPane.showMessageDialog(this,
                                    "Ce nom d'utilisateur existe déjà ou est votre nom actuel!",
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        nouveauNom1.setText("");
                        nouveauNom2.setText("");
                        JOptionPane.showMessageDialog(this,
                                "Une erreur est survenue lors du changement de nom d'utilisateur.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Les noms saisis ne correspondent pas!",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            changerMotDePasseButton.addActionListener(e -> {
                String ancienMotDePasseSaisi = new String(ancienMotDePasse.getPassword());
                String nouveauMotDePasseSaisi1 = new String(nouveauMotDePasse1.getPassword());
                String nouveauMotDePasseSaisi2 = new String(nouveauMotDePasse2.getPassword());

                if (ancienMotDePasseSaisi.isEmpty() || nouveauMotDePasseSaisi1.isEmpty() || nouveauMotDePasseSaisi2.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Tous les champs sont requis!",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (nouveauMotDePasseSaisi1.length() > 16 || nouveauMotDePasseSaisi2.length() > 16) {
                    JOptionPane.showMessageDialog(this,
                            "Le mot de passe ne peut pas dépasser 16 caractères!",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (nouveauMotDePasseSaisi1.length() < 4 || nouveauMotDePasseSaisi2.length() < 4) {
                    JOptionPane.showMessageDialog(this,
                            "Le mot de passe doit avoir un minimum de 4 caractères.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nouveauMotDePasseSaisi1.contains(",") || nouveauMotDePasseSaisi2.contains(",") ||
                        nouveauMotDePasseSaisi1.contains(" ") || nouveauMotDePasseSaisi2.contains(" ")
                        || ancienMotDePasseSaisi.contains(" ") || ancienMotDePasseSaisi.contains(",")){
                    JOptionPane.showMessageDialog(this,
                            "Le mot de passe ne peut pas contenir de virgule ou d'espace!",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (nouveauMotDePasseSaisi1.equals(nouveauMotDePasseSaisi2)) {
                    try {
                        if (db.changerMotDePasse(nomUtilisateur, ancienMotDePasseSaisi, nouveauMotDePasseSaisi1)) {
                            ancienMotDePasse.setText("");
                            nouveauMotDePasse1.setText("");
                            nouveauMotDePasse2.setText("");
                            JOptionPane.showMessageDialog(this, "Mot de passe changé avec succès!");
                        } else {
                            ancienMotDePasse.setText("");
                            nouveauMotDePasse1.setText("");
                            nouveauMotDePasse2.setText("");
                            JOptionPane.showMessageDialog(this,
                                    "L'ancien mot de passe est incorrect!",
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ancienMotDePasse.setText("");
                        nouveauMotDePasse1.setText("");
                        nouveauMotDePasse2.setText("");
                        JOptionPane.showMessageDialog(this,
                                "Une erreur est survenue lors du changement de mot de passe.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Les nouveaux mots de passe ne correspondent pas!",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }


}
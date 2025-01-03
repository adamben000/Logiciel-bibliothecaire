package GUI;
import LMS.Database;
import LMS.Utilisateur;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class AdminUtilisateur extends JPanel implements ActionListener {
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    GridLayout laGrid = new GridLayout(2, 1);

    // Panel 2
    JLabel utilisateur = new JLabel("Utilisateur:");
    JTextField utilisateurF = new JTextField("", 10);
    JButton supprimerB = new JButton("Supprimer");

    JLabel utilisateurLCreate = new JLabel("Utilisateur:");
    JTextField utilisateurFCreate = new JTextField("", 10);

    JLabel motDePasseLCreate = new JLabel("Mot de passe:");
    JTextField motDePasseFCreate = new JTextField("", 10);
    JButton ajouterB = new JButton("Ajouter");
    JButton retourB = new JButton("Retour");

    GridBagConstraints gbc = new GridBagConstraints();

    private Database db = new Database();

    CardLayout cardLayout;
    JPanel cardPanel;

    JTable j;

    public AdminUtilisateur(CardLayout cardLayout, JPanel cardPanel, JFrame frame) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        setSize(800, 600);
        setLayout(laGrid);

        String[][] data = loadUsernamesFromFile();
        String[] columnNames = {"Utilisateurs"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // I put this prevent admin from editing the usernames
            }
        };

        j = new JTable(model);
        j.setBounds(30, 40, 200, 300);

        // centrer les noms
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        centerRenderer.setVerticalAlignment(JLabel.CENTER);
        for (int i = 0; i < j.getColumnCount(); i++) {
            j.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // enlever le bleu quand on clique
        j.getTableHeader().setReorderingAllowed(false);
        j.setRowSelectionAllowed(false);
        j.setColumnSelectionAllowed(false);
        j.setFocusable(false);
        j.getTableHeader().setResizingAllowed(false);

        JScrollPane sp = new JScrollPane(j);
        panel1.setLayout(new BorderLayout());
        panel1.add(sp, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        // Panel 2
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel2.add(utilisateur, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel2.add(utilisateurF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel2.add(supprimerB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel2.add(retourB, gbc);

        // panel 3
        panel3.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(10, 10, 10, 10);

        gbc1.gridx = 0;
        gbc1.gridy = 0;
        panel3.add(utilisateurLCreate, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 0;
        panel3.add(utilisateurFCreate, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 1;
        panel3.add(motDePasseLCreate, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 1;
        panel3.add(motDePasseFCreate, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 3;
        gbc1.gridwidth = 2;
        panel3.add(ajouterB, gbc1);

        add(panel1, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        bottomPanel.add(panel2);
        bottomPanel.add(panel3);

        supprimerB.addActionListener(this);
        ajouterB.addActionListener(this);

        retourB.addActionListener(e -> {frame.setSize(600, 400);frame.setTitle("Librairie-Management");frame.setLocationRelativeTo(null);cardLayout.show(cardPanel, "AdminOptionStack");});
        setVisible(true);

    }

    private boolean verification() {
        String nom = utilisateurFCreate.getText();
        String pass = motDePasseFCreate.getText();

        List<String> errorMessages = new ArrayList<>();

        if (nom.isEmpty()) {
            errorMessages.add("Le champ \"Nom d'utilisateur\" ne peut pas etre vide!");
        } else if (nom.length() > 10) {
            errorMessages.add("Ne peut pas entrer plus de 10 characteres pour nom d'utilisateur.");
        } else if (nom.contains(" ")) {
            errorMessages.add("Ne peut pas entrer d'espace pour nom d'utilisateur!");
        } else if (nom.contains(",")) {
            errorMessages.add("Le \"Nom d'utilisateur\" ne peut pas contenir de virgule!");
        }

        if (pass.isEmpty()) {
            errorMessages.add("Le champ \"Mot de passe\" ne peut pas etre vide!");
        } else if (pass.length() > 16) {
            errorMessages.add("Ne peut pas entrer plus de 16 characteres pour mot de passe.");
        } else if (pass.contains(" ")) {
            errorMessages.add("Ne peut pas entrer d'espace pour mot de passe!");
        } else if (pass.contains(",")) {
            errorMessages.add("Le \"Mot de passe\" ne peut pas contenir de virgule!");
        }

        if (!errorMessages.isEmpty()) {
            JOptionPane.showMessageDialog(
                    AdminUtilisateur.this,
                    String.join("\n", errorMessages),
                    "Erreurs:",
                    JOptionPane.ERROR_MESSAGE
            );
            enleverCharacteres();
            return false;
        } else {
            return true;
        }
    }
    public void enleverCharacteres() {
        utilisateurF.setText("");
        utilisateurFCreate.setText("");
        motDePasseFCreate.setText("");
    }

    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);

        String[][] data = loadUsernamesFromFile();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        if (command.equals("Supprimer")) {
            String nom = utilisateurF.getText();

            try {
                if (db.utilisateurExisteSansPass(nom)) {
                    db.supprimerUtilisateur(nom);
                    JOptionPane.showMessageDialog(
                            AdminUtilisateur.this,
                            "Compte supprimer!",
                            "Info:",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    refreshTable();
                    enleverCharacteres();
                } else {
                    JOptionPane.showMessageDialog(
                            AdminUtilisateur.this,
                            "Compte n'existe pas!",
                            "Erreur:",
                            JOptionPane.ERROR_MESSAGE
                    );

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (command.equals("Ajouter")) {
            String nom = utilisateurFCreate.getText();
            String pass = motDePasseFCreate.getText();
            Utilisateur utilisateur1 = new Utilisateur(nom, pass);

            if (verification()) {
                try {
                    if (!db.utilisateurExisteSansPass(nom)) {
                        db.ajouterUtilisateur(utilisateur1);
                        JOptionPane.showMessageDialog(
                                AdminUtilisateur.this,
                                "Compte ajouter!",
                                "Info:",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        refreshTable();
                        enleverCharacteres();
                    } else {
                        JOptionPane.showMessageDialog(
                                AdminUtilisateur.this,
                                "Compte existe deja!",
                                "Erreur:",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    private String[][] loadUsernamesFromFile () {
        ArrayList<String[]> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("db/utilisateurs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] rowData = line.split(",");
                if (rowData.length > 0) {
                    dataList.add(new String[]{rowData[0]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String[][] data = new String[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }
        return data;
    }
}
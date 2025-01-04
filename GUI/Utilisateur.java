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

public class Utilisateur extends JPanel implements ActionListener {

    private Database db = new Database();

    //recherche de livre
    private JPanel panelGauche;

    //ajouter retourner livre
    private JPanel panelCentre;

    //settings, date et nom d'utilisateur
    private JPanel panelHaut;

    private String nomUtilisateur;
    JTable j;
    private TableRowSorter<DefaultTableModel> sorter;

    private void searchTable(String searchText) {
        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    public Utilisateur(CardLayout cardLayout, JPanel cardPanel, JFrame frame, String nomUtilisateur) {
        setSize(1003, 600);
        this.nomUtilisateur = nomUtilisateur;

        setLayout(new BorderLayout());

        //Panel Haut##############################################################################
        panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelHaut.setPreferredSize(new Dimension(800, 50));
        add(panelHaut, BorderLayout.NORTH);

        Font headerFont = new Font("Arial", Font.PLAIN, 16);

        JLabel nomUtilisateurL = new JLabel("Utilisateur: " + this.nomUtilisateur);
        nomUtilisateurL.setFont(headerFont);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String dateForma = dateFormat.format(date);
        JLabel dateDuJourL = new JLabel(dateForma);
        dateDuJourL.setFont(headerFont);

        ImageIcon originalIcon = new ImageIcon("GUI/assets/parametres.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        Icon icon = new ImageIcon(scaledImage);
        JButton parametresB = new JButton(icon);
        parametresB.setBorderPainted(false);
        parametresB.setContentAreaFilled(false);
        parametresB.setFocusPainted(false);
        parametresB.setPreferredSize(new Dimension(35, 35));
        parametresB.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

        // Panel Gauche####################################################################################
        panelGauche = new JPanel();
        panelGauche = new JPanel(new BorderLayout());
        panelGauche.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelGauche.setPreferredSize(new Dimension(400, 550));
        add(panelGauche, BorderLayout.WEST);
        JPanel deconnexionPanel = new JPanel();
        deconnexionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton deconnexionB = new JButton("Deconnexion");
        deconnexionPanel.add(deconnexionB);
        panelGauche.add(deconnexionPanel,BorderLayout.SOUTH);


        // Panel Centre####################################################################################
        panelCentre = new JPanel();
        panelCentre.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(panelCentre, BorderLayout.CENTER);

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

        JPanel recherchePanel = new JPanel();
        recherchePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        recherchePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rechercheL.setFont(new Font("Arial", Font.PLAIN, 14));
        recherchePanel.add(rechercheL);
        recherchePanel.add(rechercheF);

        String[][] data = loadDataFromLivres();
        String[] columnNames = {"Livres", "Auteur", "Genre", "Disponibles", "ID"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        j = new JTable(model);
        j.getTableHeader().setReorderingAllowed(false);
        j.setRowSelectionAllowed(false);
        j.setColumnSelectionAllowed(false);
        j.setFocusable(false);
        j.getTableHeader().setResizingAllowed(false);
        sorter = new TableRowSorter<>(model);
        j.setRowSorter(sorter);
        JScrollPane sp = new JScrollPane(j);
        sp.setPreferredSize(new Dimension(585, 450));
        panelCentre.add(recherchePanel, BorderLayout.NORTH);
        panelCentre.add(sp, BorderLayout.CENTER);

        deconnexionB.addActionListener(e ->{frame.setSize(600,400);frame.setTitle("Connexion-Librairie");frame.setLocationRelativeTo(null);cardLayout.show(cardPanel, "Connexion");});
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
    }

    private String[][] loadDataFromLivres() {
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

    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) j.getModel();
        model.setRowCount(0);

        String[][] data1 = loadDataFromLivres();
        for (String[] row : data1) {
            model.addRow(row);
        }
    }
}
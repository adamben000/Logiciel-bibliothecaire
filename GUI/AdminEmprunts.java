package GUI;
import LMS.Database;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class AdminEmprunts extends JPanel implements ActionListener {
    JPanel panel1 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    GridLayout laGrid = new GridLayout(2,1);

    // Panel 2
    JLabel utilisateurL = new JLabel("Utilisateur:");
    JTextField utilisateurF = new JTextField("", 10);
    JButton retournerLeLivreB = new JButton("Retourner le livre") ;
    JButton retourB = new JButton("Retour");

    // Panel 3
    JLabel utilisateurEmpruntL = new JLabel("Utilisateur:");
    JTextField utilisateurEmpruntF = new JTextField("", 10);
    JLabel LivreEmprunterL = new JLabel("Livre ID:");
    JTextField LivreEmprunterF = new JTextField("", 10);
    JButton emprunteB = new JButton("Emprunté");

    GridBagConstraints gbc = new GridBagConstraints();

    JTable j;
    JTable j1;

    private Database db = new Database();

    public AdminEmprunts(CardLayout cardLayout, JPanel cardPanel, JFrame frame) {
        setSize(1000, 600);
        setLayout(laGrid);

        String[][] data = loadDataEmprunts();
        String[] columnNames = {"Utilisateur", "Livre emprunté","ID","Date d'Emprunt", "Date de retour"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // I put this prevent admin from editing the usernames
            }
        };

        String[][] data1 = loadDataFromLivres();
        String[] columnNames1 = {"Nom des livres","ID", "Disponibles"};
        DefaultTableModel model2 = new DefaultTableModel(data1, columnNames1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // I put this prevent admin from editing the usernames
            }
        };

        j = new JTable(model);
        j.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(j);
        panel1.setLayout(new BorderLayout());
        panel1.add(sp, BorderLayout.CENTER);

        j1 = new JTable(model2);
        j1.setBounds(30, 40, 200, 300);
        JScrollPane sp1 = new JScrollPane(j1);
        panel4.setLayout(new BorderLayout());
        panel4.add(sp1, BorderLayout.CENTER);

        j.getTableHeader().setReorderingAllowed(false);
        j1.getTableHeader().setReorderingAllowed(false);
        j.setRowSelectionAllowed(false);
        j.setColumnSelectionAllowed(false);
        j1.setRowSelectionAllowed(false);
        j1.setColumnSelectionAllowed(false);
        j.setFocusable(false);
        j1.setFocusable(false);
        j.getTableHeader().setResizingAllowed(false);
        j1.getTableHeader().setResizingAllowed(false);

        JPanel topPanel = new JPanel(new GridLayout(1,2));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        // Panel 2
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        panel2.add(utilisateurL, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panel2.add(utilisateurF, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel2.add(retournerLeLivreB, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel2.add(retourB, gbc);

        // panel 3
        panel3.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(10, 10, 10, 10);

        gbc1.gridx = 0; gbc1.gridy = 0;
        panel3.add(utilisateurEmpruntL, gbc1);
        gbc1.gridx = 1; gbc1.gridy = 0;
        panel3.add(utilisateurEmpruntF, gbc1);

        gbc1.gridx = 0; gbc1.gridy = 1;
        panel3.add(LivreEmprunterL, gbc1);
        gbc1.gridx = 1; gbc1.gridy = 1;
        panel3.add(LivreEmprunterF, gbc1);

        gbc1.gridx = 0; gbc1.gridy = 3; gbc1.gridwidth = 2;
        panel3.add(emprunteB, gbc1);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        topPanel.add(panel1);
        topPanel.add(panel4);
        bottomPanel.add(panel2);
        bottomPanel.add(panel3);

        retournerLeLivreB.addActionListener(this);
        emprunteB.addActionListener(this);

        retourB.addActionListener(e -> {frame.setSize(600, 400);frame.setTitle("Librairie-Management");frame.setLocationRelativeTo(null);cardLayout.show(cardPanel, "AdminOptionStack");});

        setVisible(true);

    }

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (command.equals("Retourner le livre")) {
            String utilisateur = utilisateurF.getText().trim();

            if (utilisateur.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir le champ \"Utilisateur:\"",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (db.retournerLivre(utilisateur)) {
                    refreshTable();
                    refreshTable2();
                    utilisateurF.setText("");
                    LivreEmprunterF.setText("");
                    JOptionPane.showMessageDialog(this,
                            "Livre retourné avec succès!",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erreur lors du retour: livre non trouvé ou mauvais utilisateur",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur système",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        else if (command.equals("Emprunté")) {
            String utilisateur = utilisateurEmpruntF.getText().trim();
            String livreId = LivreEmprunterF.getText().trim();

            if (utilisateur.isEmpty() || livreId.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez remplir tous les champs",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (db.emprunterLivre(utilisateur, livreId)) {
                    refreshTable();
                    refreshTable2();
                    utilisateurEmpruntF.setText("");
                    LivreEmprunterF.setText("");
                    JOptionPane.showMessageDialog(this,
                            "Livre emprunté avec succès",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Livre non disponible ou utilisateur a déjà un emprunt ou utilisateur n'existe pas",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur système",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private String[][] loadDataEmprunts() {
        ArrayList<String[]> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("db/emprunts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                String[] empruntData = {rowData[0], rowData[1], rowData[2], rowData[3], rowData[4]};
                dataList.add(empruntData);
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


    private String[][] loadDataFromLivres() {
        ArrayList<String[]> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("db/livres.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                String[] bookData = {rowData[0], rowData[5],rowData[4]};
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

        String[][] data = loadDataEmprunts();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    public void refreshTable2() {
        DefaultTableModel model2 = (DefaultTableModel) j1.getModel();
        model2.setRowCount(0);

        String[][] data1 = loadDataFromLivres();
        for (String[] row : data1) {
            model2.addRow(row);
        }
    }

}
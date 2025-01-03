package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class AdminLivres extends JPanel implements ActionListener {
    JPanel panelLivres = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    // Panel 4
    JLabel livreIDL = new JLabel("Livre ID:");
    JTextField livreIDF = new JTextField("", 10);
    JButton supprimerDefB = new JButton("Supprimer définitivement");

    // Panel 2
    JLabel livreL = new JLabel("Livre ID:");
    JTextField livreF = new JTextField("", 10);
    JLabel livreQuantiterL = new JLabel("Quantité:");
    JTextField livreQuantiterF = new JTextField("", 10);
    JButton retirerB = new JButton("Retirer");
    JButton ajouterQB = new JButton("Ajouter");
    JButton retourB = new JButton("Retour");

    // Panel 3
    JLabel livreCL = new JLabel("Nom du livre:");
    JTextField livreCF = new JTextField("", 10);
    JLabel auteurL = new JLabel("Auteur:");
    JTextField auteurF = new JTextField("", 10);
    JLabel genreL = new JLabel("Genre:");
    JTextField genreF = new JTextField("", 10);
    JLabel quantiteL = new JLabel("Quantité:");
    JTextField quantiteF = new JTextField("", 10);
    JButton ajouterB = new JButton("Ajouter");

    JTable j;

    public AdminLivres(CardLayout cardLayout, JPanel cardPanel, JFrame frame) {
        setLayout(new BorderLayout());

        String[][] data = loadDataFromLivres();
        String[] columnNames = {"Livres", "Auteur", "Genre", "Quantité", "ID"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        j = new JTable(model);
        JScrollPane sp = new JScrollPane(j);
        sp.setPreferredSize(new Dimension(800, 300));
        panelLivres.setLayout(new BorderLayout());
        panelLivres.add(sp, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));

        // Panel 2 setup with border
        panel2.setLayout(new GridBagLayout());
        panel2.setBorder(new LineBorder(Color.BLACK, 1));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel2.add(livreL, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel2.add(livreF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel2.add(livreQuantiterL, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel2.add(livreQuantiterF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel2.add(retirerB, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel2.add(ajouterQB, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel2.add(retourB, gbc);

        // Panel 3 setup with border
        panel3.setLayout(new GridBagLayout());
        panel3.setBorder(new LineBorder(Color.BLACK,1));
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(10, 10, 10, 10);

        gbc1.gridx = 0;
        gbc1.gridy = 0;
        panel3.add(livreCL, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 0;
        panel3.add(livreCF, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 1;
        panel3.add(auteurL, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 1;
        panel3.add(auteurF, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 2;
        panel3.add(genreL, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 2;
        panel3.add(genreF, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 3;
        panel3.add(quantiteL, gbc1);
        gbc1.gridx = 1;
        gbc1.gridy = 3;
        panel3.add(quantiteF, gbc1);

        gbc1.gridx = 0;
        gbc1.gridy = 4;
        gbc1.gridwidth = 2;
        panel3.add(ajouterB, gbc1);

        // Panel 4 setup with border
        panel4.setLayout(new GridBagLayout());
        panel4.setBorder(new LineBorder(Color.BLACK,1));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(10, 10, 10, 10);

        gbc2.gridx = 0;
        gbc2.gridy = 0;
        panel4.add(livreIDL, gbc2);
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        panel4.add(livreIDF, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 1;
        gbc2.gridwidth = 2;
        panel4.add(supprimerDefB, gbc2);

        bottomPanel.add(panel2);
        bottomPanel.add(panel3);
        bottomPanel.add(panel4);

        add(panelLivres, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        ajouterB.addActionListener(this);
        supprimerDefB.addActionListener(this);
        retourB.addActionListener(e -> {
            frame.setSize(600, 400);
            frame.setTitle("Librairie-Management");
            frame.setLocationRelativeTo(null);
            cardLayout.show(cardPanel, "AdminOptionStack");
        });

        setVisible(true);
    }

    private String[][] loadDataFromLivres() {
        ArrayList<String[]> dataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("db/livres.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                dataList.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList.toArray(new String[0][]);
    }

    public void actionPerformed(ActionEvent e) {
    }
}

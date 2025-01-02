package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class AdminLivres extends JPanel implements ActionListener{
    GridLayout grid = new GridLayout(2,1);
    JPanel panelLivres = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();

    //Panel 2
    JLabel livreL = new JLabel("Nom du livre:");
    JTextField livreF = new JTextField("", 10);
    JButton supprimerB = new JButton("Supprimer");
    JButton retourB = new JButton("Retour");

    //Panel 3
    JLabel livreCL = new JLabel("Nom du livre:");
    JTextField livreCF = new JTextField("", 10);

    JLabel auteurL = new JLabel("Auteur:");
    JTextField auteurF = new JTextField("", 10);
    JLabel genreL = new JLabel("Genre:");
    JTextField genreF = new JTextField("", 10);
    JLabel quantiteL = new JLabel("Quantiter:");
    JTextField  quantiteF = new JTextField("", 10);
    JButton ajouterB = new JButton("Ajouter");

    GridBagConstraints gbc = new GridBagConstraints();

    JTable j;


    public AdminLivres(CardLayout cardLayout, JPanel cardPanel, JFrame frame){
        setLayout(grid);

        String[][] data = loadDataFromLivres();
        String[] columnNames = {"Livres","Auteur","Genre","Quantite","ID"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // I put this prevent admin from editing the usernames
            }
        };

        j = new JTable(model);
        j.setBounds(30, 40, 200, 300);
        j.getTableHeader().setReorderingAllowed(false);
        j.setRowSelectionAllowed(false);
        j.setColumnSelectionAllowed(false);
        j.setFocusable(false);
        JScrollPane sp = new JScrollPane(j);
        panelLivres.setLayout(new BorderLayout());
        panelLivres.add(sp, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        //Panel 2
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        panel2.add(livreL, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panel2.add(livreF, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel2.add(supprimerB, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel2.add(retourB, gbc);


        //Panel 3
        panel3.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(10, 10, 10, 10);

        gbc1.gridx = 0; gbc1.gridy = 0;
        panel3.add(livreCL, gbc1);
        gbc1.gridx = 1; gbc1.gridy = 0;
        panel3.add(livreCF, gbc1);

        gbc1.gridx = 0; gbc1.gridy = 1;
        panel3.add(auteurL, gbc1);
        gbc1.gridx = 1; gbc1.gridy = 1;
        panel3.add(auteurF, gbc1);

        gbc1.gridx = 0; gbc1.gridy = 2;
        panel3.add(genreL, gbc1);
        gbc1.gridx = 1; gbc1.gridy = 2;
        panel3.add(genreF, gbc1);

        gbc1.gridx = 0; gbc1.gridy = 3;
        panel3.add(quantiteL, gbc1);
        gbc1.gridx = 1; gbc1.gridy = 3;
        panel3.add(quantiteF, gbc1);

        gbc1.gridx = 0; gbc1.gridy = 4; gbc1.gridwidth = 2;
        panel3.add(ajouterB, gbc1);

        bottomPanel.add(panel2);
        bottomPanel.add(panel3);
        //add panels
        add(panelLivres);
        add(panelLivres, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // actionListener
        supprimerB.addActionListener(this);
        ajouterB.addActionListener(this);

        retourB.addActionListener(e ->{frame.setSize(600,400);frame.setTitle("Librairie-Management");frame.setLocationRelativeTo(null);cardLayout.show(cardPanel, "AdminOptionStack");});
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

        String[][] data = new String[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }
        return data;
    }
    public void actionPerformed( ActionEvent actionEvent ) {

    }

}

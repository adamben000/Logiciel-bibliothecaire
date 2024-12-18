package GUI;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


public class AdminUtilisateur extends JFrame implements ActionListener {
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    GridLayout laGrid = new GridLayout(2,1);

    // Panel 2
    JLabel utilisateur = new JLabel("utilisateur:");
    JTextField utilisateurF = new JTextField("", 10);
    JButton supprimerB = new JButton("Supprimer") ;

    JLabel utilisateurLCreate = new JLabel("utilisateur:");
    JTextField utilisateurFCreate = new JTextField("", 10);

    JLabel motDePasseLCreate = new JLabel("mot de passe:");
    JTextField motDePasseFCreate = new JTextField("", 10);
    JButton ajouterB = new JButton("Ajouter");

    GridBagConstraints gbc = new GridBagConstraints();



    JTable j;
    AdminUtilisateur() {
        setTitle("Liste des Utilisateurs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(laGrid);
        setLocationRelativeTo(null);
        setResizable(false);

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

        JScrollPane sp = new JScrollPane(j);
        panel1.setLayout(new BorderLayout());
        panel1.add(sp, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        // Panel 2
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        panel2.add(utilisateur, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panel2.add(utilisateurF, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel2.add(supprimerB, gbc);

        // panel 3
        panel3.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.insets = new Insets(10, 10, 10, 10);

        gbc1.gridx = 0; gbc1.gridy = 0;
        panel3.add(utilisateurLCreate, gbc1);
        gbc1.gridx = 1; gbc1.gridy = 0;
        panel3.add(utilisateurFCreate, gbc1);

        gbc1.gridx = 0; gbc1.gridy = 1;
        panel3.add(motDePasseLCreate, gbc1);
        gbc1.gridx = 1; gbc1.gridy = 1;
        panel3.add(motDePasseFCreate, gbc1);

        gbc1.gridx = 0; gbc1.gridy = 3; gbc1.gridwidth = 2;
        panel3.add(ajouterB, gbc1);

        add(panel1, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        bottomPanel.add(panel2);
        bottomPanel.add(panel3);

        supprimerB.addActionListener(this);

        setVisible(true);

    }

    public void actionPerformed( ActionEvent actionEvent ) {
        String command = actionEvent.getActionCommand();
    }
    private String[][] loadUsernamesFromFile() {
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

    public static void main(String[] args) {
        AdminUtilisateur JeuDevinettesRun = new AdminUtilisateur();

    }

}

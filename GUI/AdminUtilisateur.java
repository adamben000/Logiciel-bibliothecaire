package GUI;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AdminUtilisateur extends JFrame implements ActionListener {
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    GridLayout laGrid = new GridLayout(2,1);
    JButton button;

    JTable j;
    AdminUtilisateur() {
        // Frame Initialization
        setTitle("Liste des Utilisateurs");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(laGrid);
        setLocationRelativeTo(null);
        setResizable(false);

        // ================= Panel 1: JTable =================
        // Data to be displayed in the JTable
        String[][] data = loadUsernamesFromFile();
        String[] columnNames = {"Utilisateurs"};

        // Initialize JTable
        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        // Wrap JTable in JScrollPane
        JScrollPane sp = new JScrollPane(j);
        panel1.setLayout(new BorderLayout()); // Add JTable to panel1
        panel1.add(sp, BorderLayout.CENTER);

        // ================= Panel 2: Button =================
        button = new JButton("Supprimer");
        button.setPreferredSize(new Dimension(100, 30));
        button.addActionListener(this); // Attach ActionListener
        panel2.add(button); // Add button to panel2

        // ================= Add Panels to Frame =================
        add(panel1); // Add panel1 (JTable) to the frame
        add(panel2); // Add panel2 (Button) to the frame

        // Set frame visibility
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

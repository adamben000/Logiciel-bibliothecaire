package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class AdminLivres extends JFrame implements ActionListener{
    GridLayout grid = new GridLayout(1,2);
    JPanel panelOptions = new JPanel();
    JPanel panelLivres = new JPanel();


    public AdminLivres(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(grid);

        String[] columnNames = {"Livres","Auteur","Genre","Quantite"};
        add(panelLivres);
        add(panelOptions);

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

    public static void main(String[] args){
        AdminLivres run = new AdminLivres();
    }
}

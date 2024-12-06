import java.awt.*;
import javax.swing.*;
public class ConnectionGUI extends JFrame{

	JLabel  Title = new JLabel("Page de connection");
	JLabel  etiquette1 = new JLabel("Username");
	JTextField box1 = new JTextField("", 8);
	JLabel  etiquette2 = new JLabel("Password");
	JPasswordField box2 = new JPasswordField("", 8);
    JButton bouton = new JButton("Login");
    JButton bouton2 = new JButton("Creation de compte");
    

	JPanel  rangee1 = new JPanel();
	JPanel  rangee2 = new JPanel();
	JPanel  rangee3 = new JPanel();
    JPanel  rangee4 = new JPanel();
    JPanel  rangee5 = new JPanel();
    JPanel  rangee6 = new JPanel();
    JPanel  rangee7 = new JPanel();

	GridLayout leGrid = new GridLayout(7,1);

	FlowLayout flowRangee1 = new FlowLayout(FlowLayout.CENTER);
	FlowLayout flowRangee2 = new FlowLayout(FlowLayout.CENTER);
	FlowLayout flowRangee3 = new FlowLayout(FlowLayout.CENTER);
    FlowLayout flowRangee4 = new FlowLayout(FlowLayout.CENTER);
    FlowLayout flowRangee5 = new FlowLayout(FlowLayout.CENTER);
    FlowLayout flowRangee6 = new FlowLayout(FlowLayout.CENTER);
    FlowLayout flowRangee7 = new FlowLayout(FlowLayout.CENTER);

   
   


    public ConnectionGUI () {
        super ("LoginPage");
            setSize (400,400);
            setResizable(false);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            rangee1.setLayout(flowRangee1);
            rangee2.setLayout(flowRangee2);
            rangee3.setLayout(flowRangee3);
            rangee4.setLayout(flowRangee4);
            rangee5.setLayout(flowRangee5);
            rangee6.setLayout(flowRangee6);
            rangee7.setLayout(flowRangee7);

            rangee1.add(Title);
            rangee2.add(etiquette1);
            rangee3.add(box1);
            rangee4.add(etiquette2);
            rangee5.add(box2);
            rangee6.add(bouton);
            rangee7.add(bouton2);

            add(rangee1);
            add(rangee2);
            add(rangee3);
            add(rangee4);
            add(rangee5);
            add(rangee6);
            add(rangee7);

            setLayout(leGrid);
		  	setVisible(true);
       
     }

     public static void main (String[] arguments) {
        ConnectionGUI GUI = new ConnectionGUI();
        }

}


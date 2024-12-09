import java.awt.*;
import javax.swing.*;
public class ConnectionGUI extends JFrame{
//we
	JLabel titre = new JLabel("Page de connexion");
	JLabel  utilisateurTitre = new JLabel("Nom d'utilisateur:");
	JTextField utilisateur = new JTextField("", 10);
	JLabel  motDePasseTitre = new JLabel("Password:");
	JPasswordField motDePasse = new JPasswordField("", 8);
    JButton seConnecterBouton = new JButton("Connexion");
    JButton creationDeCompteBouton = new JButton("Creation de compte");


    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
   
   


    public ConnectionGUI () {
            super("Librairie-Management");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(gridLayout);
            setLocationRelativeTo(null);
            setResizable(false);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);


            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
            titre.setFont(new Font("Arial", Font.BOLD, 24));
            titre.setHorizontalAlignment(SwingConstants.CENTER);
            add(titre, gbc);

            gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
            add(utilisateurTitre, gbc);
            gbc.gridx = 1; gbc.gridy = 1;
            add(utilisateur, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            add(motDePasseTitre, gbc);
            gbc.gridx = 1; gbc.gridy = 2;
            add(motDePasse, gbc);

            gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
            seConnecterBouton.setPreferredSize(new Dimension(200, 40));
            add(seConnecterBouton, gbc);

            gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;
            add(creationDeCompteBouton, gbc);

            seConnecterBouton.setBackground(Color.black);
            seConnecterBouton.setForeground(Color.white);

		  	setVisible(true);
       
     }

     public static void main (String[] arguments) {
        ConnectionGUI GUI = new ConnectionGUI();
        }

}


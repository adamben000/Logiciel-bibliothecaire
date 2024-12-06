import java.awt.EventQueue;
004.
import java.awt.event.WindowAdapter;
005.
import java.awt.event.WindowEvent;
006.
import java.sql.Connection;
007.
import java.sql.DriverManager;
008.
import java.sql.PreparedStatement;
009.
import java.sql.ResultSet;
010.
import java.sql.SQLException;
011.
import javax.swing.JComponent;
012.
import javax.swing.JFrame;
013.
import javax.swing.JLabel;
014.
import javax.swing.JOptionPane;
015.
import javax.swing.JPasswordField;
016.
import javax.swing.JTextField;
017.
import java.awt.Font;
018.
 
019.
public class MyForm extends JFrame {
020.
 
021.
public static String userName;
022.
public static String passWord;
023.
static JLabel lblWelcome;
024.
 
025.
public static void main(String[] args) {
026.
EventQueue.invokeLater(new Runnable() {
027.
public void run() {
028.
MyForm form = new MyForm();
029.
form.setVisible(true);
030.
}
031.
});
032.
}
033.
 
034.
 
035.
public MyForm() {
036.
 
037.
// Create Form Frame
038.
super("ThaiCreate.Com Tutorial");
039.
setSize(679, 385);
040.
setLocation(500, 280);
041.
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
042.
getContentPane().setLayout(null);
043.
 
044.
// Label Welcome
045.
lblWelcome = new JLabel("lblWelcome",JLabel.CENTER);
046.
lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));
047.
lblWelcome.setBounds(168, 153, 336, 25);
048.
getContentPane().add(lblWelcome);
049.
 
050.
// When Frame Loaded
051.
addWindowListener(new WindowAdapter() {
052.
@Override
053.
public void windowOpened(WindowEvent e) {
054.
LoginDialog();
055.
}
056.
});
057.
 
058.
}
059.
 
060.
public static Boolean getLogin() {
061.
 
062.
Connection connect = null;
063.
PreparedStatement pre = null;
064.
Boolean status = false;
065.
 
066.
try {
067.
Class.forName("com.mysql.jdbc.Driver");
068.
 
069.
connect =  DriverManager.getConnection("jdbc:mysql://localhost/mydatabase" +
070.
"?user=root&password=root");   
071.
 
072.
String sql = " SELECT * FROM  member " +
073.
" WHERE Username = ? " +
074.
" AND Password = ? ";
075.
pre = connect.prepareStatement(sql);
076.
pre.setString(1, userName);
077.
pre.setString(2, passWord);
078.
 
079.
ResultSet rec = pre.executeQuery();
080.
if(rec.next()) {
081.
lblWelcome.setText("Welcome : " + rec.getString("Name"));
082.
status = true;
083.
} else {
084.
JOptionPane.showMessageDialog(null, "Incorrect Username/Password");
085.
}
086.
 
087.
} catch (Exception e) {
088.
// TODO Auto-generated catch block
089.
JOptionPane.showMessageDialog(null, e.getMessage());
090.
e.printStackTrace();
091.
}
092.
 
093.
try {
094.
if(pre != null) {
095.
pre.close();
096.
connect.close();
097.
}
098.
} catch (SQLException e) {
099.
// TODO Auto-generated catch block
100.
System.out.println(e.getMessage());
101.
e.printStackTrace();
102.
}
103.
 
104.
return status;
105.
}
106.
 
107.
public static void LoginDialog() {
108.
 
109.
JLabel title = new JLabel("Login Username and Password");
110.
JTextField username = new JTextField();
111.
JPasswordField password = new JPasswordField();
112.
final JComponent[] inputs = new JComponent[] {
113.
title,
114.
new JLabel("Username"),
115.
username,
116.
new JLabel("Password"),
117.
password
118.
};
119.
JOptionPane.showMessageDialog(null, inputs, "Login", JOptionPane.PLAIN_MESSAGE);
120.
 
121.
userName = username.getText();
122.
passWord =  new String(password.getPassword());
123.
 
124.
// Check Login
125.
if(!getLogin())
126.
{
127.
LoginDialog();
128.
}
129.
 
130.
}
131.
}
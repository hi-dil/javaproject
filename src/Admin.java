import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Admin extends JFrame{
    String admin = "haidil";
    String adminpassword = "haidil272";
    JPanel panel, panel1;
    JLabel user_label, password_label, message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit;


    public void login(){
        JFrame frame = new JFrame();

        // User Label
        user_label = new JLabel("User Name :", SwingConstants.RIGHT);
        userName_text = new JTextField();

        // Password

        password_label = new JLabel("Password: ", SwingConstants.RIGHT);
        password_text = new JPasswordField();

        userName_text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    password_text.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        password_text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    submit.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        // Submit

        submit = new JButton("SUBMIT");


        panel = new JPanel(new GridLayout(3, 1));

        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);
        message = new JLabel();
        panel.add(message);
        panel.add(submit);



        // Adding the listeners to components..
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userName_text.getText();
                String password = password_text.getText();
                if ((userName.equals(admin)) && (password.equals(adminpassword))) {
                    message.setText(" Hello " + userName
                            + "");
                    menu();
                    frame.setVisible(false);
                } else {
                    message.setText(" Invalid user.. ");
                }
            }
        });

        frame.add(panel);
        frame.setSize(200, 100);
        frame.setTitle("Please Login Here !");
        //frame.getRootPane().setDefaultButton(submit);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void menu(){
        JFrame frame1 = new JFrame();
        panel1 = new JPanel(new BorderLayout());

        JPanel northpanel = new JPanel();
        JPanel centerpanel = new JPanel(new GridLayout(5,1));

        JLabel welcome = new JLabel("Welcome to the palm specimen record!");
        JButton addrecord = new JButton("Add Record");
        JButton displayrecord = new JButton("Display Record");
        JButton editrecord = new JButton("Edit Record");
        JButton search = new JButton("Search Record");
        JButton generatesummary = new JButton("Generate Summary");

        northpanel.add(welcome);
        centerpanel.add(addrecord);
        centerpanel.add(displayrecord);
        centerpanel.add(editrecord);
        centerpanel.add(search);
        centerpanel.add(generatesummary);

        panel1.add(northpanel, BorderLayout.NORTH);
        panel1.add(centerpanel, BorderLayout.CENTER);

        addrecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addrecord();
                frame1.setVisible(false);
            }
        });

        frame1.add(panel1);
        frame1.pack();
        //frame1.setSize(400, 600);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
    }

    public void addrecord(){
        JFrame frame2 = new JFrame();
        JPanel panel2 = new JPanel(new BorderLayout());

        JPanel northpanel = new JPanel();
        JPanel centerpanel = new JPanel();
        JPanel southpanel = new JPanel();

        JLabel maintext = new JLabel("Please enter your new record here");

        JLabel commonname = new JLabel("Common Name :");
        JLabel genus = new JLabel("Genus :");
        JLabel species = new JLabel("Species :");
        JLabel photo = new JLabel("Photo :");
        JLabel characteristics = new JLabel("Characteristics :");
        JLabel stem = new JLabel("Stem :");
        JLabel leaf = new JLabel("Leaf :");

        JTextField commonname_textfield = new JTextField();
        JTextField genus_textfield = new JTextField();
        JTextField species_textfield = new JTextField();
        JTextArea stem_textarea = new JTextArea();
        JTextArea leaf_textarea = new JTextArea();



        northpanel.add(maintext);

        panel2.add(northpanel, BorderLayout.NORTH);
        panel2.add(centerpanel, BorderLayout.CENTER);
        panel2.add(southpanel, BorderLayout.SOUTH);

        frame2.add(panel2);
        frame2.pack();
        //frame1.setSize(400, 600);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
    }

}

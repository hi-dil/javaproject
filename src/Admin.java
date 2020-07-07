import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
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
        submit.addActionListener(e -> {
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
        GridBagConstraints c = new GridBagConstraints();

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

        addrecord.addActionListener(e -> {
            addrecord();
            frame1.setVisible(false);
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
        //JPanel centerpanel = new JPanel(new GridLayout(5,1, 0, 25));
        JPanel centerpanel = new JPanel(new GridBagLayout());
        JPanel southpanel = new JPanel();
        JPanel se = new JPanel(new GridLayout(3,1));
        GridBagConstraints c = new GridBagConstraints();

        JLabel maintext = new JLabel("Please enter your new record here");

        JLabel commonname = new JLabel("Common Name");
        JLabel genus = new JLabel("Genus");
        JLabel species = new JLabel("Species");
        JLabel photo = new JLabel("Photo");
        JLabel characteristics = new JLabel("Characteristics :");
        JLabel stem = new JLabel("Stem");
        JLabel leaf = new JLabel("Leaf");
        JLabel samplingevent = new JLabel("Found at: ");
        JLabel l = new JLabel("no file selected");

        JCheckBox se1 = new JCheckBox("Sampling event 1");
        JCheckBox se2 = new JCheckBox("Sampling event 2");
        JCheckBox se3 = new JCheckBox("Sampling event 3");

        JTextField commonname_textfield = new JTextField();
        JTextField genus_textfield = new JTextField();
        JTextField species_textfield = new JTextField();
        JTextArea stem_textarea = new JTextArea(6,30);
        JScrollPane scrollPane = new JScrollPane( stem_textarea );
        JTextArea leaf_textarea = new JTextArea(6, 30);
        JScrollPane leafscroll = new JScrollPane(leaf_textarea);

        JButton back = new JButton("Back");
        JButton save = new JButton("Save");
        JButton browse = new JButton("Browse");

        TitledBorder charac = new TitledBorder("Characteristics");

        commonname_textfield.setPreferredSize(new Dimension(300, 20));
        species_textfield.setPreferredSize(new Dimension(300, 20));
        genus_textfield.setPreferredSize(new Dimension(300, 20));

        stem_textarea.setLineWrap(true);
        stem_textarea.setWrapStyleWord(true);

        leaf_textarea.setLineWrap(true);
        leaf_textarea.setWrapStyleWord(true);

        back.addActionListener(e -> {
            menu();
            frame2.setVisible(false);
        });

        browse.addActionListener(e -> {
            // create an object of JFileChooser class
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            // invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);

            // if the user selects a file
            if (r == JFileChooser.APPROVE_OPTION)

            {
                // set the label to the path of the selected file
                l.setText(j.getSelectedFile().getAbsolutePath());
            }
            // if the user cancelled the operation
            else
                l.setText("the user cancelled the operation");
        });

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(20,10,20,0);
        c.anchor = GridBagConstraints.WEST;
        centerpanel.add(commonname, c);

        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(20,30,20,10);
        centerpanel.add(commonname_textfield, c);

        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0,10,20,0);
        centerpanel.add(species, c);

        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0,30,20,10);
        centerpanel.add(species_textfield, c);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0,10,20,0);
        centerpanel.add(genus, c);

        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(0,30,20,10);
        centerpanel.add(genus_textfield, c);

        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(0,10,20,0);
        centerpanel.add(photo, c);

        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(0,30,20,10);
        centerpanel.add(browse, c);

        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(0,30,20,10);
        centerpanel.add(l, c);

        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(21,10,20,0);
        centerpanel.add(characteristics, c);

        c.gridx = 0;
        c.gridy = 6;
        c.insets = new Insets(0,10,20,0);
        centerpanel.add(stem, c);

        c.gridx = 1;
        c.gridy = 6;
        c.insets = new Insets(0,30,20,10);
        centerpanel.add(scrollPane, c);

        c.gridx = 0;
        c.gridy = 7;
        c.insets = new Insets(0,10,20,0);
        centerpanel.add(leaf, c);

        c.gridx = 1;
        c.gridy = 7;
        c.insets = new Insets(0,30,20,10);
        centerpanel.add(leafscroll, c);

        c.gridx = 0;
        c.gridy = 8;
        c.insets = new Insets(20,10,20,0);
        centerpanel.add(samplingevent, c);

        se.add(se1);
        se.add(se2);
        se.add(se3);

        c.gridx = 1;
        c.gridy = 8;
        c.insets = new Insets(20,30,20,10);
        centerpanel.add(se, c);



        southpanel.add(back);
        southpanel.add(save);




        /*
        centerpanel.add(commonname);
        centerpanel.add(commonname_textfield);

        centerpanel.add(genus);
        centerpanel.add(genus_textfield);

        centerpanel.add(species);
        centerpanel.add(species_textfield);

        centerpanel.add(stem);
        centerpanel.add(scrollPane);

        centerpanel.add(leaf);
        centerpanel.add(leaf_textarea);

         */

        northpanel.add(maintext);

        panel2.add(northpanel, BorderLayout.NORTH);
        panel2.add(centerpanel, BorderLayout.CENTER);
        panel2.add(southpanel, BorderLayout.SOUTH);

        frame2.add(panel2);
        frame2.setSize(500, 800);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
    }

}

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Admin extends JFrame{
    String admin = "admin";
    String adminpassword = "admin";





    public void login(){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 383, 251);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel centerpanel = new JPanel();
        frame.getContentPane().add(centerpanel, BorderLayout.CENTER);
        centerpanel.setLayout(null);

        JLabel welcomelable = new JLabel("Welcome! Please login to continue");
        welcomelable.setBounds(84, 10, 261, 48);
        centerpanel.add(welcomelable);

        JLabel usernamelable = new JLabel("Username");
        usernamelable.setBounds(57, 68, 101, 40);
        centerpanel.add(usernamelable);

        JPasswordField passwordField = new JPasswordField();

        JTextField usernametextfield = new JTextField();
        usernametextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    passwordField.requestFocus();
                }
            }
        });
        usernametextfield.setBounds(127, 79, 182, 19);
        centerpanel.add(usernametextfield);
        usernametextfield.setColumns(10);

        JLabel lblNewLabel = new JLabel("Password");
        lblNewLabel.setBounds(57, 121, 66, 13);
        centerpanel.add(lblNewLabel);

        passwordField.setBounds(127, 118, 182, 19);
        centerpanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(224, 169, 85, 21);
        centerpanel.add(loginButton);

        loginButton.addActionListener(e -> {
            String userName = usernametextfield.getText();
            String password = passwordField.getText();
            if ((userName.equals(admin)) && (password.equals(adminpassword))) {
                mainmenu();
                frame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }



    public void mainmenu(){
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(UIManager.getColor("Button.disabledShadow"));
        frame.setBounds(100, 100, 368, 451);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Welcome Back!");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel.setBounds(101, 0, 151, 50);
        frame.getContentPane().add(lblNewLabel);

        JButton addRecord = new JButton("Add Record");
        addRecord.setBackground(UIManager.getColor("Button.foreground"));
        addRecord.setBounds(80, 93, 193, 32);
        frame.getContentPane().add(addRecord);

        addRecord.addActionListener(e -> {
            addrecord();
            frame.setVisible(false);
        });

        JButton displayRecord = new JButton("Display Record");
        displayRecord.setBounds(80, 141, 193, 32);
        frame.getContentPane().add(displayRecord);

        displayRecord.addActionListener(e -> {
            displayrecord();
            frame.setVisible(false);
        });

        JButton deleteRecord = new JButton("Delete Record");
        deleteRecord.setBounds(80, 243, 193, 32);
        frame.getContentPane().add(deleteRecord);

        JButton searchRecord = new JButton("Search Record");
        searchRecord.setBounds(80, 295, 193, 32);
        frame.getContentPane().add(searchRecord);

        searchRecord.addActionListener(e -> {
            searchrecord();
            frame.setVisible(false);
        });

        JLabel lblNewLabel_1 = new JLabel("Please select what you want to do");
        lblNewLabel_1.setBounds(80, 40, 193, 21);
        frame.getContentPane().add(lblNewLabel_1);

        JButton summary = new JButton("Generate Summary");
        summary.setBounds(80, 346, 193, 32);
        frame.getContentPane().add(summary);

        JButton editRecord = new JButton("EditRecord");
        editRecord.setBounds(80, 192, 193, 32);
        frame.getContentPane().add(editRecord);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        editRecord.addActionListener(e -> {
            editrecord();
            frame.setVisible(false);
        });
    }



    void addrecord(){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 345, 678);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Please enter the details to add record");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(welcome, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 32, 314, 152);
        panel_1.setBorder(new TitledBorder(null, "Specimen Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.add(panel_1);
        panel_1.setLayout(null);

        JLabel commonnamelable = new JLabel("Common Name");
        commonnamelable.setBounds(6, 15, 146, 21);
        panel_1.add(commonnamelable);

        JTextField gtextfield = new JTextField();

        JTextField commonnametextfield = new JTextField();
        commonnametextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    gtextfield.requestFocus();
                }
            }
        });
        commonnametextfield.setBounds(109, 15, 199, 21);
        panel_1.add(commonnametextfield);
        commonnametextfield.setColumns(10);

        JLabel glable = new JLabel("Genus");
        glable.setBounds(6, 41, 146, 21);
        panel_1.add(glable);

        JTextField stextfield = new JTextField();

        gtextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    stextfield.requestFocus();
                }
            }
        });
        gtextfield.setBounds(109, 41, 199, 21);
        panel_1.add(gtextfield);
        gtextfield.setColumns(10);

        JLabel slable = new JLabel("species");
        slable.setBounds(6, 67, 146, 26);
        panel_1.add(slable);

        JLabel Photo = 	new JLabel("Photo");
        Photo.setBounds(6, 107, 45, 13);
        panel_1.add(Photo);

        JLabel l = new JLabel("no file selected");
        l.setBounds(107, 129, 106, 13);

        JButton browse = new JButton("browse");
        browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        browse.setBounds(109, 103, 85, 21);
        panel_1.add(browse);

        panel_1.add(l);

        stextfield.setBounds(109, 71, 199, 22);
        panel_1.add(stextfield);
        stextfield.setColumns(10);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "Characteristics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(10, 194, 314, 265);
        panel.add(panel_2);
        panel_2.setLayout(null);

        JLabel stemlable = new JLabel("Stem");
        stemlable.setBounds(10, 62, 41, 13);
        panel_2.add(stemlable);

        JTextArea leafta = new JTextArea();

        JTextArea stemtextarea = new JTextArea();
        stemtextarea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    leafta.requestFocus();
                }
            }
        });
        stemtextarea.setBounds(161, 41, 143, 97);
        panel_2.add(stemtextarea);

        JScrollPane stemscroll = new JScrollPane(stemtextarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        stemscroll.setBounds(110, 30, 194, 97);
        panel_2.add(stemscroll);

        JLabel leaflable = new JLabel("Leaf");
        leaflable.setBounds(10, 186, 45, 13);
        panel_2.add(leaflable);


        leafta.setBounds(110, 147, 194, 97);
        panel_2.add(leafta);

        JScrollPane leafscroll = new JScrollPane(leafta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leafscroll.setBounds(110, 147, 194, 97);
        panel_2.add(leafscroll);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "Sampling Event", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_3.setBounds(10, 469, 311, 97);
        panel.add(panel_3);
        panel_3.setLayout(new GridLayout(0, 1, 0, 5));

        JLabel lblNewLabel = new JLabel("Please select at where this specimen was found");
        panel_3.add(lblNewLabel);

        JCheckBox chckbxNewCheckBox = new JCheckBox("Sampling Event 1");
        panel_3.add(chckbxNewCheckBox);

        JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Sampling Event 2");
        panel_3.add(chckbxNewCheckBox_1);

        JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Sampling Event 3");
        panel_3.add(chckbxNewCheckBox_2);

        JButton save = new JButton("Save");
        save.setBounds(236, 597, 85, 21);
        panel.add(save);

        save.addActionListener(e -> {
            String commonnameaction = commonnametextfield.getText();
            String genusaction = gtextfield.getText();
            String speciesaction = stextfield.getText();
            String photolocation = l.getText();
            String stemaction = stemtextarea.getText();
            String leafaction = leafta.getText();

            specimenData(commonnameaction, genusaction, speciesaction, photolocation, stemaction, leafaction);

        });

        JButton back = new JButton("Back");
        back.setBounds(141, 597, 85, 21);
        panel.add(back);

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }



    public void displayrecord(){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 1218, 692);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Record");
        lblNewLabel.setBounds(0, 10, 1204, 30);
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 52, 1184, 540);
        frame.getContentPane().add(scrollPane);

        JTable table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                },
                new String[] {
                        "Common Name", "Genus", "Species", "Stem", "Leaf", "Time", "Date", "Location"
                }
        ) {
            Class[] columnTypes = new Class[] {
                    String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
            };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });

        JButton back = new JButton("back");
        back.setBounds(571, 613, 98, 21);
        frame.getContentPane().add(back);
        table.getColumnModel().getColumn(0).setPreferredWidth(90);

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void editrecord(){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 983, 643);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Please enter the details to add record");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(welcome, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(645, 10, 314, 152);
        panel_1.setBorder(new TitledBorder(null, "Specimen Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.add(panel_1);
        panel_1.setLayout(null);

        JLabel commonnamelable = new JLabel("Common Name");
        commonnamelable.setBounds(6, 15, 146, 21);
        panel_1.add(commonnamelable);

        JTextField stextfield = new JTextField();

        JTextField gtextfield = new JTextField();
        gtextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    stextfield.requestFocus();
                }
            }});

        JTextField commonnametextfield = new JTextField();
        commonnametextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    gtextfield.requestFocus();
                }
            }
        });
        commonnametextfield.setBounds(109, 15, 199, 21);
        panel_1.add(commonnametextfield);
        commonnametextfield.setColumns(10);

        JLabel glable = new JLabel("Genus");
        glable.setBounds(6, 41, 146, 21);
        panel_1.add(glable);

        gtextfield.setBounds(109, 41, 199, 21);
        panel_1.add(gtextfield);
        gtextfield.setColumns(10);

        JLabel slable = new JLabel("species");
        slable.setBounds(6, 67, 146, 26);
        panel_1.add(slable);

        JLabel Photo = 	new JLabel("Photo");
        Photo.setBounds(6, 107, 45, 13);
        panel_1.add(Photo);

        JLabel l = new JLabel("no file selected");
        l.setBounds(107, 129, 106, 13);

        JButton btnNewButton = new JButton("browse");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        btnNewButton.setBounds(109, 103, 85, 21);
        panel_1.add(btnNewButton);

        panel_1.add(l);

        stextfield.setBounds(109, 71, 199, 19);
        panel_1.add(stextfield);
        stextfield.setColumns(10);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "Characteristics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(645, 172, 314, 265);
        panel.add(panel_2);
        panel_2.setLayout(null);

        JLabel stemlable = new JLabel("Stem");
        stemlable.setBounds(10, 62, 41, 13);
        panel_2.add(stemlable);

        JTextArea leafta = new JTextArea();

        JTextArea textArea = new JTextArea();
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    leafta.requestFocus();
                }}
        });
        textArea.setBounds(161, 41, 143, 97);
        panel_2.add(textArea);

        JScrollPane stemscroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        stemscroll.setBounds(110, 30, 194, 97);
        panel_2.add(stemscroll);

        JLabel leaflable = new JLabel("Leaf");
        leaflable.setBounds(10, 186, 45, 13);
        panel_2.add(leaflable);


        leafta.setBounds(110, 147, 194, 97);
        panel_2.add(leafta);

        JScrollPane leafscroll = new JScrollPane(leafta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leafscroll.setBounds(110, 147, 194, 97);
        panel_2.add(leafscroll);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "Sampling Event", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_3.setBounds(645, 447, 311, 97);
        panel.add(panel_3);
        panel_3.setLayout(new GridLayout(0, 1, 0, 5));

        JLabel lblNewLabel = new JLabel("Please select at where this specimen was found");
        panel_3.add(lblNewLabel);

        JCheckBox chckbxNewCheckBox = new JCheckBox("Sampling Event 1");
        panel_3.add(chckbxNewCheckBox);

        JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Sampling Event 2");
        panel_3.add(chckbxNewCheckBox_1);

        JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Sampling Event 3");
        panel_3.add(chckbxNewCheckBox_2);

        JButton save = new JButton("Save");
        save.setBounds(874, 554, 85, 21);
        panel.add(save);

        JButton back = new JButton("Back");
        back.setBounds(779, 554, 85, 21);
        panel.add(back);

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });

        JPanel panel_4 = new JPanel();
        panel_4.setBorder(new TitledBorder(null, "Records", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_4.setBounds(10, 10, 625, 534);
        panel.add(panel_4);
        panel_4.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 30, 605, 494);
        panel_4.add(scrollPane);

        JTable table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {
                        {null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null, null},
                },
                new String[] {
                        "RecordId", "Common Name", "Genus", "Species", "Stem", "Leaf", "Time", "Date", "Location"
                }
        ));
        scrollPane.setViewportView(table);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }




    public void searchrecord(){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 638, 416);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblNewLabel = new JLabel("Please enter what you want to search");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 19));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JTextField textField = new JTextField();
        textField.setBounds(68, 24, 379, 19);
        panel.add(textField);
        textField.setColumns(10);

        JButton search = new JButton("Search");
        search.setBounds(457, 23, 85, 21);
        panel.add(search);

        JButton back = new JButton("back");
        back.setBounds(250, 313, 85, 21);
        panel.add(back);

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void specimenData(String specimen, String genus, String species, String photo, String stem, String leaf){
        Specimen sp1 = new Specimen(specimen, genus, species, photo, stem, leaf);
    }
}


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;

public class Admin extends JFrame{
    String admin = "admin";
    String adminpassword = "admin";
    JTable table;
    JComboBox comboBox = new JComboBox();
    final String[] recordvalue = new String[6];
    JLabel imagelable, imagetable;
    byte[] specimenimage = null;



    public ArrayList<Specimen> specimenList() {
        ArrayList<Specimen> specimenList = new ArrayList<>();

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
            Statement statement = myConn.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from specimen");

            Specimen specimen;
            while(resultSet.next()){
                specimen = new Specimen(resultSet.getInt("specimenId"), resultSet.getString("Commonname"),
                        resultSet.getString("Genus"), resultSet.getString("Species"), resultSet.getBytes("Photo"),
                        resultSet.getString("Stem"), resultSet.getString("Leaf"));

                specimenList.add(specimen);
            }

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }

        return specimenList;
    }


    public void show_specimen() {
        ArrayList<Specimen> list = specimenList();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object[] row = new Object[7];

        for (Specimen specimen : list) {
            row[0] = specimen.getSpecimenId();
            row[1] = specimen.getCommonName();
            row[2] = specimen.getGenus();
            row[3] = specimen.getSpecies();
            row[4] = specimen.getStem();
            row[5] = specimen.getLeaf();

            model.addRow(row);
        }
    }


    public ImageIcon ResizeImage(String path){
        ImageIcon image = new ImageIcon(path);
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(imagelable.getWidth(), imagelable.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }



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

        deleteRecord.addActionListener(e -> {
            deleteRecord();
            frame.setVisible(false);
        });

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
        frame.setBounds(100, 100, 345, 775);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Please enter the details to add record");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(welcome, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 10, 314, 294);
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
        Photo.setBounds(6, 103, 45, 13);
        panel_1.add(Photo);

        JLabel l = new JLabel("no file selected");
        l.setBounds(109, 134, 195, 13);

        imagelable = new JLabel();

        JButton browse = new JButton("browse");
        browse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // create an object of JFileChooser class
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
                j.addChoosableFileFilter(filter);

                // invoke the showsOpenDialog function to show the save dialog
                int r = j.showOpenDialog(null);

                // if the user selects a file
                if (r == JFileChooser.APPROVE_OPTION)

                {
                    File selectedFile = j.getSelectedFile();
                    // set the label to the path of the selected file
                    l.setText(j.getSelectedFile().getAbsolutePath());
                    String path = selectedFile.getAbsolutePath();
                    imagelable.setIcon(ResizeImage(path));

                    try {
                        File image = new File(path);
                        FileInputStream fis = new FileInputStream(image);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        for(int readNum; (readNum=fis.read(buf)) != -1;) bos.write(buf, 0, readNum);

                        specimenimage = bos.toByteArray();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
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

        imagelable.setBounds(109, 152, 124, 130);
        panel_1.add(imagelable);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "Characteristics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(10, 312, 314, 265);
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
        stemtextarea.setLineWrap(true);
        stemtextarea.setBounds(161, 41, 143, 97);
        panel_2.add(stemtextarea);

        JScrollPane stemscroll = new JScrollPane(stemtextarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        stemscroll.setBounds(110, 30, 194, 97);
        panel_2.add(stemscroll);

        JLabel leaflable = new JLabel("Leaf");
        leaflable.setBounds(10, 186, 45, 13);
        panel_2.add(leaflable);

        leafta.setLineWrap(true);
        leafta.setBounds(110, 147, 194, 97);
        panel_2.add(leafta);

        JScrollPane leafscroll = new JScrollPane(leafta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leafscroll.setBounds(110, 147, 194, 97);
        panel_2.add(leafscroll);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "Sampling Event", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_3.setBounds(13, 587, 311, 97);
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
        save.setBounds(231, 694, 85, 21);
        panel.add(save);

        save.addActionListener(e -> {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this record?", "Add Record",
                    JOptionPane.OK_CANCEL_OPTION);

            if (input == 0){
                String commonnameaction = commonnametextfield.getText();
                String genusaction = gtextfield.getText();
                String speciesaction = stextfield.getText();
                String photolocation = l.getText();
                String stemaction = stemtextarea.getText();
                String leafaction = leafta.getText();

                Specimen specimen = new Specimen();
                specimen.addrecord(commonnameaction, genusaction, speciesaction, photolocation, stemaction, leafaction);

                JOptionPane.showMessageDialog(null, "Record added!");
            }


        });

        JButton back = new JButton("Back");
        back.setBounds(136, 694, 85, 21);
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
        frame.setBounds(100, 100, 1218, 451);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Record");
        lblNewLabel.setBounds(0, 10, 1204, 30);
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Records", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_1.setBounds(10, 51, 815, 317);
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 15, 795, 292);
        panel_1.add(scrollPane_1);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();

                byte[] img = (specimenList().get(i).getPhoto());
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(imagetable.getWidth(), imagetable.getHeight(), Image.SCALE_SMOOTH));
                imagetable.setIcon(imageIcon);
            }
        });
        scrollPane_1.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Specimen Id", "Common Name", "Genus", "Species", "Stem", "Leaf"
                }
        ) {
            Class[] columnTypes = new Class[] {
                    String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
            };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        table.setDefaultRenderer(String.class, centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(90);


        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        JButton back = new JButton("back");
        back.setBounds(554, 378, 98, 21);
        frame.getContentPane().add(back);

        JPanel imagepanel = new JPanel();
        imagepanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Pictures", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        imagepanel.setBounds(835, 50, 359, 318);
        frame.getContentPane().add(imagepanel);
        imagepanel.setLayout(null);

        imagetable = new JLabel("Pictures");
        imagetable.setBounds(10, 23, 339, 285);
        imagepanel.add(imagetable);

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });

        show_specimen();

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void editrecord(){

        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 348, 689);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Please enter the details to add record");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(welcome, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 52, 314, 152);
        panel_1.setBorder(new TitledBorder(null, "Specimen Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.add(panel_1);
        panel_1.setLayout(null);

        JLabel commonnamelable = new JLabel("Common Name");
        commonnamelable.setBounds(6, 15, 146, 21);
        panel_1.add(commonnamelable);

        JTextField stextfield = new JTextField(recordvalue[2]);

        JTextField gtextfield = new JTextField(recordvalue[1]);
        gtextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    stextfield.requestFocus();
                }
            }});

        JTextField commonnametextfield = new JTextField(recordvalue[0]);
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

        JLabel l = new JLabel(recordvalue[3]);
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
        panel_2.setBounds(10, 214, 314, 265);
        panel.add(panel_2);
        panel_2.setLayout(null);

        JLabel stemlable = new JLabel("Stem");
        stemlable.setBounds(10, 62, 41, 13);
        panel_2.add(stemlable);

        JTextArea leafta = new JTextArea(recordvalue[5]);

        JTextArea stemta = new JTextArea(recordvalue[4]);
        stemta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    leafta.requestFocus();
                }}
        });
        stemta.setLineWrap(true);
        stemta.setBounds(161, 41, 143, 97);
        panel_2.add(stemta);

        JScrollPane stemscroll = new JScrollPane(stemta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        stemscroll.setBounds(110, 30, 194, 97);
        panel_2.add(stemscroll);

        JLabel leaflable = new JLabel("Leaf");
        leaflable.setBounds(10, 186, 45, 13);
        panel_2.add(leaflable);

        leafta.setLineWrap(true);
        leafta.setBounds(110, 147, 194, 97);
        panel_2.add(leafta);

        JScrollPane leafscroll = new JScrollPane(leafta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leafscroll.setBounds(110, 147, 194, 97);
        panel_2.add(leafscroll);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "Sampling Event", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_3.setBounds(10, 489, 311, 97);
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

        JButton update = new JButton("Update");
        update.setBounds(229, 596, 85, 21);
        panel.add(update);

        update.addActionListener(e -> {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this record?", "Update Record",
                    JOptionPane.OK_CANCEL_OPTION);

            if (input==0){
                int value = (int) comboBox.getSelectedItem();
                String commonnameaction = commonnametextfield.getText();
                String genusaction = gtextfield.getText();
                String speciesaction = stextfield.getText();
                String photolocation = l.getText();
                String stemaction = stemta.getText();
                String leafaction = leafta.getText();

                Specimen specimen = new Specimen();
                specimen.updateRecord(value, commonnameaction, genusaction, speciesaction, photolocation, stemaction, leafaction);

                JOptionPane.showMessageDialog(null, "The record has been updated!");
            }
            else {
                editrecord();
                frame.setVisible(false);
            }

        });

        JButton back = new JButton("Back");
        back.setBounds(134, 596, 85, 21);
        panel.add(back);

        comboBox = new JComboBox();
        comboBox.setBounds(21, 21, 198, 21);
        panel.add(comboBox);
        updateCombo();

        JButton apply = new JButton("Apply");
        apply.setBounds(229, 21, 85, 21);
        panel.add(apply);

        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = (int) comboBox.getSelectedItem();

                try {
                    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
                    PreparedStatement stmt = myConn.prepareStatement("select commonname, genus, species, photo, stem, leaf from specimen " +
                            "where specimenid = ?");
                    stmt.setInt(1, value);

                    ResultSet resultSet = stmt.executeQuery();

                    while(resultSet.next()){
                        recordvalue[0] = resultSet.getString("commonname");
                        recordvalue[1] = resultSet.getString("genus");
                        recordvalue[2] = resultSet.getString("species");
                        recordvalue[3] = resultSet.getString("photo");
                        recordvalue[4] = resultSet.getString("stem");
                        recordvalue[5] = resultSet.getString("leaf");
                    }
                    myConn.close();
                    editrecord();
                    frame.setVisible(false);

                }
                catch (Exception j){
                    JOptionPane.showMessageDialog(null, j);
                }
            }
        });

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });


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



    public void updateCombo(){
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
            PreparedStatement pst = myConn.prepareStatement("select * from specimen");

            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next())
                comboBox.addItem(resultSet.getInt("specimenid"));

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }



    public void deleteRecord() {
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 1079, 606);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        comboBox = new JComboBox();
        comboBox.setBounds(31, 107, 255, 25);
        frame.getContentPane().add(comboBox);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(342, 10, 713, 549);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Specimen Id", "Common Name", "Genus", "Species", "Stem", "Leaf"
                }
        ) {
            Class[] columnTypes = new Class[] {
                    String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
            };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });

        show_specimen();
        updateCombo();

        JLabel lblNewLabel = new JLabel("SpecimenId");
        lblNewLabel.setBounds(31, 84, 92, 13);
        frame.getContentPane().add(lblNewLabel);

        JButton delete = new JButton("Delete");
        delete.setBounds(201, 538, 85, 21);
        frame.getContentPane().add(delete);

        delete.addActionListener(e -> {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Delete Record",
                    JOptionPane.OK_CANCEL_OPTION);

            if (input == 0){
                int value = (int) comboBox.getSelectedItem();
                Specimen specimen = new Specimen();

                specimen.deleteRecord(value);

                table = new JTable();
                scrollPane.setViewportView(table);

                table.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                                "Specimen Id", "Common Name", "Genus", "Species", "Photo", "Stem", "Leaf"
                        }
                ) {
                    Class[] columnTypes = new Class[] {
                            String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
                    };
                    public Class getColumnClass(int columnIndex) {
                        return columnTypes[columnIndex];
                    }
                });

                JOptionPane.showMessageDialog(null, "The record has been deleted!");

                show_specimen();
                deleteRecord();
                frame.setVisible(false);
            }
            else{
                deleteRecord();
                frame.setVisible(false);
            }

        });

        JButton back = new JButton("Back");
        back.setBounds(99, 538, 85, 21);
        frame.getContentPane().add(back);

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });

        JLabel lblNewLabel_1 = new JLabel("Please select specimenId to delete the said record");
        lblNewLabel_1.setBounds(31, 10, 335, 41);
        frame.getContentPane().add(lblNewLabel_1);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}


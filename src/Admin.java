import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
    final String[] recordvalue = new String[5];
    JLabel imagelable, imagetable, imageedit, imagesummary;
    byte[] specimenimage = null;
    ImageIcon editimage;
    int valuecombobox = 0;
    JTable tablesampling;
    String combosummary = "no filter";

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

        summary.addActionListener(e -> {
            generateSummary();
            frame.setVisible(false);
        });

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
        panel_3.setBounds(10, 587, 314, 97);
        panel.add(panel_3);
        panel_3.setLayout(null);

        JLabel lblNewLabel = new JLabel("Please select at where this specimen was found");
        lblNewLabel.setBounds(6, 15, 295, 15);
        panel_3.add(lblNewLabel);

        JCheckBox samplingevent1 = new JCheckBox("Sampling Event 1");
        samplingevent1.setBounds(6, 35, 149, 15);
        panel_3.add(samplingevent1);

        JCheckBox samplingevent2 = new JCheckBox("Sampling Event 2");
        samplingevent2.setBounds(6, 55, 149, 15);
        panel_3.add(samplingevent2);

        JCheckBox samplingevent3 = new JCheckBox("Sampling Event 3");
        samplingevent3.setBounds(6, 75, 137, 15);
        panel_3.add(samplingevent3);

        JCheckBox samplingevent4 = new JCheckBox("Sampling Event 4");
        samplingevent4.setBounds(157, 32, 127, 21);
        panel_3.add(samplingevent4);

        JCheckBox samplingevent5 = new JCheckBox("Sampling Event 5");
        samplingevent5.setBounds(157, 52, 136, 21);
        panel_3.add(samplingevent5);

        JCheckBox samplingevent6 = new JCheckBox("Sampling Event 6");
        samplingevent6.setBounds(157, 72, 136, 21);
        panel_3.add(samplingevent6);

        JButton save = new JButton("Save");
        save.setBounds(239, 694, 85, 21);
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

                int id = specimen.getSpecimenId();
                Connection myConn = null;
                try {
                    myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");


                    PreparedStatement stmt = myConn.prepareStatement("insert into specimentake(specimenid, specimeneventid)" +
                            "values(?, ?)");

                    if(samplingevent1.isSelected()){
                        stmt.setInt(1, id);
                        stmt.setInt(2, 1);
                        stmt.executeUpdate();
                    }

                    if(samplingevent2.isSelected()){
                        stmt.setInt(1, id);
                        stmt.setInt(2, 2);
                        stmt.executeUpdate();
                    }

                    if(samplingevent3.isSelected()){
                        stmt.setInt(1, id);
                        stmt.setInt(2, 3);
                        stmt.executeUpdate();
                    }

                    if(samplingevent4.isSelected()){
                        stmt.setInt(1, id);
                        stmt.setInt(2, 4);
                        stmt.executeUpdate();
                    }

                    if(samplingevent5.isSelected()){
                        stmt.setInt(1, id);
                        stmt.setInt(2, 5);
                        stmt.executeUpdate();
                    }

                    if(samplingevent6.isSelected()){
                        stmt.setInt(1, id);
                        stmt.setInt(2, 6);
                        stmt.executeUpdate();
                    }

                    myConn.close();



                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex);
                }

                JOptionPane.showMessageDialog(null, "Record added!");
            }

            addrecord();
            frame.setVisible(false);
        });

        JButton back = new JButton("Back");
        back.setBounds(10, 694, 85, 21);
        panel.add(back);

        JButton help = new JButton("help");
        help.setBounds(169, 694, 60, 21);

        help.addActionListener(e -> {
            String message =
                    "Sapling event:             location;                                           date;           time\n" +
                    "Sampling event 1:          Kubah National Park;                  2020-07-01;     17:08:04\n" +
                    "Sampling event 2:          Bako National;                          2020-07-03;     09:08:04\n" +
                    "Sampling event 1:          Mount Santubong;                    2020-07-01;     07:09:13\n" +
                    "Sampling event 1:          Maludam National Park;            2020-06-18;     08:43:29\n" +
                    "Sampling event 1:          Kubah National Park;                2020-06-10;     10:43:29\n" +
                    "Sampling event 1:          Maludam National Park;            2020-07-07;     13:45:40\n";
            JOptionPane.showMessageDialog(null, message);
        });

        panel.add(help);

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
        frame.setBounds(100, 100, 348, 771);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Please enter the details to add record");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(welcome, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 52, 314, 246);
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

        JLabel l = new JLabel();
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

        imageedit = new JLabel("");
        imageedit.setIcon(editimage);
        imageedit.setBounds(109, 129, 117, 107);
        panel_1.add(imageedit);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "Characteristics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(10, 308, 314, 265);
        panel.add(panel_2);
        panel_2.setLayout(null);

        JLabel stemlable = new JLabel("Stem");
        stemlable.setBounds(10, 62, 41, 13);
        panel_2.add(stemlable);

        JTextArea leafta = new JTextArea(recordvalue[4]);

        JTextArea stemta = new JTextArea(recordvalue[3]);
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
        panel_3.setBounds(10, 583, 314, 97);
        panel.add(panel_3);
        panel_3.setLayout(null);

        JLabel lblNewLabel = new JLabel("Please select at where this specimen was found");
        lblNewLabel.setBounds(6, 15, 299, 15);
        panel_3.add(lblNewLabel);

        JCheckBox samplingevent1 = new JCheckBox("Sampling Event 1");
        samplingevent1.setBounds(6, 35, 146, 15);
        panel_3.add(samplingevent1);

        JCheckBox samplingevent2 = new JCheckBox("Sampling Event 2");
        samplingevent2.setBounds(6, 55, 146, 15);
        panel_3.add(samplingevent2);

        JCheckBox samplingevent3 = new JCheckBox("Sampling Event 3");
        samplingevent3.setBounds(6, 75, 146, 15);
        panel_3.add(samplingevent3);

        JCheckBox samplingevent4 = new JCheckBox("Sampling Event 4");
        samplingevent4.setBounds(163, 32, 142, 21);
        panel_3.add(samplingevent4);

        JCheckBox samplingevent6 = new JCheckBox("Sampling Event 6");
        samplingevent6.setBounds(163, 72, 142, 21);
        panel_3.add(samplingevent6);

        JCheckBox samplingevent5 = new JCheckBox("Sampling Event 5");
        samplingevent5.setBounds(163, 52, 142, 21);
        panel_3.add(samplingevent5);

        JButton update = new JButton("Update");
        update.setBounds(239, 690, 85, 21);
        panel.add(update);

        update.addActionListener(e -> {
            int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this record?", "Update Record",
                    JOptionPane.OK_CANCEL_OPTION);

            if (input==0){
                int value = (int) comboBox.getSelectedItem();

                if (!samplingevent1.isSelected() && !samplingevent2.isSelected() && !samplingevent3.isSelected() &&
                !samplingevent1.isSelected() && !samplingevent2.isSelected() && !samplingevent3.isSelected()){
                    System.out.println("nice one");
                }
                else{
                    try{
                        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
                        PreparedStatement stmt = myConn.prepareStatement("delete from specimentake where specimenid = ?");

                        stmt.setInt(1, value);
                        stmt.executeUpdate();
                        myConn.close();
                    }
                    catch (Exception ex){
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }

                String commonnameaction = commonnametextfield.getText();
                String genusaction = gtextfield.getText();
                String speciesaction = stextfield.getText();
                String photolocation = l.getText();
                String stemaction = stemta.getText();
                String leafaction = leafta.getText();

                Specimen specimen = new Specimen();
                specimen.updateRecord(value, commonnameaction, genusaction, speciesaction, photolocation, stemaction, leafaction);

                JOptionPane.showMessageDialog(null, "The record has been updated!");

                try{
                    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
                    PreparedStatement stmt = myConn.prepareStatement("insert into specimentake(specimenid, specimeneventid)" +
                            "values(?, ?)");

                    if(samplingevent1.isSelected()){
                        stmt.setInt(1, value);
                        stmt.setInt(2, 1);
                        stmt.executeUpdate();
                    }

                    if(samplingevent2.isSelected()){
                        stmt.setInt(1, value);
                        stmt.setInt(2, 2);
                        stmt.executeUpdate();
                    }

                    if(samplingevent3.isSelected()){
                        stmt.setInt(1, value);
                        stmt.setInt(2, 3);
                        stmt.executeUpdate();
                    }

                    if(samplingevent4.isSelected()){
                        stmt.setInt(1, value);
                        stmt.setInt(2, 4);
                        stmt.executeUpdate();
                    }

                    if(samplingevent5.isSelected()){
                        stmt.setInt(1, value);
                        stmt.setInt(2, 5);
                        stmt.executeUpdate();
                    }

                    if(samplingevent6.isSelected()){
                        stmt.setInt(1, value);
                        stmt.setInt(2, 6);
                        stmt.executeUpdate();
                    }

                    myConn.close();
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(null, ex);
                }

            }
            else {
                editrecord();
                frame.setVisible(false);
            }

        });

        JButton back = new JButton("Back");
        back.setBounds(10, 690, 85, 21);
        panel.add(back);

        comboBox = new JComboBox();
        //comboBox.setSelectedIndex(valuecombobox);
        comboBox.setBounds(21, 21, 198, 21);
        panel.add(comboBox);
        updateCombo();

        JButton apply = new JButton("Apply");
        apply.setBounds(229, 21, 85, 21);
        panel.add(apply);

        JButton btnNewButton_1 = new JButton("help");
        btnNewButton_1.setBounds(166, 690, 63, 21);

        btnNewButton_1.addActionListener(e -> {
            String message =
                    "Sapling event:             location;                                           date;           time\n" +
                            "Sampling event 1:          Kubah National Park;                  2020-07-01;     17:08:04\n" +
                            "Sampling event 2:          Bako National;                          2020-07-03;     09:08:04\n" +
                            "Sampling event 1:          Mount Santubong;                    2020-07-01;     07:09:13\n" +
                            "Sampling event 1:          Maludam National Park;            2020-06-18;     08:43:29\n" +
                            "Sampling event 1:          Kubah National Park;                2020-06-10;     10:43:29\n" +
                            "Sampling event 1:          Maludam National Park;            2020-07-07;     13:45:40\n";
            JOptionPane.showMessageDialog(null, message);
        });
        panel.add(btnNewButton_1);

        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = (int) comboBox.getSelectedItem();
                valuecombobox = comboBox.getSelectedIndex();


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
                        recordvalue[3] = resultSet.getString("stem");
                        recordvalue[4] = resultSet.getString("leaf");

                        byte[] img = resultSet.getBytes("photo");
                        ImageIcon image = new ImageIcon(img);
                        Image im = image.getImage();
                        Image myImg = im.getScaledInstance(imageedit.getWidth(), imageedit.getHeight(), Image.SCALE_SMOOTH);
                        editimage = new ImageIcon(myImg);
                        imageedit.setIcon(editimage);

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


    //TODO: add functionalities to search record
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

            comboBox.setSelectedIndex(valuecombobox);
        }catch (SQLException sq){
            JOptionPane.showMessageDialog(null, sq);
        } catch (IllegalArgumentException iae){
            System.out.println("nani");
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

                tablesampling = new JTable();
                scrollPane.setViewportView(tablesampling);

                tablesampling.setModel(new DefaultTableModel(
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


    public ArrayList<SpecimenSamplingList> specimenSamplingList() {
        ArrayList<SpecimenSamplingList> specimensamplinglist = new ArrayList<>();

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet resultSet = null;

            if(combosummary.equals("Kubah National Park")){
                resultSet = statement.executeQuery("select * " +
                        "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                        "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid) " +
                        "where specimenevent.location = 'Kubah National Park'");
            }
            else if(combosummary.equals("Bako National Park")){
                resultSet = statement.executeQuery("select * " +
                        "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                        "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid) " +
                        "where specimenevent.location = 'Bako National Park'");
            }
            else if(combosummary.equals("Maludam National Park")) {
                resultSet = statement.executeQuery("select * " +
                        "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                        "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid) " +
                        "where specimenevent.location = 'Maludam '");
            }
            else if(combosummary.equals("Mount Santubong")) {
                resultSet = statement.executeQuery("select * " +
                        "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                        "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid) " +
                        "where specimenevent.location = 'Mount Santubong'");
            }
            else{
                resultSet = statement.executeQuery("select * " +
                        "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                        "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid)");
            }



            SpecimenSamplingList specimenSampling;
            while(resultSet.next()){
                specimenSampling = new SpecimenSamplingList(resultSet.getString("location"), resultSet.getString("date"),
                        resultSet.getString("time"), resultSet.getString("commonname"), resultSet.getString("genus"),
                        resultSet.getString("species"), resultSet.getString("stem"), resultSet.getString("leaf"),
                        resultSet.getInt("specimentakeid"), resultSet.getBytes("photo"));

                specimensamplinglist.add(specimenSampling);
            }

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }

        return specimensamplinglist;
    }


    public void showspecimensampling() {
        ArrayList<SpecimenSamplingList> list = specimenSamplingList();
        DefaultTableModel model = (DefaultTableModel) tablesampling.getModel();
        Object[] row = new Object[9];

        for (SpecimenSamplingList specimen : list) {
            row[0] = specimen.getId();
            row[1] = specimen.getCommonname();
            row[2] = specimen.getGenus();
            row[3] = specimen.getSpecies();
            row[4] = specimen.getStem();
            row[5] = specimen.getLeaf();
            row[6] = specimen.getLocation();
            row[7] = specimen.getDate();
            row[8] = specimen.getTime();

            model.addRow(row);
        }
    }


    //TODO: add functionalities to generate report
    public void generateSummary(){
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 1262, 504);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("filter location:");
        lblNewLabel.setBounds(21, 31, 91, 27);
        frame.getContentPane().add(lblNewLabel);

        JComboBox comboBoxsummary = new JComboBox();
        comboBoxsummary.addItem("no filter");
        comboBoxsummary.addItem("Kubah National Park");
        comboBoxsummary.addItem("Bako National Park");
        comboBoxsummary.addItem("Maludam National Park");
        comboBoxsummary.addItem("Mount Santubong");

        comboBoxsummary.setBounds(108, 30, 164, 29);
        frame.getContentPane().add(comboBoxsummary);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Summary", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(372, 20, 866, 393);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 15, 846, 368);
        panel.add(scrollPane);

        imagesummary = new JLabel("");
        imagesummary.setBounds(6, 15, 346, 297);

        tablesampling = new JTable();
        tablesampling.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tablesampling.getSelectedRow();

                byte[] img = (specimenSamplingList().get(i).getPhoto());
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(imagesummary.getWidth(), imagesummary.getHeight(), Image.SCALE_SMOOTH));
                imagesummary.setIcon(imageIcon);
            }
        });
        tablesampling.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Id", "Common Name", "Genus", "Species", "Stem", "Leaf", "Location", "Date", "Time"
                }
        ));
        scrollPane.setViewportView(tablesampling);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Pictures", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_1.setBounds(21, 82, 341, 331);
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        panel_1.add(imagesummary);

        JButton apply = new JButton("apply");
        apply.setBounds(277, 31, 78, 27);
        frame.getContentPane().add(apply);

        apply.addActionListener(e -> {
            combosummary = (String) comboBoxsummary.getSelectedItem();
            System.out.println(combosummary);
            generateSummary();
            frame.setVisible(false);
        });

        JButton back = new JButton("Back");
        back.setVerticalAlignment(SwingConstants.BOTTOM);
        back.setBounds(547, 434, 85, 21);
        frame.getContentPane().add(back);

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });

        showspecimensampling();

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}


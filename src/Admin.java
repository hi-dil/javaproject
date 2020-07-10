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
import java.util.stream.Stream;

public class Admin extends JFrame{

    // Use in login()
    private String admin = "admin";
    private String adminpassword = "admin";

    // Use in addrecord()
    private byte[] specimenimage = null;

    // Use in editrecord()
    private String[] recordvalue = new String[5];
    private JLabel imageedit;
    private ImageIcon editimage;
    private int valuecombobox = 0;

    // Use in displayrecord()
    private JLabel imagetable;

    // Use in deleterecord()
    private JTable table;

    // Use in searchrecord()
    private String locationsearchtext = "";
    private String speciessearchtext = "";

    // Use in generatesummary()
    private String combosummary = "no filter";
    private String comboboxdatesummary = "no filter";

    // Use in generatesummary and searchrecord()
    private JLabel imagesummary;
    private JTable tablesampling;

    //Use in addrecord() and editrecord()
    private JLabel imagelable;

    // Use in editrecord() and deleterecord()
    private JComboBox comboBox = new JComboBox();

    //+-+-+-+-+-+-+- use to connect sql db for displayrecord() and deleterecord()-+-+-+-+-+-+-+

    // used to get data from db and store it inside specimenlist arraylist
    public ArrayList<Specimen> specimenList() {
        ArrayList<Specimen> specimenList = new ArrayList<>();

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
            Statement statement = myConn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from specimen");

            Specimen specimen;
            while(resultSet.next()){

                // use to store the row record of the table and store it as a object insinde an arraylist
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

    // use to show the data inside a db to jtable
    public void show_specimen() {

        // call the specimenlist method to get the data and store it inside a list of arraylist
        ArrayList<Specimen> list = specimenList();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object[] row = new Object[7];

        // use to store the data based on column in table
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

    //-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-


    //+-+-+-+-+-+-+- use to connect sql db for searchrecord()-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    // use to get data from db using where query and return the value of wanted value inside the searchlist array list
    public ArrayList<SpecimenSamplingList> search() {
        ArrayList<SpecimenSamplingList> searchList = new ArrayList<>();

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
            ResultSet resultSet = null;

            // run the query based on the jtextfield is filled or not
            if(!speciessearchtext.isEmpty() && !locationsearchtext.isEmpty()) {
                String sql = "select * " +
                        "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                        "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid)" +
                        "where specimenevent.location = ? and specimen.species = ?";
                PreparedStatement statement = myConn.prepareStatement(sql);

                statement.setString(1, locationsearchtext);
                statement.setString(2, speciessearchtext);

                resultSet = statement.executeQuery();
            }
            else if(!speciessearchtext.isEmpty()){
                String sql = "select * " +
                        "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                        "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid)" +
                        "where specimen.species = ?";
                PreparedStatement statement = myConn.prepareStatement(sql);

                statement.setString(1, speciessearchtext);


                resultSet = statement.executeQuery();
            }
            else{
                String sql = "select * " +
                        "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                        "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid)" +
                        "where specimenevent.location = ?";
                PreparedStatement statement = myConn.prepareStatement(sql);

                statement.setString(1, locationsearchtext);


                resultSet = statement.executeQuery();
            }

            SpecimenSamplingList specimenSampling;
            while(resultSet.next()){
                specimenSampling = new SpecimenSamplingList(resultSet.getString("location"), resultSet.getString("date"),
                        resultSet.getString("time"), resultSet.getString("commonname"), resultSet.getString("genus"),
                        resultSet.getString("species"), resultSet.getString("stem"), resultSet.getString("leaf"),
                        resultSet.getInt("specimentakeid"), resultSet.getBytes("photo"));

                searchList.add(specimenSampling);
            }

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "The data didn't exist in database");
        }

        return searchList;
    }

    // use to show the data inside a db to jtable
    public void showsearch() {
        ArrayList<SpecimenSamplingList> list = search();
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

    //-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-


    //+-+-+-+-+-+-+- use to connect sql db for generatesummary()-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    // used for get the value of multiple table in sql using inner join query
    public ArrayList<SpecimenSamplingList> specimenSamplingList() {
        ArrayList<SpecimenSamplingList> specimensamplinglist = new ArrayList<>();

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
            ResultSet resultSet = null;

            if(comboboxdatesummary.equals("no filter")){
                if(combosummary.equals("no filter")){
                    Statement statement = myConn.createStatement();
                    resultSet = statement.executeQuery("select * " +
                            "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                            "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid)");
                }
                else{
                    String sql = "select * " +
                            "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                            "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid) " +
                            "where specimenevent.location = ?";

                    PreparedStatement statement = myConn.prepareStatement(sql);
                    statement.setString(1, combosummary);

                    resultSet = statement.executeQuery();
                }
            }
            else if (combosummary.equals("no filter")){
                if(comboboxdatesummary.equals("no filter")){
                    Statement statement = myConn.createStatement();
                    resultSet = statement.executeQuery("select * " +
                            "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                            "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid)");
                }
                else{
                    String sql = "select * " +
                            "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                            "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid) " +
                            "where specimenevent.date = ?";

                    PreparedStatement statement = myConn.prepareStatement(sql);
                    statement.setString(1, comboboxdatesummary);

                    resultSet = statement.executeQuery();
                }
            }
            else{
                String sql = "select * " +
                        "from ((specimentake inner join specimen on specimentake.specimenid = specimen.specimenid) " +
                        "inner join specimenevent on specimentake.specimeneventid = specimenevent.specimeneventid) " +
                        "where specimenevent.date = ? and specimenevent.location = ?";

                PreparedStatement statement = myConn.prepareStatement(sql);
                statement.setString(1, comboboxdatesummary);
                statement.setString(2, combosummary);

                resultSet = statement.executeQuery();
            }

            SpecimenSamplingList specimenSampling;
            while(resultSet.next()){

                // use to get the value from the db and store inside the arraylist by creating specimensamplinglist object
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

    //-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-


    // Use for showing image to label in addrecord() and editrecord()
    public ImageIcon ResizeImage(String path, JLabel imagelable){
        ImageIcon image = new ImageIcon(path);
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(imagelable.getWidth(), imagelable.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }


    // Use to sync up the JComboBox with the value in database in deleterecord()
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


    // Use to validate if the user has filled in all the field
    public boolean validatefields(String commonname, String genus, String species, String photo, String stem, String leaf,
                               boolean event1, boolean event2, boolean event3, boolean event4, boolean event5,
                               boolean event6, String function)
    {
        if(function.equals("add")){
            if(Stream.of(commonname, genus, species, photo, stem, leaf).anyMatch(String::isEmpty)){
                JOptionPane.showMessageDialog(null, "Please complete all the fields");
                return false;
            }

            if(!event1 && !event2 && !event3 && !event4 && !event5 && !event6){
                JOptionPane.showMessageDialog(null, "Please select any of the sampling event!");
                return false;
            }

        }
        else{
            if(Stream.of(commonname, genus, species, stem, leaf).anyMatch(String::isEmpty)){
                JOptionPane.showMessageDialog(null, "Please complete all the fields");
                return false;
            }

        } return true;
    }


    //+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ Main Function -+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    public void login(){

        // Component Declaration
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 383, 251);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel centerpanel = new JPanel();
        frame.getContentPane().add(centerpanel, BorderLayout.CENTER);

        JLabel welcomelable = new JLabel("Welcome! Please login to continue");
        welcomelable.setBounds(84, 10, 261, 48);

        JLabel usernamelable = new JLabel("Username");
        usernamelable.setBounds(57, 68, 101, 40);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(127, 118, 182, 19);

        JTextField usernametextfield = new JTextField();
        usernametextfield.setBounds(127, 79, 182, 19);
        usernametextfield.setColumns(10);

        JLabel lblNewLabel = new JLabel("Password");
        lblNewLabel.setBounds(57, 121, 66, 13);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(224, 169, 85, 21);


        // Event Handler
        usernametextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    passwordField.requestFocus();
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    String userName = usernametextfield.getText();
                    String password = passwordField.getText();
                    if ((userName.equals(admin)) && (password.equals(adminpassword))) {
                        mainmenu();
                        frame.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
                }
            }
        });

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


        // Component Placement
        centerpanel.setLayout(null);
        centerpanel.add(welcomelable);
        centerpanel.add(usernamelable);
        centerpanel.add(usernametextfield);
        centerpanel.add(lblNewLabel);
        centerpanel.add(passwordField);
        centerpanel.add(loginButton);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void mainmenu(){

        // Component Declaration
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(UIManager.getColor("Button.disabledShadow"));
        frame.setBounds(100, 100, 368, 451);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Welcome Back!");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel.setBounds(101, 0, 151, 50);

        JButton addRecord = new JButton("Add Record");
        addRecord.setBackground(UIManager.getColor("Button.foreground"));
        addRecord.setBounds(80, 93, 193, 32);

        JButton displayRecord = new JButton("Display Record");
        displayRecord.setBounds(80, 141, 193, 32);

        JButton deleteRecord = new JButton("Delete Record");
        deleteRecord.setBounds(80, 243, 193, 32);

        JButton searchRecord = new JButton("Search Record");
        searchRecord.setBounds(80, 295, 193, 32);

        JLabel lblNewLabel_1 = new JLabel("Please select what you want to do");
        lblNewLabel_1.setBounds(80, 40, 193, 21);

        JButton summary = new JButton("Generate Summary");
        summary.setBounds(80, 346, 193, 32);

        JButton editRecord = new JButton("EditRecord");
        editRecord.setBounds(80, 192, 193, 32);


        // Event Handler
        addRecord.addActionListener(e -> {
            addrecord();
            frame.setVisible(false);
        });

        displayRecord.addActionListener(e -> {
            displayrecord();
            frame.setVisible(false);
        });

        deleteRecord.addActionListener(e -> {
            deleteRecord();
            frame.setVisible(false);
        });

        searchRecord.addActionListener(e -> {
            searchrecord();
            frame.setVisible(false);
        });

        summary.addActionListener(e -> {
            generateSummary();
            frame.setVisible(false);
        });

        editRecord.addActionListener(e -> {
            editrecord(false);
            frame.setVisible(false);
        });


        // Component Placement
        frame.getContentPane().add(lblNewLabel);
        frame.getContentPane().add(addRecord);
        frame.getContentPane().add(displayRecord);
        frame.getContentPane().add(deleteRecord);
        frame.getContentPane().add(searchRecord);
        frame.getContentPane().add(lblNewLabel_1);
        frame.getContentPane().add(summary);
        frame.getContentPane().add(editRecord);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void addrecord(){

        // Component Declaration
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 345, 775);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Please enter the details to add record");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 10, 314, 294);
        panel_1.setBorder(new TitledBorder(null, "Specimen Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JLabel commonnamelable = new JLabel("Common Name");
        commonnamelable.setBounds(6, 15, 146, 21);

        JTextField gtextfield = new JTextField();
        gtextfield.setBounds(109, 41, 199, 21);
        gtextfield.setColumns(10);

        JTextField commonnametextfield = new JTextField();
        commonnametextfield.setBounds(109, 15, 199, 21);
        commonnametextfield.setColumns(10);

        JLabel glable = new JLabel("Genus");
        glable.setBounds(6, 41, 146, 21);

        JTextField stextfield = new JTextField();
        stextfield.setBounds(109, 71, 199, 22);
        stextfield.setColumns(10);

        JLabel slable = new JLabel("species");
        slable.setBounds(6, 67, 146, 26);

        JLabel Photo = 	new JLabel("Photo");
        Photo.setBounds(6, 103, 45, 13);

        JButton browse = new JButton("browse");
        browse.setBounds(109, 103, 85, 21);

        JLabel l = new JLabel("no file selected");
        l.setBounds(109, 134, 195, 13);

        imagelable = new JLabel();
        imagelable.setBounds(109, 152, 124, 130);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "Characteristics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(10, 312, 314, 265);

        JLabel stemlable = new JLabel("Stem");
        stemlable.setBounds(10, 62, 41, 13);

        JTextArea leafta = new JTextArea();
        leafta.setLineWrap(true);
        leafta.setBounds(110, 147, 194, 97);

        JTextArea stemtextarea = new JTextArea();
        stemtextarea.setLineWrap(true);
        stemtextarea.setBounds(161, 41, 143, 97);

        JScrollPane stemscroll = new JScrollPane(stemtextarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        stemscroll.setBounds(110, 30, 194, 97);

        JLabel leaflable = new JLabel("Leaf");
        leaflable.setBounds(10, 186, 45, 13);

        JScrollPane leafscroll = new JScrollPane(leafta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leafscroll.setBounds(110, 147, 194, 97);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "Sampling Event", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_3.setBounds(10, 587, 314, 97);

        JLabel lblNewLabel = new JLabel("Please select at where this specimen was found");
        lblNewLabel.setBounds(6, 15, 295, 15);

        JCheckBox samplingevent1 = new JCheckBox("Sampling Event 1");
        samplingevent1.setBounds(6, 35, 149, 15);

        JCheckBox samplingevent2 = new JCheckBox("Sampling Event 2");
        samplingevent2.setBounds(6, 55, 149, 15);

        JCheckBox samplingevent3 = new JCheckBox("Sampling Event 3");
        samplingevent3.setBounds(6, 75, 137, 15);

        JCheckBox samplingevent4 = new JCheckBox("Sampling Event 4");
        samplingevent4.setBounds(157, 32, 127, 21);

        JCheckBox samplingevent5 = new JCheckBox("Sampling Event 5");
        samplingevent5.setBounds(157, 52, 136, 21);

        JCheckBox samplingevent6 = new JCheckBox("Sampling Event 6");
        samplingevent6.setBounds(157, 72, 136, 21);

        JButton save = new JButton("Save");
        save.setBounds(239, 694, 85, 21);

        JButton back = new JButton("Back");
        back.setBounds(10, 694, 85, 21);

        JButton help = new JButton("help");
        help.setBounds(169, 694, 60, 21);


        // Event Handler
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
                    imagelable.setIcon(ResizeImage(path, imagelable));

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

        stemtextarea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    leafta.requestFocus();
                }
            }
        });

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

        gtextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    stextfield.requestFocus();
                }
            }
        });

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });

        commonnametextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    gtextfield.requestFocus();
                }
            }
        });

        save.addActionListener(e -> {
            String commonnameaction = commonnametextfield.getText();
            String genusaction = gtextfield.getText();
            String speciesaction = stextfield.getText();
            String photolocation = l.getText();
            String stemaction = stemtextarea.getText();
            String leafaction = leafta.getText();

            if(l.getText().equals("no file selected")){
                photolocation = "";
            }

            boolean verify;

            verify = validatefields(commonnameaction, genusaction, speciesaction, photolocation, stemaction, leafaction,
                        samplingevent1.isSelected(), samplingevent2.isSelected(), samplingevent3.isSelected(), samplingevent4.isSelected(),
                        samplingevent5.isSelected(), samplingevent6.isSelected(), "add");

            if (verify){
                int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this record?", "Add Record",
                        JOptionPane.OK_CANCEL_OPTION);

                if (input == 0){

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
            }
        });


        // Component placement
        frame.getContentPane().add(welcome, BorderLayout.NORTH);
        panel.setLayout(null);
        panel.add(panel_1);
        panel_1.setLayout(null);
        panel_1.add(commonnamelable);
        panel_1.add(commonnametextfield);
        panel_1.add(glable);
        panel_1.add(gtextfield);
        panel_1.add(slable);
        panel_1.add(Photo);
        panel_1.add(browse);
        panel_1.add(l);
        panel_1.add(stextfield);
        panel_1.add(imagelable);
        panel.add(panel_2);

        panel_2.setLayout(null);
        panel_2.add(stemlable);
        //panel_2.add(stemtextarea);
        panel_2.add(stemscroll);
        panel_2.add(leaflable);
        //panel_2.add(leafta);
        panel_2.add(leafscroll);
        panel.add(panel_3);

        panel_3.setLayout(null);
        panel_3.add(lblNewLabel);
        panel_3.add(samplingevent1);
        panel_3.add(samplingevent2);
        panel_3.add(samplingevent3);
        panel_3.add(samplingevent4);
        panel_3.add(samplingevent5);
        panel_3.add(samplingevent6);

        panel.add(save);
        panel.add(back);
        panel.add(help);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void displayrecord(){

        // Component Declaration
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 1218, 451);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Record");
        lblNewLabel.setBounds(0, 10, 1204, 30);
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Records", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_1.setBounds(10, 51, 815, 317);
        frame.getContentPane().add(panel_1);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 15, 795, 292);

        table = new JTable();
        scrollPane_1.setViewportView(table);

        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Specimen Id", "Common Name", "Genus", "Species", "Stem", "Leaf"
                }
        ) {
            final Class[] columnTypes = new Class[] {
                    String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
            };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        table.setDefaultRenderer(String.class, centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(90);

        imagetable = new JLabel("");
        imagetable.setBounds(10, 23, 339, 285);

        JButton back = new JButton("back");
        back.setBounds(554, 378, 98, 21);

        JPanel imagepanel = new JPanel();
        imagepanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Pictures", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        imagepanel.setBounds(835, 50, 359, 318);


        // Event handler
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();

                byte[] img = (specimenList().get(i).getPhoto());
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(imagetable.getWidth(), imagetable.getHeight(), Image.SCALE_SMOOTH));
                imagetable.setIcon(imageIcon);
            }
        });

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });


        // Component Placement
        frame.getContentPane().add(lblNewLabel);

        panel_1.setLayout(null);
        panel_1.add(scrollPane_1);

        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        frame.getContentPane().add(back);
        frame.getContentPane().add(imagepanel);

        imagepanel.setLayout(null);
        imagepanel.add(imagetable);

        // Use to update the table from database when load the displayrecord
        show_specimen();

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void editrecord(boolean stop){

        // Component Declaration
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 348, 771);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Please enter the details to add record");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 52, 314, 246);
        panel_1.setBorder(new TitledBorder(null, "Specimen Details", TitledBorder.LEADING, TitledBorder.TOP,
                null, null));

        JLabel commonnamelable = new JLabel("Common Name");
        commonnamelable.setBounds(6, 15, 146, 21);

        JTextField stextfield = new JTextField(recordvalue[2]);
        stextfield.setBounds(109, 71, 199, 19);
        stextfield.setColumns(10);

        JTextField gtextfield = new JTextField(recordvalue[1]);
        gtextfield.setBounds(109, 41, 199, 21);
        gtextfield.setColumns(10);

        JTextField commonnametextfield = new JTextField(recordvalue[0]);
        commonnametextfield.setBounds(109, 15, 199, 21);
        commonnametextfield.setColumns(10);

        JLabel glable = new JLabel("Genus");
        glable.setBounds(6, 41, 146, 21);

        JLabel slable = new JLabel("species");
        slable.setBounds(6, 67, 146, 26);

        JLabel photo = 	new JLabel("Photo");
        photo.setBounds(6, 107, 45, 13);

        JLabel l = new JLabel();
        l.setBounds(107, 129, 106, 13);

        JButton btnNewButton = new JButton("browse");
        btnNewButton.setBounds(109, 103, 85, 21);

        imageedit = new JLabel("");
        imageedit.setIcon(editimage);
        imageedit.setBounds(109, 129, 117, 107);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "Characteristics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(10, 308, 314, 265);
        panel_2.setLayout(null);

        JLabel stemlable = new JLabel("Stem");
        stemlable.setBounds(10, 62, 41, 13);

        JTextArea leafta = new JTextArea(recordvalue[4]);
        leafta.setLineWrap(true);
        leafta.setBounds(110, 147, 194, 97);

        JTextArea stemta = new JTextArea(recordvalue[3]);
        stemta.setLineWrap(true);
        stemta.setBounds(161, 41, 143, 97);

        JScrollPane stemscroll = new JScrollPane(stemta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        stemscroll.setBounds(110, 30, 194, 97);

        JLabel leaflable = new JLabel("Leaf");
        leaflable.setBounds(10, 186, 45, 13);

        JScrollPane leafscroll = new JScrollPane(leafta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leafscroll.setBounds(110, 147, 194, 97);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "Sampling Event", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_3.setBounds(10, 583, 314, 97);
        panel_3.setLayout(null);

        JLabel lblNewLabel = new JLabel("Please select at where this specimen was found");
        lblNewLabel.setBounds(6, 15, 299, 15);

        JCheckBox samplingevent1 = new JCheckBox("Sampling Event 1");
        samplingevent1.setBounds(6, 35, 146, 15);

        JCheckBox samplingevent2 = new JCheckBox("Sampling Event 2");
        samplingevent2.setBounds(6, 55, 146, 15);

        JCheckBox samplingevent3 = new JCheckBox("Sampling Event 3");
        samplingevent3.setBounds(6, 75, 146, 15);

        JCheckBox samplingevent4 = new JCheckBox("Sampling Event 4");
        samplingevent4.setBounds(163, 32, 142, 21);

        JCheckBox samplingevent6 = new JCheckBox("Sampling Event 6");
        samplingevent6.setBounds(163, 72, 142, 21);

        JCheckBox samplingevent5 = new JCheckBox("Sampling Event 5");
        samplingevent5.setBounds(163, 52, 142, 21);

        JButton update = new JButton("Update");
        update.setBounds(239, 690, 85, 21);

        JButton back = new JButton("Back");
        back.setBounds(10, 690, 85, 21);

        //comboBox.setSelectedIndex(valuecombobox);
        comboBox.setBounds(21, 21, 198, 21);

        if(!stop)
            comboBox.addItem("");

        JButton apply = new JButton("Apply");
        apply.setBounds(229, 21, 85, 21);

        JButton btnNewButton_1 = new JButton("help");
        btnNewButton_1.setBounds(166, 690, 63, 21);


        // Event Handler
        gtextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    stextfield.requestFocus();
                }
            }
        });

        commonnametextfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    gtextfield.requestFocus();
                }
            }
        });

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // create an object of JFileChooser class
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                // invoke the showsOpenDialog function to show the save dialog
                int r = j.showOpenDialog(null);

                // if the user selects a file
                if (r == JFileChooser.APPROVE_OPTION)

                {
                    File selectedFile = j.getSelectedFile();
                    // set the label to the path of the selected file
                    l.setText(j.getSelectedFile().getAbsolutePath());
                    String path = selectedFile.getAbsolutePath();
                    imageedit.setIcon(ResizeImage(path, imageedit));
                }
                // if the user cancelled the operation
                else
                    l.setText("the user cancelled the operation");
            }
        });

        stemta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == 10){
                    leafta.requestFocus();
                }
            }
        });

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

        update.addActionListener(e -> {
            if(comboBox.getSelectedItem().equals("")){
                JOptionPane.showMessageDialog(null, "Please selected any specimen Id and click apply!");
            }
            else {
                String commonnameaction = commonnametextfield.getText();
                String genusaction = gtextfield.getText();
                String speciesaction = stextfield.getText();
                String photolocation = l.getText();
                String stemaction = stemta.getText();
                String leafaction = leafta.getText();

                boolean verify = validatefields(commonnameaction, genusaction, speciesaction, photolocation, stemaction, leafaction,
                        samplingevent1.isSelected(), samplingevent2.isSelected(), samplingevent3.isSelected(), samplingevent4.isSelected(),
                        samplingevent5.isSelected(), samplingevent6.isSelected(), "edit");

                if (verify) {
                    int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this record?", "Update Record",
                            JOptionPane.OK_CANCEL_OPTION);

                    if (input == 0) {
                        int value = (int) comboBox.getSelectedItem();

                        if (!samplingevent1.isSelected() && !samplingevent2.isSelected() && !samplingevent3.isSelected() &&
                                !samplingevent1.isSelected() && !samplingevent2.isSelected() && !samplingevent3.isSelected()) {
                            System.out.println("nice one");
                        } else {
                            try {
                                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
                                PreparedStatement stmt = myConn.prepareStatement("delete from specimentake where specimenid = ?");

                                stmt.setInt(1, value);
                                stmt.executeUpdate();
                                myConn.close();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex);
                            }
                        }


                        Specimen specimen = new Specimen();
                        specimen.updateRecord(value, commonnameaction, genusaction, speciesaction, photolocation, stemaction, leafaction);

                        JOptionPane.showMessageDialog(null, "The record has been updated!");

                        try {
                            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
                            PreparedStatement stmt = myConn.prepareStatement("insert into specimentake(specimenid, specimeneventid)" +
                                    "values(?, ?)");

                            if (samplingevent1.isSelected()) {
                                stmt.setInt(1, value);
                                stmt.setInt(2, 1);
                                stmt.executeUpdate();
                            }

                            if (samplingevent2.isSelected()) {
                                stmt.setInt(1, value);
                                stmt.setInt(2, 2);
                                stmt.executeUpdate();
                            }

                            if (samplingevent3.isSelected()) {
                                stmt.setInt(1, value);
                                stmt.setInt(2, 3);
                                stmt.executeUpdate();
                            }

                            if (samplingevent4.isSelected()) {
                                stmt.setInt(1, value);
                                stmt.setInt(2, 4);
                                stmt.executeUpdate();
                            }

                            if (samplingevent5.isSelected()) {
                                stmt.setInt(1, value);
                                stmt.setInt(2, 5);
                                stmt.executeUpdate();
                            }

                            if (samplingevent6.isSelected()) {
                                stmt.setInt(1, value);
                                stmt.setInt(2, 6);
                                stmt.executeUpdate();
                            }

                            myConn.close();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex);
                        }

                    } else {
                        editrecord(true);
                        frame.setVisible(false);
                    }
                }
            }
        });

        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!comboBox.getSelectedItem().equals("")){
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
                        editrecord(true);
                        frame.setVisible(false);

                    }
                    catch (Exception j){
                        JOptionPane.showMessageDialog(null, j);
                    }
                }
            }
        });

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });


        // Component Placement
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(welcome, BorderLayout.NORTH);

        panel.setLayout(null);

        panel.add(panel_1);
        panel_1.setLayout(null);
        panel_1.add(commonnamelable);
        panel_1.add(commonnametextfield);
        panel_1.add(glable);
        panel_1.add(gtextfield);
        panel_1.add(slable);
        panel_1.add(photo);
        panel_1.add(btnNewButton);
        panel_1.add(l);
        panel_1.add(stextfield);
        panel_1.add(imageedit);

        panel.add(panel_2);
        panel_2.add(stemlable);
        //panel_2.add(stemta);
        panel_2.add(stemscroll);
        panel_2.add(leaflable);
        //panel_2.add(leafta);
        panel_2.add(leafscroll);

        panel.add(panel_3);
        panel_3.add(lblNewLabel);
        panel_3.add(samplingevent1);
        panel_3.add(samplingevent2);
        panel_3.add(samplingevent3);
        panel_3.add(samplingevent4);
        panel_3.add(samplingevent6);
        panel_3.add(samplingevent5);

        panel.add(update);
        panel.add(back);
        panel.add(comboBox);
        panel.add(apply);
        panel.add(btnNewButton_1);

        // Outside function
        // Use to automatically fill the combobox with specimenid when the user click the apply button
        if(!stop)
            updateCombo();
    }

    public void searchrecord(){

        // Component Declaration
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 1135, 614);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblNewLabel = new JLabel("Please enter what you want to search");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 19));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JTextField speciessearch = new JTextField();
        speciessearch.setBounds(371, 24, 359, 19);
        speciessearch.setColumns(10);

        JTextField locationsearch = new JTextField();
        locationsearch.setBounds(371, 50, 359, 19);
        locationsearch.setColumns(10);

        JButton search = new JButton("Search");
        search.setBounds(744, 49, 85, 21);

        JButton back = new JButton("back");
        back.setBounds(526, 523, 85, 21);

        JLabel species = new JLabel("Species");
        species.setHorizontalAlignment(SwingConstants.RIGHT);
        species.setBounds(297, 27, 64, 13);

        JLabel location = new JLabel("Location");
        location.setHorizontalAlignment(SwingConstants.RIGHT);
        location.setBounds(297, 53, 64, 13);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Record", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_1.setBounds(4, 76, 673, 441);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 15, 653, 416);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "Photo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(688, 76, 429, 441);
        panel_2.setLayout(null);

        imagesummary = new JLabel("");
        imagesummary.setBounds(10, 22, 409, 405);

        tablesampling = new JTable();

        tablesampling.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Common Name", "Genus", "Species", "Stem", "Leaf", "Location", "Date", "Time"
                }
        ));
        scrollPane.setViewportView(tablesampling);


        // Event handler
        search.addActionListener(e -> {
            locationsearchtext = locationsearch.getText();
            speciessearchtext = speciessearch.getText();

            searchrecord();
            frame.setVisible(false);

        });

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });

        tablesampling.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tablesampling.getSelectedRow();

                byte[] img = (specimenSamplingList().get(i).getPhoto());
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(imagesummary.getWidth(), imagesummary.getHeight(), Image.SCALE_SMOOTH));
                imagesummary.setIcon(imageIcon);
            }
        });


        // Component Placement
        frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);

        panel.setLayout(null);
        panel.add(speciessearch);
        panel.add(locationsearch);
        panel.add(search);
        panel.add(back);
        panel.add(species);
        panel.add(location);

        panel.add(panel_1);
        panel_1.setLayout(null);
        panel_1.add(scrollPane);
        panel_2.add(imagesummary);

        panel.add(panel_2);

        frame.getRootPane().setDefaultButton(search);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        //outside funtion
        // will show the data inside table if and only if there's a query made
        if (!speciessearchtext.isEmpty() || !locationsearchtext.isEmpty())
            showsearch();
    }

    public void deleteRecord() {

        // Component Declaration
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 1079, 606);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        comboBox = new JComboBox();
        comboBox.setBounds(31, 107, 255, 25);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(342, 10, 713, 549);

        table = new JTable();
        scrollPane.setViewportView(table);

        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Specimen Id", "Common Name", "Genus", "Species", "Stem", "Leaf"
                }
        ) {
            final Class[] columnTypes = new Class[] {
                    String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
            };
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });

        JLabel lblNewLabel = new JLabel("SpecimenId");
        lblNewLabel.setBounds(31, 84, 92, 13);

        JButton delete = new JButton("Delete");
        delete.setBounds(201, 538, 85, 21);

        JButton back = new JButton("Back");
        back.setBounds(99, 538, 85, 21);

        JLabel lblNewLabel_1 = new JLabel("Please select specimenId to delete the said record");
        lblNewLabel_1.setBounds(31, 10, 335, 41);


        // Event Handler
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
                    final Class[] columnTypes = new Class[] {
                            String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
                    };
                    public Class getColumnClass(int columnIndex) {
                        return columnTypes[columnIndex];
                    }
                });

                JOptionPane.showMessageDialog(null, "The record has been deleted!");

                show_specimen();
            }

            deleteRecord();
            frame.setVisible(false);

        });

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });


        // Component Placement
        frame.getContentPane().add(comboBox);
        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(lblNewLabel);
        frame.getContentPane().add(delete);
        frame.getContentPane().add(back);
        frame.getContentPane().add(lblNewLabel_1);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


        // Outside function
        // Use to show data inside table
        show_specimen();
        // Use to update the value inside combobox
        updateCombo();
    }

    public void generateSummary(){

        // Component Declaration
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 1262, 504);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("filter location:");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setBounds(0, 31, 91, 27);

        JComboBox comboBoxsummary = new JComboBox();
        comboBoxsummary.addItem("no filter");
        comboBoxsummary.addItem("Kubah National Park");
        comboBoxsummary.addItem("Bako National");
        comboBoxsummary.addItem("Maludam National Park");
        comboBoxsummary.addItem("Mount Santubong");
        comboBoxsummary.setSelectedItem(combosummary);
        comboBoxsummary.setBounds(108, 30, 164, 29);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Summary", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(372, 20, 866, 393);
        panel.setLayout(null);

        JComboBox comboBoxdate = new JComboBox();
        comboBoxdate.addItem("no filter");
        comboBoxdate.addItem("2020-06-10");
        comboBoxdate.addItem("2020-06-18");
        comboBoxdate.addItem("2020-07-01");
        comboBoxdate.addItem("2020-07-03");
        comboBoxdate.setSelectedItem(comboboxdatesummary);
        comboBoxdate.setBounds(108, 69, 164, 27);

        JLabel filterdate = new JLabel("filter date");
        filterdate.setHorizontalAlignment(SwingConstants.RIGHT);
        filterdate.setBounds(21, 76, 72, 13);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 15, 846, 368);

        imagesummary = new JLabel("");
        imagesummary.setBounds(6, 15, 346, 297);

        tablesampling = new JTable();
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
        panel_1.setBounds(21, 114, 341, 299);
        panel_1.setLayout(null);

        JButton apply = new JButton("apply");
        apply.setBounds(282, 69, 78, 27);

        JButton back = new JButton("Back");
        back.setVerticalAlignment(SwingConstants.BOTTOM);
        back.setBounds(547, 434, 85, 21);


        // Event Handler
        tablesampling.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tablesampling.getSelectedRow();

                byte[] img = (specimenSamplingList().get(i).getPhoto());
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(imagesummary.getWidth(), imagesummary.getHeight(), Image.SCALE_SMOOTH));
                imagesummary.setIcon(imageIcon);
            }
        });

        apply.addActionListener(e -> {
            combosummary = (String) comboBoxsummary.getSelectedItem();
            comboboxdatesummary = (String) comboBoxdate.getSelectedItem();
            System.out.println(combosummary);
            System.out.println(comboboxdatesummary);
            generateSummary();
            frame.setVisible(false);
        });

        back.addActionListener(e -> {
            mainmenu();
            frame.setVisible(false);
        });


        // Component Placement
        frame.getContentPane().add(lblNewLabel);
        frame.getContentPane().add(comboBoxsummary);
        frame.getContentPane().add(panel);

        panel.add(scrollPane);

        frame.getContentPane().add(panel_1);

        panel_1.add(imagesummary);

        frame.getContentPane().add(apply);
        frame.getContentPane().add(back);
        frame.getContentPane().add(filterdate);
        frame.getContentPane().add(comboBoxdate);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


        // Outside funtion
        // Used to fill the data in table
        showspecimensampling();
    }

    //-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
}


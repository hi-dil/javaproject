import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.sql.*;

public class Specimen extends Admin{
    private String commonName, genus, species, stem, leaf;
    private int specimenId;
    private byte[] photo;
    private int specimenid;

    public Specimen(){
        System.out.println("Specimen created");
    }

    public int getSpecimenid() {
        return specimenid;
    }

    public Specimen(int specimenId, String commonName, String genus, String species, byte[] photo, String stem, String leaf) {
        this.specimenId = specimenId;
        this.commonName = commonName;
        this.genus = genus;
        this.species = species;
        this.photo = photo;
        this.stem = stem;
        this.leaf = leaf;
    }

    public int getSpecimenId() {
        return specimenId;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getGenus() {
        return genus;
    }

    public String getSpecies() {
        return species;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getStem() {
        return stem;
    }

    public String getLeaf() {
        return leaf;
    }

    public void addrecord(String commonName, String genus, String species, String photo, String stem, String leaf){
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
            PreparedStatement stmt = myConn.prepareStatement("insert into specimen(commonname, genus, species, photo, stem, leaf)" +
                    "values(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, commonName);
            stmt.setString(2, genus);
            stmt.setString(3, species);

            FileInputStream fin = new FileInputStream(photo);
            stmt.setBinaryStream(4, fin);

            stmt.setString(5, stem);
            stmt.setString(6, leaf);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            while(rs.next()){
                specimenId = rs.getInt(1);
            }

            myConn.close();

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void deleteRecord(int value){
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
            PreparedStatement stmt = myConn.prepareStatement("delete from specimen where specimenid = ?");
            stmt.setInt(1, value);

            stmt.executeUpdate();
            myConn.close();

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void updateRecord(int value, String commonName, String genus, String species, String photo, String stem, String leaf){

        if (!photo.isEmpty()){
            try {
                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
                PreparedStatement stmt = myConn.prepareStatement("update specimen set commonname=?, genus=?, species=?, photo=?, stem=?, leaf=?" +
                        "where specimenid = ?");
                stmt.setString(1, commonName);
                stmt.setString(2, genus);
                stmt.setString(3, species);

                FileInputStream fin = new FileInputStream(photo);
                stmt.setBinaryStream(4, fin);

                stmt.setString(5, stem);
                stmt.setString(6, leaf);
                stmt.setInt(7, value);

                stmt.executeUpdate();

                myConn.close();

            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        else{
            try {
                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Palm?serverTimezone=UTC", "root", "");
                PreparedStatement stmt = myConn.prepareStatement("update specimen set commonname=?, genus=?, species=?, stem=?, leaf=?" +
                        "where specimenid = ?");
                stmt.setString(1, commonName);
                stmt.setString(2, genus);
                stmt.setString(3, species);
                stmt.setString(4, stem);
                stmt.setString(5, leaf);
                stmt.setInt(6, value);

                stmt.executeUpdate();

                myConn.close();

            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
}

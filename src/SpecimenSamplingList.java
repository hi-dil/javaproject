public class SpecimenSamplingList {
    private String location, date, time;
    private String commonname, genus, species, stem, leaf;
    private byte[] photo;
    private int id;


    public SpecimenSamplingList(String location, String date, String time, String commonname, String genus, String species,
                                String stem, String leaf, int id, byte[] photo) {
        this.location = location;
        this.date = date;
        this.time = time;
        this.commonname = commonname;
        this.genus = genus;
        this.species = species;
        this.stem = stem;
        this.leaf = leaf;
        this.id = id;
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommonname() {
        return commonname;
    }

    public void setCommonname(String commonname) {
        this.commonname = commonname;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}



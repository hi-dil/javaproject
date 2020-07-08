public class Specimen {
    private String commonName, genus, species, photo, stem, leaf;

    public Specimen(String commonName, String genus, String species, String photo, String stem, String leaf) {
        this.commonName = commonName;
        this.genus = genus;
        this.species = species;
        this.photo = photo;
        this.stem = stem;
        this.leaf = leaf;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
}

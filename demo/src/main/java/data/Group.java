package data;

public class Group {
    private Software[] softwares;
    private Technique[] techniques;
    private String name;

    public Group(String name){
        super();
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Software[] getSoftwares() {
        return softwares;
    }

    public Technique[] getTechniques() {
        return techniques;
    }

    public void setTechniques(Technique[] techniques) {
        this.techniques = techniques;
    }

    public void setSoftwares(Software[] softwares) {
        this.softwares = softwares;
    }
}

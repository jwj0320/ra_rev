package data;

import java.util.ArrayList;

public class Asset {
    public static final int SOFTWARE=0;
    public static final int DATA=1;
    public static final int PLATFORM=2;
    public static final int HARDWARE=3;
    
    private int type;
    private String id="";
    private String name="";
    private String description="";
    private ArrayList<Threat> threatList=new ArrayList<Threat>();
    private ArrayList<Evidence> evidenceList=new ArrayList<Evidence>();
    
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public ArrayList<Evidence> getEvidenceList() {
        return evidenceList;
    }

    public void setEvidenceList(ArrayList<Evidence> evidenceList) {
        this.evidenceList = evidenceList;
    }

    public Evidence getEvidence(String id){
        for (Evidence ev:evidenceList){
            if(ev.getId().equals(id)){
                return ev;
            }
        }
        return null;
    }

    public ArrayList<Threat> getThreatList() {
        return threatList;
    }

    public void setThreatList(ArrayList<Threat> threatList) {
        this.threatList = threatList;
    }

    public Asset(){

    }

    public Asset(String type, String id){
        switch (type.toUpperCase()) {
            case "SOFTWARE":
                this.type=SOFTWARE;
                break;
            case "DATA":
                this.type=DATA;
                break;
            case "PLATFORM":
                this.type=PLATFORM;
                break;
            case "HARDWARE":
                this.type=HARDWARE;
                break;
            default:
                this.type=-1;
                break;
        }
        this.id=id;
        ArrayList<String[]> info=ProcessedData.ontologyFunc.LoadAssetInfo(id);
        if(info.size()!=0){
            this.name=info.get(0)[1];
            this.description=info.get(0)[2];
        }
    }

    public String getTypeName(){
        switch (type) {
            case SOFTWARE:
                return "Software";
            case DATA:
                return "Data";
            case PLATFORM:
                return "Platform";
            case HARDWARE:
                return "Hardware";
            default:
                return null;
        }
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}

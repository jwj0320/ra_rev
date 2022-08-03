package data;

import java.util.ArrayList;

public class Asset {
    public static final int SOFTWARE=0;
    public static final int DATA=1;
    public static final int PLATFORM=2;
    public static final int HARDWARE=3;
    
    private int type;
    private String name;
    private ArrayList<Threat> threatList=new ArrayList<Threat>();

    public ArrayList<Threat> getThreatList() {
        return threatList;
    }

    public void setThreatList(ArrayList<Threat> threatList) {
        this.threatList = threatList;
    }

    public Asset(){

    }

    public Asset(String type, String name){
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
        this.name=name;
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
    

    
}

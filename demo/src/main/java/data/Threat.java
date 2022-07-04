package data;

import java.util.ArrayList;

public class Threat {
    private static int number=1;
    private String name;
    private ArrayList<String> tacticList;
    private Technique technique;
    private ArrayList<Software> softwareList = new ArrayList<Software>(); 
    private Vulnerability vulnerability = new Vulnerability();
    private Asset asset = new Asset();
    private ArrayList<String> mitigationList = new ArrayList<String>();
    
    public Threat(){
        this.name = "Threat"+String.format("%02d",number++);
    }

    
    public ArrayList<String> getMitigationList() {
        return mitigationList;
    }    


    public void setMitigationList(ArrayList<String> mitigationList) {
        this.mitigationList = mitigationList;
    }    



    public ArrayList<String> getTacticList() {
        return tacticList;
    }


    public void setTacticList(ArrayList<String> tacticList) {
        this.tacticList = tacticList;
    }


    public Vulnerability getVulnerability() {
        return vulnerability;
    }


    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }


    public Asset getAsset() {
        return asset;
    }


    public void setAsset(Asset asset) {
        this.asset = asset;
    }


    public ArrayList<Software> getSoftwareList() {
        return softwareList;
    }


    public void setSoftwareList(ArrayList<Software> softwareList) {
        this.softwareList = softwareList;
    }


    public Technique getTechnique() {
        return technique;
    }


    public void setTechnique(Technique technique) {
        this.technique = technique;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }



}

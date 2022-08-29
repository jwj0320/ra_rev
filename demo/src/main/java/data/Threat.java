package data;

import java.util.ArrayList;

public class Threat {
    private static int index=0;

    private String id;
    private String tactic;
    private String technique;
    private int step;

    private ArrayList<Asset> assetList=new ArrayList<Asset>();
    private ArrayList<SecReq> srList = new ArrayList<SecReq>();
    
    
    
    private ArrayList<String> mitigationList = new ArrayList<String>();
    private ArrayList<String> CAPEC = new ArrayList<String>();
    private ArrayList<String> CWE = new ArrayList<String>();
    private ArrayList<String> CVE = new ArrayList<String>();
    
    
    public ArrayList<SecReq> getSrList() {
        return srList;
    }


    public void setSrList(ArrayList<SecReq> srList) {
        this.srList = srList;
    }
    public ArrayList<Asset> getAssetList() {
        return assetList;
    }


    public void setAssetList(ArrayList<Asset> assetList) {
        this.assetList = assetList;
    }


    public int getStep() {
        return step;
    }


    public void setStep(int step) {
        this.step = step;
    }
    public ArrayList<String> getCAPEC() {
        return CAPEC;
    }


    public void setCAPEC(ArrayList<String> cAPEC) {
        CAPEC = cAPEC;
    }


    public ArrayList<String> getCVE() {
        return CVE;
    }


    public void setCVE(ArrayList<String> cVE) {
        CVE = cVE;
    }


    public ArrayList<String> getCWE() {
        return CWE;
    }


    public void setCWE(ArrayList<String> cWE) {
        CWE = cWE;
    }


    
    public Threat(){
        this.id = "Th"+String.format("%04d",index++);
    }

    
    public ArrayList<String> getMitigationList() {
        return mitigationList;
    }    


    public void setMitigationList(ArrayList<String> mitigationList) {
        this.mitigationList = mitigationList;
    }    



    public String getTactic() {
        return tactic;
    }


    public void setTactic(String tactic) {
        this.tactic = tactic;
    }



    public String getTechnique() {
        return technique;
    }


    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public String getId() {
        return id;
    }


    public void setId(String name) {
        this.id = name;
    }



}

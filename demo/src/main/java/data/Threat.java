package data;

import java.util.ArrayList;

public class Threat {
    private String name;
    private String tactic;
    private String technique;
    private int step;
    
    private ArrayList<Asset> assetList=new ArrayList<Asset>();
    private SecReq secReq = new SecReq();
    
    private ArrayList<String> mitigationList = new ArrayList<String>();
    private ArrayList<String> CAPEC;
    private ArrayList<String> CWE;
    private ArrayList<String> CVE;
    
    
    public ArrayList<Asset> getAssetList() {
        return assetList;
    }


    public void setAssetList(ArrayList<Asset> assetList) {
        this.assetList = assetList;
    }
    
    public SecReq getSecReq() {
        return secReq;
    }


    public void setSecReq(SecReq secReq) {
        this.secReq = secReq;
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


    
    public Threat(int index){
        this.name = "Th"+String.format("%04d",index);
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

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }



}

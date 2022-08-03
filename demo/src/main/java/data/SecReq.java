package data;

public class SecReq {
    private String DT="";
    private String RP="";
    private String PD="";
    private String PV="";
    private Evidence dtEvidence=new Evidence();
    private Evidence rpEvidence=new Evidence();
    private Evidence pdEvidence=new Evidence();
    private Evidence pvEvidence=new Evidence();

    public Evidence getEvidence(String code){
        switch (code) {
            case "SRDT":
                return dtEvidence;
            case "SRRP":
                return rpEvidence;
            case "SRPD":
                return pdEvidence;
            case "SRPV":
                return pvEvidence;
            default:
                return null;
        }
    }
    
    public String getDT() {
        return DT;
    }
    public void setDT(String dT) {
        DT = dT;
    }
    public String getRP() {
        return RP;
    }
    public void setRP(String rP) {
        RP = rP;
    }
    public String getPD() {
        return PD;
    }
    public void setPD(String pR) {
        PD = pR;
    }
    public String getPV() {
        return PV;
    }
    public void setPV(String pV) {
        PV = pV;
    }
    public String getSR(String code){
        switch (code) {
            case "SRDT":
                return DT;
            case "SRRP":
                return RP;
            case "SRPD":
                return PD;
            case "SRPV":
                return PV;
            default:
                return null;
        }
    }
    
}

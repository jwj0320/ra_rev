package data;

public class SecReq {
    public static final int DT=0;
    public static final int RP=1;
    public static final int PD=2;
    public static final int PV=3;

    private Evidence evidence=new Evidence();
    private int type=-1;
    private String id="";
    private String text="";
    
    public Evidence getEvidence() {
        return evidence;
    }
    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }
    public String getType() {
        switch (type) {
            case DT:
                return "DT";
            case RP:
                return "RP";
            case PD:
                return "PD";
            case PV:
                return "PV";
            
            default:
                return "";
        }
    }
    public SecReq(){

    }

    public SecReq(int type, String id, String text){
        this.type=type;
        this.id=id;
        this.text=text;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    
}

package data;

public class SecReq {
    public static final int DT=0;
    public static final int RP=1;
    public static final int PD=2;
    public static final int PV=3;

    private int type=-1;
    private String id="";
    private String text="";
    private Threat threat;
    
    public Threat getThreat() {
        return threat;
    }
    public void setThreat(Threat threat) {
        this.threat = threat;
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

    public void setType(String type){
        switch (type){
            case "DT":
                this.type=DT;
                return;
            case "RP":
                this.type=RP;
                return;
            case "PD":
                this.type=PD;
                return;
            case "PV":
                this.type=PV;
                return;
            
            default:
                return;
        }
    }

    public SecReq(){

    }

    public SecReq(int type, String id, String text){
        this.type=type;
        this.id=id;
        this.text=text;
    }

    public SecReq(String id, String text){
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

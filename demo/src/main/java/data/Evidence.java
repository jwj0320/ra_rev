package data;

public class Evidence {
    private String id="";
    private String content="";
    private double score;
    private boolean isEvaluated=false;
    private SecReq sr;
    
    public SecReq getSr() {
        return sr;
    }


    public void setSr(SecReq sr) {
        this.sr = sr;
    }


    public boolean isEvaluated() {
		return isEvaluated;
	}


	public void setEvaluated(boolean isEvaluated) {
		this.isEvaluated = isEvaluated;
	}


	public double getScore() {
        return score;
    }


    public void setScore(double score) {
        this.score = score;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Evidence(){
        
    }

    public Evidence(String id){
        this.id=id;
    }
    public Evidence(String id, String content){
        this.id=id;
        this.content=content;
    }
}

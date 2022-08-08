package data;

public class Evidence {
    private String id="";
    private String content="";

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
    }
}

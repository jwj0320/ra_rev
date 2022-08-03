package data;

public class Evidence {
    private String name="";
    private String content="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Evidence(){
        
    }

    public Evidence(String name){
        this.name=name;
    }
    public Evidence(String name, String content){
        this.name=name;
    }
}

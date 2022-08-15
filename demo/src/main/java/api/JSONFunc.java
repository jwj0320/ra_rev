package api;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONFunc {
    private JSONObject jsonObject=new JSONObject();
    private String filePath="";
    
    public JSONFunc(){
        
    }
    public JSONObject getJsonObject() {
        return jsonObject;
    }
    public int setFilePath(String filePath){
        try {
            File file = new File(filePath);
            FileReader reader = new FileReader(file);
            JSONParser jsonParser = new JSONParser();
            jsonObject=(JSONObject)jsonParser.parse(reader);
            this.filePath = filePath;

            return 1;
        } catch (Exception e) {
            //TODO: handle exception
            System.err.println(e);
            return -1;
        }
        
    }
    
}

package api;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONFunc {
    private JSONAware jsonAware=new JSONObject();
    private String filePath="";
    
    public JSONFunc(){
        
    }
    public JSONAware getJSONAware() {
        return jsonAware;
    }
    public int setFilePath(String filePath){
        try {
            File file = new File(filePath);
            FileReader reader = new FileReader(file);
            JSONParser jsonParser = new JSONParser();
            Object rawObject = jsonParser.parse(reader);
            jsonAware=(JSONAware)rawObject;
            // if(rawObject instanceof JSONObject){
            //     jsonAware = (JSONObject)rawObject;
                
            // }
            // else if(rawObject instanceof JSONArray){
            //     JSONArray jsonArray = (JSONArray)rawObject;
                
            // }
            // else{
            //     return -1;
            // }

            this.filePath = filePath;

            return 1;
        } catch (Exception e) {
            //TODO: handle exception
            System.err.println(e);
            return -1;
        }
        
    }
    
}

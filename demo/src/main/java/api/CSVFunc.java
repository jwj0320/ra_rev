package api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CSVFunc {
    private String[] header;
    private Iterable<String[]> content;

    public CSVFunc(String[] header, Iterable<String[]> content){
        this.header=header;
        this.content=content;
    }

    public void saveToFile(String filePath){

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(header);
            writer.writeAll(content);
            writer.close();
            
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }

    public void loadFromeFile(String filePath){

        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            header=reader.readNext(); 
            content=reader.readAll(); 
            reader.close();
            
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }

    public JSONArray toJSONArray(){
        JSONArray jsonArray = new JSONArray();
        JSONObject subObject;
        for(String[] row:content){
            for(int i=0;i<header.length;i++){
                subObject=new JSONObject();
                subObject.put(header[i],row[i]);
                jsonArray.add(subObject);
            }
        }
        return jsonArray;
    }
}



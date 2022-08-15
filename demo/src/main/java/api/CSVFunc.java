package api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFunc {

    // public static void writeDataToCsv(String filePath) throws IOException {
    //     CSVWriter writer = new CSVWriter(new FileWriter(filePath));
    //     String[] entries = "EW#City#State".split("#");  // 1
    //     writer.writeNext(entries);  // 2

    //     String[] entries1 = {"W", "Youngstown", "OH"};  // 3
    //     writer.writeNext(entries1);

    //     String[] entries2 = {"W", "Williamson", "WV"};
    //     writer.writeNext(entries2);

    //     writer.close();
    // }

    public static void saveToFile(String filePath,String[] header, Iterable<String[]> data){

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(header);
            writer.writeAll(data);
            writer.close();
            
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }

    // public static void main(String args[]) throws IOException {
    //     writeDataToCsv("./sample.csv");
    // }
}



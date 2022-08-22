package api;

public class RadarChartData{

    private String category;
    private String type;
    private double value;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public RadarChartData(){

    }
    public RadarChartData(String type, String category, double value){
        this.type=type;
        this.category=category;
        this.value=value;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
}

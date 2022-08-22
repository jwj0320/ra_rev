package data;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import api.JSONFunc;
import api.OntologyFunc;

// import gui.tabbedContent.api.OntologyFunc;

public class ProcessedData {
    private static String organization="";
    
    private static ArrayList<Threat> threatList = new ArrayList<Threat>();
    private static ArrayList<Asset> assetList = new ArrayList<Asset>();
    private static ArrayList<SecReq> srList=new ArrayList<SecReq>();
    
    private static Step step1=new Step();
    private static Step step2=new Step();
    private static Step step3=new Step();
    private static Step step4=new Step();
    
    public static OntologyFunc ontologyFunc = new OntologyFunc();
    
    public static String getOrganization() {
        return organization;
    }

    public static void setOrganization(String organization) {
        ProcessedData.organization = organization;
    }
    public static SecReq getSr(String id){
        for(SecReq sr:srList){
            if(sr.getId().equals(id)){
                return sr;
            }
        }
        return null;
    }

    public static ArrayList<SecReq> getSrList() {
        return srList;
    }

    public static void setSrList(ArrayList<SecReq> srList) {
        ProcessedData.srList = srList;
    }
    public static Step getStep(int num){
        switch (num) {
            case 1:
                return step1;
            case 2:
                return step2;
            case 3:
                return step3;
            case 4:
                return step4;
        
            default:
                return null;
        }
    }

    public static ArrayList<Asset> getAssetList() {
        return assetList;
    }

    public static void setAssetList(ArrayList<Asset> assetList) {
        ProcessedData.assetList = assetList;
    }


    public static ArrayList<Threat> getThreatList(){
        return threatList;
    }

    public static void setThreatList(ArrayList<Threat> newList){
        threatList=newList;
    }

    public static Threat getThreat(String name){
        for (Threat t: threatList){
            if(t.getId().equals(name)){
                return t;
            }
        }
        return null;
    }

    public static Asset getAsset(String name){
        for (Asset a: assetList){
            if(a.getName().equals(name)){
                return a;
            }
        }
        return null;
    }

    public static ArrayList<Asset> getThreatAffectedAssets(){
        ArrayList<Asset> threatAffectedAssets=new ArrayList<Asset>();
        for (Threat th:threatList){
            for(Asset as: th.getAssetList()){
                if (as.getThreatList().size()>0 && (threatAffectedAssets.contains(as)==false)){
                    threatAffectedAssets.add(as);
                }
            }
        }
        return threatAffectedAssets;
    }

    public static void setThreatAffectedAssets(ArrayList<Asset> threatAffectedAssets){
        //
    }

    public static void saveDataToJSON(String filePath){
        JSONFunc jsonFunc=new JSONFunc();
        JSONObject jsonObject=(JSONObject)jsonFunc.getJSONAware();
        JSONArray assetArray = getJSONArrayFromAsset();
        
        String orgName=ProcessedData.getOrganization();

        jsonObject.put("organization",orgName);
        jsonObject.put("asset",assetArray);

        jsonFunc.saveToFile(filePath);

    }

    private static JSONArray getJSONArrayFromAsset(){
        JSONArray assetArray = new JSONArray();
        ArrayList<Asset> assetList=ProcessedData.getThreatAffectedAssets();

        for(Asset asset:assetList){
            assetArray.add(assetToJSON(asset));
        }
        return assetArray;
    }

    private static JSONObject assetToJSON(Asset asset){
        JSONObject assetObject=new JSONObject();
        String name= asset.getName();
        int type=asset.getType();
        String typeName=asset.getTypeName();
        ArrayList<Evidence> evidenceList= asset.getEvidenceList();
        // ArrayList<Threat> threatList= asset.getThreatList();
        JSONArray evidenceArray=new JSONArray();

        assetObject.put("name",name);
        assetObject.put("type",type);
        assetObject.put("typeName",typeName);

        for(Evidence ev:evidenceList){
            evidenceArray.add(evidenceToJSON(ev));
        }
        assetObject.put("evidence",evidenceArray);

        // assetObject.put("name",name);
        

        return assetObject;
    }

    private static JSONObject evidenceToJSON(Evidence evidence){
        JSONObject evidenceObject = new JSONObject();

        String id= evidence.getId();
        String content= evidence.getContent();
        double score= evidence.getScore();
        SecReq sr= evidence.getSr();
        JSONObject srObject=srToJSON(sr);

        evidenceObject.put("id",id);
        evidenceObject.put("content",content);
        evidenceObject.put("score",score);
        evidenceObject.put("sr",srObject);
        

        return evidenceObject;
    }

    private static JSONObject srToJSON(SecReq sr){
        JSONObject srObject=new JSONObject();
        String id=sr.getId();
        String text=sr.getText();
        String type=sr.getType();
        Threat threat=sr.getThreat(); 
        JSONObject threatObject=threatToJSON(threat);

        srObject.put("id",id);
        srObject.put("text", text);
        srObject.put("type",type);
        srObject.put("threat",threatObject);
        
        
        return srObject;
    }

    private static JSONObject threatToJSON(Threat threat){
        JSONObject threatObject = new JSONObject();
        String id=threat.getId();
        int step=threat.getStep();
        String tactic=threat.getTactic();
        String technique=threat.getTechnique();
        ArrayList<String> CAPECList=threat.getCAPEC();
        ArrayList<String> CVEList=threat.getCVE();
        ArrayList<String> CWEList=threat.getCWE();
        ArrayList<String> mitiList=threat.getMitigationList();

        JSONArray CAPECArray = new JSONArray();
        JSONArray CVEArray = new JSONArray();
        JSONArray CWEArray = new JSONArray();
        JSONArray mitiArray = new JSONArray();

        CAPECArray.addAll(CAPECList);
        CVEArray.addAll(CVEList);
        CWEArray.addAll(CWEList);
        mitiArray.addAll(mitiList);

        threatObject.put("id",id);
        threatObject.put("step",step);
        threatObject.put("tactic",tactic);
        threatObject.put("technique",technique);
        threatObject.put("CAPEC",CAPECArray);
        threatObject.put("CVE",CVEArray);
        threatObject.put("CWE",CWEArray);
        threatObject.put("mitigation",mitiArray);

        return threatObject;
    }

    public static void loadDataFromJSON(String filePath){
        JSONFunc jsonFunc=new JSONFunc();
        jsonFunc.loadFromFile(filePath);
        JSONObject jsonObject=(JSONObject)jsonFunc.getJSONAware();
        
        String orgName=(String)jsonObject.get("organization");
        JSONArray assetArray=(JSONArray)jsonObject.get("asset");
        ArrayList<Asset> assetList= getAssetListFromJSONArray(assetArray);

        ProcessedData.setOrganization(orgName);
        // 조직 선택 후 할 수 있는 행동 추가
        ProcessedData.setThreatAffectedAssets(assetList);
        
    }

    private static ArrayList<Asset> getAssetListFromJSONArray(JSONArray assetArray){
        ArrayList<Asset> assetList=ProcessedData.getThreatAffectedAssets();

        // for(Asset asset:assetList){
        //     assetArray.add(assetToJSON(asset));
        // }

        for(Object obj:assetArray){
            JSONToAsset((JSONObject)obj);
        }

        return assetArray;
    }

    private static void JSONToAsset(JSONObject assetObject){
        String name= (String)assetObject.get("name");
        int type=(int)assetObject.get("type");
        String typeName=(String)assetObject.get("typeName");

        
    }
    

    // public static ArrayList<String> getTactics(Technique technique){
    //     ArrayList<String> matchedList = new ArrayList<String>();

    //     return matchedList;
    // }

    // public static ArrayList<Software> getSWListInTech(Technique technique){
    //     ArrayList<Software> matchedList = new ArrayList<Software>();
    //     ArrayList<String> searchedList = ontologyFunc.LoadTechSW(technique.getName());
    //     for (String searched:searchedList){
    //         for (Software software:softwareList){
    //             if(searched.equals(software.getName())){
    //                 matchedList.add(software);
    //             }

    //         }
    //     }
    //     return matchedList;
    // }
}

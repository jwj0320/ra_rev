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

    public static Asset getAsset(String id){
        for (Asset a: assetList){
            if(a.getId().equals(id)){
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

    // public static void setThreatAffectedAssets(ArrayList<Asset> threatAffectedAssets){
    //     for(Asset as:threatAffectedAssets){
    //         for(Threat th:as.getThreatList()){
                
    //         }
    //     }
    // }

    public static void saveDataToJSON(String filePath){
        JSONFunc jsonFunc=new JSONFunc();
        JSONObject jsonObject=(JSONObject)jsonFunc.getJSONAware();
        JSONArray assetArray = getJSONArrayFromAsset();
        JSONArray threatArray = getJSONArrayFromThreat();
        
        String orgName=ProcessedData.getOrganization();

        jsonObject.put("organization",orgName);
        jsonObject.put("asset",assetArray);
        jsonObject.put("threat",threatArray);

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

    private static JSONArray getJSONArrayFromThreat(){
        JSONArray threatArray = new JSONArray();
        ArrayList<Threat> threatList=ProcessedData.getThreatList();

        for(Threat th:threatList){
            threatArray.add(threatToJSON(th));
        }

        return threatArray;
    }

    private static JSONObject assetToJSON(Asset asset){
        JSONObject assetObject=new JSONObject();
        String id=asset.getId();
        String name= asset.getName();
        int type=asset.getType();
        String typeName=asset.getTypeName();
        ArrayList<Evidence> evidenceList= asset.getEvidenceList();
        ArrayList<Threat> threatList= asset.getThreatList();
        JSONArray threatArray=new JSONArray();
        JSONArray evidenceArray=new JSONArray();

        assetObject.put("id",id);
        assetObject.put("name",name);
        assetObject.put("type",type);
        assetObject.put("typeName",typeName);

        for(Threat th:threatList){
            threatArray.add(threatToJSON(th));
        }
        assetObject.put("threat",threatArray);

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
        boolean isEvaluated=evidence.isEvaluated();
        double score= evidence.getScore();
        SecReq sr= evidence.getSr();
        JSONObject srObject=srToJSON(sr);

        evidenceObject.put("id",id);
        evidenceObject.put("content",content);
        evidenceObject.put("isEvaluated",isEvaluated);
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
        initializeVar();

        JSONFunc jsonFunc=new JSONFunc();
        jsonFunc.loadFromFile(filePath);
        JSONObject jsonObject=(JSONObject)jsonFunc.getJSONAware();
        
        String orgName=(String)jsonObject.get("organization");
        JSONArray assetArray=(JSONArray)jsonObject.get("asset");
        // ArrayList<Asset> assetList= setAssetListFromJSONArray(assetArray);

        JSONArray threatArray = (JSONArray)jsonObject.get("threat");
        ArrayList<Threat> threatList= getThreatListFromJSONArray(threatArray);

        ProcessedData.setOrganization(orgName);
        ProcessedData.setAssetListFromOrg(orgName);
        ProcessedData.setThreatList(threatList);
        ProcessedData.setAssetListFromJSONArray(assetArray);
        // ProcessedData.setThreatAffectedAssets(assetList);
        
    }

    private static void setAssetListFromJSONArray(JSONArray assetArray){
        Asset asset=null;
        JSONObject assetObject=null;
        for(Object obj:assetArray){
            assetObject=(JSONObject)obj;
            asset=ProcessedData.getAsset((String)assetObject.get("id"));
            JSONToAsset(asset, assetObject);
            
        }

        return;
    }

    // private static ArrayList<Asset> getAssetListFromJSONArray(JSONArray assetArray){
    //     // ArrayList<Asset> assetList=ProcessedData.getThreatAffectedAssets();
    //     ArrayList<Asset> assetList=new ArrayList<Asset>();

    //     // for(Asset asset:assetList){
    //     //     assetArray.add(assetToJSON(asset));
    //     // }

    //     for(Object obj:assetArray){
    //         assetList.add(JSONToAsset((JSONObject)obj));
    //     }

    //     return assetList;
    // }

    private static ArrayList<Threat> getThreatListFromJSONArray(JSONArray threatArray){
        ArrayList<Threat> threatList=new ArrayList<Threat>();
        
        for(Object obj:threatArray){
            threatList.add(JSONToThreat((JSONObject)obj));
        }

        return threatList;
    }

    private static void setAssetListFromOrg(String orgName){
        ArrayList<String> bps = ontologyFunc.LoadBPFromOrg(orgName);
        ArrayList<Asset> itsList=new ArrayList<Asset>();
        for (String bp : bps) {
            ArrayList<String> roles = ontologyFunc.LoadRoleFromBP(bp);
            for (String role : roles) {
                ArrayList<String> softwares = ontologyFunc.LoadSWFromRole(role);
                for (String software : softwares) {
                    if (itsList.contains(software)) {
                        continue;
                    }
                    itsList.add(new Asset("Software",software));
                    ArrayList<String> datas = ontologyFunc.LoadDataFromSW(software);
                    for (String data : datas) {
                        if (itsList.contains(data)) {
                            continue;
                        }
                        itsList.add(new Asset("Data",data));
                    }
                    ArrayList<String> platforms = ontologyFunc.LoadPlatformFromSW(software);
                    for (String platform : platforms) {
                        if (itsList.contains(platform)) {
                            continue;
                        }
                        itsList.add(new Asset( "Platform", platform ));
                    }
                    ArrayList<String> hardwares = ontologyFunc.LoadHardwareFromSW(software);
                    for (String hardware : hardwares) {
                        if (itsList.contains(hardware)) {
                            continue;
                        }
                        itsList.add(new Asset( "Hardware", hardware ));
                    }
                }
            }
        }
        ProcessedData.setAssetList(itsList);
    }

    private static void JSONToAsset(Asset asset,JSONObject assetObject){
        // String name= (String)assetObject.get("name");
        // int type=(int)assetObject.get("type");
        // String typeName=(String)assetObject.get("typeName");
        // Asset asset = new Asset(typeName,name);
        JSONArray threatArray=(JSONArray)assetObject.get("threat");
        Threat threat=null;
        for(Object obj:threatArray){
            threat=ProcessedData.getThreat((String)((JSONObject)obj).get("id"));
            threat.getAssetList().add(asset);
            asset.getThreatList().add(threat);
        }


        JSONArray evidenceArray=(JSONArray)assetObject.get("evidence");
        Evidence evidence=null;
        for(Object obj:evidenceArray){
            evidence=JSONToEvidence((JSONObject)obj);
            asset.getEvidenceList().add(evidence);
        }

        return;
    }

    // private static Asset JSONToAsset(JSONObject assetObject){
    //     String name= (String)assetObject.get("name");
    //     // int type=(int)assetObject.get("type");
    //     String typeName=(String)assetObject.get("typeName");
    //     Asset asset = new Asset(typeName,name);
    //     JSONArray threatArray=(JSONArray)assetObject.get("threat");
    //     Threat threat=null;
    //     for(Object obj:threatArray){
    //         threat=ProcessedData.getThreat((String)((JSONObject)obj).get("id"));
    //         threat.getAssetList().add(asset);
    //         asset.getThreatList().add(threat);
    //     }


    //     JSONArray evidenceArray=(JSONArray)assetObject.get("evidence");
    //     Evidence evidence=null;
    //     for(Object obj:evidenceArray){
    //         evidence=JSONToEvidence((JSONObject)obj);
    //         asset.getEvidenceList().add(evidence);
    //     }

    //     return asset;
    // }

    private static Evidence JSONToEvidence(JSONObject evidenceObject){
        String id=(String)evidenceObject.get("id");
        String content=(String)evidenceObject.get("content");
        boolean isEvaluated=(boolean)evidenceObject.get("isEvaluated");
        double score=(double)evidenceObject.get("score");
        Evidence evidence = new Evidence(id, content);
        evidence.setEvaluated(isEvaluated);
        evidence.setScore(score);

        JSONObject srObject = (JSONObject)evidenceObject.get("sr");
        SecReq sr=JSONToSR((JSONObject)srObject);
        evidence.setSr(sr);


        return evidence;
        
    }

    private static SecReq JSONToSR(JSONObject srObject){
        String id=(String)srObject.get("id");
        String text=(String)srObject.get("text");
        String type=(String)srObject.get("type");
        SecReq sr=new SecReq(id,text);
        sr.setType(type);

        JSONObject threatObject=(JSONObject)srObject.get("threat");
        // Threat threat=JSONToThreat(threatObject); // threat을 미리 저장한 후에 나중에 찾자
        // sr.setThreat(threat);

        String threatId=(String)threatObject.get("id");
        Threat threat=ProcessedData.getThreat(threatId);
        sr.setThreat(threat);


        return sr;
    }

    private static Threat JSONToThreat(JSONObject threatObject){
        String id=(String)threatObject.get("id");
        long step=(long)threatObject.get("step");
        String technique = (String)threatObject.get("technique");
        String tactic = (String)threatObject.get("tactic");
        Threat threat = new Threat();
        threat.setId(id);
        threat.setStep((int)step);
        threat.setTechnique(technique);
        threat.setTactic(tactic);

        JSONArray CAPECArray=(JSONArray)threatObject.get("CAPEC");
        ArrayList<String> CAPECList=threat.getCAPEC();
        for(Object obj:CAPECArray){
            CAPECList.add((String)obj);
        }

        JSONArray CWEArray=(JSONArray)threatObject.get("CWE");
        ArrayList<String> CWEList=threat.getCWE();
        for(Object obj:CWEArray){
            CWEList.add((String)obj);
        }

        JSONArray CVEArray=(JSONArray)threatObject.get("CVE");
        ArrayList<String> CVEList=threat.getCVE();
        for(Object obj:CVEArray){
            CVEList.add((String)obj);
        }

        JSONArray mitigationArray=(JSONArray)threatObject.get("mitigation");
        ArrayList<String> mitigationList=threat.getMitigationList();
        for(Object obj:mitigationArray){
            mitigationList.add((String)obj);
        }// Threat 중복 문제

        return threat;

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

    public static void initializeVar(){
        organization="";
        threatList = new ArrayList<Threat>();
        assetList = new ArrayList<Asset>();
        srList=new ArrayList<SecReq>();
        step1=new Step();
        step2=new Step();
        step3=new Step();
        step4=new Step();

    }
}

package data;

import java.util.ArrayList;

import api.OntologyFunc;

// import gui.tabbedContent.api.OntologyFunc;

public class ProcessedData {
    private static ArrayList<Threat> threatList = new ArrayList<Threat>();
    private static ArrayList<Asset> assetList = new ArrayList<Asset>();
    private static ArrayList<SecReq> srList=new ArrayList<SecReq>();
    
    private static Step step1=new Step();
    private static Step step2=new Step();
    private static Step step3=new Step();
    private static Step step4=new Step();
    
    public static OntologyFunc ontologyFunc = new OntologyFunc();

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
            if(t.getName().equals(name)){
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

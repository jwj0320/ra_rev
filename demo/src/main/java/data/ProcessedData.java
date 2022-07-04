package data;

import java.util.ArrayList;

// import gui.tabbedContent.api.OntologyFunc;

public class ProcessedData {
    public static ArrayList<Technique> techniqueList=new ArrayList<Technique>();
    public static ArrayList<Software> softwareList=new ArrayList<Software>();
    public static ArrayList<Threat> threatList = new ArrayList<Threat>();
    // private static OntologyFunc ontologyFunc = new OntologyFunc();

    public static boolean containsTechnique(String name){
        for (Technique t:techniqueList){
            if (t.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public static boolean containsSoftware(String name){
        for (Software s:softwareList){
            if (s.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public static Threat getThreat(String name){
        for (Threat t: threatList){
            if(t.getName().equals(name)){
                return t;
            }
        }
        return null;
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

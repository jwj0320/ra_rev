package data;

import java.util.ArrayList;

import javax.swing.JTabbedPane;

public class TabbedPaneInfo extends JTabbedPane{
    private ArrayList<Group> groupList=new ArrayList<>();
    private ArrayList<Software> softwareList=new ArrayList<>();
    private ArrayList<Technique> techniqueList=new ArrayList<>();

    public ArrayList<Group> getGroupList() {
        return groupList;
    }
    public ArrayList<Technique> getTechniqueList() {
        return techniqueList;
    }
    public void setTechniqueList(ArrayList<Technique> techniqueList) {
        this.techniqueList = techniqueList;
    }
    public ArrayList<Software> getSoftwareList() {
        return softwareList;
    }
    public void setSoftwareList(ArrayList<Software> softwareList) {
        this.softwareList = softwareList;
    }
    public void setGroupList(ArrayList<Group> groupList) {
        this.groupList = groupList;
    }

}


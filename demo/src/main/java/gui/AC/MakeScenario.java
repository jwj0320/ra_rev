package gui.AC;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel; // 패널크기 고정으로 지정하자
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.jena.base.Sys;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;

import com.github.jsonldjava.utils.Obj;

import gui.GridBagPanel;
import api.OntologyFunc;
import data.Asset;
import data.ProcessedData;

public class MakeScenario extends JDialog {
    private GridBagPanel dialogPane = new GridBagPanel();
    private OntologyFunc ontologyFunc = ProcessedData.ontologyFunc;
    private JButton nextButton = new JButton("Next");
    private JPanel cardPanel = new JPanel();
    private JTable scenarioTable;

    public MakeScenario(JTable scenarioTable) {
        this.scenarioTable=scenarioTable;
        setTitle("In progress...");
        File imageFile = new File(this.getClass().getResource("").getPath(), "../../../../icon.png");
        try {
            setIconImage(ImageIO.read(imageFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setModal(true);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        // changePane(new Org(), new Org());
        add(dialogPane);

        cardPanel.setLayout(new CardLayout());
        cardPanel.add(new Org());
        cardPanel.add(new Tactic());
        dialogPane.addGBLComponent(cardPanel, 0, 0, 2, 1, 0, 8);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //마지막 카드 판별 필요
                ((CardLayout) (cardPanel.getLayout())).next(cardPanel);
                // nextButton.setEnabled(false); //  알고리즘 생각해 보자
            }
        });

        dialogPane.addGBLComponent(nextButton, 1, 1, 1, 1, 0, 2, "NONE", GridBagConstraints.LINE_END);

        setVisible(true);
    }

    private class Org extends GridBagPanel {

        public Org() {
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")),
                    "Organization"));

            GridBagPanel labelPanel = new GridBagPanel();

            JLabel infoLabel = new JLabel();
            infoLabel.setText("Select an organizaion to assess security risks for an attack.");

            JLabel blankLabel = new JLabel();
            blankLabel.setPreferredSize(new Dimension(10, 10));

            labelPanel.addGBLComponent(infoLabel, 0, 0);
            labelPanel.addGBLComponent(blankLabel, 0, 1);

            addGBLComponent(labelPanel, 0, 0, 5, 1, 0, 0, "NONE", GridBagConstraints.LINE_START);

            JLabel orgLabel = new JLabel();
            orgLabel.setText("Organizaion: ");
            addGBLComponent(orgLabel, 0, 1);

            JComboBox<Object> orgComboBox = new JComboBox<>();
            for (String org : ontologyFunc.LoadAllOrg()) {
                orgComboBox.addItem(org);
            }
            orgComboBox.setSelectedIndex(-1);
            orgComboBox.setPreferredSize(new Dimension(250, 22));

            addGBLComponent(orgComboBox, 1, 1, 4, 1, 0, 0, "NONE", GridBagConstraints.LINE_START);

            JTable bpTable = new JTable(new DefaultTableModel(new String[] { "Business Process" }, 0));
            JScrollPane bpTableScPane = new JScrollPane(bpTable);
            bpTableScPane.setPreferredSize(new Dimension(130, 300));

            JTable humanTable = new JTable(new DefaultTableModel(new String[] { "Role", "Person" }, 0));
            JScrollPane humanTableScPane = new JScrollPane(humanTable);
            humanTableScPane.setPreferredSize(new Dimension(210, 300));

            JTable itsTable = new JTable(new DefaultTableModel(new String[] { "Type", "Content" }, 0));
            JScrollPane itsTableScPane = new JScrollPane(itsTable);
            itsTableScPane.setPreferredSize(new Dimension(210, 300));

            JTable peTable = new JTable(new DefaultTableModel(new String[] { "Type", "Content" }, 0));
            JScrollPane peTableScPane = new JScrollPane(peTable);
            peTableScPane.setPreferredSize(new Dimension(210, 300));

            bpTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting() || bpTable.getSelectedRowCount() == 0) {
                        return;
                    }
                    humanTable.clearSelection();
                    for (String role : ontologyFunc
                            .LoadRoleFromBP(bpTable.getValueAt(bpTable.getSelectedRow(), 0).toString())) {
                        int index = findRow(humanTable, role, 0);
                        humanTable.addRowSelectionInterval(index, index);
                    }
                }
            });

            humanTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting() || humanTable.getSelectedRowCount() == 0) {
                        return;
                    }
                    itsTable.clearSelection();
                    for (String software : ontologyFunc
                            .LoadSWFromRole(humanTable.getValueAt(humanTable.getSelectedRow(), 0).toString())) {
                        int index = findRow(itsTable, software, 1);
                        itsTable.addRowSelectionInterval(index, index);
                    }
                }
            });
            // 수정작업 하자
            // 수정작업 하자
            // 수정작업 하자
            // 수정작업 하자
            // 수정작업 하자
            // 수정작업 하자

            // itsTable.getSelectionModel().addListSelectionListener(new
            // ListSelectionListener(){
            // @Override
            // public void valueChanged(ListSelectionEvent e){
            // if (e.getValueIsAdjusting() || itsTable.getSelectedRowCount()==0){
            // return;
            // }
            // dataTable.clearSelection();
            // for (String data :
            // ontologyFunc2.LoadDataFromSW(softwareTable.getValueAt(roleTable.getSelectedRow(),0).toString()))
            // {
            // int index = findRow(dataTable, data);
            // dataTable.addRowSelectionInterval(index,index);
            // }
            // }
            // });

            addGBLComponent(bpTableScPane, 0, 2, 2, 1);
            addGBLComponent(humanTableScPane, 2, 2);
            addGBLComponent(itsTableScPane, 3, 2);
            addGBLComponent(peTableScPane, 4, 2);

            orgComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ((DefaultTableModel) bpTable.getModel()).setRowCount(0);
                    ArrayList<String> bps = ontologyFunc.LoadBPFromOrg(orgComboBox.getSelectedItem().toString());
                    for (String bp : bps) {
                        ((DefaultTableModel) bpTable.getModel()).addRow(new String[] { bp });
                        ArrayList<String> roles = ontologyFunc.LoadRoleFromBP(bp);
                        for (String role : roles) {
                            ArrayList<String> people = ontologyFunc.LoadPersonFromRole(role);
                            for (String person : people) {

                                ((DefaultTableModel) humanTable.getModel()).addRow(new String[] { role, person });
                            }
                            ArrayList<String> softwares = ontologyFunc.LoadSWFromRole(role);
                            for (String software : softwares) {
                                if (isIn(itsTable, software)) {
                                    continue;
                                }
                                ((DefaultTableModel) itsTable.getModel()).addRow(new String[] { "Software", software });
                                ArrayList<String> datas = ontologyFunc.LoadDataFromSW(software);
                                for (String data : datas) {
                                    if (isIn(itsTable, data)) {
                                        continue;
                                    }
                                    ((DefaultTableModel) itsTable.getModel()).addRow(new String[] { "Data", data });
                                }
                                ArrayList<String> platforms = ontologyFunc.LoadPlatformFromSW(software);
                                for (String platform : platforms) {
                                    if (isIn(itsTable, platform)) {
                                        continue;
                                    }
                                    ((DefaultTableModel) itsTable.getModel())
                                            .addRow(new String[] { "Platform", platform });
                                }
                                ArrayList<String> hardwares = ontologyFunc.LoadHardwareFromSW(software);
                                for (String hardware : hardwares) {
                                    if (isIn(itsTable, hardware)) {
                                        continue;
                                    }
                                    ((DefaultTableModel) itsTable.getModel())
                                            .addRow(new String[] { "Hardware", hardware });
                                    ArrayList<String> das = ontologyFunc.LoadDAFromHW(hardware);
                                    for (String da : das) {
                                        if (isIn(peTable, da)) {
                                            continue;
                                        }
                                        ((DefaultTableModel) peTable.getModel()).addRow(new String[] { "DA", da });
                                    }
                                }
                                ArrayList<String> mds = ontologyFunc.LoadMDFromSW(software);
                                for (String md : mds) {
                                    if (isIn(peTable, md)) {
                                        continue;
                                    }
                                    ((DefaultTableModel) peTable.getModel()).addRow(new String[] { "MD", md });
                                }
                            }
                        }
                    }
                    ArrayList<Asset> assetList=new ArrayList<Asset>();
                    Vector vector =((DefaultTableModel)(itsTable.getModel())).getDataVector();
                    String type="";
                    String name="";
                    for (Object obj:vector){
                        type=((String)((Vector)obj).toArray()[0]);
                        name=((String)((Vector)obj).toArray()[1]);
                        assetList.add(new Asset(type, name));
                    }
                    ProcessedData.setAssetList(assetList);


                }
            });
        }

        private boolean isIn(JTable table, String element) {
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, 1).toString().equals(element)) {
                    return true;
                }
            }
            return false;
        }

        private int findRow(JTable table, String element, int index) {
            for (int i = 0; i < table.getRowCount(); i++) {
                if (table.getValueAt(i, index).toString().equals(element)) {
                    return i;
                }
            }
            System.out.println("Couldnt find " + element);
            return -1;
        }

    }

    private class Tactic extends GridBagPanel {

        public Tactic() {

            setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")),
                    "Tactics"));

            GridBagPanel labelPanel = new GridBagPanel();

            JLabel infoLabel = new JLabel();
            infoLabel.setText("Select tatics.");

            JLabel blankLabel = new JLabel();
            blankLabel.setPreferredSize(new Dimension(10, 10));

            labelPanel.addGBLComponent(infoLabel, 0, 0);
            labelPanel.addGBLComponent(blankLabel, 0, 1);

            addGBLComponent(labelPanel, 0, 0, 5, 1, 0, 0, "NONE", GridBagConstraints.LINE_START);

            JTable step1Table = new JTable(new DefaultTableModel(new String[] { "Step 1" }, 0));
            JScrollPane step1TableScPane = new JScrollPane(step1Table);
            step1TableScPane.setPreferredSize(new Dimension(190, 300));

            JTable step2Table = new JTable(new DefaultTableModel(new String[] { "Step 2" }, 0));
            JScrollPane step2TableScPane = new JScrollPane(step2Table);
            step2TableScPane.setPreferredSize(new Dimension(190, 300));

            JTable step3Table = new JTable(new DefaultTableModel(new String[] { "Step 3" }, 0));
            JScrollPane step3TableScPane = new JScrollPane(step3Table);
            step3TableScPane.setPreferredSize(new Dimension(190, 300));

            JTable step4Table = new JTable(new DefaultTableModel(new String[] { "Step 4" }, 0));
            JScrollPane step4TableScPane = new JScrollPane(step4Table);
            step4TableScPane.setPreferredSize(new Dimension(190, 300));

            addGBLComponent(step1TableScPane, 0, 2, 2, 1);
            addGBLComponent(step2TableScPane, 2, 2);
            addGBLComponent(step3TableScPane, 3, 2);
            addGBLComponent(step4TableScPane, 4, 2);

            JButton button1 = new JButton("Add");
            JButton button2 = new JButton("Add");
            JButton button3 = new JButton("Add");
            JButton button4 = new JButton("Add");
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TacticSelection tacticSelection=new TacticSelection(step1Table);
                    tacticSelection.setVisible(true);
                    revalidate();
                }
            });
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TacticSelection tacticSelection=new TacticSelection(step2Table);
                    tacticSelection.setVisible(true);
                    revalidate();
                }
            });
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TacticSelection tacticSelection=new TacticSelection(step3Table);
                    tacticSelection.setVisible(true);
                    revalidate();
                }
            });
            button4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TacticSelection tacticSelection=new TacticSelection(step4Table);
                    tacticSelection.setVisible(true);
                    revalidate();
                }
            });

            JButton applyButton = new JButton("Apply");
            addGBLComponent(applyButton, 4, 4,0,0,"NONE",GridBagConstraints.LINE_END);

            applyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardPanel.add(makeTech(step1Table,1));
                    cardPanel.add(makeTech(step2Table,2));
                    cardPanel.add(makeTech(step3Table,3));
                    cardPanel.add(makeTech(step4Table,4));
                    cardPanel.add(new CompletePanel());
                    nextButton.setEnabled(true);
                    applyButton.setEnabled(false);

                }

                private Tech makeTech(JTable table, int step){
                    Vector tableVector=((DefaultTableModel)(table.getModel())).getDataVector();
                    ArrayList<Object> data=new ArrayList<Object>();
                    for(Object row:tableVector){
                        data.add(((String)((Vector)row).toArray()[0]));
                    }

                    return new Tech(data.toArray(),step);
                }
            });
            

            addGBLComponent(button1, 0, 3, 2, 1, "BOTH");
            addGBLComponent(button2, 2, 3, 1, 1, "BOTH");
            addGBLComponent(button3, 3, 3, 1, 1, "BOTH");
            addGBLComponent(button4, 4, 3, 1, 1, "BOTH");

            // changeButtonListener(this, new Tech());
        }

        private class TacticSelection extends JDialog {
            private JTable tacticTable = new JTable(new DefaultTableModel(new String[] { "V", "Tactics" }, 0){
                public java.lang.Class<?> getColumnClass(int columnIndex) {
                    switch (columnIndex) {
                        case 0:
                            return Boolean.class;
                        default:
                            return String.class;
                    }
                };
            });
            private JPanel searchLayout;

            private JTextField searchField;
            private JLabel searchLabel;

            private JButton confirmButton;

            public TacticSelection(JTable table) {
                setModal(true);
                setSize(400, 300);
                setResizable(false);
                setLocationRelativeTo(null);
                setLayout(new BorderLayout());

                tacticTable.getColumnModel().getColumn(0).setPreferredWidth(10);
                tacticTable.getColumnModel().getColumn(1).setPreferredWidth(350);

                searchField = new JTextField();
                searchField.setColumns(50);

                searchLabel = new JLabel("Search: ");

                searchLayout = new JPanel();
                searchLayout.setLayout(new BorderLayout());
                searchLayout.add(searchLabel, BorderLayout.WEST);
                searchLayout.add(searchField, BorderLayout.CENTER);

                add(searchLayout, BorderLayout.NORTH);

                ArrayList<String[]> tacticList = ontologyFunc.LoadAllTactics();

                Vector tableVector=((DefaultTableModel)(table.getModel())).getDataVector();
                Object[] rowData=tableVector.toArray();
                boolean b=false;
                for (String[] tactic : tacticList) {
                    if (rowData.length!=0){
                        for(Object row:tableVector){
                            if(((String)((Vector)row).toArray()[0]).equals(tactic[0]))
                                b=true;
                        }
                    }
                    if(b){
                        ((DefaultTableModel) (tacticTable.getModel())).addRow(new Object[] { true, tactic[0] });
                    }
                    else{
                        ((DefaultTableModel) (tacticTable.getModel())).addRow(new Object[] { false, tactic[0] });
                    }
                    b=false;
                }

                tacticTable.setDefaultEditor(Object.class, null);
                tacticTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tacticTable.getModel());
                tacticTable.setRowSorter(sorter);

                searchField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
                    }

                });

                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JScrollPane scrollPane = new JScrollPane(tacticTable);
                scrollPane.setPreferredSize(new Dimension(150, 180));
                ;

                confirmButton = new JButton("Confirm");
                confirmButton.setEnabled(false);

                tacticTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (tacticTable.getSelectedRow() != -1)
                            confirmButton.setEnabled(true);
                        else
                            confirmButton.setEnabled(false);
                    }
                });

                JPanel tempPanel = new JPanel();

                tempPanel.setLayout(new BorderLayout());
                // tempPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                tempPanel.add(scrollPane, BorderLayout.NORTH);
                tempPanel.add(confirmButton, BorderLayout.CENTER);
                add(tempPanel, BorderLayout.CENTER);

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        ArrayList<Object> selectedData=new ArrayList<Object>();
                        ((DefaultTableModel)(table.getModel())).setRowCount(0);
                        for(Object vector:((DefaultTableModel)(tacticTable.getModel())).getDataVector())
                        {
                            Object[] rowData=((Vector)vector).toArray();
                            if ((Boolean)rowData[0]==true){
                                ((DefaultTableModel)(table.getModel())).addRow(new Object[]{rowData[1]});
                                selectedData.add(rowData[1]);
                            }
                        }
                        
                        dispose();
                    }
                });
            }

        }
    }

    private class Tech extends GridBagPanel {

        private ArrayList<TechData> techDataList=new ArrayList<TechData>();

        private class TechData {
            private String tacticName;
            private ArrayList<String> dataList=new ArrayList<String>();
            public TechData(String tacticName){
                this.tacticName=tacticName;
            }

            public String gettacticName(){
                return tacticName;
            }

            public ArrayList<String> getDataList(){
                return dataList;
            }
        }

        private ArrayList<String> findDataList(String tacticName){
            for (TechData td:techDataList){
                if(td.gettacticName().equals(tacticName)){
                    return td.getDataList();
                }
            }

            return null;
        }

        public Tech(Object[] selectedData,int step) {
            setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(10, 10, 10, 10),
                            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#005BAC")),
                                    "Tactics Step "+step+".")));

            
            JTable tacticTable = new JTable(new DefaultTableModel(new String[] { "Tactics" }, 0));
            for (Object o:selectedData){
                ((DefaultTableModel)(tacticTable.getModel())).addRow(new Object[]{o});
                techDataList.add(new TechData((String)o));
            }
            // nameTable.setTableHeader(null);

            JScrollPane tacticTabSc = new JScrollPane(tacticTable);

            tacticTabSc.setPreferredSize(new Dimension(120, 470));
            addGBLComponent(tacticTabSc, 0, 1, 1,1);

            GridBagPanel detailPane = new GridBagPanel();
            JLabel label2 = makeHeader("Selected Techniques");
            JTable techTable = makeContentTable();
            JScrollPane techSc = new JScrollPane(techTable);
            techSc.setPreferredSize(new Dimension(270, 340));
            
            JButton addButton = new JButton("Add ..");
            detailPane.addGBLComponent(addButton, 0, 2,0,0,"BOTH");
            
            detailPane.addGBLComponent(label2, 0, 0, 1, 1, "BOTH");
            detailPane.addGBLComponent(techSc, 0, 1, 1, 1, "BOTH");
            
            addGBLComponent(detailPane, 2, 1, 1,1, "BOTH");

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (TechData td:techDataList){
                        for (String tech:td.getDataList()){
                            ((DefaultTableModel)(scenarioTable.getModel())).addRow(new String[]{""+step,td.gettacticName(),tech});
                        }
                    }
                }
            });
            addGBLComponent(saveButton, 2, 2);

            tacticTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    String tacticString = tacticTable.getValueAt(tacticTable.getSelectedRow(), 0).toString();
                    ArrayList<String> techList=findDataList(tacticString);
                    String[] techs = new String[techList.size()];
                    for (int i = 0; i < techList.size(); i++) {
                    techs[i] = techList.get(i) ;
                    }
                    ((DefaultTableModel) techTable.getModel()).setRowCount(0);
                    for (String row : techs) {
                        ((DefaultTableModel) techTable.getModel()).addRow(new String[]{row});
                    }

                    for (ActionListener al : addButton.getActionListeners()) {
                        addButton.removeActionListener(al);
                    }

                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            TechSelection tacticSelection=new TechSelection(techTable, techList);
                            tacticSelection.setVisible(true);
                            revalidate();
                        }
                    });

                   

                }
            });

          

        }

        private class TechSelection extends JDialog {
            private JTable tacticTable = new JTable(new DefaultTableModel(new String[] { "V", "Tactics" }, 0){
                public java.lang.Class<?> getColumnClass(int columnIndex) {
                    switch (columnIndex) {
                        case 0:
                            return Boolean.class;
                        default:
                            return String.class;
                    }
                };
            });
            private JPanel searchLayout;

            private JTextField searchField;
            private JLabel searchLabel;

            private JButton confirmButton;

            public TechSelection(JTable table, ArrayList<String> techList) {
                setModal(true);
                setSize(400, 300);
                setResizable(false);
                setLocationRelativeTo(null);
                setLayout(new BorderLayout());

                tacticTable.getColumnModel().getColumn(0).setPreferredWidth(10);
                tacticTable.getColumnModel().getColumn(1).setPreferredWidth(350);

                searchField = new JTextField();
                searchField.setColumns(50);

                searchLabel = new JLabel("Search: ");

                searchLayout = new JPanel();
                searchLayout.setLayout(new BorderLayout());
                searchLayout.add(searchLabel, BorderLayout.WEST);
                searchLayout.add(searchField, BorderLayout.CENTER);

                add(searchLayout, BorderLayout.NORTH);

                ArrayList<String[]> tacticList = ontologyFunc.LoadAllTech();

                Vector tableVector=((DefaultTableModel)(table.getModel())).getDataVector();
                Object[] rowData=tableVector.toArray();
                boolean b=false;
                for (String[] tactic : tacticList) {
                    if (rowData.length!=0){
                        for(Object row:tableVector){
                            if(((String)((Vector)row).toArray()[0]).equals(tactic[0]))
                                b=true;
                        }
                    }
                    if(b){
                        ((DefaultTableModel) (tacticTable.getModel())).addRow(new Object[] { true, tactic[0] });
                    }
                    else{
                        ((DefaultTableModel) (tacticTable.getModel())).addRow(new Object[] { false, tactic[0] });
                    }
                    b=false;
                }

                tacticTable.setDefaultEditor(Object.class, null);
                tacticTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tacticTable.getModel());
                tacticTable.setRowSorter(sorter);

                searchField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
                    }

                });

                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JScrollPane scrollPane = new JScrollPane(tacticTable);
                scrollPane.setPreferredSize(new Dimension(150, 180));
                ;

                confirmButton = new JButton("Confirm");
                confirmButton.setEnabled(false);

                tacticTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (tacticTable.getSelectedRow() != -1)
                            confirmButton.setEnabled(true);
                        else
                            confirmButton.setEnabled(false);
                    }
                });

                JPanel tempPanel = new JPanel();

                tempPanel.setLayout(new BorderLayout());
                // tempPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                tempPanel.add(scrollPane, BorderLayout.NORTH);
                tempPanel.add(confirmButton, BorderLayout.CENTER);
                add(tempPanel, BorderLayout.CENTER);

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        techList.clear();
                        ArrayList<Object> selectedData=new ArrayList<Object>();
                        ((DefaultTableModel)(table.getModel())).setRowCount(0);
                        for(Object vector:((DefaultTableModel)(tacticTable.getModel())).getDataVector())
                        {
                            Object[] rowData=((Vector)vector).toArray();
                            if ((Boolean)rowData[0]==true){
                                ((DefaultTableModel)(table.getModel())).addRow(new Object[]{rowData[1]});
                                selectedData.add(rowData[1]);
                                techList.add((String)rowData[1]);
                            }
                        }
                        
                        dispose();
                    }
                });
            }

        }
    }

    private class CompletePanel extends GridBagPanel{
        public CompletePanel(){
            JLabel label = new JLabel("Completed!");
            // for (ActionListener al: nextButton.getActionListeners()){
            //     nextButton.removeActionListener(al);
            // }
            // nextButton.addActionListener(new ActionListener(){
            //     @Override
            //     public void actionPerformed(ActionEvent e) {
            //         // TODO Auto-generated method stub
            //         dispose();
            //     }
            // });
        }
    }
}

package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;


public class RadarChart{
    public RadarChart() {

    }
    public RadarChart(String title) {
        // CategoryDataset dataset = createDataset();
        ArrayList<ChartData> rawDataList=new ArrayList<ChartData>();
        rawDataList.add(new ChartData("太郎타로","優しさ부드러움",9.0));
        rawDataList.add(new ChartData("太郎타로","強さ힘",4.0));
        rawDataList.add(new ChartData("太郎타로","賢さ영리",3.0));
        rawDataList.add(new ChartData("太郎타로","ユーモア유머",1.0));
        rawDataList.add(new ChartData("太郎타로","かっこよさ멋있음",5.0));
        CategoryDataset dataset = makeDataset(rawDataList);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        
    }

    public static CategoryDataset makeDataset(Collection<ChartData> rawDataList){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(ChartData rawData:rawDataList){
            dataset.addValue(rawData.getValue(), rawData.getType(), rawData.getCategory());
        }
        return dataset;
    }

    

    public static JFreeChart createChart(CategoryDataset dataset) {
        SpiderWebPlot plot = new SpiderWebPlot(dataset);
        plot.setStartAngle(3);
        plot.setInteriorGap(0.40);
        plot.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        JFreeChart chart = new JFreeChart("人気者比較인기인비교",
        TextTitle.DEFAULT_FONT, plot, false);
        LegendTitle legend = new LegendTitle(plot);
        legend.setPosition(RectangleEdge.BOTTOM);
        chart.addSubtitle(legend);  
        return chart;  
    }
}

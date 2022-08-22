package api;

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

    public static CategoryDataset makeDataset(Collection<RadarChartData> rawDataList){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(RadarChartData rawData:rawDataList){
            dataset.addValue(rawData.getValue(), rawData.getType(), rawData.getCategory());
        }
        return dataset;
    }

    

    public static JFreeChart createChart(String title, CategoryDataset dataset) {
        SpiderWebPlot plot = new SpiderWebPlot(dataset);
        plot.setStartAngle(3);
        plot.setInteriorGap(0.40);
        plot.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        JFreeChart chart = new JFreeChart(title,
            TextTitle.DEFAULT_FONT, plot, false);
        LegendTitle legend = new LegendTitle(plot);
        legend.setPosition(RectangleEdge.BOTTOM);
        chart.addSubtitle(legend);  
        return chart;  
    }
}

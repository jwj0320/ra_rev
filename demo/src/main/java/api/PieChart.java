package api;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
 
public class PieChart {
   
   // public PieChart( String title , PieDataset dataset) {
      
   //    setContentPane(createDemoPanel(dataset));
   // }
   
   // private static PieDataset createDataset( ) {
   //    DefaultPieDataset dataset = new DefaultPieDataset( );
   //    dataset.setValue( "IPhone 5s" , new Double( 20 ) );  
   //    dataset.setValue( "SamSung Grand" , new Double( 20 ) );   
   //    dataset.setValue( "MotoG" , new Double( 40 ) );    
   //    dataset.setValue( "Nokia Lumia" , new Double( 10 ) );  
   //    return dataset;         
   // }
   
   private static JFreeChart createPieChart(String title, PieDataset dataset ) {
      JFreeChart chart = ChartFactory.createPieChart(      
         title,   // chart title 
         dataset,          // data    
         false,             // include legend   
         true, 
         false);

      return chart;
   }
   
   // public static JPanel createDemoPanel(PieDataset dataset) {
   //    JFreeChart chart = createChart(dataset);  
   //    return new ChartPanel( chart ); 
   // }

}
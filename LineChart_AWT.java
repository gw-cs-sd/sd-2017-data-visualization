import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.Drawable;
import org.jfree.ui.RectangleAnchor;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
 
public class LineChart_AWT extends ApplicationFrame
{
   public LineChart_AWT( String applicationTitle , String chartTitle ) 
   {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Years","Number of Schools",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }
   
   public LineChart_AWT(double[] list1, double[] list2, String title, String x, String y) {
	   super(title);
	   JFreeChart lineChart= createChart( list1,  list2,  title,  x,  y);
	   ChartPanel chartPanel = new ChartPanel( lineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      setContentPane( chartPanel );
   }
   
   public LineChart_AWT( String chartTitle, double[] temp, String labelx, String[] points, String labely )
   {
      super(chartTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         labely,labelx,
         createDataset(temp, labelx, points),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset( )
   {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      dataset.addValue( 15 , "schools" , "1970" );
      dataset.addValue( 30 , "schools" , "1980" );
      dataset.addValue( 60 , "schools" ,  "1990" );
      dataset.addValue( 120 , "schools" , "2000" );
      dataset.addValue( 240 , "schools" , "2010" );
      dataset.addValue( 300 , "schools" , "2014" );
      return dataset;
   }
   
   private DefaultCategoryDataset createDataset(double[] temp, String type, String[] points){
	   
	   DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	   
	   for(int x=0; x<temp.length;x++){
		   dataset.addValue(temp[x], type, points[x]);
	   }
	   return dataset;
   }
   
   private JFreeChart createChart(double[] list1, double[] list2, String title, String x, String y) {
       
	   int scorecor = 0;
	   int scoreout = 0;
	   int scoreobs = 0;
	   int scoremed = 0;
	   int scoremea = 0;
	   int scoremod = 0;
	   int scoremin = 0;
	   int scoremax = 0;
	   
       // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart(
           title,      // chart title
           x,                      // x axis label
           y,                      // y axis label
           createDataset(list1, list2),                  // data
           PlotOrientation.VERTICAL,
           true,                     // include legend
           true,                     // tooltips
           false                     // urls
       );

       // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
       chart.setBackgroundPaint(Color.white);

//       final StandardLegend legend = (StandardLegend) chart.getLegend();
 //      legend.setDisplaySeriesShapes(true);
       
       // get a reference to the plot for further customisation...
       final XYPlot plot = chart.getXYPlot();
       plot.setBackgroundPaint(Color.white);
   //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
       plot.setDomainGridlinePaint(Color.white);
       plot.setRangeGridlinePaint(Color.white);
       
       final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
       
       //final SamplingXYLineAndShapeRenderer r = new SamplingXYLineAndShapeRenderer();
       
       renderer.setSeriesLinesVisible(0, false);
       renderer.setSeriesShapesVisible(1, true);
       renderer.setUseOutlinePaint(false);
       plot.setRenderer(renderer); 

       // change the auto tick unit selection to integer units only...
       final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
       rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
       // OPTIONAL CUSTOMISATION COMPLETED.
       
       ReadCSV csv = new ReadCSV();
       double rvalue = csv.Correlation(list1, list2);
       
       System.out.println("R value: " + rvalue);
       
       if(rvalue > 70) {
    	   scorecor = 10;
       }
       
       else if(rvalue > 30 && rvalue < 70) {
    	   scorecor = 5;
       }
       
       else {
    	   scorecor = 1;
       }
       
       double [] summary_x = csv.interquartile(list1);
       double [] summary_y = csv.interquartile(list2);
       
       ArrayList<Double> mildx = csv.findMildOutliers(list1, summary_x[0], summary_x[2], summary_x[1]);
       ArrayList<Double> extremex = csv.findExtremeOutliers(list1, summary_x[0], summary_x[2], summary_x[1]);
       ArrayList<Double> mildy = csv.findMildOutliers(list2, summary_y[0], summary_y[2], summary_y[1]);
       ArrayList<Double> extremey = csv.findExtremeOutliers(list2, summary_y[0], summary_y[2], summary_y[1]);
       
       for(int i=0; i<extremey.size(); i++) {
    	   System.out.println("In extreme y list: " + extremey.get(i));
       }
       
       for(int j=0; j<extremex.size(); j++) {
    	   System.out.println("In extreme x list: " + extremex.get(j));
       }
       
       //outliers a significant factor - outside bother inner fence and outer fence.
       //if(extremex.size() > 0 || extremey.size() > 0) {
    	//   scoreout = 10;
      // }
       
       if(extremey.size() > 0) {
    	   scoreout = 10;
       }
       
       //outliers a moderate factor - outside inner fence but not inside outer fence.
       else if(mildx.size() > 0 || mildy.size() > 0) {
    	   scoreout = 5;
       }
       
       //not a factor - no outliers in data.
       else {
    	   scoreout = 1;
       }
       
       //important insignificant observations.
       if(list1.length <1000|| list2.length <1000) {
    	  scoreobs = 10;
       }
       
       //not a factor.
       else {
    	   scoreobs = 1;
       }
       
       int [] scorearray = new int[3];
       int labelsplaced = 0;
       scorearray[0] = scorecor;
       scorearray[1] = scoreout;
       scorearray[2] = scoreobs;
       
       if(scorearray[0] == 10) {
    	   labelsplaced++;
    	   
    	   XYTextAnnotation annotation = null;
           Font font = new Font("SansSerif", Font.BOLD, 10);
           System.out.println(y); 
           annotation = new XYTextAnnotation("Variables " + x + " and " + y + " appear to be highly correlated", 0, ReadCSV.min(list2));
           annotation.setFont(font); 
           annotation.setTextAnchor(TextAnchor.TOP_LEFT);
           System.out.println("Text anchor: " + annotation.getTextAnchor());
           plot.addAnnotation(annotation);
           
           double ax = list1[list1.length] - list1[0];
           double ay = list2[list2.length] - list2[0];
           
           double a = ay/ax;
           
           int rand = (int) (Math.random() * list1.length);
           
           double b = list2[rand] - (a * list1[rand]);
    	   
           LineFunction2D linefunction2d = new LineFunction2D(a, b);
           
           XYDataset data = DatasetUtilities.sampleFunction2D(linefunction2d, 0D, 300, 100, "Fitted Regression Line");
           
           XYPlot xyplot = chart.getXYPlot();
           xyplot.setDataset(1, data);
           XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(true, false);
           xylineandshaperenderer.setSeriesPaint(0,  Color.YELLOW);
           xyplot.setRenderer(1, xylineandshaperenderer);
    	    
       }
       
       if(scorearray[1] == 10) {
    	   labelsplaced++;

         //  for(int i=0; i<extremex.size(); i++) {
        //	   if(extremex.get(i) != 0) {
        //		   System.out.println("Reached this step!");
        //		   final CircleDrawer cd = new CircleDrawer(Color.red, new BasicStroke(1.0f), null);
        //		   final XYAnnotation outlier = new XYDrawableAnnotation(extremex.get(i), list2[i], 11, 11, cd);
        //		   plot.addAnnotation(outlier); 
        		   //final XYPointerAnnotation pointoutlier = new XYPointerAnnotation("outlier", olist[i], olist1[i], 3 * Math.PI/4.0);
        		   //pointoutlier.setBaseRadius(35.0); 
        		   //pointoutlier.setTipRadius(10.0);
        		   //pointoutlier.setFont(new Font("SansSerif", Font.PLAIN, 9));
        		   //pointoutlier.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
        		   //plot.addAnnotation(pointoutlier); 
        //	   }
         //  } 
           
    	   //int f = 12;
    	   
           for(int i=0; i<extremey.size(); i++) {
        	   if(extremey.get(i) != 0.0) {
        		   System.out.println("Reached this step!");
        		   final CircleDrawer cd = new CircleDrawer(Color.red, new BasicStroke(1.0f), null);
        		   final XYAnnotation outlier = new XYDrawableAnnotation(i, extremey.get(i), 11, 11, cd);
        		   plot.addAnnotation(outlier); 
        		   //f = f + 4;
        		   //System.exit(0);
        		   //final XYPointerAnnotation pointoutlier = new XYPointerAnnotation("outlier", olist[i], olist1[i], 3 * Math.PI/4.0);
        		   //pointoutlier.setBaseRadius(35.0); 
        		   //pointoutlier.setTipRadius(10.0);
        		   //pointoutlier.setFont(new Font("SansSerif", Font.PLAIN, 9));
        		   //pointoutlier.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
        		   //plot.addAnnotation(pointoutlier); 
        	   }
           } 
       }
       
       if(scorearray[2] == 10) {
    	   labelsplaced++;
    	   
    	   XYTextAnnotation annotation2 = null;
           Font font2 = new Font("SansSerif", Font.BOLD, 10);
           System.out.println(y); 
           annotation2 = new XYTextAnnotation("Insignificant number of observations", 0, 0);
           annotation2.setFont(font2); 
           annotation2.setTextAnchor(TextAnchor.BOTTOM_LEFT);
           System.out.println("Text anchor: " + annotation2.getTextAnchor());
           plot.addAnnotation(annotation2);  
       }
       
       boolean hasPlaced = false;
       
       while(labelsplaced != 3) {
    	   int number = (int) (Math.random() * 10 + 1);
    	   
    	   System.out.print("Random number generated: " + number); 
    	   
    	   if((number >= 1 && number <= 4) && hasPlaced == false) {
    		   System.out.println("Random number was: " + number + "Median reached here");
    		   labelsplaced++;
    		   hasPlaced = true;
    		   double med = csv.findMedian(list2);
    		   Marker medmarker = new ValueMarker(med);
    		   medmarker.setPaint(Color.RED);
    		   medmarker.setLabel("Median Value");
    		   medmarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
    		   medmarker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
    		   plot.addRangeMarker(medmarker); 
    	   }
    	   
    	   else if((number >= 5 && number <= 7) && hasPlaced == false) {
    		   System.out.println("Random number was: " + number + "Mean reached here");
    		   labelsplaced++;
    		   hasPlaced = true;
    		   double mean = csv.findMean(list2);
    		   Marker meanmarker = new ValueMarker(mean);
    		   meanmarker.setPaint(Color.RED);
    		   meanmarker.setLabel("Mean Value");
    		   meanmarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
    		   meanmarker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
    		   plot.addRangeMarker(meanmarker); 
    	   }
    	    
    	   else if(number == 8 && hasPlaced == false) {
    		   System.out.println("Random number was: " + number + "Min reached here");
    		   labelsplaced++;
    		   hasPlaced = true;
    		   double mini = csv.min(list2);
    		   Marker minzip = new ValueMarker(mini);
    		   minzip.setPaint(Color.RED);
    		   minzip.setLabel("Lowest Value");
    		   minzip.setLabelAnchor(RectangleAnchor.RIGHT);
    		   minzip.setLabelTextAnchor(TextAnchor.CENTER);
    		   plot.addRangeMarker(minzip);
    	   }
    	   
    	   else if(number == 9 && hasPlaced == false) {
    		   System.out.println("Random number was: " + number + "Max reached here");
    		   labelsplaced++;
    		   hasPlaced = true;
    		   double maxi = csv.max(list2); 
    		   Marker maxzip = new ValueMarker(maxi);
    		   maxzip.setPaint(Color.RED);
    		   maxzip.setLabel("Highest Value");
    		   maxzip.setLabelAnchor(RectangleAnchor.BOTTOM_LEFT);
    		   maxzip.setLabelTextAnchor(TextAnchor.CENTER);
    		   plot.addRangeMarker(maxzip); 
    	   }
    	   
    	   else if(number == 10 && hasPlaced == false) {
    		   System.out.println("Random number was: " + number + "Mode reached here");
    		   labelsplaced++;
    		   hasPlaced = true;
    		   double mod = csv.findMode(list2);
    		   Marker modzip = new ValueMarker(mod);
    		   modzip.setPaint(Color.RED);
    		   modzip.setLabel("Most Common Value");
    		   modzip.setLabelAnchor(RectangleAnchor.BOTTOM_LEFT);
    		   modzip.setLabelTextAnchor(TextAnchor.CENTER);
    		   plot.addRangeMarker(modzip); 
    	   }
       }
       
       /*
       XYTextAnnotation annotation = null;
       Font font = new Font("SansSerif", Font.BOLD, 10);
       System.out.println(y); 
       annotation = new XYTextAnnotation("Variables " + x + " and " + y + " appear to be highly correlated", 0, ReadCSV.min(list2));
       annotation.setFont(font); 
       annotation.setTextAnchor(TextAnchor.TOP_LEFT);
       System.out.println("Text anchor: " + annotation.getTextAnchor());
       plot.addAnnotation(annotation);  
       
       if(list1.length <1000|| list2.length <1000) {
    	   XYTextAnnotation annotation2 = null;
           Font font2 = new Font("SansSerif", Font.BOLD, 10);
           System.out.println(y); 
           annotation2 = new XYTextAnnotation("Insignificant number of observations", 0, 0);
           annotation2.setFont(font2); 
           annotation2.setTextAnchor(TextAnchor.BOTTOM_LEFT);
           System.out.println("Text anchor: " + annotation2.getTextAnchor());
           plot.addAnnotation(annotation2);  
       }
	   
	   double minimum = ReadCSV.min(list1); 
	   
	   System.out.println("Minimum is: " + minimum);
	   
	   int i;
	   
	   for(i=0; i<list1.length; i++) {
		   if(list1[i] == minimum) {
			   System.out.println("About to exit!");
			   System.out.println("Index: " + i);
			   break;
		   }
	   } 
	   
	   System.out.println("Size of list 1: " + list1.length);
	   System.out.println("Size of list 2: " + list2.length);
	   
	   final CircleDrawer cd = new CircleDrawer(Color.GREEN, new BasicStroke(1.0f), null); 
	   final XYAnnotation minvalue = new XYDrawableAnnotation(15, 60000, 11, 11, cd);
	   plot.addAnnotation(minvalue); 
	   
	  // final XYPointerAnnotation minpoint = new XYPointerAnnotation("The minimum value of " + x, minimum, list2[i], 1 * Math.PI/4.0);
	   final XYPointerAnnotation minpoint = new XYPointerAnnotation("Outlier observation in data at Zip Code 15 with a Family Income of $60,000", 15, 60000, 1 * Math.PI/4.0);
	   minpoint.setBaseRadius(35.0); 
	   minpoint.setTipRadius(10.0);
	   minpoint.setFont(new Font("SansSerif", Font.PLAIN, 9));
	   minpoint.setTextAnchor(TextAnchor.TOP_LEFT);
	   plot.addAnnotation(minpoint); 
	   
	   //double sum = 0;
	   
	  // for(int z=0; z<list2.length; z++) {
		 //  sum += list2[z];
		  // System.out.println(z + "observation with a value of: " + list2[z]);
	  // }
	   
	   //System.out.println("The first income observation is: " + list2[16]); 
	   
	   //System.out.println("The sum of the income is: " + sum);
	    
	   //double m = sum/list2.length;
	   //System.out.println("The mean of the income is: " + m);
	   
	   final Marker mean = new ValueMarker(41010.72);
	   mean.setPaint(Color.RED);
	   mean.setLabel("Mean Income");
	   mean.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
	   mean.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
	   plot.addRangeMarker(mean);
       
	   final Marker minzip = new ValueMarker(11);
	   minzip.setPaint(Color.RED);
	   minzip.setLabel("Lowest Income Zip Code");
	   minzip.setLabelAnchor(RectangleAnchor.RIGHT);
	   minzip.setLabelTextAnchor(TextAnchor.CENTER);
	   plot.addDomainMarker(minzip);
	   
	   final Marker maxzip = new ValueMarker(15);
	   maxzip.setPaint(Color.RED);
	   maxzip.setLabel("Highest Income Zip Code");
	   maxzip.setLabelAnchor(RectangleAnchor.BOTTOM_LEFT);
	   maxzip.setLabelTextAnchor(TextAnchor.CENTER);
	   plot.addDomainMarker(maxzip); 
       */
       /*
       ReadCSV c1 = new ReadCSV();
       
       double [] quar = ReadCSV.interquartile(list1); 
       double [] olist = ReadCSV.findOutliers(list1, quar[0], quar[2], quar[1]);
       
       double [] quar1 = ReadCSV.interquartile(list2);
       double [] olist1 = ReadCSV.findOutliers(list2, quar1[0], quar1[2], quar1[1]);
       
       if(olist[0] != 0.0) {
    	   System.out.println("First item in the list" + x +  "is: " + olist[0]);
       }
       
       if(olist1[0] != 0.0) {
    	   System.out.println("First item in the second list" + y +  "is: " + olist1[0]);
       }
        
       for(int i=0; i<olist.length; i++) {
    	   if(olist[i] != 0.0) {
    		   System.out.println("Reached this step!");
    		   final CircleDrawer cd = new CircleDrawer(Color.red, new BasicStroke(1.0f), null);
    		   final XYAnnotation outlier = new XYDrawableAnnotation(326, olist1[i], 11, 11, cd);
    		   plot.addAnnotation(outlier); 
    		   final XYPointerAnnotation pointoutlier = new XYPointerAnnotation("outlier", olist[i], olist1[i], 3 * Math.PI/4.0);
    		   pointoutlier.setBaseRadius(35.0); 
    		   pointoutlier.setTipRadius(10.0);
    		   pointoutlier.setFont(new Font("SansSerif", Font.PLAIN, 9));
    		   pointoutlier.setTextAnchor(TextAnchor.HALF_ASCENT_RIGHT);
    		   plot.addAnnotation(pointoutlier); 
    	   }
       } 
       */  
       
       getFeedback();
       
       return chart;
       
   }
   
   public void getFeedback() {
	   JFrame jf2 = new JFrame("Feedback");
       //JFrame jf3 = new JFrame();
       
       JTextField message = new JTextField(100);
       message.setText("On a scale from 1-5, how accurately does the graph depict the data at hand?");
       
       JOptionPane.showMessageDialog(jf2, "On a scale from 1-5, how accurately does the graph depict the data at hand?");
       
   	//JFrame.setDefaultLookAndFeelDecorated(true);
       //JDialog.setDefaultLookAndFeelDecorated(true);
   	jf2.setSize(600, 300);
   	jf2.setLocation(200, 200);
   	jf2.addWindowListener(new WindowAdapter() { 
   		public void windowClosing(WindowEvent we) { 
   			System.exit(0);
   		} 
   	});
   	
   	JPanel optionPanel2 = new JPanel();
   	final ButtonGroup choicegroup2 = new ButtonGroup();
       JRadioButton radioButton2, radioButton3, radioButton4, radioButton5, radioButton6;
       optionPanel2.add(radioButton2 = new JRadioButton("1"));
       radioButton2.setActionCommand("1 chosen");
       choicegroup2.add(radioButton2);
       optionPanel2.add(radioButton3 = new JRadioButton("2"));
       radioButton3.setActionCommand("2 chosen");
       choicegroup2.add(radioButton3);
       optionPanel2.add(radioButton4 = new JRadioButton("3"));
       radioButton4.setActionCommand("3 chosen");
       choicegroup2.add(radioButton4);
       optionPanel2.add(radioButton5 = new JRadioButton("4"));
       radioButton5.setActionCommand("4 chosen");
       choicegroup2.add(radioButton5);
       optionPanel2.add(radioButton6 = new JRadioButton("5"));
       radioButton6.setActionCommand("5 chosen");
       choicegroup2.add(radioButton6); 
       
       JPanel confirmPanel2 = new JPanel();
       JButton confirmButton2 = new JButton("Confirm");
       confirmPanel2.add(confirmButton2);
       
       Container content2 = jf2.getContentPane();
       content2.setLayout(new GridLayout(5, 1));
       content2.add(optionPanel2);  
       content2.add(confirmPanel2);
       
       confirmButton2.addActionListener(new ActionListener() { 
       	public void actionPerformed(ActionEvent ae) { 
       		
       		String response = choicegroup2.getSelection().getActionCommand(); 
       		System.out.println("You selected " + response); 
   
       	}  
       });
       
       jf2.setVisible(true);
       //jf3.setVisible(true);
   }
   
   private XYDataset createDataset(double[] temp, double[] temp2){
	   
	   XYSeries series1 = new XYSeries("");
	   
	   //for(int x=0; x<temp.length; x++) {
	//	   series1.add(x, temp[x]);
	//   }
	   
	   XYSeries series2 = new XYSeries("Trend line");
	   
	   for(int x=0; x<temp2.length; x++) {
		   System.out.println("Value of this variable: " + temp2[x]);
		   series2.add(temp[x], temp2[x]);
	   } 
	   
	   
/*
	   double x = ReadCSV.findMean(temp);
	   series1.add(x, x);
	   
	   
	   series1.add(0, ReadCSV.findMean(temp));
	   series1.add(1, ReadCSV.difference(temp));
	   series1.add(2, ReadCSV.min(temp));
	   series1.add(3, ReadCSV.max(temp));
	    
	   series2.add(0, ReadCSV.findMean(temp2));
	   series2.add(1, ReadCSV.difference(temp2));
	   series2.add(2, ReadCSV.min(temp2));
	   series2.add(3, ReadCSV.max(temp2));
	   
	   
	   */
       final XYSeriesCollection dataset = new XYSeriesCollection();
       dataset.addSeries(series1);
       dataset.addSeries(series2);
       
	   return dataset;
   }
   
   
   public static void main( String[ ] args ) 
   {
      LineChart_AWT chart = new LineChart_AWT(
      "School Vs Years" ,
      "Numer of Schools vs years");

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
}
//package org.jfree.chart.demo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryAnnotation;
import org.jfree.chart.annotations.CategoryPointerAnnotation;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.annotations.TextAnnotation;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor; 

public class BarChart_AWT extends ApplicationFrame {
   
	public BarChart_AWT(String applicationTitle, String chartTitle) {
		super(applicationTitle);        
      
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Category", "Score", createDataset(), PlotOrientation.VERTICAL, true, true, false); 
      
		ChartPanel chartPanel = new ChartPanel(barChart);        
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));        
		setContentPane(chartPanel); 
	}
   
	public BarChart_AWT(double[] list1, double[] list2, String title, String x, String y) {
		super(title);
		
		JFreeChart barChart= createChart(list1, list2, title, x, y);
		
		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}
	
	public BarChart_AWT(String chartTitle, double[] temp, String info, String labelx, String labely) {
	    super(chartTitle);
	    
	    JFreeChart barChart = ChartFactory.createLineChart(chartTitle, labely, labelx, createDataset(temp, labelx, info), PlotOrientation.VERTICAL, true, true, false);
	         
	    ChartPanel chartPanel = new ChartPanel(barChart);
	    chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
	    setContentPane(chartPanel);
	}
	
	private CategoryDataset createDataset() {
		
		final String fiat = "FIAT";        
		final String audi = "AUDI";        
		final String ford = "FORD";        
		final String speed = "Speed";        
		final String mileage = "Mileage";        
		final String userrating = "User Rating";        
		final String safety = "safety";        
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();  

		dataset.addValue(1.0, fiat, speed);        
		dataset.addValue(3.0, fiat, userrating);        
		dataset.addValue(5.0, fiat, mileage); 
		dataset.addValue(5.0, fiat, safety);           
		dataset.addValue(5.0, audi, speed);        
		dataset.addValue(6.0, audi, userrating);       
		dataset.addValue(10.0, audi, mileage);        
		dataset.addValue(4.0, audi, safety);
		dataset.addValue(4.0, ford, speed);        
		dataset.addValue(2.0, ford, userrating);        
		dataset.addValue(3.0, ford, mileage);        
		dataset.addValue(6.0, ford, safety);               

		return dataset; 
	}
	
	private DefaultCategoryDataset createDataset(double[] temp, String type, String info) {
		   
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		   
		for(int x=0; x<temp.length; x++) {
			dataset.addValue(temp[x], type, info);
		}
		   
		return dataset; 
	}
	
	private DefaultCategoryDataset createDataset(double[] temp2, String series, String [] newarray) {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		for(int x=0; x<temp2.length; x++) {
			
			//String str = Double.toString(temp[x]);
			System.out.println("Value of this variable bar: " + temp2[x]);
			dataset.addValue(temp2[x], series, newarray[x]);
		} 
		
		return dataset;
	}
	
	/*
	private CategoryDataset createDataset(double[] temp, double[] temp2){
		   
		   XYSeries series1 = new XYSeries("First");
		   
		   for(int x=0; x<temp.length; x+=100){
			   
			   series1.add(x, temp[x]);
		   }
		   
		   XYSeries series2 = new XYSeries("Second");
		   
		   for(int x=0; x<temp2.length; x+=100){
			   series2.add(x, temp2[x]);
		   }
		   
	*/	   
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
	//     final XYSeriesCollection dataset = new XYSeriesCollection();
	//     dataset.addSeries(series1);
	//     dataset.addSeries(series2);
	       
	//	   return (CategoryDataset) dataset;
	// }
	
	private JFreeChart createChart(double[] list1, double[] list2, String title, String x, String y) {
	    
	//	for(int j=0; j<list1.length; j++) {
	//		System.out.println(list1[j]);
	//	}
		
		double scoreske = 0;
		double scoreobs = 0;
	    double scoremed = 0;
		double scoremea = 0;
		double scoremod = 0;
		double scoremin = 0;
		double scoremax = 0;
		
		String [] newarray = new String[list1.length];
		
		for(int i=0; i<list1.length; i++) {
			String str = Double.toString(list1[i]);
			newarray[i] = str;
	//		System.out.println(newarray[i]);
		}
		 
		//create the chart
	    JFreeChart chart = ChartFactory.createBarChart(title, x, y, createDataset(list2, "Income", newarray), PlotOrientation.VERTICAL, true, true, false);

	    //customized options.
	    chart.setBackgroundPaint(Color.white);
	     
	    //references to barchart.
	    final CategoryPlot plot = chart.getCategoryPlot();
	    plot.setBackgroundPaint(Color.lightGray);
	    plot.setDomainGridlinePaint(Color.white);
	    plot.setRangeGridlinePaint(Color.white);
	    
	    //range of axis constraints.
	    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	       
	    //create bar borders.
	    final BarRenderer renderer = (BarRenderer) plot.getRenderer();
	    renderer.setDrawBarOutline(false);

	    //gradient painting
	    final GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f, 0.0f, Color.lightGray);
	    final GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, Color.lightGray);  
	    final GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f, 0.0f, Color.lightGray);
	    
	    renderer.setSeriesPaint(0, gp0);
	    renderer.setSeriesPaint(1, gp1);
	    renderer.setSeriesPaint(2, gp2);
	    
	    final CategoryAxis domainAxis = plot.getDomainAxis();
	    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI/6.0));
	    
	    ReadCSV csv = new ReadCSV();
	    
	    double calcmean = csv.findMean(list2);
	    double calcmed = csv.findMedian(list2);
	    
	    double ssknum = 3 * (calcmean - calcmed);
	    
	    double samplessd = csv.findDeviation(list2);
	    
	    double skew = ssknum/samplessd;
	    
	    skew = Math.floor(skew);
	    
	    System.out.println("Value of skew: " + skew);
	    
	    if(skew == 0) {
	    	scoreske = 5;
	    	 
	    	CategoryTextAnnotation annotation = null;
	        Font font = new Font("SansSerif", Font.BOLD, 10);
	        System.out.println(y); 
	        annotation = new CategoryTextAnnotation("Variables " + x + " and " + y + " appear to be relatively symmetric", 0, ReadCSV.min(list2));
	        annotation.setFont(font); 
	        annotation.setTextAnchor(TextAnchor.TOP_LEFT);
	        System.out.println("Text anchor: " + annotation.getTextAnchor());
	        plot.addAnnotation(annotation);
	    }
	    
	    else if(skew > 0) {
	    	scoreske = 10;
	    	
	    	CategoryTextAnnotation annotation = null;
	        Font font = new Font("SansSerif", Font.BOLD, 10);
	        System.out.println(y); 
	        annotation = new CategoryTextAnnotation("The distribution between variables " + x + " and " + y + " appears to be skewed right", 0, ReadCSV.max(list2));
	        annotation.setFont(font); 
	        annotation.setTextAnchor(TextAnchor.TOP_LEFT);
	        System.out.println("Text anchor: " + annotation.getTextAnchor());
	        plot.addAnnotation(annotation);
	    }
	    
	    else if(skew < 0) {
	    	scoreske = 10;
	    	
	    	CategoryTextAnnotation annotation = null;
	        Font font = new Font("SansSerif", Font.BOLD, 10);
	        System.out.println(y); 
	        annotation = new CategoryTextAnnotation("The distribution between variables " + x + " and " + y + " appears to be skewed left", 0, ReadCSV.min(list2));
	        annotation.setFont(font); 
	        annotation.setTextAnchor(TextAnchor.TOP_LEFT);
	        System.out.println("Text anchor: " + annotation.getTextAnchor());
	        plot.addAnnotation(annotation);
	    } 
	    
	    //important insignificant observations.
	    if(list1.length <1000 || list2.length <1000) {
	    	scoreobs = 10;
	    	
	    	CategoryTextAnnotation annotation2 = null;
	        Font font2 = new Font("SansSerif", Font.BOLD, 10);
	        System.out.println(y); 
	        annotation2 = new CategoryTextAnnotation("Insignificant number of observations", 0, 0);
	        annotation2.setFont(font2); 
	        annotation2.setTextAnchor(TextAnchor.BOTTOM_LEFT);
	        System.out.println("Text anchor: " + annotation2.getTextAnchor());
	        plot.addAnnotation(annotation2);  
	    }
	        
	    //not a factor.
	    else {
	    	scoreobs = 1;
	    }
	    
	    int number = (int) (Math.random() * 10 + 1);
	    	   
	    if(number >= 1 && number <= 4) {
	    	double med = csv.findMedian(list2);
	    	final Marker medmarker = new ValueMarker(med);
   		   	medmarker.setPaint(Color.RED);
   		   	medmarker.setLabel("Median value");
   		   	medmarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
   		   	medmarker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
   		   	plot.addRangeMarker(medmarker); 
	    }
	    	   
	    else if(number >= 5 && number <= 7) {
	    	double mean = csv.findMean(list2);
	    	final Marker meanmarker = new ValueMarker(mean);
    		meanmarker.setPaint(Color.RED);
    		meanmarker.setLabel("Mean value");
    		meanmarker.setLabelAnchor(RectangleAnchor.BOTTOM_RIGHT);
    		meanmarker.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
    		plot.addRangeMarker(meanmarker);
	    }
	    	   
	    else if(number == 8) {
	    	double mini = csv.min(list2);
	    	final Marker minzip = new ValueMarker(mini);
   		   	minzip.setPaint(Color.RED);
   		   	minzip.setLabel("Lowest value");
   		   	minzip.setLabelAnchor(RectangleAnchor.RIGHT);
   		   	minzip.setLabelTextAnchor(TextAnchor.CENTER);
   		   	plot.addRangeMarker(minzip);
	    }
	    	   
	    else if(number == 9) {
	    	double maxi = csv.max(list2);
	    	final Marker maxzip = new ValueMarker(maxi);
   		   	maxzip.setPaint(Color.RED);
   		   	maxzip.setLabel("Highest value");
   		   	maxzip.setLabelAnchor(RectangleAnchor.BOTTOM_LEFT);
   		   	maxzip.setLabelTextAnchor(TextAnchor.CENTER);
   		   	plot.addRangeMarker(maxzip); 
	    }
	    	   
	    else if(number == 10) {
	    	double mod = csv.findMode(list2);
	    	final Marker modzip = new ValueMarker(mod);
 		   	modzip.setPaint(Color.RED);
 		   	modzip.setLabel("Most common value");
 		   	modzip.setLabelAnchor(RectangleAnchor.BOTTOM_LEFT);
 		   	modzip.setLabelTextAnchor(TextAnchor.CENTER);
 		    plot.addRangeMarker(modzip); 
	    }
	    
	    /*
	    for(int i=0; i<list2.length; i++) { 
	    	if(list2[i] == 60000) {
 				final CategoryPlot plot1 = chart.getCategoryPlot();
 				plot.setBackgroundPaint(Color.lightGray);
 				plot.setDomainGridlinePaint(Color.white);
 				plot.setRangeGridlinePaint(Color.white);
 			    final BarRenderer renderer1 = (BarRenderer) plot.getRenderer();
 			    renderer.setDrawBarOutline(false);
 			    final GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, Color.lightGray); 
 			    renderer.setSeriesPaint(1, gp1);
 			} 
	    }
	    */
	    
	    /*
	    CategoryAnnotation annotation = null;
	    Font font = new Font("SansSerif", Font.BOLD, 10);
	    System.out.println(y); 
	    annotation = new CategoryTextAnnotation("Variables " + x + " and " + y + " appear to be normally distributed", 0, ReadCSV.min(list2));
	    ((CategoryTextAnnotation) annotation).setFont(font);  
	    ((CategoryTextAnnotation) annotation).setTextAnchor(TextAnchor.TOP_LEFT);
	    System.out.println("Text anchor: " + ((CategoryTextAnnotation) annotation).getTextAnchor());
	    plot.addAnnotation(annotation);  
	        
	    if(list1.length <1000|| list2.length <1000) {
	    	CategoryTextAnnotation annotation2 = null;
	        Font font2 = new Font("SansSerif", Font.BOLD, 10);
	        System.out.println(y); 
	        annotation2 = new CategoryTextAnnotation("Insignificant number of observations", 0, 0);
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
		   
		  // final CircleDrawer cd = new CircleDrawer(Color.GREEN, new BasicStroke(1.0f), null); 
		  // final CategoryAnnotation maxvalue = new CategoryPointerAnnotation("Maximum value", "JDK 1.2", 60000, 180);
		  // plot.addAnnotation(maxvalue);
	    
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
	    */   
		//   final Marker minzip = new ValueMarker(11);
		//   minzip.setPaint(Color.RED);
		//   minzip.setLabel("Lowest Income Zip Code");
		//   minzip.setLabelAnchor(RectangleAnchor.RIGHT);
		//   minzip.setLabelTextAnchor(TextAnchor.CENTER);
		//   plot.addDomainMarker((CategoryMarker) minzip);
		   
		//   final Marker maxzip = new ValueMarker(15);
		//   maxzip.setPaint(Color.RED);
		//   maxzip.setLabel("Highest Income Zip Code");
		//   maxzip.setLabelAnchor(RectangleAnchor.BOTTOM_LEFT);
		//   maxzip.setLabelTextAnchor(TextAnchor.CENTER);
		//   plot.addDomainMarker((CategoryMarker) maxzip); 
	    
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
	
   public static void main(String [] args) {
	   
      BarChart_AWT chart = new BarChart_AWT("Car Usage Statistics", "Which car do you like?");
      
      chart.pack();        
      RefineryUtilities.centerFrameOnScreen(chart);        
      chart.setVisible(true); 
   }
}
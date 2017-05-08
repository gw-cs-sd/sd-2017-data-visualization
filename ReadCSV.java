
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.ui.RefineryUtilities;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.text.*;



public class ReadCSV {
	
	static double graphNum =95;
	static boolean meanchoice = false;
    static boolean medianchoice = false;
    static boolean modechoice = false;
    static boolean rangechoice = false;
    static boolean minchoice = false;
    static boolean maxchoice = false;
    static boolean iqrchoice = false;
    static boolean outlierchoice = false;
    static boolean correlationchoice = false;
    static boolean sdchoice = false;
    static boolean variancechoice = false;

	public static void main(String[] args) {

    	String csvFile = "gfa25.csv";
    	String csvFile2 = "us_state_emplchange_2011-2012.txt";
    	//String csvFile3 = "test-3.csv";
    	 
    	int max=10000;
    	DecimalFormat df = new DecimalFormat("#.###");
    	
    	JFrame jf = new JFrame("Menu");
 		final JFileChooser fc = new JFileChooser();
 		//final File selectedFile;
    	//JFrame.setDefaultLookAndFeelDecorated(true);
        //JDialog.setDefaultLookAndFeelDecorated(true);
    	jf.setSize(600, 300);
    	jf.setLocation(200, 200);
    	jf.addWindowListener(new WindowAdapter() { 
    		public void windowClosing(WindowEvent we) { 
    			System.exit(0);
    		} 
    	});
    	
    	JPanel attachPanel = new JPanel();
        JButton attachButton = new JButton("Select File");
        attachPanel.add(attachButton);
    	
    	JPanel optionPanel = new JPanel();
    	final ButtonGroup choicegroup = new ButtonGroup();
        JRadioButton radioButton;
        optionPanel.add(radioButton = new JRadioButton("Graph"));
        radioButton.setActionCommand("Graph");
        choicegroup.add(radioButton);
         
        final JPanel statPanel = new JPanel();
        statPanel.add(new JCheckBox("Mean"));
        statPanel.add(new JCheckBox("Median"));
        statPanel.add(new JCheckBox("Mode"));
        statPanel.add(new JCheckBox("Range"));
        statPanel.add(new JCheckBox("Min"));
        statPanel.add(new JCheckBox("Max"));
        statPanel.add(new JCheckBox("Interquartile Range"));
        statPanel.add(new JCheckBox("Outliers"));
        statPanel.add(new JCheckBox("Correlation"));
        statPanel.add(new JCheckBox("Standard Deviation"));
        statPanel.add(new JCheckBox("Variance"));
        
        JPanel confirmPanel = new JPanel();
        JButton confirmButton = new JButton("Analyze");
        confirmPanel.add(confirmButton);
        
        Container content = jf.getContentPane();
        content.setLayout(new GridLayout(1, 1));
        content.add(attachPanel);
        content.add(optionPanel);
        content.add(statPanel);
        content.add(confirmPanel);
        
        attachButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		int value = fc.showOpenDialog(null);
        		
        		if (value == JFileChooser.APPROVE_OPTION) {
        			File selectedFile = fc.getSelectedFile();
        			
        			//String response = choicegroup.getSelection().getActionCommand(); 
            		//System.out.println("You selected " + response); 
            		Component[] components = statPanel.getComponents(); 
            		
            		for(int i=0; i<components.length; i++) { 
            			JCheckBox cb = (JCheckBox) components[i]; 
            			
            			if(cb.isSelected()) {
            				//System.out.println("Also selected: " + cb.getText()); 
            				
            				if(cb.getText().equals("Mean")) {
            					//System.out.println("I may never make it to this point.");
                				meanchoice = true; 
                			}
                			
                			if(cb.getText().equals("Median")) {
                				medianchoice = true;
                			}
                			
                			if(cb.getText().equals("Mode")) {
                				modechoice = true;
                			}
                			
                			if(cb.getText().equals("Range")) {
                				rangechoice = true;
                			}
                			
                			if(cb.getText().equals("Min")) {
                				minchoice = true;
                			}
                			
                			if(cb.getText().equals("Max")) {
                				maxchoice = true;
                			}
                			
                			if(cb.getText().equals("Interquartile Range")) {
                				iqrchoice = true;
                			}
                			
                			if(cb.getText().equals("Outliers")) {
                				outlierchoice = true;
                			}
                			
                			if(cb.getText().equals("Correlation")) {
                				correlationchoice = true;
                			}
                			
                			if(cb.getText().equals("Standard Deviation")) {
                				sdchoice = true;
                			}
                			
                			if(cb.getText().equals("Variance")) {
                				variancechoice = true;
                			}
            			}
            		} 
            		
        			try {
						convert(selectedFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
          		 
              		System.out.println(selectedFile.getName());
              	
                } 
        	} 
        });
        
        confirmButton.addActionListener(new ActionListener() { 
        	public void actionPerformed(ActionEvent ae) { 
        		
        	}  
        }); 
         
        jf.setVisible(true);
        
//    	for (int x = 0; x < list2.length; x++) {
//			System.out.println(Arrays.toString(list2[x]));
//    	}
        
        //File csvFile3 = fc.getSelectedFile();
        
    	//char[] type1 = checkType(list1);
    	//char[] type2 = checkType(list2);
    	
    	//double[][] dList1 = to2dDouble(list1, type1);
    	//double[][] dList2 = to2dDouble(list2, type2);
    	/*
    	char[] type3 = checkType(list3);
    	double[][] dList3 = to2dDouble(list3, type3);
    	String[] names3 = nameList(list3, type3);
    	corAnalysis(dList3, names3);
    	
    	graphNum =63;
    	
    	//String[] names1 = nameList(list1, type1);
    	//String[] names2 = nameList(list2, type2);
    	
    	barLine(dList3, names3);
    	
    	pieModel(dList3, names3);
    	
    	String title = names3[0] + " Vs " + names3[1];
    	//String title = names1[0] + " Vs " + names1[1];
    	
    	//LineChart_AWT chart1 = new LineChart_AWT(dList3[0], dList3[1], title, names3[0], names3[1]);
		//chart1.pack( );
		//RefineryUtilities.centerFrameOnScreen( chart1 );
		//chart1.setVisible( true ); 
    	
    	for(int x=0; x<dList3.length-1; x++) {
    		for(int y=x+1; y<dList3.length; y++) {
    			LineChart_AWT chart1 = new LineChart_AWT(dList3[x], dList3[y], title, names3[x], names3[y]);
    			chart1.pack( );
    			RefineryUtilities.centerFrameOnScreen( chart1 );
    			chart1.setVisible( true );
    		}
    	}
    	*/
    	//corAnalysis(dList1, names1);
    	//corAnalysis(dList2, names2);
    	//corAnalysis(dList1, dList2, names1, names2);
    	 
//    	double[] one = arrayToDouble(list, 1, 5);
//    	//System.out.println(Arrays.toString(one));
//    	double[] two = arrayToDouble(list2, 1, 3);
//    	String[] te = list[4];
//    	Arrays.sort(te);
////    	System.out.println("first " + te[te.length-1]);
////    	double[] zero = arrayToDouble(list, 1, 0);
//    	System.out.println("The mean of "+ list[5][0]  +" is " + df.format(findMean(one)));
//    	System.out.println("The mean of" + list2[3][0] +" is " + df.format(findMean(two)));
//    	System.out.println("Correlation between " + list2[3][0] +" and "+ list[5][0]  +" is %" + df.format(Correlation(one, two)*100));
//    	
//    	LineChart_AWT chart1 = new LineChart_AWT( "Amount vs Count", one, list[5][0], numWords(max), "count");
//    	LineChart_AWT chart2 = new LineChart_AWT( "INIT_ESTB vs Count", two, list2[3][0], numWords(max), "count");
//        
//    	chart1.pack( );
//        RefineryUtilities.centerFrameOnScreen( chart1 );
//        chart1.setVisible( true );
//        
//        chart2.pack( );
//        RefineryUtilities.centerFrameOnScreen( chart2 );
//        chart2.setVisible( true );

//        System.out.println("The interquartile of "+ list[5][0]  +" is " + df.format(interquartile(one)));
//        System.out.println("The interquartile of" + list2[3][0] +" is " + df.format(interquartile(two)));
//        System.out.println("The Median of "+ list[5][0]  +" is " + df.format(findMedian(one)));
//        System.out.println("The Median of" + list2[3][0] +" is " + df.format(findMedian(two)));
//        
//       	System.out.println("The max of "+ list[5][0]  +" is " + df.format(max(one)));
//    	System.out.println("The max of" + list2[3][0] +" is " + df.format(max(two)));
//    	System.out.println("The min of "+ list[5][0]  +" is " + df.format(min(one)));
//    	System.out.println("The min of" + list2[3][0] +" is " + df.format(min(two)));
//    	System.out.println("The difference of "+ list[5][0]  +" is " + df.format(difference(one)));
//    	System.out.println("The difference of" + list2[3][0] +" is " + df.format(difference(two)));
    	
//    	range(one);
//    	
//    	System.out.println("Correlation between " + list2[3][0] +" and "+ list[5][0]  +" is %" + df.format(Correlation(one, two)*100));
//    	System.out.println("Correlation between zero and two is %" + df.format(Correlation(two, zero)*100));
//    	System.out.println("Correlation between zero and one is %" + df.format(Correlation(one, zero)*100));
//    	
//    	System.out.println(Arrays.toString(findNumRows(list)));
//    	double[][] iList = to2dDouble(list);
//    	for (int x = 0; x < list.length; x++) {
//			System.out.println(Arrays.toString(list[x]));
//    	}
//    	System.out.println("Employchange");
//    	for (int x = 0; x < list2.length; x++) {
//			System.out.println(Arrays.toString(list2[x]));
//    	}
    }
	
	public static void convert(File selectedFile) throws Exception {
		
		String filestring = selectedFile.getAbsolutePath();
		int csv = countLines(filestring);
		
		System.out.println("Number of lines counted in the csv file: " + csv);
		 
		String [][] list3 = csvReader(selectedFile, csv-1); 
		char[] type3 = checkType(list3);
  		double [][] dList3 = to2dDouble(list3, type3); 
  		String[] names3 = nameList(list3, type3);
  		String[] time3 = getTime(list3, type3);
  		double [] results = decideGraph(time3, dList3);
  		//corAnalysis(dList3, names3);
  		
  		DecimalFormat df = new DecimalFormat("#.###");
  		
  		for(int d=0; d<results.length; d++) {
  			//System.out.println("Value of results array at index " + d + "is: " + results[d]);
  			results[d] = Double.valueOf(df.format(results[d] * 100));
  			//System.out.println("Value of results array now at index " + d + "is: " + results[d]);
  		}
  		
  		int counter = 0;
  		double highest = 0; 
  		
  		for(int a=0; a<results.length-1; a++) {
  			
  			//System.out.println("Value of results array later at index " + a + "is: " + results[a]);
  			
  			if((results[a] > results[a+1]) && (results[a] > highest)) {
  				counter = a;
  				highest = results[a];
  			} 
  		}
  		
  		//System.out.println("The value of counter is: " + counter);
  	  
  		graphNum =63;
  		
  		//System.out.println("Value of bool variable meanchoice: " + meanchoice);
  		
  		for(int i=0; i<dList3.length; i++) {
  			for(int j=0; j<dList3[i].length; j++) {
  				System.out.println("Index of dList: " + j + "and its value: " + dList3[i][j]);
  			}
  		}
  		
  		System.out.println("Exited out of dList array");
  		
  		if(meanchoice == true) {
  			for(int x=0; x<dList3.length; x++){
  				double averagevar = findMean(dList3[x]);
				System.out.println("The mean of variable " + x + " is: " + averagevar);
			}
  		} 
  		
  		if(medianchoice == true) {
  			for(int x=0; x<dList3.length; x++){
  				double medianvar = findMedian(dList3[x]);
				System.out.println("The median of variable " + x + " is: " + medianvar);
			}
  		}
  		
  		if(modechoice == true) {
  			for(int x=0; x<dList3.length; x++){
  				double modevar = findMode(dList3[x]);
				System.out.println("The mode of variable " + x + " is: " + modevar);
			}
  		}
  		
  		if(rangechoice == true) {
  			for(int x=0; x<dList3.length; x++){
  				double boundaryvar = range(dList3[x]); 
				System.out.println("The range of variable " + x + " is: " + boundaryvar);
			}
  		}
  		
  		if(minchoice == true) {
  			for(int x=0; x<dList3.length; x++){
  				double smallvar = min(dList3[x]);	
				System.out.println("The minimum value of variable " + x + " is: " + smallvar);
			}
  		}
  		
  		if(maxchoice == true) {
  			for(int x=0; x<dList3.length; x++){
  				double largevar = max(dList3[x]);
				System.out.println("The maximum value of variable " + x + " is: " + largevar);
  			}
  		}
  		
  		if(iqrchoice == true) {
  			for(int x=0; x<dList3.length; x++){
  				double [] summaryvar = interquartile(dList3[x]);
				System.out.println("The interquartile range value of variable " + x + " is: " + summaryvar[1]);
			}
  		}
  		
  		if(outlierchoice == true) {
  			for(int x=0; x<dList3.length; x++){
				//for(int y=x+1; y<dList3.length; y++){
					double [] summary_x = interquartile(dList3[x]);
					//double [] summary_y = interquartile(dList3[y]);
					ArrayList<Double> modoutx = findMildOutliers(dList3[x], summary_x[0], summary_x[2], summary_x[1]);
					//ArrayList<Double> modouty = findMildOutliers(dList3[y], summary_y[0], summary_y[2], summary_y[1]);
					ArrayList<Double> extremeoutx = findExtremeOutliers(dList3[x], summary_x[0], summary_x[2], summary_x[1]);
					//ArrayList<Double> extremeouty = findExtremeOutliers(dList3[y], summary_y[0], summary_y[2], summary_y[1]);
		           
					System.out.println("The mild outlier value(s) of variable " + x + " is: " + modoutx);
					//System.out.println("The moderate outlier value(s) of variable" + y + "is: " + modouty);
					System.out.println("The extreme outlier value(s) of variable " + x + " is: " + extremeoutx);
					//System.out.println("The extreme outlier value(s) of variable" + y + "is: " + extremeouty);
				//} 
			}
  		}
  		
  		if(correlationchoice == true) {
  			for(int x=0; x<dList3.length-1; x++){
				for(int y=x+1; y<dList3.length; y++){
					double correlate = Correlation(dList3[x], dList3[y]);
					System.out.println("The correlation between variables" + x + "and" + y + "is: " + correlate);
				}
			}
  		}
  		
  		if(sdchoice == true) {
  			for(int x=0; x<dList3.length; x++){
				double sdvar = findDeviation(dList3[x]);
				System.out.println("The standard deviation of variable" + x + "is: " + sdvar);
			}
  		}
  		
  		if(variancechoice == true) {
  			for(int x=0; x<dList3.length-1; x++){
					double vdvar = findVariance(dList3[x]);
					System.out.println("The variance between variable" + x + "is: " + vdvar);
			} 
  		}
  		
  		counter = 3;
  		
  		if(counter == 0) {
  			list3 = csvReader(selectedFile, 20); 
  			type3 = checkType(list3);
  	  		dList3 = to2dDouble(list3, type3);
  	  		names3 = nameList(list3, type3);
  	  		corAnalysis(dList3, names3);
  	  		
  	  		for(int x=0; x<dList3.length-1; x++) {
  				for(int y=x+1; y<dList3.length; y++) {
  					String title = names3[x] + " Vs " + names3[y];
  					list3 = csvReader(selectedFile, 20); 
  					type3 = checkType(list3);
  			  		dList3 = to2dDouble(list3, type3);
  			  		names3 = nameList(list3, type3);
  			  		corAnalysis(dList3, names3);
  					//System.out.println("Value of dList3 in x: " + list[0]);
  	  				//System.out.println("Value of dList3 in y: " + list[1]);
  					BarChart_AWT chart1 = new BarChart_AWT(dList3[x], dList3[y], title, names3[x], names3[y]);
  					chart1.pack();
  					RefineryUtilities.centerFrameOnScreen(chart1);
  					chart1.setVisible(true); 
  				}  
  			}
  	  		//barLine(dList3, names3);
  		}
  		
  		else if(counter == 1) {
  			for(int x=0; x<dList3.length-1; x++) {
  	  			for(int y=x+1; y<dList3.length; y++) {
  	  				String title = names3[x] + " Vs " + names3[y];
  	  				//System.out.println("Value of dList3 in x: " + dList3[x]);
  	  				//System.out.println("Value of dList3 in y: " + dList3[y]);
  	  				list3 = csvReader(selectedFile, 20); 
  	  				type3 = checkType(list3);
  	  		  		dList3 = to2dDouble(list3, type3);
  	  		  		names3 = nameList(list3, type3);
  	  		  		corAnalysis(dList3, names3);
  	  				
  	  				
  	  				LineChart_AWT chart1 = new LineChart_AWT(dList3[x], dList3[y], title, names3[x], names3[y]);
  	  				chart1.pack( );
  	  				RefineryUtilities.centerFrameOnScreen( chart1 );
  	  				chart1.setVisible( true );
  	  			}
  	  		}
  	  		
  			//lineChart(dList3, names3);
  		}
  		
  		else if(counter == 2) {
  			for(int x=0; x<dList3.length-1; x++) {
  				for(int y=x+1; y<dList3.length; y++) {
  					String title = names3[x] + " Vs " + names3[y];
  					list3 = csvReader(selectedFile, 20); 
  					type3 = checkType(list3);
  			  		dList3 = to2dDouble(list3, type3);
  			  		names3 = nameList(list3, type3);
  			  		corAnalysis(dList3, names3);
  					//System.out.println("Value of dList3 in x: " + list[0]);
  	  				//System.out.println("Value of dList3 in y: " + list[1]);
  					ScatterChart_AWT chart1 = new ScatterChart_AWT(dList3[x], dList3[y], title, names3[x], names3[y]);
  					chart1.pack();
  					RefineryUtilities.centerFrameOnScreen(chart1);
  					chart1.setVisible(true); 
  				}  
  			}
  	  		//scatterChart(dList3, names3);
  		}
		
  		else if(counter == 3) {
  			list3 = csvReader(selectedFile, 20); 
  			type3 = checkType(list3);
  	  		dList3 = to2dDouble(list3, type3);
  	  		names3 = nameList(list3, type3);
  	  		corAnalysis(dList3, names3);
  	  		
  	  		pieModel(dList3, names3);
  		}
	}
	
	public static double[] choosegraph(double a, double b, double c, double d, double e) throws Exception{
		weka.core.Attribute Attribute1 = new Attribute("firstNumeric");
		weka.core.Attribute Attribute2 = new Attribute("secondNumeric");
		weka.core.Attribute Attribute3 = new Attribute("thirdNumeric");
		weka.core.Attribute Attribute4 = new Attribute("fourthNumeric");
		weka.core.Attribute Attribute5 = new Attribute("fifthNumeric");
		
		 ArrayList<String> types = new ArrayList<String>(4);
		 types.add("B");
		 types.add("L");
		 types.add("S");
		 types.add("P");
		 weka.core.Attribute classAttribute = new Attribute("GraphTypes", types);
		 
		 ArrayList<Attribute> allAttributes = new ArrayList<Attribute>(6);
		 allAttributes.add(Attribute1);
		 allAttributes.add(Attribute2);
		 allAttributes.add(Attribute3);
		 allAttributes.add(Attribute4);
		 allAttributes.add(Attribute5);
		 allAttributes.add(classAttribute);
		
		 
		 
		 
		DataSource source = new DataSource("GraphTrainingData.csv");
		weka.core.Instances trainingSet = source.getDataSet();
		 trainingSet.setClassIndex(5);
		 
		weka.classifiers.Classifier model = (Classifier) new NaiveBayes();
		model.buildClassifier(trainingSet);
		
		 // Create an empty training set
		 Instances testSet = new Instances("Rel", allAttributes, 10);
		 // Set class index
		 testSet.setClassIndex(5);
		
		Instance test = new DenseInstance(5);
		test.setValue((Attribute)allAttributes.get(0), a);
		test.setValue((Attribute)allAttributes.get(1), b);
		test.setValue((Attribute)allAttributes.get(2), c);
		test.setValue((Attribute)allAttributes.get(3), d);
		test.setValue((Attribute)allAttributes.get(4), e);
		
		testSet.add(test);
		
		test.setDataset(trainingSet);
		
		double[] results = model.distributionForInstance(test);
		
		DecimalFormat df = new DecimalFormat("#.###");
		
		System.out.println("Bar Graph: " + df.format(results[0]*100) + "%");
		System.out.println("Line Graph: " + df.format(results[1]*100) + "%");
		System.out.println("Scatter Graph: " + df.format(results[2]*100) + "%");
		System.out.println("Pie Graph: " + df.format(results[3]*100) + "%");
		
//		double result = model.classifyInstance(test);
		
//		System.out.println(result);

		double max = results[0];

		for (int i = 1; i < results.length; i++) {
		    if (results[i] > max) {
		      max = results[i];
		    }
		}
//		
//		if(max == 0){
//			return "Line";
//		}
//		else if(max == 1){
//			return "Scatter";
//		}
//		else if(max == 2){
//			return "Bar";
//		}
//		else{
//			return "Pie";
//		}
		
		return results;
	}
	
	public static void demoDecideGraph(String[] time, double[][] data){
		//Between bar and scatter plot
		//decided by outliers, numbers and range
		
		int[] info = {0,0,0,0,0};
		
		if (time == null){
			info[0] =0;
		}
		else{

				info[0] =uniques(time);
			
		}
		if(data.length ==1){
			//check if sum is %10
			info[1] =1;
			
			
			for(int x =0; x<data.length; x++){
			info[2] +=divTen(data[x]);
			}
			
		}

		else{
			//check all correlations
			//how many times does one data set avg go into another
			info[1] =data.length;
			
			info[3]= checkHighCor(data, 65);
			
		}
		

			info[4] =data[0].length;
			

		
		//check avg difference between points
		//check for size of outliers
		
			if((info[0]==0) && (info[2]==1) && (info[1]==1) ){
				System.out.println("Rule Results: Pie");
			}
			else if((info[0]==0) && (info[4]>100) && (info[1]==2) ){
				System.out.println("Rule Results: Scatter");
			}
			else if((info[0]<=5) && (info[4]<100) ){
				System.out.println("Rule Results: Bar");
			}
			else{
				System.out.println("Rule Results: Line");
			}
		

	}
	
	public static double[] decideGraph(String[] time, double[][] data) throws Exception{
		//Between bar and scatter plot
		//decided by outliers, numbers and range
		
		int[] info = {0,0,0,0,0};
		
		if (time == null){
			info[0] =0;
		}
		else{

				info[0] =uniques(time);
			
		}
		if(data.length ==1){
			//check if sum is %10
			info[1] =1;
			
			for(int x =0; x<data.length; x++){
				info[2] +=divTen(data[x]);
				}
			
		}

		else{
			//check all correlations
			//how many times does one data set avg go into another
			info[1] =data.length;
			
			info[3]= checkHighCor(data, 65);
			
		}
		

			info[4] =data[0].length;
			

		
		//check avg difference between points
		//check for size of outliers
			
			
		
//		for(int x=0; x<info.length;x++){
//			System.out.print(info[x]+ "\t");
//		}
//		System.out.println();
		
			return choosegraph(info[0], info[1], info[2], info[3], info[4]);

	}
	
	public static int countLines(String filename) throws IOException {
	    LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
	    int cnt = 0;
	    String lineRead = ""; 
	
	    while ((lineRead = reader.readLine()) != null) {}

	    cnt = reader.getLineNumber(); 
	    reader.close();
	    return cnt;
	}
	
	public static int divTen(double[] data){
		double sum =0;
		
		for(int x =0; x<data.length; x++){
			sum +=data[x];
		}
		
		if(sum%10==0){
			return 1;
		}
		
		return 0;
	}
	
	public static int checkHighCor(double[][] data, int high){
		int count=0;
		
		for(int x =0; x<data.length; x++){
			for(int y =x+1; y<data.length;y++){
				if((Correlation(data[x], data[y])>high)||(Correlation(data[x], data[y])<(high*-1))){
					count++;
				}
			}
		}
		
		return count;
	}
	
	public static int uniques(String[] times){
		
		int uniques =0;
		String[] found = new String[times.length];
		
		for(int x =0; x<times.length; x++){
			
			if(Arrays.asList(found).contains(times[x])==false){
				found[uniques]= times[x];
				uniques++;
			}
		}
		
		return uniques;
	}
	
	public static String[] nameList(String[][] list, char[] types){
    	
    	String[] names = new String[numD(types)];
    	int column =0;
    	for(int x =0; x<types.length;x++){
    		if(types[x]=='D'){
    			names[column] = list[x][0].substring(0, list[x][0].length()-1);
    			column++;
    		}
    	}
    	return names;
    }
	
	public static void corAnalysis(double[][] list, String[] names){
		DecimalFormat df = new DecimalFormat("#.###");
		for(int x =0; x<list.length-1; x++){
			for(int y =x+1; y<list.length; y++){
				double temp = Correlation(list[x], list[y])*100;
				System.out.println("Correlation between column " + names[x] +" and column "+ names[y]  +" is %" + df.format(temp));
				if(Math.abs(temp) > graphNum){
					twoLine(list[x], list[y], names[x], names[y]);
				}
			}
		}
	}
	
	public static void corAnalysis(double[][] list1, double[][] list2, String[] names1, String[] names2){
		DecimalFormat df = new DecimalFormat("#.###");
		for(int x =0; x<list1.length; x++){
			for(int y =0; y<list2.length; y++){
				double temp = Correlation(list1[x], list2[y])*100;
				System.out.println("Correlation between list 1 column " + names1[x] +" and list 2 column "+ names2[y]  +" is %" + df.format(temp));
				if(Math.abs(temp) > graphNum){
					twoLine(list1[x], list2[y], names1[x], names2[y]);
				}
			}
		}

	}
	
	public static void twoLine(double[] list1, double[] list2, String name1, String name2){
		DecimalFormat df = new DecimalFormat("#.###");
		double[] dif1 = pDifference(list1);
		double[] dif2 = pDifference(list2);
		 //String title = name1 + " Vs " + name2;
		//LineChart_AWT chart1 = new LineChart_AWT(list1, list2, title, name1, name2);
		//chart1.pack( );
		//RefineryUtilities.centerFrameOnScreen( chart1 );
		//chart1.setVisible( true );
		
		 
		System.out.println("\tThe mean of " + name1 + " is " + df.format(findMean(list1)));
		System.out.println("\tThe mean of " + name2 + " is " + df.format(findMean(list2)));
		
		System.out.println("\tThe median of " + name1 + " is " + df.format(findMedian(list1)));
		System.out.println("\tThe median of " + name2 + " is " + df.format(findMedian(list2)));
		
	}
	
	/*
	public static void barLine(double[][] list, String[] names) {
		DecimalFormat df = new DecimalFormat("#.###");
		
		for(int x=0; x<list.length-1; x++) {
			for(int y=x+1; y<list.length; y++) {
				String title = names[x] + " Vs " + names[y];
				//System.out.println("Value of dList3 in x: " + list[0]);
  				//System.out.println("Value of dList3 in y: " + list[1]);
				BarChart_AWT chart1 = new BarChart_AWT(list[x], list[y], title, names[x], names[y]);
				chart1.pack();
				RefineryUtilities.centerFrameOnScreen(chart1);
				chart1.setVisible(true); 
			}  
		}
	}
	*/
	
	
	public static void pieModel(double[][]list, String[] names) {
	 	DecimalFormat df = new DecimalFormat("#.###");
	 	
	 	System.out.println("I've reached here!");
	 	 
	 	for(int x=0; x<list.length-1; x++) {
	 		for(int y=x+1; y<list.length; y++) {
	 			String title = names[x] + "Vs" + names[y];
	 			//System.out.println("Reached here as well!");
	 			//System.out.println("Value of dList3 in x: " + list[0]);
  				//System.out.println("Value of dList3 in y: " + list[1]);
	 			PieChart_AWT chart1 = new PieChart_AWT(list[x], list[y], title, names[x], names[y]);
	 			chart1.pack();
	 			RefineryUtilities.centerFrameOnScreen(chart1);
	 			chart1.setVisible(true);
	 		}		
	 	}
	 }
	 
	/*
	public static void lineChart(double[][]list, String[] names) {
		DecimalFormat df = new DecimalFormat("#.###");
		
		for(int x=0; x<list.length; x++) {
			for(int y=0; y<list[x].length; y++) {
				System.out.print("Value of Column 1 and Column 2: " + list[x][y]);
			}
		}
		
		for(int x=0; x<list.length-1; x++) {
  			for(int y=x+1; y<list.length; y++) {
  				String title = names[x] + " Vs " + names[y];
  				//System.out.println("Value of dList3 in x: " + dList3[x]);
  				//System.out.println("Value of dList3 in y: " + dList3[y]);
  				
  				LineChart_AWT chart1 = new LineChart_AWT(list[x], list[y], title, names[x], names[y]);
  				chart1.pack( );
  				RefineryUtilities.centerFrameOnScreen( chart1 );
  				chart1.setVisible( true );
  			}
  		}
	}
	*/
	
	public static void scatterChart(double[][]list, String[] names) {
		DecimalFormat df = new DecimalFormat("#.###");
		
		for(int x=0; x<list.length; x++) {
			for(int y=0; y<list[x].length; y++) {
				System.out.print("Value of Column 1 and Column 2: " + list[x][y]);
			}
		}
		
		for(int x=0; x<list.length-1; x++) {
  			for(int y=x+1; y<list.length; y++) {
  				String title = names[x] + " Vs " + names[y];
  				//System.out.println("Value of dList3 in x: " + dList3[x]);
  				//System.out.println("Value of dList3 in y: " + dList3[y]);
  				
  				ScatterChart_AWT chart1 = new ScatterChart_AWT(list[x], list[y], title, names[x], names[y]);
  				chart1.pack( );
  				RefineryUtilities.centerFrameOnScreen( chart1 );
  				chart1.setVisible( true );
  			}
  		}
	}
	
	public static double[] pDifference(double[] list){
		double[] dif = new double[list.length-1];
		for(int x =1; x<list.length; x++){
			dif[x-1] = (list[x-1] -list[x])/(list[x-1]*100);
		}
		return dif;
	}
	
	public static char[] checkType(String[][] list){
		char[] types = new char[list.length];
		for(int x =0; x<types.length; x++){
			types[x] = list[x][0].charAt(list[x][0].length()-1);
		}
		return types;
	}
	
	
    public static double[][] to2dDouble(String[][] list, char[] types){
    	
    	double[][] dList = new double[numD(types)][list[0].length-1];
    	int column =0;
    	for(int x =0; x<types.length;x++){
    		if(types[x]=='D'){
    			dList[column] = arrayToDouble(list, 1, x);
    			column++;
    		}
    	}
    	return dList;
    }
    
    public static String[] getTime(String[][] list, char[] types){
    	
    	String[] time = new String[list[0].length-1];
    	int column =0;
    	
    	for(int x =0; x<types.length;x++){
    		if(types[x]=='T'){
    			for(int y =0; y<time.length;y++){
    				time[y] = list[x][y+1];
    			}
    			
    			column++;
    		}
    	}
    	
    	return time;
    }

	
    public static int numD(char[] iRows){
    	int num =0;
    	for(int x =0; x<iRows.length; x++){
    		if(iRows[x] == 'D'){
    			num++;
    		}
    	}
    	return num;
    }
	
	
	public static double [] interquartile(double[] temp) {
    	double[] x = temp;
    	double firstq;
    	double thirdq;
    	double iqr;
    	int middle;
    	//double quartileone;
    	double quartilethree;
    	
    	Arrays.sort(temp);
    	
    	//System.out.println("The value of temp.length in interquartile is: " + temp.length);
    	
    	//for(int i=0; i<temp.length; i++) {
    	//	System.out.println("Index in interquartile: " + i + "value is: " + temp[i]);
    	//} 
    	 
    	if(temp.length % 2 == 0) {
    		middle = temp.length/2;
    		
    		if(middle % 2 == 0 ) {
    			
    			//System.out.println("I've reached here, because middle is even with a value of: " + middle);
    			 
    			firstq = (temp[middle/2 - 1] + temp[((middle/2) + 1) - 1]) / 2;
    			
    			//System.out.println("The value of temp[middle/2 -1] is: " + temp[middle/2 - 1]);
    			
    			thirdq = (temp[3 * (middle/2) - 1] + temp[((3 * (middle/2)) + 1) - 1]) / 2;
    		}
    		 
    		else {
    			firstq = temp[((middle/2) + 1) - 1];
    			thirdq = temp[(3 * ((middle/2) + 1) - 1) - 1];
    		}
    	}
    	
    	else {
    		middle = temp.length/2 + 1;
    		
    		if((middle-1) % 2 == 0) {
    			firstq = (temp[((middle-1)/2) - 1] + temp[((middle-1)/2 + 1) - 1]) / 2;
    			thirdq = (temp[((3 * ((middle-1)/2)) + 1) - 1] + temp[((3 * ((middle-1)/2 + 1)) - 1) - 1]) / 2;
    		}
    		
    		else {
    			firstq = temp[((middle-1)/2 + 1) - 1];
    			thirdq = temp[(3 * ((middle-1)/2 + 1)) - 1];
    		}
    	}
    	
    	iqr = thirdq - firstq;
    	
    	//findOutliers(temp, firstq, thirdq, iqr);
    	double [] summary = {firstq, iqr, thirdq};
    	
    	System.out.println("First quartile is: " + firstq);
    	System.out.println("Interquartile range is: " + iqr);
    	System.out.println("Third quartile is: " + thirdq);
    	 
    	return summary;
    }

    public static double findMedian(double[] temp) {
    	double[] x = temp;
    	double median;
    	 
    	Arrays.sort(temp);
    	
    	//for(int i=0; i<temp.length; i++) {
    		//System.out.println("Index of this median array: " + i);
    	//}
    	
    	//System.out.println("Exited the median array loop");
    	
    	if(temp.length % 2 == 0) {
    		median = (temp[temp.length/2] + temp[temp.length/2 + 1])/2;
    	}
    	
    	else {
    		median = temp[temp.length/2 + 1];
    	}
    	
    	return median;
    }
    
    public static double findMode(double[] temp) {
    	
    	double maxval = 0;
    	double maxcounter = 0;
    	
    	for(int i=0; i<temp.length; i++) {
    		double counter = 0;
    		
    		for(int j=0; j<temp.length; j++) {
    			if(temp[j] == temp[i]) {
    				counter++;
    			}
    		}
    		
    		if(counter > maxcounter) {
    			maxcounter = counter;
    			maxval = temp[i];
    		}
    	}
    	 
    	return maxval;
    }
    
    public static double findDeviation(double[] temp) {
    	
    	return Math.sqrt(findVariance(temp));
    }  
    
    public static double findVariance(double[] temp) {
    	
    	double mean = findMean(temp);
    	double ssd = 0;
    	
    	for(int i=0; i<temp.length; i++) {
    		ssd += ((temp[i] - mean) * (temp[i] - mean));
    	}
    	
    	double variance = ssd/temp.length;
    	
    	return variance;
    }
    
    public static ArrayList<Double> findExtremeOutliers(double[] temp, double firstq, double thirdq, double iqr) {
    	//double lower_inner_fence;
    	//double upper_inner_fence;
    	double lower_outer_fence;
    	double upper_outer_fence;
    	 
    	//int counter = 0;
    	
    	//double [] outlierlist = new double[temp.length];
    	ArrayList<Double> outlierlist = new ArrayList<Double>();
    	
    	for(int i=0; i<temp.length; i++) {
    		outlierlist.add(i, 0.0);
    	} 
    	
    	//for(int i=0; i<temp.length; i++) {
    		//System.out.println("Value of temp index " + i + " in extreme: " + temp[i]);
    	//}
    	
    	//lower_inner_fence = firstq - (1.5 * iqr);
    	//upper_inner_fence = thirdq + (1.5 * iqr);
    	lower_outer_fence = firstq - (3 * iqr);
    	upper_outer_fence = thirdq + (3 * iqr);
    	
    	//System.out.println("Upper outer fence is: " + upper_outer_fence);
    	 
    	for(int i=0; i<temp.length; i++) {
    		if(temp[i] >= upper_outer_fence || temp[i] <= lower_outer_fence) {
    			outlierlist.add(i, temp[i]); 
    			//System.out.println("Outlier value at index " + i + "value is: " + temp[i]);
    			//counter++;
    		}
    	}
    	
    	//System.out.println("First outlier observation is: " + outlierlist[0]); 
    	//System.out.println("List of outliers");
    	//for(int j=0; j<outlierlist.length; j++) {
    	//	if(outlierlist[j] != 0) {
    	//		System.out.print(outlierlist[j] + " "); 
    	//	}
    	//}
  
     	return outlierlist;
    }
    
    public static ArrayList<Double> findMildOutliers(double[] temp, double firstq, double thirdq, double iqr) {
    	double lower_inner_fence;
    	double upper_inner_fence;
    	double lower_outer_fence;
    	double upper_outer_fence;
    	 
    	//int counter = 0;
    	
    	//double [] outlierlist = new double[temp.length];
    	ArrayList<Double> outlierlist = new ArrayList<Double>();
    	
    	for(int i=0; i<temp.length; i++) {
    		outlierlist.add(i, 0.0);
    	}
    	
    	//for(int i=0; i<temp.length; i++) {
    		//System.out.println("Value of temp index " + i + " in mild: " + temp[i]);
    	//}
    	
    	lower_inner_fence = firstq - (1.5 * iqr);
    	upper_inner_fence = thirdq + (1.5 * iqr);
    	lower_outer_fence = firstq - (3 * iqr);
    	upper_outer_fence = thirdq + (3 * iqr);
    	
    	//System.out.println("Upper outer fence is: " + upper_outer_fence);
    	 
    	for(int i=0; i<temp.length; i++) {
    		if(((temp[i] <= upper_outer_fence) && (temp[i] >= upper_inner_fence)) || ((temp[i] >= lower_outer_fence) && (temp[i] <= lower_inner_fence))) {
    			outlierlist.add(i, temp[i]);
    			//counter++;
    		}
    	}
    	
    	//System.out.println("First outlier observation is: " + outlierlist[0]); 
    	//System.out.println("List of outliers");
    	//for(int j=0; j<outlierlist.length; j++) {
    	//	if(outlierlist[j] != 0) {
    	//		System.out.print(outlierlist[j] + " "); 
    	//	}
    	//}
  
     	return outlierlist;
    }

    public static String[] numWords(int max){
    	String[] temp = new String[max];
    	for(int x=0; x<max; x++){
    		String word = Integer.toString(x);
    		temp[x]= word;
    	}
    	return temp;
    }
   
    public static int numTrue(boolean[] iRows){
    	int num =0;
    	for(int x =0; x<iRows.length; x++){
    		if(iRows[x]){
    			num++;
    		}
    	}
    	return num;
    }
    public static boolean[] findNumRows(String[][] list){
    	boolean[] iRows = new boolean[list.length];
    	for(int x =0; x<list.length; x++){
    		boolean temp = true;
    		for(int y=1; y<15; y++){
    			if(list[x].length>x+1){
    				if(!list[x][y].matches("[-+]?\\d*\\.?\\d+")){
    					if((list[x][y].matches("[A-Za-z]"))){
    						temp = false;
    					}
    				}
    				if((list[x][y].equals("--")) || (list[x][y].equals(null))) {
    					temp = false;
    				}
    			}
    		}
    		iRows[x]=temp;
    	}
    	
    	return iRows;
    }
    
    public static double Correlation(double[] xs, double[] ys) {


        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;

        int n = xs.length;

        for(int i = 0; i < n; ++i) {
          double x = xs[i];
          double y = ys[i];

          sx += x;
          sy += y;
          sxx += x * x;
          syy += y * y;
          sxy += x * y;
        }


        double cov = sxy / n - sx * sy / n / n;

        double sigmax = Math.sqrt(sxx / n -  sx * sx / n / n);

        double sigmay = Math.sqrt(syy / n -  sy * sy / n / n);


        return cov / sigmax / sigmay;
      }
    
    
    public static double range(double[] temp){
    	double[] x = temp;
    	Arrays.sort(temp);
    	
    	double range = temp[temp.length-1] - temp[0];
//    	System.out.println(Arrays.toString(temp));
//    	System.out.println(Arrays.toString(range));
    	temp = x; 
    	return range;
    	
    }
    
    
    public static double min(double[] temp){
    	//double[] x = temp;
    	//Arrays.sort(temp);
    	double min = temp[0];
    	
    	//System.out.println("Value of temp min at 0 is: " + temp[0]);
    	
    	for(int i=1; i<temp.length; i++) {
    		//System.out.println("Value of temp min at " + i + " is: " + temp[i]);
    		
    		if(temp[i] < min) {
    			min = temp[i];
    		}
    		
    	}
    	//temp = x;
    	//System.out.println("The minimum value is: " + min);
    	return min; 
    			
    }
    
    public static double max(double[] temp){
    	//double[] x = temp;
    	//Arrays.sort(temp);
    	double max = temp[0];
    	
    	//System.out.println("Value of temp max at 0 is: " + temp[0]);
    	
    	for(int i=1; i<temp.length; i++) {
    		//System.out.println("Value of temp max at " + i + " is: " + temp[i]);
    		
    		if(temp[i] > max) {
    			max = temp[i];
    		}
    		
    	}
    	
    	//System.out.println("The maximum value is: " + max);
    	//temp = x;
    	//System.out.println("The max value is: ");
    	return max;		
    }
    
    public static double difference(double[] temp){
    	double[] x = temp;
    	Arrays.sort(temp);
    	double dif =  temp[0]- temp[temp.length-1];
    	temp =x;
    	return dif;
    			
    }
    
    public static double findMean(double[] temp){
    	double sum =0;
    	for(int x =0; x<temp.length; x++){
    		sum = sum + temp[x];
    	}
    	double length = temp.length;
    	double mean = sum/length;
    	return mean;
    }
    
    public static double[] arrayToDouble(String[][] list, int start, int row){
    	double[] temp = new double[list[row].length-start-1];
    	for(int x = 0; x<temp.length; x++){
    		if((!list[row][start+x].equals(null))&&(!Character.isLetter(list[row][start+x].charAt(0)))){

    			Double i = Double.valueOf(list[row][start+x]);
    			temp[x] = i.doubleValue();

    		}

    	}
    	return temp;
    }
    
 
    
    public static String[][] csvReader(File csvFile, int max){
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";
    	int count =0;

    	String[][] list = null;
    	String[] one = null;
    	String quotes = "\"";
    	try {
    		

    		br = new BufferedReader(new FileReader(csvFile) );
    		while (((line = br.readLine()) != null)&& count<max){
    			
				if (count == 0) {
					String[] first = line.split(cvsSplitBy);
					one = new String[first.length];
					list = new String[one.length][max];
				}
				
				String[] temp = line.split(cvsSplitBy);
				for (int y = 0; y < one.length; y++) {
					if(y< temp.length){
					one[y] = temp[y];
					}

					
					
					
				}

				for (int x = 0; x < one.length; x++) {
					if (one[x] != null)
						list[x][count] = one[x];
					else
						list[x][count] = "-";
				}

				count++;

			}
//			for (int x = 0; x < list.length; x++) {
//				System.out.println(Arrays.toString(list[x]));
//			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	return list;

    }
    
    public static String[] quoteFix(String[] temp, int y){
    	int q=0;
    	int x;
    	for( x =0; x<temp.length-y;x++){
    		for(int z =0; z<temp[y+x].length();z++){
    			if(temp[y+x].charAt(z)=='"'){
    				q++;
    			} 
    			if(q ==2){
    				break;
    			}
    		}
    		if(q ==2){
				break;
			}
    	}
    	if(q!=2){
    		return temp;
    	}
    	else{
    		for(int a =1; a<=x; a++){
    			temp[y]= temp[y].concat(temp[y+a]);
    		}
    		for(y = y+1; y+x<temp.length-1; y++){
    			temp[y] = temp[y+x+1];
    		}
    	}
    	return temp;
    }

}


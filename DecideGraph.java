import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
 
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.LogitBoost;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.SparseInstance;
import weka.core.Utils;
import weka.filters.Filter;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Vector;

import org.jfree.ui.RefineryUtilities;

import java.text.*;

public class DecideGraph {



	public static void main(String[] args) throws Exception {

    	String csvFile2 = "SD-test1.csv";
    	String csvFile = "gfa25.csv";
    	String csvFile3 = "test-3.csv";
    	String csvFile4 = "Iris.csv";
    	String csvFile5 = "Census_Demographics.csv";
    	
    	int max=10000;
    	DecimalFormat df = new DecimalFormat("#.###");
    	
    	int csv1 = countLines(csvFile);
    	int csv2 = countLines(csvFile2);
    	int csv3 = countLines(csvFile3);
    	int csv4 = countLines(csvFile4);
    	int csv5 = countLines(csvFile5);
    	

    	
    	String[][] list1 = csvReader(csvFile, csv1-1);
//    	String[][] list2 = csvReader(csvFile2, csv2-1);
//    	String[][] list3 = csvReader(csvFile3, csv3-1);
//    	String[][] list4 = csvReader(csvFile4, csv4-1);
//    	String[][] list5 = csvReader(csvFile5, csv5-1);
    	

    	
    	
     	char[] type1 = checkType(list1);
//    	char[] type2 = checkType(list2);
//    	char[] type3 = checkType(list3);
//    	char[] type4 = checkType(list4);
//    	char[] type5 = checkType(list5);
    	
    	
    	double[][] dList1 = to2dDouble(list1, type1);
//    	double[][] dList2 = to2dDouble(list2, type2);
//    	double[][] dList3 = to2dDouble(list3, type3);
//    	double[][] dList4 = to2dDouble(list4, type4);
//    	double[][] dList5 = to2dDouble(list5, type5);
    	
    	String[] names1 = nameList(list1, type1);
//    	String[] names2 = nameList(list2, type2);
//    	String[] names3 = nameList(list3, type3);
//    	String[] names4 = nameList(list4, type4);
//    	String[] names5 = nameList(list5, type5);
    	
//    	String[] time2 = getTime(list2, type2);
    	String[] time1 = getTime(list1, type1);
//    	String[] time3 = getTime(list3, type3);
//    	String[] time4 = getTime(list4, type4);
//    	String[] time5 = getTime(list5, type5);
    	
    	
    	System.out.println("Test");
 //   	double[] results = decideGraph(time2, dList2);
    	double[] results = decideGraph(time1, dList1);
 //   	double[] results = decideGraph(time3, dList3);
 //   	double[] results = decideGraph(time4, dList4);
 //   	double[] results = decideGraph(time5, dList5);
 //   	demoDecideGraph(time2, dList2);


    	
    	
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



public static String[][] csvReader(String csvFile, int max){
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
//		for (int x = 0; x < list.length; x++) {
//			System.out.println(Arrays.toString(list[x]));
//		}

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
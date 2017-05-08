import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.AttributedString;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryAnnotation;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.LabelBlock;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;
 
public class PieChart_AWT extends ApplicationFrame 
{
   public PieChart_AWT( String title ) 
   {
      super( title ); 
      setContentPane(createDemoPanel( ));
   }
   
   public PieChart_AWT(double[] list1, double[] list2, String title, String x, String y) {
		super(title);
		
		System.out.println("Hello!");
		
		JFreeChart pieChart= createChart(list1, list2, title, x, y);
		
		ChartPanel chartPanel = new ChartPanel(pieChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
	}
   
   private static PieDataset createDataset( ) 
   {
      DefaultPieDataset dataset = new DefaultPieDataset( );
      dataset.setValue( "IPhone 5s" , new Double( 20 ) );  
      dataset.setValue( "SamSung Grand" , new Double( 20 ) );   
      dataset.setValue( "MotoG" , new Double( 40 ) );    
      dataset.setValue( "Nokia Lumia" , new Double( 10 ) );  
      return dataset;         
   }
   
   private static PieDataset createDataset(String [] newarray, double [] list2) {
	   DefaultPieDataset dataset = new DefaultPieDataset();
	   
	   for(int i=0; i<newarray.length; i++) {
		   System.out.println("Value of this variable pie: " + list2[i]);
		   dataset.setValue(newarray[i], new Double(list2[i]));  
	   } 
	   
	   /*
	   double freq1 =0;
	   double freq2 = 0;
	   double freq3 = 0;
	   double freq4 = 0;
	   double freq5 = 0;
	   double freq6 = 0; 
	   double freq7 = 0;
	    
		for(int j=0; j<names.length; j++) {
			
			if(names[j].equals("Total Grains/Cereals and Root Crops (R&T)")) {
				freq1 += list2[j];
			}
			
			else if(names[j].equals("Total Grains/Cereals")) {
				freq2 += list2[j]; 
			}
			
			else if(names[j].equals("Root Crops (R&T)")) {
				freq3 += list2[j];
			}
			
			else if(names[j].equals("Population")) {
				freq4 += list2[j];
			}
			
			else if(names[j].equals("Economic Data")) {
				freq5 += list2[j];
			}
			
			else if(names[j].equals("Food Aid")) {
				freq6 += list2[j];
			}
			
			else if(names[j].equals("Other")) {
				freq7 += list2[j];
			}
		}
		
		dataset.setValue("Total Grains/Cereals and Root Crops (R&T)", new Double(freq1));  
	    dataset.setValue("Total Grains/Cereals", new Double(freq2));   
	    dataset.setValue("Root Crops (R&T)", new Double(freq3));    
	    dataset.setValue("Population", new Double(freq4));
	    dataset.setValue("Economic Data", new Double(freq5));  
	    dataset.setValue("Food Aid", new Double(freq6));   
	    dataset.setValue("Other", new Double(freq7));
	   */
	   return dataset;
   }
   
   private static JFreeChart createChart(PieDataset dataset) {
       
       JFreeChart chart = ChartFactory.createPieChart(
           "Pie Chart Demo 1",  // chart title
           dataset,             // data
           true,               // include legend
           true,
           false
       );

       PiePlot plot = (PiePlot) chart.getPlot();
       plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
       plot.setNoDataMessage("No data available");
       plot.setCircular(false);
       plot.setLabelGap(0.02);
       return chart; 
       
   }
   
private static JFreeChart createChart(double[] list1, double[] list2, String title, String x, String y) {
	
	System.out.println("Hello!");
	
	String [] newarray = new String[list1.length];
	
	for(int i=0; i<list1.length; i++) {
		String str = Double.toString(list1[i]);
		newarray[i] = str;
//		System.out.println(newarray[i]);
	}  

       JFreeChart chart = ChartFactory.createPieChart(
           title,  // chart title
           createDataset(newarray, list2),             // data
           true,               // include legend
           true,
           false
       );
       
       PiePlot plot = (PiePlot) chart.getPlot();
       
       /*
       final PiePlot plot = (PiePlot) chart.getPlot();
       CategoryAnnotation annotation = null;
	   Font font = new Font("SansSerif", Font.BOLD, 10);
	   System.out.println(y); 
	   annotation = new CategoryTextAnnotation("Variables " + x + " and " + y + " appear to be normally distributed", 0, ReadCSV.min(list2));
	   ((CategoryTextAnnotation) annotation).setFont(font);  
	   ((CategoryTextAnnotation) annotation).setTextAnchor(TextAnchor.TOP_LEFT);
	   System.out.println("Text anchor: " + ((CategoryTextAnnotation) annotation).getTextAnchor());
	   ((CategoryPlot) plot).addAnnotation(annotation);  
        */
       
       
        // Section labels      
      plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})"));
      plot.setLabelBackgroundPaint(new Color(220, 220, 220));
      plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 8));
            
      StandardPieSectionLabelGenerator slbl = new StandardPieSectionLabelGenerator( 
               "{0}={2}", 
               new DecimalFormat("#,##0"), 
               new DecimalFormat("0%")); 
      plot.setLabelGenerator(slbl);
      plot.setLegendLabelGenerator(slbl);

      plot.setInteriorGap(0.12);
      
      chart.removeLegend();
      
      LegendTitle legend = new LegendTitle(chart.getPlot());
        legend.setItemFont(new Font("SansSerif", Font.PLAIN, 10));
        BlockContainer wrapper = new BlockContainer(new BorderArrangement());
        wrapper.setBorder(new BlockBorder(1.0, 1.0, 1.0, 1.0));
        
        //LabelBlock subtitle = new LabelBlock(subtitleBuffer.toString());
        //subtitle.setFont(new Font("SansSerif", Font.PLAIN, 8));
        //subtitle.setPadding(5, 5, 2, 5);
        //wrapper.add(subtitle, RectangleEdge.BOTTOM);
        
        // *** this is important - you need to add the item container to
        // the wrapper, otherwise the legend items won't be displayed when
        // the wrapper is drawn... ***
        BlockContainer items = legend.getItemContainer();
        items.setPadding(2, 10, 5, 2);
        wrapper.add(items);
        legend.setWrapper(wrapper);
        
        legend.setPosition(RectangleEdge.BOTTOM);
        legend.setHorizontalAlignment(HorizontalAlignment.CENTER);
        legend.setBorder(new BlockBorder(new Color(0,0,0,0)));
        chart.addSubtitle(legend);
        
        chart.fireChartChanged(); 
        
        
       
       StandardPieSectionLabelGenerator spslg = new StandardPieSectionLabelGenerator();
       StandardPieSectionLabelGenerator spslg1 = new StandardPieSectionLabelGenerator();
       
       AttributedString as1 = new AttributedString("Maximum value");
       AttributedString as2 = new AttributedString("Minimum value");
       
       spslg.setAttributedLabel(0, as1);
       spslg1.setAttributedLabel(1, as2);
       System.out.println("Did it work?");
       
       plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
       plot.setNoDataMessage("No data available");
       plot.setCircular(false);
       plot.setLabelGap(0.02);
       
       getFeedback();
       
       return chart; 
       
   }

public static void getFeedback() {
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
   
   public static JPanel createDemoPanel( )
   {
      JFreeChart chart = createChart(createDataset( ) );  
      return new ChartPanel( chart ); 
   }
   public static void main( String[ ] args )
   {
      PieChart_AWT demo = new PieChart_AWT( "Mobile Sales" );  
      demo.setSize( 560 , 367 );    
      RefineryUtilities.centerFrameOnScreen( demo );    
      demo.setVisible( true ); 
   }
}
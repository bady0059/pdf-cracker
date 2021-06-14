import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

public class Frame {

	//the file
	static File file;
	// type of attack
	static String typeAttack = "BruteForce";

	//dictionary
	static File fileDictionary;
	
	//BruteForce
	static int passLength;
	static boolean uppercaseLetters = false;
	static boolean lowercaseLetters = false;
	static boolean numbers = false;
	static boolean symbols = false;

	//nope
	private static JFrame[] frameCurrent = setFrameCurrent();
	public static JButton buttonAttack1;
	public static JButton buttonAttack2;
	public static JProgressBar pb;
	public static JLabel label;
	
	//constructor 
	Frame(){
		run();
	}

	//create the first frame
	private static void run() {
		createWindow(frameCurrent[0]);
	}

	//set all frames to array and create their UI 
	private static JFrame[] setFrameCurrent() {
		JFrame frameImportFile = new JFrame("Cracker");
		JFrame frameChooseWay = new JFrame("Cracker");
		JFrame frameBruteForce = new JFrame("Cracker");
		JFrame frameDictionary = new JFrame("Cracker");
		JFrame frameAttack = new JFrame("Cracker");

		frameImportFile = frameImportFileUI(frameImportFile);
		frameChooseWay = frameChooseWayUI(frameChooseWay);
		frameBruteForce = frameBruteForceUI(frameBruteForce);
		frameDictionary = frameDictionaryUI(frameDictionary);
		frameAttack = frameAttackUI(frameAttack);

		JFrame[] frameCurrent = {frameImportFile, frameChooseWay, frameBruteForce, frameDictionary, frameAttack};
		return frameCurrent;
	}

	//import file UI
	private static JFrame frameImportFileUI(JFrame frame) {
		//buttons
		JButton buttonChooseFile = new JButton("choose file");
		JButton buttonNext = new JButton("next");

		//panel and label
		JLabel label = new JLabel("enter your pdf file: ");
		JPanel panel = new JPanel();

		//chose the pdf file and save it 
		buttonChooseFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(true);

				int option = fileChooser.showOpenDialog(frame);
				if(option == JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
					String fileName = file.getName();
					label.setText("File Selected: " + fileName);
				}else{
					label.setText("Open command canceled");
				}
			}
		});
		
		//go to next frame
		buttonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextFrame();
			}
		});

		//add
		panel.setLayout(null);
		panel.setBackground(Color.GRAY);
	    buttonNext.setBounds(400,300,100,20);
	    buttonChooseFile.setBounds(150,20,100,20);
	    label.setBounds(20, 20, 200, 20);
		panel.add(label);
		panel.add(buttonChooseFile);
		panel.add(buttonNext);
		frame.add(panel);
		
		return frame;
	}

	//choose the way UI
	private static JFrame frameChooseWayUI(JFrame frame) {

		//buttons
		JButton buttonNext = new JButton("next");

		ButtonGroup buttonAttacks = new ButtonGroup();
		JRadioButton buttonBruteForce = new JRadioButton("brute force");
		JRadioButton buttonDictionary = new JRadioButton("dictionary");

		//panel
		JPanel panel = new JPanel();
		JLabel label = new JLabel("enter your crake way: ");

		//next frame
		buttonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextFrame();
			}
		});
		
		//brute force
		buttonBruteForce.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				typeAttack = "BruteForce";
			}
		});
		
		//dictionary
		buttonDictionary.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				typeAttack = "Dictionary";
			}
		});

		//sets
		buttonAttacks.add(buttonBruteForce);
		buttonAttacks.add(buttonDictionary);
		buttonBruteForce.isSelected();
		
		//add
		panel.setLayout(null);
		panel.setBackground(Color.GRAY);
		buttonNext.setBounds(400,300,100,20);
		buttonBruteForce.setBounds(150,20,100,20);
		buttonDictionary.setBounds(250,20,100,20);
	    label.setBounds(20, 20, 200, 20);
	    panel.add(label);
		panel.add(buttonNext);
		panel.add(buttonBruteForce);
		panel.add(buttonDictionary);
		frame.add(panel);

		return frame;
	}

	//brute force UI
	private static JFrame frameBruteForceUI(JFrame frame) {

		//buttons
		String[] passLengthStrings = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13" , "14", "15", "16", "17", "18" };
		
		//choose password length
		JComboBox<Object> comboBoxPassLength = new JComboBox<Object>(passLengthStrings);
		comboBoxPassLength.setSelectedIndex(8);
		
		//choose chars
		JRadioButton buttonUppercase = new JRadioButton("Uppercase letters");
		JRadioButton buttonLowercase = new JRadioButton("Lowercase letters");
		JRadioButton buttonNumbers = new JRadioButton("Numbers");
		JRadioButton buttonSymbols = new JRadioButton("Symbols");

		buttonAttack1 = new JButton("attack");

		//panel
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("enter max password length: ");
		JLabel label2 = new JLabel("choose password chars: ");
		label = new JLabel("");
		
		//next frame and set the variables 
		buttonAttack1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(buttonUppercase.isSelected()) {
					uppercaseLetters = true;
				}
				if(buttonLowercase.isSelected()) {
					lowercaseLetters = true;
				}
				if(buttonNumbers.isSelected()) {
					numbers = true;
				}
				if(buttonSymbols.isSelected()) {
					symbols = true;
				}
				passLength = Integer.parseInt(comboBoxPassLength.getSelectedItem().toString());
				nextFrame();
			}
		});

		//add
		panel.setLayout(null);
		panel.setBackground(Color.GRAY);
		label1.setBounds(20,20,200,20);
		label2.setBounds(20,80,200,20);
		comboBoxPassLength.setBounds(250,20,50,20);
		buttonUppercase.setBounds(20,120,150,20);
		buttonLowercase.setBounds(170,120,180,20);
		buttonNumbers.setBounds(350,120,120,20);
		buttonSymbols.setBounds(470,120,120,20);
		buttonAttack1.setBounds(400,300,100,20);
		panel.add(label1);
		panel.add(label2);
		panel.add(buttonUppercase);
		panel.add(buttonLowercase);
		panel.add(buttonNumbers);
		panel.add(buttonSymbols);
		panel.add(comboBoxPassLength);
		panel.add(buttonAttack1);
		frame.add(panel);

		return frame;
	}

	//dictionary UI
	private static JFrame frameDictionaryUI(JFrame frame) {

		//buttons
		JButton buttonChooseFile = new JButton("choose file");
		buttonAttack2 = new JButton("attack");


		//panel and label
		JLabel label1 = new JLabel("enter dictionary file: ");
		JPanel panel = new JPanel();
		
		//choose the dictionary file
		// go to next frame
		buttonChooseFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(true);

				int option = fileChooser.showOpenDialog(frame);
				if(option == JFileChooser.APPROVE_OPTION){
					fileDictionary = fileChooser.getSelectedFile();
					String fileName = file.getName();
					label1.setText("File Selected: " + fileName);
				}else{
					label1.setText("Open command canceled");
				}
				nextFrame();
			}
		});

		//add
		panel.setLayout(null);
		panel.setBackground(Color.GRAY);
		label1.setBounds(20,20,200,20);
		buttonChooseFile.setBounds(150,20,200,20);
		buttonAttack2.setBounds(400,300,100,20);
		panel.add(label1);
		panel.add(buttonChooseFile);
		panel.add(buttonAttack2);
		frame.add(panel);

		return frame;
	}
	
	//attack UI
	private static JFrame frameAttackUI(JFrame frame) {
		
		//progress bar and label and panel
		JPanel panel = new JPanel();
		pb = new JProgressBar();
		label = new JLabel("");
		
		//sets
	    label.setBounds(100, 100, 200, 20);
		 pb.setMinimum(0);
		 pb.setMaximum(100);
		 pb.setStringPainted(true);
		 pb.setBounds(150,150,300,30);
		 
		 //add
		 panel.setLayout(null);
		 panel.setBackground(Color.GRAY);
		 panel.add(pb);
		 panel.add(label);
		 frame.add(panel); 
        
		return frame;
	}
	
	//set progress bar value 
	public void setProgressBarVul(int vul) {
		//System.out.println(vul);
		pb.setValue(vul);
	}
	
	//set text on attacker frame
	public void setText(String message) {
		label.setForeground(Color.green);
	    label.setText(message);
	  }
	
	//create frame window
	private static void createWindow(JFrame frame) {    
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	     
		frame.setSize(600, 400);        
		frame.setVisible(true);
	}

	//when next button is enable go to next frame
	private static void nextFrame() {
		if (frameCurrent[0].isActive()) {
			frameCurrent[0].setVisible(false);
			createWindow(frameCurrent[1]);
		}
		else if (frameCurrent[1].isActive()) {
			frameCurrent[1].setVisible(false);
			if(typeAttack.equals("BruteForce")) {
				createWindow(frameCurrent[2]);
			}
			else {
				createWindow(frameCurrent[3]);
			}
		}
		else {
			createWindow(frameCurrent[4]);
		}
	}
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main2 {
	
	//calculate how many tries there are going to be
	//get the password length and the password chars
	public static BigDecimal all(int charLen, int passLen) {
		BigDecimal result = BigDecimal.ZERO;
		for(int i = 1; i <= passLen; i++) {

			result = result.add( BigDecimal.valueOf(Math.pow(charLen, i)) );
		}
		return result;
	}

	//create all the  possible combinations
	public static void generate(FilePDF file,char[] arr, int i, String pass, int len, int passLength, MyThreadPoolExecutor pool,Frame frame, BigDecimal allTries) throws IOException 
	{ 	
		if (i == 0) 
		{ 
			pool.run(pass, file);
			return; 
		} 

		for (int j = 0; j < len; j++) 
		{ 
			String appended = pass + arr[j]; 
			generate(file, arr, i - 1, appended, len, passLength, pool,frame,allTries); 

			if(i > passLength) {
				break;
			}
		} 

		return; 
	} 
	
	//go over all length of password, from 1 to the wanted length
	public static void crack(FilePDF file, char[] arr, int len, int passLength, MyThreadPoolExecutor pool, Frame frame, BigDecimal allTries) throws IOException 
	{ 
		for (int i = 1; i <= passLength; i++) 
		{ 
			generate(file, arr, i, "", len, passLength,pool,frame,allTries); 
		} 
	} 
	
	//create an array with all of the chars
	public static char[] createArr(boolean uppercaseLetters, boolean lowercaseLetters, boolean numbers ,boolean symbols) {

		int arrLength = 0;
		if(uppercaseLetters) {
			arrLength += 26;
		}
		if(lowercaseLetters) {
			arrLength += 26;
		}
		if(numbers) {
			arrLength += 10;
		}
		if(symbols) {
			arrLength += 32;
		}

		char[] chars = new char[arrLength];
		int place = 0;

		for(int i = 0; i < 126; i++) {
			if(uppercaseLetters && i >=65 && i <=90) {
				chars[place] = (char)i;
				place++;
			}
			else if(lowercaseLetters && i >=97 && i <=122) {
				chars[place] = (char)i;
				place++;
			}
			else if(numbers && i >=0 && i <=9) {
				chars[place] = Integer.toString((char)i).toCharArray()[0];
				place++;
			}
			else if(symbols && ((i >=33 && i <=47) || (i >=58 && i <=64) || (i >=91 && i <=96) || (i >=123 && i <=126))) {
				chars[place] = (char)i;
				place++;
			}
		}

		return chars;
	}

	//go over the the file with the passwords and try the password on the file
	public static void openFile(FilePDF file, File fileDictionary, MyThreadPoolExecutor pool) throws IOException {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileDictionary));
			String pass = reader.readLine();
			while (pass != null) {
				pool.run(pass, file);
				pass = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//create and get all the things you need
	public static void start(Frame frame) throws IOException {
		//the pdf file
		FilePDF file = new FilePDF(Frame.file);
		//dictionary things
		File fileDictionary;

		//BruteForce things
		int passLength;
		boolean uppercaseLetters = false;
		boolean lowercaseLetters = false;
		boolean numbers = false;
		boolean symbols = false;
		char[] chars;
		String fileType = file.getFileType();

		//num of tries
		BigDecimal allTries = BigDecimal.ONE;

		//threads
		MyThreadPoolExecutor pool;

		//get values 
		if(Frame.typeAttack.equals("BruteForce")) {
			passLength = Frame.passLength;
			uppercaseLetters = Frame.uppercaseLetters;
			lowercaseLetters = Frame.lowercaseLetters;
			numbers = Frame.numbers;
			symbols = Frame.symbols;
			chars = createArr(uppercaseLetters, lowercaseLetters, numbers, symbols);
			allTries = all(chars.length, passLength);
			pool = new MyThreadPoolExecutor(frame, allTries);
			crack(file,chars,chars.length,passLength,pool,frame,allTries);

		}
		else {

			fileDictionary = Frame.fileDictionary;
			
			//count how many lines are in the file
			FileReader fr = new FileReader(fileDictionary);
			BufferedReader bufr = new BufferedReader(fr); 

			String line = bufr.readLine(); 
			while(line != null){ 
			 line = bufr.readLine(); 
			 allTries = allTries.add(BigDecimal.ONE); 
			} 
			bufr.close();
			
			pool = new MyThreadPoolExecutor(frame, allTries);
			openFile(file, fileDictionary,pool);
		}

	}

	//wait for the user to start and get the way to crack the pdf
	public static void run(Frame frame) throws IOException, InterruptedException {
		
		//start from button 1
		Frame.buttonAttack1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				try {
					start(frame);
				} catch (IOException e1) {
					//frame.setText("you didn't enter file");
				}
			}
		});
		//start from button 2
		Frame.buttonAttack2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				try {
					start(frame);
				} catch (IOException e1) {
					//frame.setText("you didn't enter file");
				}
			}
		});


	}
	
	//main
	public static void main(String[] args) throws IOException, InterruptedException{
		Frame frame = new Frame();
		run(frame);
	}
}

//class to manage all the threads
class MyThreadPoolExecutor{
	//get all the available cores
	int cores = Runtime.getRuntime().availableProcessors() / 2;
	//create pool
	ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(cores);
	
	boolean passFound = false;
	Frame frame;
	BigDecimal allTries;
	
	//get the frame and number of tries
	MyThreadPoolExecutor(Frame frame, BigDecimal allTries){
		this.frame = frame;
		this.allTries = allTries;
	}
	
	//the method that the threads run on
	//try the password
	//update the graphic in accordance
	public void run(String pass, FilePDF file) throws IOException {
		try {
			pool.execute(file.tryPass(pass));
			pool.execute(updateFrame(pass, file));
		}
		catch(Exception e){
			
		}
	}
	
	//check if there is password, if yes so display it on the frame
	//calculate how many tries left and change the process bar
	public Runnable updateFrame(String pass, FilePDF file) {

		Runnable run = new Runnable(){
			public void run() {
				if(file.isPassFound()) {
					frame.setText("the password is:" + file.getPass());
					pool.shutdownNow();
				}
				int precent = file.getCounter().divide(allTries, 3, RoundingMode.CEILING).multiply(BigDecimal.valueOf(100)).intValue();
				frame.setProgressBarVul(precent);

			}
		};

		return run; 
	}
}

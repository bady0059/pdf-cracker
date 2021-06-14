import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import org.apache.pdfbox.pdmodel.PDDocument;

public class FilePDF{
	
	//where the file is in the memory 
	private String filePath;
	//the file type make sure it is pdf
	private String fileType;
	//the file it self
	private File file;
	//the password
	private String password;
	//if the password found
	private boolean passFound = false;
	//count how many password tried
	private static BigDecimal counter = BigDecimal.ONE;
	
	//add counter one and print the password
	public static void myPrintln(String str) {
		counter = counter.add(BigDecimal.valueOf(1));
	  System.out.println(str);
	}
	
	public BigDecimal getCounter() {
		return counter;
	}
	
	public String getPass() {
		return password;
	}

	public boolean isPassFound() {
		return passFound;
	}

	public FilePDF(File file) {
		this.file = file;
	}

	public String getFilePath() {
		return file.getAbsolutePath();
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	//get the file type
	public String getFileType() {
		String fileName = file.getName();
		String fileType = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			fileType = fileName.substring(i+1);
		}

		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	//get a password to try on the pdf
	//if the password found print it, change the boolean to true, open the file
	//if not print the pass that was tried
	public Runnable tryPass(String pass) throws IOException {

		Runnable runnable = new Runnable() {
			public void run() {
				PDDocument document = new PDDocument();
				try {
					File input = getFile();
					document = PDDocument.load(input,pass);
					document.setAllSecurityToBeRemoved(true);
					passFound = true;
					System.out.println("oh yea, the pass is: " + pass);
					password = pass;
					if(passFound) {
						Desktop.getDesktop().open(input);
					}
				}catch(Exception e){
					myPrintln("pass try: " + pass);
				}
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		return runnable;
	}

}

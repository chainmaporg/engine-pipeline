package article;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFTextExtractor {
	
	public static String extract(String url) throws MalformedURLException, FileNotFoundException
	{
		URL url1 =
		new URL(url);

		byte[] ba1 = new byte[1024];
		int baLength;
		FileOutputStream fos1 = new FileOutputStream("download.pdf");

		try {
			// Contacting the URL
			//System.out.print("Connecting to " + url1.toString() + " ... ");
			URLConnection urlConn = url1.openConnection();

			// Checking whether the URL contains a PDF
			if (!urlConn.getContentType().equalsIgnoreCase("application/pdf")) {
				return null;
			} else {
			    try {

			    	// Read the PDF from the URL and save to a local file
			        InputStream is1 = url1.openStream();
			        while ((baLength = is1.read(ba1)) != -1) {
			        	fos1.write(ba1, 0, baLength);
			        }
			        fos1.flush();
			        fos1.close();
			        is1.close();

			        // Load the PDF document and display its page count
			        
			        File file = new File("download.pdf");
			        PDDocument doc = PDDocument.load(file);
			        
			        PDFTextStripper pdfStripper = new PDFTextStripper();
			        
			        String text = pdfStripper.getText(doc);
			        
			        doc.close();
			        
			        return text;


			    } catch (NullPointerException npe) {
			      System.out.println("FAILED.\n[" + npe.getMessage() + "]\n");
			    }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}

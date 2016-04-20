package CaptureImages;

import java.net.*;
import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Enumeration;
import java.io.*;

//import javax.swing.text.MutableAttributeSet;
//import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
//import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.parser.ParserDelegator;

public abstract class HtmlWebPageReader {

    // Variables returned from the various calls
    private String strImageUrl;
    private LocalDate ldImageDate;
    private String strImageDate;
    private String strImageId = "";
    private String strImageExtension = "jpg";

    private String strImageName = "brewsterrockit";
    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private URL urlImageWebPage;

    private LocalDate ldWorkingDate;


//    HtmlWebPageReader(DisplayDebugMessage dbgDisplayObject) throws Exception {
//	this.dbgDisplay = dbgDisplayObject;
//    }

 HtmlWebPageReader(DisplayDebugMessage dbgDisplayObject) {
}

   abstract void readImageWebPage();
   
   abstract void parseImageUrlFromImageWebPage(String strDomain);
   
    abstract String getImageUrl();

    abstract String getImageExtension();

    abstract String getImageId();
    
    abstract URL getWebPageURL();
    
    abstract void setWorkingDate(LocalDate ldWorkingDate);
    
    abstract void setImageName(String strImageName);

    abstract void setWebPageURL(URL webPageURL);

    abstract LocalDate getImageDate();

    public String createStringFromReader(BufferedReader brInput) {
	int intLineNumber = 0;
	String htmlDocumentString = "";
	String inputLine;
	try {
	    while ((inputLine = brInput.readLine()) != null) {
		++intLineNumber;
		htmlDocumentString += inputLine + "\n";

		System.out.println(intLineNumber + ")~" + inputLine);
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println("***********************************************************************************");

	return htmlDocumentString;
    }


}

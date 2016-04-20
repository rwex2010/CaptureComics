package CaptureImages;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class KevinAndKellHtmlWebPageReader extends HtmlWebPageReader {

    private String strImageUrl;
    private String strImageExtension = "jpg";
    private String strImageId;
    private String strComicCode = "kk";
    private LocalDate ldWorkingDate;
    private String strImageName;
    private URL urlImageWebPage;
    private LocalDate ldImageDate;
    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String strImageDate;

    public KevinAndKellHtmlWebPageReader(DisplayDebugMessage dbgDisplayObject) {
	super(dbgDisplayObject);
	this.dbgDisplay = dbgDisplayObject;
    }

    @Override
    void readImageWebPage() {
	//I can create the Image URL directly for Kevin and Kell sample:
	//   http://www.kevinandkell.com/2014/strips/kk20140903.jpg
	
    }
    
    @Override
    void parseImageUrlFromImageWebPage(String strDomain) {
	// TODO Auto-generated method stub
	String ImageDomain = "http://www.kevinandkell.com/";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	int workingYear = ldWorkingDate.getYear();
	strImageDate = ldWorkingDate.format(formatter); 
	strImageUrl = ImageDomain + workingYear + "/strips/"+strComicCode +strImageDate + ".jpg";
	
	strImageId = strComicCode + strImageDate;

	this.intDebugCode = 4;
	this.strDebugMsg = "**KevinAndKellWebPageURL.setupWebPageUrl** Image Url: " + strImageUrl;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

    }

    @Override
    public String getImageUrl() {
	return this.strImageUrl;
    }

    @Override
    public String getImageExtension() {
	return this.strImageExtension;
    }

    @Override
    public String getImageId() {
	return this.strImageId;
    }

    @Override
    public URL getWebPageURL() {
	return this.urlImageWebPage;
    }

    @Override
    public void setWorkingDate(LocalDate ldWorkingDate) {
	this.ldWorkingDate = ldWorkingDate;
    }

    @Override
    public void setImageName(String strImageName) {
	this.strImageName = strImageName;
    }

    @Override
    public void setWebPageURL(URL webPageURL) {
	this.urlImageWebPage = webPageURL;
    }

    @Override
    public LocalDate getImageDate() {
	return this.ldImageDate;
    }

}

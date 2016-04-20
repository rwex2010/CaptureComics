package CaptureImages;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class ArcamaxHtmlWebPageReader extends HtmlWebPageReader {

    private String strImageUrl;
    private String strServer = "http://www.arcamax.com";
    private String strImageExtension = "jpg";
    private String strImageId;
    private LocalDate ldWorkingDate;
    private String strImageName;
    private URL urlImageWebPage;
    private URL urlImageWebPageForPreviousDay;
    private String strImageWebPageForPreviousDay;
    private LocalDate ldImageDate;
    private String strImageDate;
    private BufferedReader bufferedStreamReader = null;
    private DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String NL = "\n";

    public ArcamaxHtmlWebPageReader(DisplayDebugMessage dbgDisplayObject) {
	super(dbgDisplayObject);
	this.dbgDisplay = dbgDisplayObject;
    }

    @Override
    void readImageWebPage() {
	try {
	    this.bufferedStreamReader = new BufferedReader(new InputStreamReader(this.urlImageWebPage.openStream()));
	} catch (IOException e) {
	    intDebugCode = 1;
	    strDebugMsg = NL + "ArcamaxHtmlWebPageReader ->  readImageWebPage";
	    strDebugMsg += NL + "Error caught: " + e.getMessage();
	    strDebugMsg += NL + "Error caught: " + e.toString();
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    // e.printStackTrace();
	}
    }

    @Override
    void parseImageUrlFromImageWebPage(String strDomain) {
	this.readImageWebPage();
	HTMLEditorKit.Parser parser = new ParserDelegator();
	Reader myLineReader = new LineNumberReader(bufferedStreamReader);
	this.intDebugCode = 2;
	this.strDebugMsg = NL + "dbg=" + intDebugCode + ") ArcamaxHtmlWebPageReader ->parseImageUrlFromImageWebPage ***  Next is parse *****";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	this.intDebugCode = 128;
	if (dbgDisplay.thisDoesPrint(intDebugCode)) {
	    this.strDebugMsg = NL + "dbg=" + intDebugCode + ") ArcamaxHtmlWebPageReader ->parseImageUrlFromImageWebPage *** Printing HTML *****";
	    this.strDebugMsg += NL + "WebPage URL: " + this.urlImageWebPage.toExternalForm();
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    String strHtml = super.createStringFromReader(bufferedStreamReader);
	    // System.exit(0);
	}

	ArcamaxWebPageHtmlParser htmlParserCallBack = new ArcamaxWebPageHtmlParser();
	htmlParserCallBack.setDebugMessageHandler(dbgDisplay);
	htmlParserCallBack.setWorkingDate(ldWorkingDate);
	try {
	    parser.parse(myLineReader, htmlParserCallBack, true);
	    myLineReader.close();
	    this.intDebugCode = 2;
	    this.strDebugMsg = NL + "dbg=" + intDebugCode + ") ArcamaxHtmlWebPageReader ->parseImageUrlFromImageWebPage *** Parsing is complete *****";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    bufferedStreamReader.close();
	} catch (IOException e) {
	    intDebugCode = 1;
	    strDebugMsg = NL + "ArcamaxHtmlWebPageReader ->  parseImageUrlFromImageWebPage";
	    strDebugMsg += NL + "IOException Error caught: " + e.getMessage();
	    strDebugMsg += NL + "Error caught: " + e.toString();
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	} catch (Exception e) {
	    intDebugCode = 1;
	    strDebugMsg = NL + "ArcamaxHtmlWebPageReader ->  parseImageUrlFromImageWebPage";
	    strDebugMsg += NL + "Error caught: " + e.getMessage();
	    strDebugMsg += NL + "Error caught: " + e.toString();
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	}
	this.strImageWebPageForPreviousDay = htmlParserCallBack.getStrImageWebPageUrlForPreviousDay();
	strImageDate = htmlParserCallBack.getImageStringDate();
	ldImageDate = htmlParserCallBack.getImageLocalDate();
	strImageUrl = this.strServer + htmlParserCallBack.getImageUrl();

	try {
	    strImageId = strImageName + ldImageDate.format(dtImageDateFormatter);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    intDebugCode = 1;
	    strDebugMsg += NL + "**************Error caught *****************";
	    strDebugMsg = NL + "ArcamaxHtmlWebPageReader ->  parseImageUrlFromImageWebPage";
	    strDebugMsg += NL + "Error caught: " + e.getMessage();
	    strDebugMsg += NL + "Error caught: " + e.toString();
	    strDebugMsg += NL + "strImageName: " + strImageName;
	    strDebugMsg += NL + "strImageUrl: " + strImageUrl;
	    strDebugMsg += NL + "strImageDate: " + strImageDate;
	    strDebugMsg += NL + "ldImageDate: " + ldImageDate;
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	}

	this.intDebugCode = 4;
	this.strDebugMsg = NL + "dbg=" + intDebugCode + ") ArcamaxHtmlWebPageReader ->parseImageUrlFromImageWebPage*** Capturing data *****";
	this.strDebugMsg += NL + "image Url: " + strImageUrl;
	this.strDebugMsg += NL + "Url for previous day: " + this.strImageWebPageForPreviousDay;
	this.strDebugMsg += NL + "String Image Date: " + strImageDate;
	this.strDebugMsg += NL + "Image Date: " + ldImageDate.toString();
	this.strDebugMsg += NL + "image ID: " + strImageId;
	// this.strDebugMsgArray = new String[5];
	// this.strDebugMsgArray[0] = NL + "dbg=" + intDebugCode +
	// ") ArcamaxHtmlWebPageReader ->parseImageUrlFromImageWebPage*** Capturing data *****";
	// this.strDebugMsgArray[1] = "image Url: " + strImageUrl;
	// this.strDebugMsgArray[2] = "String Image Date: " + strImageDate;
	// this.strDebugMsgArray[3] = "Image Date: " + ldImageDate.toString();
	// this.strDebugMsgArray[4] = "image ID: " + strImageId;
	// dbgDisplay.ShowMessageArray(strDebugMsgArray, intDebugCode);
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

    public String getStrImageWebPageForPreviousDay() {
	return strImageWebPageForPreviousDay;
    }

    public void setStrImageWebPageForPreviousDay(String strImageWebPageForPreviousDay) {
	this.strImageWebPageForPreviousDay = strImageWebPageForPreviousDay;
    }

}

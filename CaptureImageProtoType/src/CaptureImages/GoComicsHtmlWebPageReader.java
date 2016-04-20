package CaptureImages;

import java.io.*;
import java.net.*;
import java.time.LocalDate;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class GoComicsHtmlWebPageReader extends HtmlWebPageReader {

    private String strImageUrl;
    private String strImageExtension = "jpg";
    private String strImageId;
    private LocalDate ldWorkingDate;
    private String strImageName;
    private URL urlImageWebPage;
    private LocalDate ldImageDate;
    private BufferedReader bufferedStreamReader = null;
    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String strImageDate;
    private GoComicsStepThruWebPageTagsToGetImageUrl StepsToFollow;

    public String strResultsTest1 = "Not Yet";
    public String strResultsTest2 = "Not Yet";
    public String strResultsTest3 = "Not Yet";

    public GoComicsHtmlWebPageReader(DisplayDebugMessage dbgDisplayObject) throws Exception {
	super(dbgDisplayObject);
	this.dbgDisplay = dbgDisplayObject;
    }

    @Override
    public void readImageWebPage() {
	try {
	    this.bufferedStreamReader = new BufferedReader(new InputStreamReader(this.urlImageWebPage.openStream()));
	    strResultsTest1 = "GotIt";
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    public void parseImageUrlFromImageWebPage(String strDomain) {
	this.readImageWebPage();

	HTMLEditorKit.Parser parser = new ParserDelegator();
	Reader myLineReader = new LineNumberReader(bufferedStreamReader);
	this.intDebugCode = 2;
	this.strDebugMsg = "*****URLReader.createImageUrl ***  Next is parse *****";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	this.intDebugCode = 128;
	if (dbgDisplay.thisDoesPrint(intDebugCode)) {
	    this.strDebugMsg = "*****URLReader.createImageUrl *** Printing HTML *****";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    String strHtml = super.createStringFromReader(bufferedStreamReader);
	}
	/*
	 * 
	 */
//	GoComicsWebPageHtmlParser htmlParserCallBack = new GoComicsWebPageHtmlParser();

	// ***** FOLLOWING CODE FOR GENERIC CALL TO HTML PARSER
	RichWebPageHtmlParser htmlParserCallBack = new RichWebPageHtmlParser();
	StepsToFollow = new GoComicsStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
	htmlParserCallBack.setStepsToFollow(StepsToFollow);

	htmlParserCallBack.setDebugMessageHandler(dbgDisplay);
	htmlParserCallBack.setWorkingDate(ldWorkingDate);
	htmlParserCallBack.setImageName(strImageName);
	try {
	    parser.parse(myLineReader, htmlParserCallBack, true);
	    myLineReader.close();
	    strResultsTest2 = "CompletedNoErrors";
	    this.intDebugCode = 2;
	    this.strDebugMsg = "*****URLReader.createImageUrl *** Parsing is complete *****";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    bufferedStreamReader.close();
	} catch (IOException e) {

	    System.out.println("*** IOException Error ***");
	    System.out.println(e.getMessage());
	    e.printStackTrace();

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    System.out.println("*** oops ***");
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	}
	strResultsTest3 = htmlParserCallBack.getStepObjectName();
	strImageDate = htmlParserCallBack.getImageStringDate();
	ldImageDate = htmlParserCallBack.getImageLocalDate();
	strImageUrl = htmlParserCallBack.getImageUrl();
	strImageId = strImageName + strImageDate;

	this.intDebugCode = 4;
	this.strDebugMsgArray = new String[5];
	this.strDebugMsgArray[0] = "*****URLReader.createImageUrl *** Parsing is complete *****";
	this.strDebugMsgArray[1] = "image Url: " + strImageUrl;
	this.strDebugMsgArray[2] = "String Image Date: " + strImageDate;
//	this.strDebugMsgArray[3] = "Image Date: " + ldImageDate.toString();
	this.strDebugMsgArray[4] = "image ID: " + strImageId;
	dbgDisplay.ShowMessageArray(strDebugMsgArray, intDebugCode);
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
    
    public String getStepObjectName() {
	return StepsToFollow.strObjectName;
    }


}

package CaptureImages;

//import java.io.LineNumberReader;
//import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Tag;

/**
 * *****************************************************************************
 * 
 * This class is the callback that feeds the parser
 * 
 * @author wecksr
 * 
 *         *********************************************************************
 *         ********
 */
class RichWebPageHtmlParser extends HTMLEditorKit.ParserCallback {
    // class SwingHtmlParser extends HtmlParserCallback {

    // 9=output everything
    private ProcessStartTags objProcessStartTags = new ProcessStartTags();
     private GoComicsStepThruWebPageTagsToGetImageUrl StepsToFollow;
    // private DilbertStepThruWebPageTagsToGetImageUrl StepsToFollow;
//    private StepThruWebPageTagsToGetImageUrl StepsToFollow;
    private String MyResults = "NotWhatIWasLookingFor";
    private String MyTestResults = "";
    private int MyTestStepToCheck = 0;

    // private boolean blnFoundDate = false;
    private String strImageName = "";
    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    private LocalDate ldWorkingDate;
    // private DateTimeFormatter dtFormatter =
    // DateTimeFormatter.ofPattern("MM/dd/yyyy");

    // private boolean blnFoundRightDivTag = false;
    // private boolean blnLookForFirstDivTag = true;
    // private String strComicDateNoDelimeter = "";
    // private String strTagToFind = "div";

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 16;
    private String NL = "\n";

    public void setDebugMessageHandler(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

    public void setWorkingDate(LocalDate ldWorkingDate) {
	this.ldWorkingDate = ldWorkingDate;
    }

    public void setImageName(String strImageName) {
	this.strImageName = strImageName;
    }

     public void setStepsToFollow(GoComicsStepThruWebPageTagsToGetImageUrl stepsToFolllow) {
//    public void setStepsToFollow(StepThruWebPageTagsToGetImageUrl stepsToFolllow) {
	this.StepsToFollow = stepsToFolllow;
    }

    public void initializeStepsToFollow() {
	// StepsToFollow = new
	// DilbertStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
	StepsToFollow = new GoComicsStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
    }

    public void handleSimpleTag(Tag tag, MutableAttributeSet attrSet, int pos) {
	handleAllStartTags(tag, attrSet, pos);
    }

    private void printTagWithAttributes(Tag tag, MutableAttributeSet attrSet) {
	intDebugCode = 64;
	this.strDebugMsg = "*************************************\nParse Html - printTagWithAttributes:\n";
	strDebugMsg += "tag: " + tag.toString();
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    }

    public void handleStartTag(Tag tag, MutableAttributeSet attrSet, int pos) {
	handleAllStartTags(tag, attrSet, pos);
    }

    public void handleText(char[] data, int pos) {
	intDebugCode = 64;
	this.strDebugMsg = "*************************************\nParse Html - handleText:\n";
	strDebugMsg += "Tag Text => " + new String(data);
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    }

    public void handleComment(char[] data, int pos) {
	intDebugCode = 64;
	this.strDebugMsg = "*************************************\nParse Html - handleComment:\n";
	strDebugMsg += "Comment Text => " + new String(data);
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    }

    public void handleEndOfLineString(String data) {
	// This is invoked after the stream has been parsed, but before
	// flush.
	// eol will be one of \n, \r or \r\n, which ever is
	// encountered the most in parsing the stream.
	intDebugCode = 64;
	this.strDebugMsg = "*************************************\nParse Html - handleEndOfLineString:\n";
	strDebugMsg += "End of Line String => " + data;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    }

    public void handleEndTag(Tag tag, int pos) {

	intDebugCode = 64;
	this.strDebugMsg = "*************************************\nParse Html - handleEndTag:\n";
	strDebugMsg += "Closing: " + tag.toString();
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    }

    public void handleError(String err, int pos) {
	intDebugCode = 64;
	this.strDebugMsg = "*************************************\nParse Html - handleError:\n";
	strDebugMsg += "Error => " + err;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    }

    /*
     * Call to new class
     */
    private void handleAllStartTags(Tag tagReturnedFromParse, MutableAttributeSet attributeSetReturnedFromParse, int pos) {
	if (StepsToFollow == null) {
	    initializeStepsToFollow();
	}
	StepsToFollow.processNextStep();
	try {
	    if (StepsToFollow.tagToFind == null) {
		this.strImageDate = StepsToFollow.getStrImageDate();
		this.strImageUrl = StepsToFollow.getStrImageUrl();
		this.ldImageDate = StepsToFollow.getLdImageDate();
	    } else {
		if (StepsToFollow.tagToFind.equals(tagReturnedFromParse)) {
		    if (MyTestStepToCheck != StepsToFollow.intNextStepToCheck) {
			String strTagToFind = StepsToFollow.tagToFind.toString();
			MyTestResults += strTagToFind + "|";
			MyTestStepToCheck = StepsToFollow.intNextStepToCheck;
		    }
		    MyResults = objProcessStartTags.processThisTag(tagReturnedFromParse, attributeSetReturnedFromParse, StepsToFollow.tagToFind, StepsToFollow.attributeToFind, StepsToFollow.strValueToFind, StepsToFollow.intLengthOfValueToFind, StepsToFollow.attributeToGetValueFrom);
		    StepsToFollow.checkReturnValues(MyResults);
		}
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    String myMsg = "Error:";
	    myMsg += NL + "RichWebPageHtmlParser";
	    myMsg += NL + "imageName: " + this.strImageName;
	    myMsg += NL + "Tag returned: " + tagReturnedFromParse;
	    myMsg += NL + "Tag to find: " + StepsToFollow.tagToFind;
	    myMsg += NL + "Next step to check: " + StepsToFollow.intNextStepToCheck;
	    System.out.println(myMsg);
	}

    }

    /*
     * End of call to new class
     */


    public String getImageStringDate() {
	if (this.strImageDate == null) {
	    this.strImageDate = StepsToFollow.getStrImageDate();
	}
	return strImageDate;
    }

    public LocalDate getImageLocalDate() {
	if (ldImageDate == null) {
	    this.ldImageDate = StepsToFollow.getLdImageDate();
	}
	return ldImageDate;
    }

    public String getImageUrl() {
	if (this.strImageUrl == null) {
	    this.strImageUrl = StepsToFollow.getStrImageUrl();
	}
	return strImageUrl;
    }

    public String getMyTestResults() {
	return MyTestResults;
    }

    public String getStepObjectName() {
	String objName = StepsToFollow.getStrObjectName();
	// return StepsToFollow.strObjectName;
	return objName;
    }

}
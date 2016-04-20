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
class GoComicsWebPageHtmlParser extends HTMLEditorKit.ParserCallback {
    // class SwingHtmlParser extends HtmlParserCallback {

    // 9=output everything
    private ProcessStartTags objProcessStartTags = new ProcessStartTags();
    private GoComicsStepThruWebPageTagsToGetImageUrl StepsToFollow;
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

    public void initializeStepsToFollow() {
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
	    myMsg += NL + "GoComicsWebPageHtmlParser";
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

    /**
     * method to handle all Start tags and locates the ones needed. The switch
     * statement must execute each case statement in the following order 1 -
     * 'div' - this looks for the div tag with an id of 'content' and skips over
     * all up to that point 2 - 'h1' - this tag does not contain any information
     * but a child of this tag is the 'a' tag 3 - 'a' - This tag contains an
     * href attribute with a value that contains the date of the image 4 - 'p' -
     * This tag also does not contain any information, but the next 'img' tag
     * contains the url information 5 - 'img' - This tag contains an src
     * attribute with a value that contains the url of the image with the 'code'
     * for this image ***
     * 
     * @param tag
     *            is the HTML.Tag that this routine is parsing
     * @param attrSet
     *            is a set of the attributes that a are part of this tag
     * @param pos
     *            is the position in the reader that this tag is located.
     */
    /*
     * start of old code private void handleAllStartTags(Tag tag,
     * MutableAttributeSet attrSet, int pos) { if (strTagToFind != "done") {
     * 
     * intDebugCode = 16; this.strDebugMsg =
     * "*************************************\nParse Html - handleAllStartTags:\n"
     * ; strDebugMsg += "strTagToFind: " + strTagToFind; strDebugMsg +=
     * "\ntag: " + tag.toString(); dbgDisplay.ShowMessage(strDebugMsg,
     * intDebugCode);
     * 
     * switch (strTagToFind) { case "div": if (tag == HTML.Tag.DIV) { if
     * (this.blnLookForFirstDivTag) { blnFoundRightDivTag = FindRightDivTag(tag,
     * attrSet, pos); if (blnFoundRightDivTag) { MyTestResults += strTagToFind +
     * "|"; displayThisTag(tag, attrSet, pos); strTagToFind = "h1";
     * this.blnLookForFirstDivTag = false; }
     * 
     * } else { blnFoundRightDivTag = FindRightDivTag(tag, attrSet, pos); if
     * (blnFoundRightDivTag) { MyTestResults += strTagToFind + "|";
     * displayThisTag(tag, attrSet, pos); strTagToFind = "img"; } } } break;
     * 
     * case "h1": if (tag == HTML.Tag.H1) { MyTestResults += strTagToFind + "|";
     * displayThisTag(tag, attrSet, pos); strTagToFind = "a"; } break;
     * 
     * case "a": if (tag == HTML.Tag.A) { displayThisTag(tag, attrSet, pos);
     * String strHrefAtt = findProperAttributes(tag, attrSet, pos,
     * HTML.Attribute.HREF); blnFoundDate = CreateDateString(strHrefAtt); if
     * (blnFoundDate) { MyTestResults += strTagToFind + "|"; strTagToFind =
     * "div"; } else { MyTestResults += strTagToFind + "|"; strTagToFind =
     * "done"; } } break;
     * 
     * case "p": if (tag == HTML.Tag.P) { MyTestResults += strTagToFind + "|";
     * displayThisTag(tag, attrSet, pos); strTagToFind = "img"; } break;
     * 
     * case "img": // look for 'src' attribute if (tag == HTML.Tag.IMG) {
     * MyTestResults += strTagToFind + "|"; displayThisTag(tag, attrSet, pos);
     * String strSrcAtt = findProperAttributes(tag, attrSet, pos,
     * HTML.Attribute.SRC); intDebugCode = 16; strDebugMsg =
     * "Tag: img -- Att: src: " + strSrcAtt; dbgDisplay.ShowMessage(strDebugMsg,
     * intDebugCode); strImageUrl = strSrcAtt; strTagToFind = "done"; } break;
     * 
     * default: break; } } }
     * 
     * private void displayThisTag(Tag tag, MutableAttributeSet attrSet, int
     * pos) { intDebugCode = 32; int intAttCnt = attrSet.getAttributeCount();
     * this.strDebugMsg = pos + "~Tag =>~" + tag.toString() + "~Attribute cnt: "
     * + intAttCnt;
     * 
     * if (intAttCnt > 0) { Enumeration<?> attributeNames =
     * attrSet.getAttributeNames(); while (attributeNames.hasMoreElements()) {
     * Object objAttrName = attributeNames.nextElement(); String attrName =
     * objAttrName.toString(); strDebugMsg += "    Tag With Attributes => " +
     * attrName + " : " + attrSet.getAttribute(objAttrName) + "\n"; } }
     * dbgDisplay.ShowMessage(strDebugMsg, intDebugCode); } /* end of old code
     */

    /**
     * 
     * @param tag
     * @param attrSet
     * @param pos
     * @param htmlAtt
     * @return
     */
    /*
     * Start of old code 2 of 2 private String findProperAttributes(Tag tag,
     * MutableAttributeSet attrSet, int pos, HTML.Attribute htmlAtt) { String
     * strReturnValue = null; int intAttCnt = attrSet.getAttributeCount();
     * 
     * if (intAttCnt > 0) { Enumeration<?> attributeNames =
     * attrSet.getAttributeNames(); while (attributeNames.hasMoreElements()) {
     * Object objAttrName = attributeNames.nextElement(); String attrName =
     * objAttrName.toString(); intDebugCode = 16; this.strDebugMsg =
     * "*************************************\nParse Html - findProperAttributes:\n"
     * ; strDebugMsg += "Tag With Attributes => " + attrName + " : " +
     * attrSet.getAttribute(objAttrName); dbgDisplay.ShowMessage(strDebugMsg,
     * intDebugCode);
     * 
     * String strAttValue = (String) attrSet.getAttribute(htmlAtt); if
     * (strAttValue != null && strReturnValue == null) { strReturnValue =
     * strAttValue; } }
     * 
     * }
     * 
     * return strReturnValue; }
     * 
     * private boolean FindRightDivTag(Tag tag, MutableAttributeSet attrSet, int
     * pos) { boolean blnReturnValue = false; try { String strDivId = "";
     * strDivId = (String) attrSet.getAttribute(HTML.Attribute.ID); String
     * strToCompare = "mutable_"; if (blnLookForFirstDivTag) { strToCompare =
     * "content";
     * 
     * } else { if (!(strDivId == null || strDivId == "")) { if
     * (strDivId.length() > 8) { strDivId = strDivId.substring(0, 8); } } } if
     * (strToCompare.equals(strDivId)) { blnReturnValue = true; } } catch
     * (Exception e) { intDebugCode = 1; this.strDebugMsg =
     * "*!*!*!*! htmlParser Callback.FindRightDivTag *!*!*!*!*!*!*"; strDebugMsg
     * += "\nException caught: tag - " + tag.toString() + "\n" + e.toString();
     * strDebugMsg += "\n" + e.getStackTrace()[0];
     * dbgDisplay.ShowMessage(strDebugMsg, intDebugCode); } return
     * blnReturnValue; }
     * 
     * private boolean CreateDateString(String strHref) { boolean blnReturnValue
     * = false;
     * 
     * String[] aryHrefDate = strHref.split("/"); intDebugCode = 64;
     * this.strDebugMsg =
     * "*************************************\nParse Html - CreateDateString:\n"
     * ; for (int ix = 0; ix < aryHrefDate.length; ix++) { strDebugMsg +=
     * "\nix = " + ix + ") value: " + aryHrefDate[ix]; }
     * dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
     * 
     * if (aryHrefDate.length >= 5) {
     * 
     * strImageDate = aryHrefDate[2] + aryHrefDate[3] + aryHrefDate[4];
     * 
     * Integer intYear = new Integer(aryHrefDate[2]); Integer intMonth = new
     * Integer(aryHrefDate[3]); Integer intDay = new Integer(aryHrefDate[4]);
     * ldImageDate = LocalDate.of(intYear, intMonth, intDay);
     * 
     * if (ldImageDate.equals(this.ldWorkingDate)) { intDebugCode = 16;
     * strDebugMsg =
     * "*************************************\nParse Html - CreateDateString:\n"
     * ; strDebugMsg += "noDelimeterDate: " + strComicDateNoDelimeter;
     * dbgDisplay.ShowMessage(strDebugMsg, intDebugCode); blnReturnValue = true;
     * } else { intDebugCode = 1; strDebugMsg =
     * "*************************************\nParse Html - CreateDateString:\n"
     * ; strDebugMsg += "Dates don't line up\n"; strDebugMsg += "Comic Date: " +
     * ldImageDate.format(dtFormatter)+"\n"; strDebugMsg += "Working Date: " +
     * this.ldWorkingDate.format(dtFormatter)+"\n"; strDebugMsg +=
     * "Comic Name: " + this.strImageName;
     * 
     * dbgDisplay.ShowMessage(strDebugMsg, intDebugCode); }
     * 
     * }
     * 
     * return blnReturnValue; } /* end of old code 2 of 2
     */

    public String getImageStringDate() {
	return strImageDate;
    }

    public LocalDate getImageLocalDate() {
	return ldImageDate;
    }

    public String getImageUrl() {
	return strImageUrl;
    }

    public String getMyTestResults() {
	return MyTestResults;
    }

    public String getStepObjectName() {
	return StepsToFollow.strObjectName;
    }

}
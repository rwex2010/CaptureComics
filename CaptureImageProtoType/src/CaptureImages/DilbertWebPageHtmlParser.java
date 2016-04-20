package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class DilbertWebPageHtmlParser extends HTMLEditorKit.ParserCallback {
    private ProcessStartTags objProcessStartTags = new ProcessStartTags();
    private DilbertStepThruWebPageTagsToGetImageUrl StepsToFollow;
    private String MyResults = "NotWhatIWasLookingFor";
    private String MyTestResults = "";
    private int MyTestStepToCheck = 0;

    // private boolean blnFoundDate = false;
    private String strImageName = "";
    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    private LocalDate ldWorkingDate;
//    private DateTimeFormatter dtWorkingDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    private DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

//    private boolean blnFoundRightDivTag = false;
//    private boolean blnLookForFirstDivTag = true;
//    private String strComicDateNoDelimeter = "";
//    private String strTagToFind = "div";

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
	StepsToFollow = new DilbertStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
    }

    @Override
    public void handleStartTag(Tag tag, MutableAttributeSet attrSet, int pos) {
	handleAllStartTags(tag, attrSet, pos);
    }

    @Override
    public void handleSimpleTag(Tag tag, MutableAttributeSet attrSet, int pos) {
	handleAllStartTags(tag, attrSet, pos);
    }

    @Override
    public void handleError(String errorMsg, int pos) {
	intDebugCode = 64;
	this.strDebugMsg = "*************************************\nParse Html - handleError:\n";
	strDebugMsg += "Error => " + errorMsg;
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
	    myMsg += NL + "DilbertWebPageHtmlParser";
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
     * start of old code
     */
    // private void handleAllStartTags(Tag tag, MutableAttributeSet attrSet, int
    // pos) {
    // if (strTagToFind != "done") {
    //
    // intDebugCode = 16;
    // this.strDebugMsg =
    // "*************************************\nParse Html - handleAllStartTags:\n";
    // strDebugMsg += "strTagToFind: " + strTagToFind;
    // strDebugMsg += "\ntag: " + tag.toString();
    // dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    //
    // switch (strTagToFind) {
    // case "div":
    // if (tag == HTML.Tag.DIV) {
    // if (this.blnLookForFirstDivTag) {
    // blnFoundRightDivTag = FindRightDivTag(tag, attrSet, pos);
    // if (blnFoundRightDivTag) {
    // displayThisTag(tag, attrSet, pos);
    // this.ldImageDate = this.ldWorkingDate;
    // this.strImageDate = this.ldWorkingDate.format(dtImageDateFormatter);
    // strTagToFind = "img";
    // }
    // }
    // }
    // break;
    //
    // case "img": // look for 'src' attribute
    // if (tag == HTML.Tag.IMG) {
    // displayThisTag(tag, attrSet, pos);
    // String strSrcAtt = findProperAttributes(tag, attrSet, pos,
    // HTML.Attribute.SRC);
    // intDebugCode = 16;
    // strDebugMsg = "Tag: img -- Att: src: " + strSrcAtt;
    // dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    // strImageUrl = strSrcAtt;
    // strTagToFind = "done";
    // }
    // break;
    //
    // default:
    // break;
    // }
    // }
    // }
    // /**
    // * Divtag looks like this:
    // * <div style="display:none;" id="strip_enlarged_2014-08-28">
    // * <img
    // src="/dyn/str_strip/000000000/00000000/0000000/200000/20000/7000/700/227707/227707.strip.zoom.gif"
    // title="The Dilbert Strip for August 28, 2014"/>
    // * </div>
    // * @param tag
    // * @param attrSet
    // * @param pos
    // * @return
    // */
    // private boolean FindRightDivTag(Tag tag, MutableAttributeSet attrSet, int
    // pos) {
    // boolean blnReturnValue = false;
    // try {
    // String strDivId = "";
    // strDivId = (String) attrSet.getAttribute(HTML.Attribute.ID);
    //
    // String strToCompare = "strip_enlarged_" +
    // ldWorkingDate.format(dtWorkingDateFormatter);
    // if (!(strDivId == null || strDivId == "")) {
    // if (strToCompare.equals(strDivId)) {
    // blnReturnValue = true;
    // }
    // }
    // } catch (Exception e) {
    // intDebugCode = 1;
    // this.strDebugMsg =
    // "*!*!*!*! htmlParser Callback.FindRightDivTag *!*!*!*!*!*!*";
    // strDebugMsg += "\nException caught: tag - " + tag.toString() + "\n" +
    // e.toString();
    // strDebugMsg += "\n" + e.getStackTrace()[0];
    // dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    // }
    // return blnReturnValue;
    // }
    //
    // private String findProperAttributes(Tag tag, MutableAttributeSet attrSet,
    // int pos, HTML.Attribute htmlAtt) {
    // String strReturnValue = null;
    // int intAttCnt = attrSet.getAttributeCount();
    //
    // if (intAttCnt > 0) {
    // Enumeration<?> attributeNames = attrSet.getAttributeNames();
    // while (attributeNames.hasMoreElements()) {
    // Object objAttrName = attributeNames.nextElement();
    // String attrName = objAttrName.toString();
    // intDebugCode = 16;
    // this.strDebugMsg =
    // "*************************************\nParse Html - findProperAttributes:\n";
    // strDebugMsg += "Tag With Attributes => " + attrName + " : " +
    // attrSet.getAttribute(objAttrName);
    // dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    //
    // String strAttValue = (String) attrSet.getAttribute(htmlAtt);
    // if (strAttValue != null && strReturnValue == null) {
    //
    // strReturnValue = "http://dilbert.com"+strAttValue;
    // }
    // }
    //
    // }
    //
    // return strReturnValue;
    // }
    //

    /*
     * end of old code
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

    private void displayThisTag(Tag tag, MutableAttributeSet attrSet, int pos) {
	intDebugCode = 32;
	int intAttCnt = attrSet.getAttributeCount();
	this.strDebugMsg = pos + "~Tag =>~" + tag.toString() + "~Attribute cnt: " + intAttCnt;

	if (intAttCnt > 0) {
	    Enumeration<?> attributeNames = attrSet.getAttributeNames();
	    while (attributeNames.hasMoreElements()) {
		Object objAttrName = attributeNames.nextElement();
		String attrName = objAttrName.toString();
		strDebugMsg += "    Tag With Attributes => " + attrName + " : " + attrSet.getAttribute(objAttrName) + "\n";
	    }
	}
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    }

}

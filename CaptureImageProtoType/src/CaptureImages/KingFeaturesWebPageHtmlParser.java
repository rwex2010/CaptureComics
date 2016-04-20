package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.util.Properties;

public class KingFeaturesWebPageHtmlParser extends HtmlParserCallback {
    private ProcessStartTags objProcessStartTags = new ProcessStartTags();
    private KingFeaturesStepThruWebPageTagsToGetImageUrl StepsToFollow;
    private String MyResults = "NotWhatIWasLookingFor";
    private String MyTestResults = "";
    private int MyTestStepToCheck = 0;

    private String strImageName = "";

    private boolean blnFoundDate = false;
    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    private LocalDate ldWorkingDate;
    private DateTimeFormatter dtWorkingDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private boolean blnFoundRightDivTag = false;
    private boolean blnLookForFirstDivTag = true;
    private String strComicDateNoDelimeter = "";
    private String strTagToFind = "div";

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 16;
    private String NL = "\n";

    public KingFeaturesWebPageHtmlParser() {
    }

    public void setImageName(String strImageName) {
	this.strImageName = strImageName;
    }

    public void initializeStepsToFollow() {
	StepsToFollow = new KingFeaturesStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
    }
public void setWorkingDate(LocalDate ldWorkingDate) {
	this.ldWorkingDate = ldWorkingDate;
    }

    public void setDebugMessageHandler(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

    public String getImageStringDate() {
	return strImageDate;
    }

    public LocalDate getImageLocalDate() {
	return ldImageDate;
    }

    public String getImageUrl() {
	return strImageUrl;
    }

    @Override
    public void flush() throws BadLocationException {
    }

    @Override
    public void handleText(char[] data, int pos) {
    }

    @Override
    public void handleComment(char[] data, int pos) {
    }

    @Override
    public void handleStartTag(Tag tag, MutableAttributeSet attrSet, int pos) {
	handleAllStartTags(tag, attrSet, pos);
    }

    @Override
    public void handleEndTag(Tag t, int pos) {
    }

    @Override
    public void handleSimpleTag(Tag tag, MutableAttributeSet attrSet, int pos) {
	handleAllStartTags(tag, attrSet, pos);
    }

    @Override
    public void handleError(String errorMsg, int pos) {
    }

    @Override
    public void handleEndOfLineString(String eol) {
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
		MyResults = "NotYet";
		if (StepsToFollow.tagToFind.equals(tagReturnedFromParse)) {
		    whatThisReturns(attributeSetReturnedFromParse, tagReturnedFromParse);
		    String attributeName = StepsToFollow.strAttributeToFind;
		    String propertyValue = StepsToFollow.strValueToFind;
		    String contentAttribute = StepsToFollow.strAttributeToGetValueFrom;
		    if (attributeSetReturnedFromParse.containsAttribute(attributeName, propertyValue)) {
			Properties result = new Properties();
			Enumeration<?> names = attributeSetReturnedFromParse.getAttributeNames();
			Object name;
			while (names.hasMoreElements()) {
			    name = names.nextElement();
			    result.setProperty(name.toString(), attributeSetReturnedFromParse.getAttribute(name).toString());
			}
			MyResults = result.getProperty(contentAttribute); 
		    }

//		    if (MyTestStepToCheck != StepsToFollow.intNextStepToCheck) {
//			String strTagToFind = StepsToFollow.tagToFind.toString();
//			MyTestResults += strTagToFind + "|";
//			MyTestStepToCheck = StepsToFollow.intNextStepToCheck;
//		    }
//		    MyResults = objProcessStartTags.processThisTag(tagReturnedFromParse, attributeSetReturnedFromParse, StepsToFollow.tagToFind, StepsToFollow.attributeToFind, StepsToFollow.strValueToFind, StepsToFollow.intLengthOfValueToFind, StepsToFollow.attributeToGetValueFrom);
		    StepsToFollow.checkReturnValues(MyResults);
		}
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    String myMsg = "Error:";
	    myMsg += NL + "KingFeaturesWebPageHtmlParser";
	    myMsg += NL + "imageName: " + this.strImageName;
	    myMsg += NL + "Tag returned: " + tagReturnedFromParse;
	    myMsg += NL + "Tag to find: " + StepsToFollow.tagToFind;
	    myMsg += NL + "Next step to check: " + StepsToFollow.intNextStepToCheck;
	    System.out.println(myMsg);
	}
	
    }
	private void whatThisReturns(MutableAttributeSet attributeSetReturnedFromParse, Tag tagReturnedFromParse) {
//	    String printThisOut = "**" + tagReturnedFromParse.toString();
//		Properties result = new Properties();
//		Enumeration<?> names = attributeSetReturnedFromParse.getAttributeNames();
//		Object name;
//		while (names.hasMoreElements()) {
//		    name = names.nextElement();
//		    result.setProperty(name.toString(), attributeSetReturnedFromParse.getAttribute(name).toString());
//		    printThisOut += result + "|";
//		}
//	System.out.println(printThisOut);
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
//    private void handleAllStartTags(Tag tag, MutableAttributeSet attrSet, int pos) {
//	if (strTagToFind != "done") {
//
//	    intDebugCode = 16;
//	    this.strDebugMsg = "*************************************\nParse Html - handleAllStartTags:\n";
//	    strDebugMsg += "strTagToFind: " + strTagToFind;
//	    strDebugMsg += "\ntag: " + tag.toString();
//	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
//
//	    switch (strTagToFind) {
//	    case "div":
//		if (tag == HTML.Tag.DIV) {
//		    // if (this.blnLookForFirstDivTag) {
//		    blnFoundRightDivTag = FindRightDivTag(tag, attrSet, pos);
//		    if (blnFoundRightDivTag) {
//			displayThisTag(tag, attrSet, pos);
//			strTagToFind = "input";
//		    }
//		    // }
//		}
//		break;
//
//	    case "input": // now look for date
//		if (tag == HTML.Tag.INPUT) {
//		    displayThisTag(tag, attrSet, pos);
//		    if (findRightInputTag(tag, attrSet, pos)) {
//			this.strImageDate = (String) attrSet.getAttribute(HTML.Attribute.VALUE);
//			this.ldImageDate = LocalDate.parse(strImageDate, dtImageDateFormatter);
//			strTagToFind = "img";
//		    }
//		}
//		break;
//
//	    case "img": // look for 'src' attribute
//		if (tag == HTML.Tag.IMG) {
//		    String strSrcAtt = findProperAttributes(tag, attrSet, pos, HTML.Attribute.SRC);
//		    intDebugCode = 16;
//		    strDebugMsg = "Tag: img -- Att: src: " + strSrcAtt;
//		    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
//		    strImageUrl = strSrcAtt;
//		    strTagToFind = "done";
//		}
//		break;
//
//	    default:
//		break;
//	    }
//	}
//    }
//
//    /**
//     * Divtag looks like this: <div class="entry-content"> <form name='safr'
//     * action='http://shop.kingfeatures.com/' method='post'> <input
//     * type='hidden' name='screenResUri' value=
//     * 'http://safr.kingfeatures.com/idn/etv/zone/dispimg.php?file=L2hvbWUvdmlydHVhbC9raW5nZmVhdHVyZXMuY29tL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS92YXIvd3d3L2NhY2hlL0JlZXRsZUJhaWxleS8yMDE0LzA5L0JlZXRsZV9CYWlsZXkuMjAxNDA5MDhfNzIwLmpwZw=='/
//     * > <input type='hidden' name='printResUri' value=
//     * 'http://safr.kingfeatures.com/idn/etv/zone/hiresimg.php?file=L2hvbWUvdmlydHVhbC9raW5nZmVhdHVyZXMuY29tL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS92YXIvd3d3L3JlcG8vQmVldGxlQmFpbGV5LzIwMTQvMDkvQmVldGxlX0JhaWxleS4yMDE0MDkwOC5qcGc='/
//     * > <input type='hidden' name='PrintResWidth' value='2175'/> <input
//     * type='hidden' name='PrintResHeight' value='801'/> <input type='hidden'
//     * name='featureDate' value='20140908'/> <input type='hidden'
//     * name='featureName' value='Beetle Bailey'/> <img src=
//     * "http://safr.kingfeatures.com/idn/test/zone/xml/content.php?file=aHR0cDovL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS9CZWV0bGVCYWlsZXkvMjAxNC8wOS9CZWV0bGVfQmFpbGV5LjIwMTQwOTA4XzkwMC5naWY="
//     * /> </form> </div>
//     * 
//     * @param tag
//     * @param attrSet
//     * @param pos
//     * @return
//     */
//    private boolean FindRightDivTag(Tag tag, MutableAttributeSet attrSet, int pos) {
//	boolean blnReturnValue = false;
//	try {
//	    String strDivClass = "";
//	    strDivClass = (String) attrSet.getAttribute(HTML.Attribute.CLASS);
//
//	    String strToCompare = "entry-content";
//	    if (!(strDivClass == null || strDivClass == "")) {
//		if (strToCompare.equals(strDivClass)) {
//		    blnReturnValue = true;
//		}
//	    }
//	} catch (Exception e) {
//	    intDebugCode = 1;
//	    this.strDebugMsg = "*!*!*!*! htmlParser Callback.FindRightDivTag *!*!*!*!*!*!*";
//	    strDebugMsg += "\nException caught: tag - " + tag.toString() + "\n" + e.toString();
//	    strDebugMsg += "\n" + e.getStackTrace()[0];
//	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
//	}
//	return blnReturnValue;
//    }
//
//    private Boolean findRightInputTag(Tag tag, MutableAttributeSet attrSet, int pos) {
//	Boolean blnReturnValue = false;
//	try {
//	    String strInputName = "";
//	    strInputName = (String) attrSet.getAttribute(HTML.Attribute.NAME);
//
//	    String strToCompare = "featureDate";
//	    if (!(strInputName == null || strInputName == "")) {
//		if (strToCompare.equals(strInputName)) {
//		    blnReturnValue = true;
//		}
//	    }
//	} catch (Exception e) {
//	    // TODO: handle exception
//	}
//	return blnReturnValue;
//    }
//
//    private void displayThisTag(Tag tag, MutableAttributeSet attrSet, int pos) {
//	intDebugCode = 32;
//	int intAttCnt = attrSet.getAttributeCount();
//	this.strDebugMsg = pos + "~Tag =>~" + tag.toString() + "~Attribute cnt: " + intAttCnt;
//
//	if (intAttCnt > 0) {
//	    Enumeration<?> attributeNames = attrSet.getAttributeNames();
//	    while (attributeNames.hasMoreElements()) {
//		Object objAttrName = attributeNames.nextElement();
//		String attrName = objAttrName.toString();
//		strDebugMsg += "    Tag With Attributes => " + attrName + " : " + attrSet.getAttribute(objAttrName) + "\n";
//	    }
//	}
//	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
//    }
//
//    private String findProperAttributes(Tag tag, MutableAttributeSet attrSet, int pos, HTML.Attribute htmlAtt) {
//	String strReturnValue = null;
//	int intAttCnt = attrSet.getAttributeCount();
//
//	if (intAttCnt > 0) {
//	    Enumeration<?> attributeNames = attrSet.getAttributeNames();
//	    while (attributeNames.hasMoreElements()) {
//		Object objAttrName = attributeNames.nextElement();
//		String attrName = objAttrName.toString();
//		intDebugCode = 16;
//		this.strDebugMsg = "*************************************\nParse Html - findProperAttributes:\n";
//		strDebugMsg += "Tag With Attributes => " + attrName + " : " + attrSet.getAttribute(objAttrName);
//		dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
//
//		String strAttValue = (String) attrSet.getAttribute(htmlAtt);
//		if (strAttValue != null && strReturnValue == null) {
//		    
//		    strReturnValue = strAttValue;
//		}
//	    }
//
//	}
//
//	return strReturnValue;
//    }
//
}

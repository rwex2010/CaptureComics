package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.UnknownTag;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.xpath.XPathExpression;

public class ArcamaxWebPageHtmlParser extends HtmlParserCallback {

    private ProcessStartTags objProcessStartTags = new ProcessStartTags();
    private ArcamaxStepThruWebPageTagsToGetImageUrl StepsToFollow;
    private String MyResults = "NotWhatIWasLookingFor";
    private String MyTestResults = "";
    private int MyTestStepToCheck = 0;

    private boolean blnFoundDate = false;
    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    private LocalDate ldWorkingDate;
    // private DateTimeFormatter dtWorkingDateFormatter =
    // DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
    private DateTimeFormatter dtSavedImageDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public boolean foundFigure = false;

    private String strImageWebPageUrlForPreviousDay = "";

    private boolean blnFoundRightDivTag = false;
    private boolean blnFoundRightSpanTag = false;
    private String strComicDateNoDelimeter = "";
    private String strTagToFind = "a";
    private String[] strClassToFind = { "prev", "zoom jquery-image-zoom" };
    private int intIndexClassToFind = 0;

    private HTML.UnknownTag FIGURE = new HTML.UnknownTag("figure");

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 16;
    private String NL = "\n";

    public ArcamaxWebPageHtmlParser() {
	this.strTagToFind = "figure";
	// TODO Auto-generated constructor stub
    }

    public void initializeStepsToFollow() {
	StepsToFollow = new ArcamaxStepThruWebPageTagsToGetImageUrl(ldWorkingDate);
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
	this.strImageUrl = StepsToFollow.getStrImageUrl();
	return strImageUrl;
    }

    public String getStrImageWebPageUrlForPreviousDay() {
	return strImageWebPageUrlForPreviousDay;
    }

    public void setStrImageWebPageUrlForPreviousDay(String strImageWebPageUrlForPreviousDay) {
	this.strImageWebPageUrlForPreviousDay = strImageWebPageUrlForPreviousDay;
    }

    @Override
    public void flush() throws BadLocationException {
    }

    @Override
    public void handleText(char[] data, int pos) {
	if (MyResults == "GotRightElement") {
	    String DateOfImage = "";
	    for (int ix = 0; ix < data.length; ix++) {
		DateOfImage += data[ix];
	    }
	    DateOfImage += ", 2015";
	    ldImageDate = LocalDate.parse(DateOfImage, dtImageDateFormatter);
	    strImageDate = ldImageDate.format(dtSavedImageDateFormatter);
	    MyResults = "";
	    intDebugCode = 16;
	    this.strDebugMsg = NL + "*************************************\nParse Html - handleText:";
	    strDebugMsg += NL + "strTagToFind: " + strTagToFind;
	    strDebugMsg += NL + "char data - length =  " + data.length;
	    strDebugMsg += NL + this.strImageDate;
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    // objectToTest.checkReturnValues(strImageDate);
	    StepsToFollow.checkReturnValues(strImageDate);

	}
	// if (blnFoundRightSpanTag) {
	// captureImageDate(data);
	// intDebugCode = 16;
	// this.strDebugMsg = NL +
	// "*************************************\nParse Html - handleText:";
	// strDebugMsg += NL + "strTagToFind: " + strTagToFind;
	// strDebugMsg += NL + "char data - length =  " + data.length;
	// strDebugMsg += NL + this.strImageDate;
	// dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	// }
	// blnFoundRightSpanTag = false;

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
//		this.ldImageDate = StepsToFollow.getLdImageDate();
		this.setStrImageWebPageUrlForPreviousDay(StepsToFollow.getStrPrevWebPageUrl());
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
	    myMsg += NL + "ArcamaxWebPageHtmlParser";
//	    myMsg += NL + "imageName: " + this.strImageName;
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
     * statement must execute each case statement in the following order skips
     * all tags until it finds the following: 1 - <section> - with a class of
     * 'comic-story-scroller' 2 - <a> - with a class of 'prev' the href
     * attribute is a partial url to the previous comic 3 - <span> with a class
     * of "cur" the text value of this tag is the date of the current comic in
     * the form 'September 9, 2014' 4 - <a> with a class of
     * "zoom jquery-image-zoom" the href attribute is a partial URL for image
     * 
     * @param tag
     *            is the HTML.Tag that this routine is parsing
     * @param attrSet
     *            is a set of the attributes that a are part of this tag
     * @param pos
     *            is the position in the reader that this tag is located.
     */
    /*
     * private void handleAllStartTags(Tag tag, MutableAttributeSet attrSet, int
     * pos) { if (strTagToFind != "done") {
     * 
     * intDebugCode = 16; this.strDebugMsg =
     * "*************************************\nParse Html - handleAllStartTags:\n"
     * ; strDebugMsg += "strTagToFind: " + strTagToFind; strDebugMsg +=
     * "\ntag: " + tag.toString(); dbgDisplay.ShowMessage(strDebugMsg,
     * intDebugCode);
     * 
     * switch (strTagToFind) { case "a": if (tag == HTML.Tag.A) {
     * blnFoundRightDivTag = findRightATag(tag, attrSet, pos); if
     * (blnFoundRightDivTag) { displayThisTag(tag, attrSet, pos); if
     * (this.intIndexClassToFind == 0) { strTagToFind = "span";
     * ++intIndexClassToFind; } else { strTagToFind = "done";
     * 
     * } } // } } break;
     * 
     * case "figure": if (tag == FIGURE) { blnFoundRightDivTag =
     * findRightATag(tag, attrSet, pos); if (blnFoundRightDivTag) {
     * displayThisTag(tag, attrSet, pos); if (this.intIndexClassToFind == 0) {
     * strTagToFind = "span"; ++intIndexClassToFind; } else { strTagToFind =
     * "done";
     * 
     * } } // } } break;
     * 
     * case "span": // now look for date if (tag == HTML.Tag.SPAN) {
     * displayThisTag(tag, attrSet, pos); if (findRightSpanTag(tag, attrSet,
     * pos)) { // this.strImageDate = (String) //
     * attrSet.getAttribute(HTML.Attribute.VALUE); // this.strImageDate =
     * (String) tag; // this.ldImageDate = LocalDate.parse(strImageDate, //
     * dtImageDateFormatter); this.blnFoundRightSpanTag = true; strTagToFind =
     * "a"; } } break;
     * 
     * // case "img": // look for 'src' attribute // if (tag == HTML.Tag.IMG) {
     * // String strSrcAtt = findProperAttributes(tag, attrSet, pos, //
     * HTML.Attribute.SRC); // intDebugCode = 16; // strDebugMsg =
     * "Tag: img -- Att: src: " + strSrcAtt; //
     * dbgDisplay.ShowMessage(strDebugMsg, intDebugCode); // strImageUrl =
     * strSrcAtt; // strTagToFind = "done"; // } // break;
     * 
     * default: break; } } }
     */
    /**
     * Divtag looks like this: <div class="entry-content"> <form name='safr'
     * action='http://shop.kingfeatures.com/' method='post'> <input
     * type='hidden' name='screenResUri' value=
     * 'http://safr.kingfeatures.com/idn/etv/zone/dispimg.php?file=L2hvbWUvdmlydHVhbC9raW5nZmVhdHVyZXMuY29tL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS92YXIvd3d3L2NhY2hlL0JlZXRsZUJhaWxleS8yMDE0LzA5L0JlZXRsZV9CYWlsZXkuMjAxNDA5MDhfNzIwLmpwZw==
     * ' / > <input type='hidden' name='printResUri' value=
     * 'http://safr.kingfeatures.com/idn/etv/zone/hiresimg.php?file=L2hvbWUvdmlydHVhbC9raW5nZmVhdHVyZXMuY29tL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS92YXIvd3d3L3JlcG8vQmVldGxlQmFpbGV5LzIwMTQvMDkvQmVldGxlX0JhaWxleS4yMDE0MDkwOC5qcGc=
     * ' / > <input type='hidden' name='PrintResWidth' value='2175'/> <input
     * type='hidden' name='PrintResHeight' value='801'/> <input type='hidden'
     * name='featureDate' value='20140908'/> <input type='hidden'
     * name='featureName' value='Beetle Bailey'/> <img src=
     * "http://safr.kingfeatures.com/idn/test/zone/xml/content.php?file=aHR0cDovL3NhZnIua2luZ2ZlYXR1cmVzLmNvbS9CZWV0bGVCYWlsZXkvMjAxNC8wOS9CZWV0bGVfQmFpbGV5LjIwMTQwOTA4XzkwMC5naWY="
     * /> </form> </div>
     * 
     * @param tag
     * @param attrSet
     * @param pos
     * @return
     */

    /*
     * private boolean findRightATag(Tag tag, MutableAttributeSet attrSet, int
     * pos) { boolean blnReturnValue = false; try { String strAClass = "";
     * strAClass = (String) attrSet.getAttribute(HTML.Attribute.CLASS);
     * 
     * String strToCompare = this.strClassToFind[this.intIndexClassToFind]; if
     * (!(strAClass == null || strAClass == "")) { if
     * (strToCompare.equals(strAClass)) { if (this.intIndexClassToFind == 0) {
     * this.strImageWebPageUrlForPreviousDay = (String)
     * attrSet.getAttribute(HTML.Attribute.HREF); } else { this.strImageUrl =
     * (String) attrSet.getAttribute(HTML.Attribute.HREF); } blnReturnValue =
     * true; } } } catch (Exception e) { intDebugCode = 1; this.strDebugMsg =
     * "*!*!*!*! htmlParser Callback.FindRightDivTag *!*!*!*!*!*!*"; strDebugMsg
     * += "\nException caught: tag - " + tag.toString() + "\n" + e.toString();
     * strDebugMsg += "\n" + e.getStackTrace()[0];
     * dbgDisplay.ShowMessage(strDebugMsg, intDebugCode); } return
     * blnReturnValue; }
     * 
     * private Boolean findRightSpanTag(Tag tag, MutableAttributeSet attrSet,
     * int pos) { Boolean blnReturnValue = false; try { String strSpanClass =
     * ""; strSpanClass = (String) attrSet.getAttribute(HTML.Attribute.CLASS);
     * 
     * String strToCompare = "cur"; if (!(strSpanClass == null || strSpanClass
     * == "")) { if (strToCompare.equals(strSpanClass)) { blnReturnValue = true;
     * } } } catch (Exception e) { intDebugCode = 1; strDebugMsg = NL +
     * "ArcamaxWebPageHtmlParser ->  findRightSpanTag"; strDebugMsg += NL +
     * "Error caught: " + e.getMessage(); strDebugMsg += NL + "Error caught: " +
     * e.toString(); dbgDisplay.ShowMessage(strDebugMsg, intDebugCode); } return
     * blnReturnValue; }
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
     * dbgDisplay.ShowMessage(strDebugMsg, intDebugCode); }
     * 
     * private String findProperAttributes(Tag tag, MutableAttributeSet attrSet,
     * int pos, HTML.Attribute htmlAtt) { String strReturnValue = null; int
     * intAttCnt = attrSet.getAttributeCount();
     * 
     * if (intAttCnt > 0) { Enumeration<?> attributeNames =
     * attrSet.getAttributeNames(); while (attributeNames.hasMoreElements()) {
     * Object objAttrName = attributeNames.nextElement(); String attrName =
     * objAttrName.toString(); intDebugCode = 16; this.strDebugMsg = NL +
     * "ArcamaxWebPageHtmlParser -> findProperAttributes:"; strDebugMsg += NL +
     * "Tag With Attributes => " + attrName + " : " +
     * attrSet.getAttribute(objAttrName); dbgDisplay.ShowMessage(strDebugMsg,
     * intDebugCode);
     * 
     * String strAttValue = (String) attrSet.getAttribute(htmlAtt); if
     * (strAttValue != null && strReturnValue == null) {
     * 
     * strReturnValue = strAttValue; } }
     * 
     * }
     * 
     * return strReturnValue; }
     * 
     * private void captureImageDate(char[] data) { this.strImageDate = ""; for
     * (int ix = 0; ix < data.length; ix++) { this.strImageDate += data[ix]; }
     * intDebugCode = 16; this.strDebugMsg = NL +
     * "ArcamaxWebPageHtmlParser -> captureImageDate:"; strDebugMsg += NL +
     * "String strImageDate: " + this.strImageDate;
     * dbgDisplay.ShowMessage(strDebugMsg, intDebugCode); this.strImageDate =
     * strImageDate.trim(); this.ldImageDate = LocalDate.parse(strImageDate,
     * dtImageDateFormatter);
     * 
     * }
     */
}

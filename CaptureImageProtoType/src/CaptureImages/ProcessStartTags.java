package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;

public class ProcessStartTags {

    // private boolean blnFoundDate = false;
    // private String strImageName = "";
    private String strImageUrl = "";
    private String strImageDate = "";
    private LocalDate ldImageDate;
    // private LocalDate ldWorkingDate;
    private DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private boolean blnFoundRightDivTag = false;
    private boolean blnLookForFirstDivTag = true;
    private String strComicDateNoDelimeter = "";
    private String strTagToFind = "div";

    // private int intCountForJUnit = 0;
    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 16;
    private String NL = "\n";

    private int intTotalStartTags = 0;
    private int intTotalDivStartTags = 0;
    private int intTotalImgTags = 0;
    // private int intTotalH1Tags = 0;
    // private int intTotalPTags = 0;
    // private int intTotalATags = 0;
    private int intTotalRightTags = 0;
    private int intTotalRightAttributeTag = 0;
    private int intTotalRightValueAttributeTag = 0;
    private Boolean blnFoundImageUrl = false;
    private String strValueToReturn = "";

    private Tag htmlTagToFind;

    public ProcessStartTags(Tag elementTagToStart) {
	// TODO Auto-generated constructor stub
	this.htmlTagToFind = elementTagToStart;
    }

    public ProcessStartTags() {
	// TODO Auto-generated constructor stub
    }

    public String processThisTag(Tag tag, MutableAttributeSet attrSet, Tag htmlTagToFind, HTML.Attribute htmlAttToFind, String strValueToFind, int intLengthOfValue, HTML.Attribute htmlAttToGetReturnValue) {
	intTotalStartTags++;
	// updateTagCount(tag);
	if (intLengthOfValue > 0) {
	    String pointToStop = "";
	    if (pointToStop == "GO") {

	    }
	}
	String strReturnValue = "TagToFind: " + htmlTagToFind.toString();
	// if (tag == htmlTagToFind) {
	if (tag.equals(htmlTagToFind)) {
	    intTotalRightTags++;
	    strReturnValue = "GotRightTag";
	    if (htmlAttToFind != null) {
System.out.println("Got Righ Tag assigned, htmlTagToFind: " +  htmlTagToFind.toString());		
		boolean GotTheRightElement = isThisTheRightElement(attrSet, htmlAttToFind, strValueToFind, intLengthOfValue, htmlAttToGetReturnValue);
		if (GotTheRightElement) {
		    if (this.strValueToReturn != null) {
			strReturnValue = this.strValueToReturn;
		    } else {
			strReturnValue = "GotRightElement";
		    }

		}
	    }
	}
	return strReturnValue;
    }

    /*
     * 
     * private void updateTagCount(Tag tag) { switch (tag.toString()) { case
     * "div": intTotalDivStartTags++; break; case "img": intTotalImgTags++;
     * break;
     * 
     * default: break; } }
     */

    private boolean isThisTheRightElement(MutableAttributeSet attrSet, HTML.Attribute htmlAttToFind, String strValueToFind, int LengthOfValue, HTML.Attribute htmlAttToGetReturnValue) {
	boolean ReturnValue = false;
	int intAttCnt = attrSet.getAttributeCount();
	String strAttributeValue = (String) attrSet.getAttribute(htmlAttToFind);
	if (strAttributeValue != null) {
	    String strAttributeValueToCompare = strAttributeValue;
	    if (LengthOfValue > 0) {
		strAttributeValueToCompare = strAttributeValue.substring(0, (LengthOfValue));
	    }
	    if (strValueToFind.equals("FinalUrl")) {
		String str = "this is just a place to put a break";

	    }
	    if (strAttributeValueToCompare.equals(strValueToFind) || strValueToFind.equals("DateFormat") || strValueToFind.equals("FinalUrl")) {
		// this.strValueToReturn = strAttributeValue;
		this.strValueToReturn = (String) attrSet.getAttribute(htmlAttToGetReturnValue);
		ReturnValue = true;
	    }
	}

	return ReturnValue;
    }

    public Tag getElementTagTofind() {
	return htmlTagToFind;
    }

    public int getIntTotalStartTags() {
	return intTotalStartTags;
    }

    public int getIntTotalDivStartTags() {
	return intTotalDivStartTags;
    }

    public int getIntTotalImgTags() {
	return intTotalImgTags;
    }

    public int getIntTotalRightTags() {
	return intTotalRightTags;
    }

    public int getIntTotalRightAttributeTag() {
	return intTotalRightAttributeTag;
    }

    public int getIntTotalRightValueAttributeTag() {
	return intTotalRightValueAttributeTag;
    }

    public Boolean getBlnFoundImageUrl() {
	return blnFoundImageUrl;
    }

    public String getStrImageUrl() {
	return strImageUrl;
    }

    public String getStrImageDate() {
	return strImageDate;
    }

    public LocalDate getLdImageDate() {
	return ldImageDate;
    }

    public DateTimeFormatter getDtFormatter() {
	return dtFormatter;
    }

    public boolean isBlnFoundRightDivTag() {
	return blnFoundRightDivTag;
    }

    public boolean isBlnLookForFirstDivTag() {
	return blnLookForFirstDivTag;
    }

    public String getStrComicDateNoDelimeter() {
	return strComicDateNoDelimeter;
    }

    public String getStrTagToFind() {
	return strTagToFind;
    }

    // public DisplayDebugMessage getDbgDisplay() {
    // return dbgDisplay;
    // }
    //
    // public String getStrDebugMsg() {
    // return strDebugMsg;
    // }
    //
    // public String[] getStrDebugMsgArray() {
    // return strDebugMsgArray;
    // }
    //
    // public int getIntDebugCode() {
    // return intDebugCode;
    // }
    //
    public void setStrImageUrl(String strImageUrl) {
	this.strImageUrl = strImageUrl;
    }

    public void setStrImageDate(String strImageDate) {
	this.strImageDate = strImageDate;
    }

    public void setLdImageDate(LocalDate ldImageDate) {
	this.ldImageDate = ldImageDate;
    }

    public void setDtFormatter(DateTimeFormatter dtFormatter) {
	this.dtFormatter = dtFormatter;
    }

    public void setBlnFoundRightDivTag(boolean blnFoundRightDivTag) {
	this.blnFoundRightDivTag = blnFoundRightDivTag;
    }

    public void setBlnLookForFirstDivTag(boolean blnLookForFirstDivTag) {
	this.blnLookForFirstDivTag = blnLookForFirstDivTag;
    }

    public void setStrComicDateNoDelimeter(String strComicDateNoDelimeter) {
	this.strComicDateNoDelimeter = strComicDateNoDelimeter;
    }

    public void setStrTagToFind(String strTagToFind) {
	this.strTagToFind = strTagToFind;
    }

    public void setDbgDisplay(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

    public void setStrDebugMsg(String strDebugMsg) {
	this.strDebugMsg = strDebugMsg;
    }

    public void setStrDebugMsgArray(String[] strDebugMsgArray) {
	this.strDebugMsgArray = strDebugMsgArray;
    }

    public void setIntDebugCode(int intDebugCode) {
	this.intDebugCode = intDebugCode;
    }

    void processDebugMessages(String strDebugLocation, int intDebugCode) {
	switch (strDebugLocation) {
	case "stepThroughDates_1":
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") GoComicsComicStripImages -> stepThroughDates - starting";
	    break;

	case "stepThroughDates_2":
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") GoComicsComicStripImages -> stepThroughDates while loop ";
	    // strDebugMsg += NL + " Domain: " + csData.Domain;
	    // strDebugMsg += NL + " limitLoop: " + intLoopLimit;
	    break;

	}
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

    }

    void displayCaughtException(String strDebugLocation, Exception exException) {
	int intDebugCode = 1;
	switch (strDebugLocation) {
	case "initializeClassVariables":
	    strDebugMsg = NL + intDebugCode + ") GoComicsComicStripImages -> initializeClassVariables - unable to instantiate GoComicsHtmlWebPageReader:";
	    strDebugMsg += NL + "Error message: " + exException.getMessage();
	    break;

	case "findImageUrl":
	    strDebugMsg = NL + intDebugCode + ") GoComicsComicStripImages -> findImageUrl - Error parsing page, or capturing image:";
	    strDebugMsg += NL + "Error message: " + exException.getMessage();
	    break;

	default:
	    break;
	}
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	intDebugCode = 256;
	if (dbgDisplay.thisDoesPrint(intDebugCode)) {
	    exException.printStackTrace();
	}
    }

}
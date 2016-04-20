package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MediaZenfsCreateImageUrl {

    private ComicStrip csData;
    private LocalDate ldWorkinDate;

    private String strMediaZenfsServer = "http://media.zenfs.com/en_us/News/ucomics.com/";
    private String strImageUrl;
    private String strImageId;
    private String strImageExtension = "gif";

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String strClassName = "MediaZenfsCreateImageUrl";
    private String NL = "\n";

    public MediaZenfsCreateImageUrl() {

    }

    public MediaZenfsCreateImageUrl(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

    public void BuildImageUrl(LocalDate ldWorkingDate, ComicStrip csData) {
	processDebugMessages("BuildImageURl_1", 2);
	// sample: http://media.zenfs.com/en_us/News/ucomics.com/far140907.gif
	// sample: http://media.zenfs.com/en_us/News/ucomics.com/ink140907.gif
	this.ldWorkinDate = ldWorkingDate;
	this.csData = csData;
	this.strImageExtension = csData.DailyExt;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
	String strWorkingDateFormated = this.ldWorkinDate.format(formatter);
	this.strImageId = csData.ComicCode + strWorkingDateFormated;
	this.strImageUrl = this.strMediaZenfsServer + this.strImageId + "." + this.strImageExtension;
	processDebugMessages("BuildImageURl_2", 4);

    }

    public void setCsData(ComicStrip csData) {
	this.csData = csData;
    }

    public String getStrImageExtension() {
	return strImageExtension;
    }

    public void setStrImageExtension(String strImageExtension) {
	this.strImageExtension = strImageExtension;
    }

    public String getStrImageUrl() {
	return strImageUrl;
    }

    public String getStrImageId() {
	return strImageId;
    }

    public void setDbgDisplay(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

    void processDebugMessages(String strDebugLocation, int intDebugCode) {
	switch (strDebugLocation) {
	case "BuildImageURl_1":
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") " + strClassName + " -> BuildImageURl - starting";
	    break;

	case "BuildImageURl_2":
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") " + strClassName + " -> BuildImageURl ";
	    strDebugMsg += NL + " Comic name: " + csData.ComicName;
	    strDebugMsg += NL + " Comic Domain: " + csData.Domain;
	    strDebugMsg += NL + " Image Url: " + this.strImageUrl;
	    break;

	default:
	    break;
	}
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

    }

}

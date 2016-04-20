package CaptureImages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UcomicsCreateImageUrl extends CreateImageUrl {

    private ComicStrip csData;
    private LocalDate ldWorkinDate;

    private String ucomicsServer = "http://images.ucomics.com/comics/";
    private String strImageUrl;
    private String strImageId;
    private String strComicCode;
    private String strImageExtension = "gif";

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String NL = "\n";

    public UcomicsCreateImageUrl(DisplayDebugMessage dbgDisplay) {
	super(dbgDisplay);
	this.dbgDisplay = dbgDisplay;
    }

    @Override
    void BuildImageUrl(LocalDate ldWorkingDate, ComicStrip csData) {
	// sample: http://images.ucomics.com/comics/tmntf/2014/tmntf140909.gif
	// sample: http://images.ucomics.com/comics/tmbot/2014/tmbot140909.gif
	// sample: http://images.ucomics.com/comics/hc/2014/hc140909.gif
	intDebugCode = 16;
	strDebugMsg = NL + intDebugCode + ") UcomicsCreateImageUrl -> BuildImageUrl  - Starting";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	this.ldWorkinDate = ldWorkingDate;
	this.csData = csData;
	this.strImageExtension = csData.DailyExt;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
	String strWorkingDateFormated = this.ldWorkinDate.format(formatter);
	DateTimeFormatter yearformat = DateTimeFormatter.ofPattern("yyyy");
	String strWorkingDateYear = this.ldWorkinDate.format(yearformat);
	this.strImageId = this.csData.ComicCode + strWorkingDateFormated;
	this.strComicCode = this.csData.ComicCode;
	this.strImageUrl = this.ucomicsServer + this.strComicCode + "/" + strWorkingDateYear + "/" + this.strImageId + "." + this.strImageExtension;

	intDebugCode = 16;
	strDebugMsg = NL + intDebugCode + ") UcomicsCreateImageUrl -> BuildImageUrl  - about to leave";
	strDebugMsg += NL + "ImageUrl: " + this.strImageUrl;
	strDebugMsg += NL + "ComicCode: " + this.strComicCode;
	strDebugMsg += NL + "ImageId: " + this.strImageId;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
    }

    @Override
    void setCsData(ComicStrip csData) {
	this.csData = csData;
    }

    @Override
    String getStrImageExtension() {
	return strImageExtension;
    }

    @Override
    void setStrImageExtension(String strImageExtension) {
	this.strImageExtension = strImageExtension;
    }

    @Override
    String getStrImageUrl() {
	return strImageUrl;
    }

    @Override
    String getStrImageId() {
	return strImageId;
    }

    @Override
    void setDbgDisplay(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

}

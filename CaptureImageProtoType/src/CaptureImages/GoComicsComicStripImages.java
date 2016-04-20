package CaptureImages;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class GoComicsComicStripImages extends ComicStripImages {

    private String strImageUrl;
    private String strImageId;
    private String strImageExtention;
    private String strFolderToSaveImg;

    private ComicStrip csData;
    private String strComicCode;
    private int intCurrentImageIndex;
    
    private URL urlWebpage = null;

    private GoComicsImageWebPageUrl goComicsWebPageURL;
    private GoComicsHtmlWebPageReader goComicsFindImageUrl;
    private CaptureImage captureImage;
    private DriverImageCapture imDriver;

    private LocalDate[] aryDates = new LocalDate[2];
    private LocalDate dtStartDate;
    private LocalDate dtEndDate;
    private Period perDateRange;
    private int intLoopLimit;
    private LocalDate ldWorkingDate;
    private LocalDate ldImageDate;

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String NL = "\n";

    public GoComicsComicStripImages(DisplayDebugMessage dbgDisplay) {
	super(dbgDisplay);
	this.dbgDisplay = dbgDisplay;
    }

    @Override
    void initializeClassVariables(LocalDate[] aryDates, String strFolderToSaveImg) {
	setAryDates(aryDates);
	this.strFolderToSaveImg = strFolderToSaveImg;
	setDateRelatedFields(aryDates);
	setGoComicWebPageUrlObject();
	try {
	    goComicsFindImageUrl = new GoComicsHtmlWebPageReader(this.dbgDisplay);
	} catch (Exception e) {
	    displayCaughtException("initializeClassVariables", e);
	}
    }

    public void setGoComicWebPageUrlObject() {
	goComicsWebPageURL = new GoComicsImageWebPageUrl(this.dbgDisplay);
    }
    
    @Override
    void stepThroughDates() {
	processDebugMessages("stepThroughDates_1", 2);

	this.strComicCode = csData.ComicCode;
	while (!(perDateRange.isNegative()) && intLoopLimit > 0) {
	    processDebugMessages("stepThroughDates_2", 2);

	    if (thisDayIsValidForThisComic()) {
		findImageUrlForThisComic();
	    }
	    ldWorkingDate = ldWorkingDate.plusDays(1);
	    perDateRange = ldWorkingDate.until(dtEndDate);
	    intLoopLimit--;
	}
	setDateRelatedFields(aryDates);
    }

    public void setDriverObject(DriverImageCapture imDriver) {
	this.imDriver = imDriver;
    }
    
    @Override
    void setAryDates(LocalDate[] aryDates) {
	this.aryDates = aryDates;
    }

    @Override
    void setDateRelatedFields(LocalDate[] aryDates) {
	this.dtStartDate = aryDates[0];
	this.dtEndDate = aryDates[1];
	this.perDateRange = dtStartDate.until(dtEndDate);
	this.intLoopLimit = (this.perDateRange.getDays() + +1) * 2;

	this.ldWorkingDate = dtStartDate;
    }

    @Override
    void setComicStrip(ComicStrip csData) {
	this.csData = csData;
    }

    Boolean thisDayIsValidForThisComic() {
	Boolean blnReturnValue = false;
	int dayOfWeek = ldWorkingDate.getDayOfWeek().getValue(); // 1=Monday
								 // 7=Sunday
	int dayOfWeekToCompare = (int) Math.pow(2, dayOfWeek);
	int daysAvailable = Integer.decode(csData.DaysAvailable);
	if ((dayOfWeekToCompare & daysAvailable) > 0) {
	    blnReturnValue = true;
	} else {
	    blnReturnValue = false;
	    processDebugMessages("stepThroughDates_4", 4);
	}
	return blnReturnValue;
    }

    void findImageUrlForThisComic() {
	findWebPageUrlForThisComic();
	imDriver.setWebPageUrl(urlWebpage);

	goComicsFindImageUrl.setWebPageURL(urlWebpage);
	goComicsFindImageUrl.setImageName(strComicCode);
	goComicsFindImageUrl.setWorkingDate(ldWorkingDate);
	processDebugMessages("stepThroughDates_3", 2);

	try {
	    goComicsFindImageUrl.parseImageUrlFromImageWebPage(csData.Domain);
	    captureComicImage();
	} catch (Exception e) {
	    imDriver.incrementFailedImageCaptureCount();
	    displayCaughtException("findImageUrl", e);
	}
    }

    void findWebPageUrlForThisComic() {
	this.urlWebpage = null;
	goComicsWebPageURL.setupWebPageUrl(strComicCode, ldWorkingDate);
	urlWebpage = goComicsWebPageURL.getWebPageUrl();

	// *** following from below the case statement in the driver
	String strWebPageUrl = urlWebpage.toString();

    }

    public LocalDate getDtStartDate() {
        return dtStartDate;
    }

    public LocalDate getDtEndDate() {
        return dtEndDate;
    }

    public Period getPerDateRange() {
        return perDateRange;
    }

    public int getIntLoopLimit() {
        return intLoopLimit;
    }

    @Override
    void captureComicImage() {
	this.strImageUrl = goComicsFindImageUrl.getImageUrl();
	this.strImageId = goComicsFindImageUrl.getImageId();
	this.strImageExtention = goComicsFindImageUrl.getImageExtension();
	this.ldImageDate = goComicsFindImageUrl.getImageDate();
	
	DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	imDriver.setImageUrl(strImageUrl);
	imDriver.setImageId(strImageId);
	imDriver.setImageDate(ldImageDate.format(dtImageDateFormatter));


	if (captureImage == null) {
	    captureImage = new CaptureImage(dbgDisplay);
	}
	captureImage.setIntImageIndex(intCurrentImageIndex);
	boolean blnImageCaptured = captureImage.saveImageToFile(strImageUrl, strFolderToSaveImg, strImageId, strImageExtention);
	if (blnImageCaptured) {
	    imDriver.incrementSuccesfulImageCaptureCount();
	} else {
	    imDriver.incrementFailedImageCaptureCount();
	}
    }

    @Override
    ComicStrip getCsData() {
	return this.csData;
    }

    @Override
    String getStrComicCode() {
	return this.strComicCode;
    }

    public void setStrComicCode(String strComicCode) {
        this.strComicCode = strComicCode;
    }

    @Override
    String getStrImageUrl() {
	return this.strImageUrl;
    }

    public URL getUrlWebpage() {
        return urlWebpage;
    }

    @Override
    void setStrImageUrl(String strImageUrl) {
	this.strImageUrl = strImageUrl;
    }

    @Override
    String getStrImageId() {
	return this.strImageId;
    }

    @Override
    void setStrImageId(String strImageId) {
	this.strImageId = strImageId;
    }

    @Override
    String getStrImageExtention() {
	return this.strImageExtention;
    }

    @Override
    void setStrImageExtention(String strImageExtention) {
	this.strImageExtention = strImageExtention;
    }

    @Override
    void setCurrentImageIndex(int intCurrentImageIndex) {
	this.intCurrentImageIndex = intCurrentImageIndex;
    }

    void processDebugMessages(String strDebugLocation, int intDebugCode) {
	switch (strDebugLocation) {
	case "stepThroughDates_1":
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") GoComicsComicStripImages -> stepThroughDates - starting";
	    break;

	case "stepThroughDates_2":
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") GoComicsComicStripImages -> stepThroughDates while loop ";
	    strDebugMsg += NL + " Domain: " + csData.Domain;
	    strDebugMsg += NL + " limitLoop: " + intLoopLimit;
	    break;

	case "stepThroughDates_3":
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") GoComicsComicStripImages->stepThroughDates blnOkayToContinue is true ";
	    strDebugMsg += NL + " Domain: " + csData.Domain;
	    strDebugMsg += NL + " comic code: " + strComicCode;
	    break;

	case "stepThroughDates_4":
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") GoComicsComicStripImages->stepThroughDates after UNsuccessful bitwise compare";
	    strDebugMsg += NL + this.csData.ComicName + " not available on: " + ldWorkingDate.getDayOfWeek().toString();
	    break;

	default:
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
	
	intDebugCode=256;
	if (dbgDisplay.thisDoesPrint(intDebugCode)) {
	    exException.printStackTrace();
	}
    }
}

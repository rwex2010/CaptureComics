package CaptureImages;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;

public class MediaZenfsComicStripImages extends ComicStripImages {

    // TODO populate implemented methods
    
    private String strImageUrl;
    private String strImageId;
    private String strImageExtention;
    private String strFolderToSaveImg;

    private ComicStrip csData;
    private String strComicCode;
    private int intCurrentImageIndex;

    private MediaZenfsCreateImageUrl mediaZenfsFindImageUrl;
    private CaptureImage captureImage;

    private LocalDate[] aryDates = new LocalDate[2];
    private LocalDate dtStartDate;
    private LocalDate dtEndDate;
    private Period perDateRange;
    private int intLoopLimit;
    private LocalDate ldWorkingDate;

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String NL = "\n";

    public MediaZenfsComicStripImages(DisplayDebugMessage dbgDisplay) {
	super(dbgDisplay);
	this.dbgDisplay = dbgDisplay;
    }

    @Override
    void initializeClassVariables(LocalDate[] aryDates, String strFolderToSaveImg) {
	setAryDates(aryDates);
	this.strFolderToSaveImg = strFolderToSaveImg;
	setDateRelatedFields(aryDates);
	try {
	    mediaZenfsFindImageUrl = new MediaZenfsCreateImageUrl(this.dbgDisplay);
	} catch (Exception e) {
	    intDebugCode = 1;
	    strDebugMsg = NL + intDebugCode + ") MediaZenfsComicStripImages -> initializeClassVariables - unable to instantiate MediaZenfsCreateImageUrl:";
	    strDebugMsg += NL + "Error message: " + e.getMessage();
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	}
    }

    @Override
    void stepThroughDates() {
	intDebugCode = 2;
	String strDebugMsg = NL + intDebugCode + ") MediaZenfsComicStripImages -> stepThroughDates - starting";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	this.strComicCode = csData.ComicCode;
	while (!(perDateRange.isNegative()) && intLoopLimit > 0) {

	    intDebugCode = 2;
	    strDebugMsg = NL + intDebugCode + ") MediaZenfsComicStripImages -> stepThroughDates while loop ";
	    strDebugMsg += NL + " Domain: " + csData.Domain;
	    strDebugMsg += NL + " limitLoop: " + intLoopLimit;
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    String strWebPageUrl = "";
	    int dayOfWeek = ldWorkingDate.getDayOfWeek().getValue(); // 1=Monday
								     // 7=Sunday
	    int dayOfWeekToCompare = (int) Math.pow(2, dayOfWeek);
	    int daysAvailable = Integer.decode(csData.DaysAvailable);
	    if ((dayOfWeekToCompare & daysAvailable) > 0) {
		intDebugCode = 2;
		strDebugMsg = NL + intDebugCode + ") MediaZenfsComicStripImages->stepThroughDates blnOkayToContinue is true ";
		strDebugMsg += NL + " Domain: " + csData.Domain;
		strDebugMsg += NL + " comic code: " + strComicCode;
		dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
		mediaZenfsFindImageUrl.setCsData(csData);
		try {
		    mediaZenfsFindImageUrl.BuildImageUrl(ldWorkingDate, csData);
		    captureComicImage();
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		// TODO step through by date
	    } else {
		intDebugCode = 4;
		strDebugMsg = NL + intDebugCode + ") MediaZenfsComicStripImages->stepThroughDates after UNsuccessful bitwise compare";
		strDebugMsg += NL + " " + this.csData.ComicName + " not available on: " + ldWorkingDate.getDayOfWeek().toString();
		dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	    }
	    ldWorkingDate = ldWorkingDate.plusDays(1);
	    perDateRange = ldWorkingDate.until(dtEndDate);
	    intLoopLimit--;
	}
	setDateRelatedFields(aryDates);
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

    @Override
    ComicStrip getCsData() {
	return this.csData;
    }

    @Override
    String getStrComicCode() {
	return this.strComicCode;
    }

    @Override
    String getStrImageUrl() {
	return this.strImageUrl;
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
    void captureComicImage() {
	this.strImageUrl = mediaZenfsFindImageUrl.getStrImageUrl();
	this.strImageId = mediaZenfsFindImageUrl.getStrImageId();
	this.strImageExtention = mediaZenfsFindImageUrl.getStrImageExtension();

	if (captureImage == null) {
	    captureImage = new CaptureImage(dbgDisplay);
	}
	captureImage.setIntImageIndex(intCurrentImageIndex);
	boolean blnImageCaptured = captureImage.saveImageToFile(strImageUrl, strFolderToSaveImg, strImageId, strImageExtention);
	if (blnImageCaptured) {
	    blnImageCaptured = false;
	}
    }

    @Override
    void setCurrentImageIndex(int intCurrentImageIndex) {
	this.intCurrentImageIndex = intCurrentImageIndex;
    }

    @Override
    void setDriverObject(DriverImageCapture imDriver) {
	// TODO Auto-generated method stub
	
    }

    @Override
    Boolean thisDayIsValidForThisComic() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    void findImageUrlForThisComic() {
	// TODO Auto-generated method stub
	
    }

    @Override
    void findWebPageUrlForThisComic() {
	// TODO Auto-generated method stub
	
    }

    @Override
    void processDebugMessages(String strDebugLocation, int intDebugCode) {
	// TODO Auto-generated method stub
	
    }

    @Override
    void displayCaughtException(String strDebugLocation, Exception exException) {
	// TODO Auto-generated method stub
	
    }

}

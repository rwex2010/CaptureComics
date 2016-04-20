package CaptureImages;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DilbertComicStripImages extends ComicStripImages {

    // TODO populate implemented methods
    
    private String strImageUrl;
    private String strImageId;
    private String strImageExtention;
    private String strFolderToSaveImg;

    private URL urlWebpage = null;

    private ComicStrip csData;
    private String strComicCode;
    private int intCurrentImageIndex;

    private DilbertImageWebPageUrl dilbertWebPageURL;
    private DilbertHtmlWebPageReader dilbertFindImageUrl;
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

    public DilbertComicStripImages(DisplayDebugMessage dbgDisplay) {
	super(dbgDisplay);
	this.dbgDisplay = dbgDisplay;
    }

    @Override
    void initializeClassVariables(LocalDate[] aryDates, String strFolderToSaveImg) {
	setAryDates(aryDates);
	this.strFolderToSaveImg = strFolderToSaveImg;
	setDateRelatedFields(aryDates);
	dilbertWebPageURL = new DilbertImageWebPageUrl(this.dbgDisplay);
	try {
	    dilbertFindImageUrl = new DilbertHtmlWebPageReader(this.dbgDisplay);
	} catch (Exception e) {
	    intDebugCode = 1;
	    strDebugMsg = NL + intDebugCode + ") DilbertComicStripImages -> initializeClassVariables - unable to instantiate DilbertHtmlWebPageReader:";
	    strDebugMsg += NL + "Error message: " + e.getMessage();
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	}
    }

    @Override
    void stepThroughDates() {
	intDebugCode = 2;
	String strDebugMsg = NL + intDebugCode + ") DilbertComicStripImages -> stepThroughDates - starting";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	this.strComicCode = csData.ComicCode;
	while (!(perDateRange.isNegative()) && intLoopLimit > 0) {

	    intDebugCode = 2;
	    strDebugMsg = NL + intDebugCode + ") DilbertComicStripImages -> stepThroughDates while loop ";
	    strDebugMsg += NL + " Domain: " + csData.Domain;
	    strDebugMsg += NL + " limitLoop: " + intLoopLimit;
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    String strWebPageUrl = "";
	    int dayOfWeek = ldWorkingDate.getDayOfWeek().getValue(); // 1=Monday
								     // 7=Sunday
	    int dayOfWeekToCompare = (int) Math.pow(2, dayOfWeek);
	    int daysAvailable = Integer.decode(csData.DaysAvailable);
	    if ((dayOfWeekToCompare & daysAvailable) > 0) {
//		URL urlWebpage = null;
		dilbertWebPageURL.setupWebPageUrl(strComicCode, ldWorkingDate);
		urlWebpage = dilbertWebPageURL.getWebPageUrl();

		// *** following from below the case statement in the driver
		strWebPageUrl = urlWebpage.toString();

		dilbertFindImageUrl.setWebPageURL(urlWebpage);
		dilbertFindImageUrl.setImageName(strComicCode);
		dilbertFindImageUrl.setWorkingDate(ldWorkingDate);
		intDebugCode = 2;
		strDebugMsg = NL + intDebugCode + ") DilbertComicStripImages->stepThroughDates blnOkayToContinue is true ";
		strDebugMsg += NL + " Domain: " + csData.Domain;
		strDebugMsg += NL + " comic code: " + strComicCode;
		dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
		try {
		    dilbertFindImageUrl.parseImageUrlFromImageWebPage(csData.Domain);
		    captureComicImage();
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		// TODO step through by date
	    } else {
		intDebugCode = 4;
		strDebugMsg = NL + intDebugCode + ") DilbertComicStripImages->stepThroughDates after UNsuccessful bitwise compare";
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

    public URL getUrlWebpage() {
        return urlWebpage;
    }

    public DilbertImageWebPageUrl getDilbertWebPageURL() {
        return dilbertWebPageURL;
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
	setClassVariablesForImage();

	DateTimeFormatter dtImageDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	imDriver.setImageUrl(this.strImageUrl);
	imDriver.setImageId(this.strImageId);
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

    public void setClassVariablesForImage() {
	this.strImageUrl = dilbertFindImageUrl.getImageUrl();
	this.strImageId = dilbertFindImageUrl.getImageId();
	this.strImageExtention = dilbertFindImageUrl.getImageExtension();
	this.ldImageDate = dilbertFindImageUrl.getImageDate();
    }

    @Override
    void setCurrentImageIndex(int intCurrentImageIndex) {
	this.intCurrentImageIndex = intCurrentImageIndex;
    }

    @Override
    void setDriverObject(DriverImageCapture imDriver) {
	this.imDriver = imDriver;
    }

    @Override
    Boolean thisDayIsValidForThisComic() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    void findImageUrlForThisComic() {
	// TODO strip out extra code
	findWebPageUrlForThisComic();
	imDriver.setWebPageUrl(urlWebpage);

//	setImageUrlForThisComic();

    }

    @Override
    void findWebPageUrlForThisComic() {
	this.urlWebpage = null;
	dilbertWebPageURL.setupWebPageUrl(strComicCode, ldWorkingDate);
	urlWebpage = dilbertWebPageURL.getWebPageUrl();

	// *** following from below the case statement in the driver
//	String strWebPageUrl = urlWebpage.toString();

    }

    @Override
    void processDebugMessages(String strDebugLocation, int intDebugCode) {
	// TODO Auto-generated method stub
	
    }

    @Override
    void displayCaughtException(String strDebugLocation, Exception exException) {
	// TODO Auto-generated method stub
	
    }

    public void setStrComicCode(String comicCode) {
	// TODO Auto-generated method stub
	
    }

}

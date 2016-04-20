package CaptureImages;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ArcamaxComicStripImages extends ComicStripImages {

    // TODO populate implemented methods

    private String strImageUrl;
    private String strImageId;
    private String strImageExtention;
    private String strFolderToSaveImg;

    private String strImageWebPageUrlForPreviousDay = null;
    private URL urlWebpage = null;

    private ComicStrip csData;
    private String strComicCode;
    private int intCurrentImageIndex;

    private ArcamaxImageWebPageUrl arcamaxWebPageURL;
    private ArcamaxHtmlWebPageReader arcamaxFindImageUrl;
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

    public ArcamaxComicStripImages(DisplayDebugMessage dbgDisplay) {
	super(dbgDisplay);
	this.dbgDisplay = dbgDisplay;
    }

    @Override
    void initializeClassVariables(LocalDate[] aryDates, String strFolderToSaveImg) {
	setAryDates(aryDates);
	this.strFolderToSaveImg = strFolderToSaveImg;
	setDateRelatedFields(aryDates);
	arcamaxWebPageURL = new ArcamaxImageWebPageUrl(this.dbgDisplay);
	try {
	    arcamaxFindImageUrl = new ArcamaxHtmlWebPageReader(this.dbgDisplay);
	} catch (Exception e) {
	    intDebugCode = 1;
	    strDebugMsg = NL + intDebugCode + ") ArcamaxComicStripImages -> initializeClassVariables - unable to instantiate ArcamaxHtmlWebPageReader:";
	    strDebugMsg += NL + "Error message: " + e.getMessage();
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	}
    }

    public void setStrComicCode(String strComicCode) {
        this.strComicCode = strComicCode;
    }

    @Override
    void setAryDates(LocalDate[] aryDates) {
	this.aryDates = aryDates;
    }

    @Override
    void setDateRelatedFields(LocalDate[] aryDates) {
	this.dtStartDate = LocalDate.now();
	this.dtEndDate = aryDates[0];
	this.perDateRange = dtEndDate.until(dtStartDate);
	this.intLoopLimit = (this.perDateRange.getDays() + +1) * 2;

	this.ldWorkingDate = dtStartDate;
    }

    @Override
    void setComicStrip(ComicStrip csData) {
	this.csData = csData;
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
	    ldWorkingDate = ldWorkingDate.minusDays(1);
	    perDateRange = dtEndDate.until(ldWorkingDate);
	    intLoopLimit--;
	}
	setDateRelatedFields(aryDates);
    }

    @Override
    void captureComicImage() {
	setClassVariablesForImage();

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

    public void setClassVariablesForImage() {
	this.strImageUrl = arcamaxFindImageUrl.getImageUrl();
	this.strImageId = arcamaxFindImageUrl.getImageId();
	this.strImageExtention = arcamaxFindImageUrl.getImageExtension();
	this.ldImageDate = arcamaxFindImageUrl.getImageDate();
    }

    public Period getPerDateRange() {
        return perDateRange;
    }

    public URL getUrlWebpage() {
        return urlWebpage;
    }

    public void setUrlWebpage(URL urlWebpage) {
        this.urlWebpage = urlWebpage;
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
    void setCurrentImageIndex(int intCurrentImageIndex) {
	this.intCurrentImageIndex = intCurrentImageIndex;
    }

    @Override
    void setDriverObject(DriverImageCapture imDriver) {
	this.imDriver = imDriver;
    }

    @Override
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

    @Override
    void findImageUrlForThisComic() {
	// TODO strip out extra code
	findWebPageUrlForThisComic();
	imDriver.setWebPageUrl(urlWebpage);

	setImageUrlForThisComic();

    }

    public void setImageUrlForThisComic() {
	arcamaxFindImageUrl.setWebPageURL(urlWebpage);
	arcamaxFindImageUrl.setImageName(strComicCode);
	arcamaxFindImageUrl.setWorkingDate(ldWorkingDate);
	processDebugMessages("stepThroughDates_3", 2);
	try {
	    arcamaxFindImageUrl.parseImageUrlFromImageWebPage(csData.Domain);
	    this.strImageWebPageUrlForPreviousDay = arcamaxFindImageUrl.getStrImageWebPageForPreviousDay();
	    // TODO get previous days url
	    if (ldWorkingDate.isEqual(aryDates[1]) || ldWorkingDate.isBefore(aryDates[1])) {
		captureComicImage();
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    void findWebPageUrlForThisComic() {
	this.urlWebpage = null;
	arcamaxWebPageURL.setStrImageWebPageUrlForPreviousDay(strImageWebPageUrlForPreviousDay);
	arcamaxWebPageURL.setupWebPageUrl(strComicCode, ldWorkingDate);
	urlWebpage = arcamaxWebPageURL.getWebPageUrl();
    }

    @Override
    void processDebugMessages(String strDebugLocation, int intDebugCode) {
	switch (strDebugLocation) {
	case "stepThroughDates_1":
	    strDebugMsg = NL + intDebugCode + ") ArcamaxComicStripImages -> stepThroughDates - starting";
	    break;

	case "stepThroughDates_2":
	    strDebugMsg = NL + intDebugCode + ") ArcamaxComicStripImages -> stepThroughDates while loop ";
	    strDebugMsg += NL + " Domain: " + csData.Domain;
	    strDebugMsg += NL + " limitLoop: " + intLoopLimit;
	    strDebugMsg += NL + " csData Comic Code: " + csData.ComicCode;
	    strDebugMsg += NL + " Class Comic Code: " + this.strComicCode;
	    break;

	case "stepThroughDates_3":
	    strDebugMsg = NL + intDebugCode + ") ArcamaxComicStripImages->stepThroughDates blnOkayToContinue is true ";
	    strDebugMsg += NL + " Domain: " + csData.Domain;
	    strDebugMsg += NL + " comic code: " + strComicCode;
	    strDebugMsg += NL + " Webpage URL: " + urlWebpage.toExternalForm();
	    break;

	case "stepThroughDates_4":
	    strDebugMsg = NL + intDebugCode + ") ArcamaxComicStripImages->stepThroughDates after UNsuccessful bitwise compare";
	    strDebugMsg += NL + this.csData.ComicName + " not available on: " + ldWorkingDate.getDayOfWeek().toString();
	    break;

	default:
	    break;
	}
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

    }

    @Override
    void displayCaughtException(String strDebugLocation, Exception exException) {
	// TODO Auto-generated method stub

    }

}

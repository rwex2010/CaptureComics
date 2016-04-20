package CaptureImages;

import java.time.LocalDate;
import java.time.Period;

public abstract class ComicStripImages {

    protected String strImageUrl;
    protected String strImageId;
    protected String strImageExtention;
    protected String strFolderToSaveImg;

    protected ComicStrip csData;
    protected String strComicCode;
    protected GoComicsImageWebPageUrl goComicsWebPageURL;
    protected GoComicsHtmlWebPageReader urlCreator;
    
    protected int intCurrentImageIndex;

    protected LocalDate[] aryDates = new LocalDate[2];
    protected Period perDateRange;
    protected LocalDate dtStartDate;
    protected LocalDate dtEndDate;
    protected int intLoopLimit;

    protected DisplayDebugMessage dbgDisplay;
    protected String strDebugMsg = "";
    protected String[] strDebugMsgArray;
    protected int intDebugCode = 512;
    protected String NL = "\n";

    ComicStripImages(DisplayDebugMessage dbgDisplay) {
	// TODO Auto-generated constructor stub
    }

    abstract void initializeClassVariables(LocalDate[] aryDates, String strFolderToSaveImg);

    abstract void stepThroughDates();
    
    abstract void setDriverObject(DriverImageCapture imDriver);

    abstract void setAryDates(LocalDate[] aryDates);

    abstract void setDateRelatedFields(LocalDate[] aryDates);

    abstract void setComicStrip(ComicStrip csData);
    
    abstract Boolean thisDayIsValidForThisComic();
    
    abstract void findImageUrlForThisComic();
    
    abstract void findWebPageUrlForThisComic();

    abstract ComicStrip getCsData();

    abstract String getStrComicCode();

    abstract String getStrImageUrl();

    abstract void setStrImageUrl(String strImageUrl);

    abstract String getStrImageId();

    abstract void setStrImageId(String strImageId);

    abstract String getStrImageExtention();

    abstract void setStrImageExtention(String strImageExtention);
    
    abstract void captureComicImage();

   abstract void setCurrentImageIndex(int intCurrentImageIndex);
   
   abstract void processDebugMessages(String strDebugLocation, int intDebugCode);
   
   abstract void displayCaughtException(String strDebugLocation, Exception exException);

}

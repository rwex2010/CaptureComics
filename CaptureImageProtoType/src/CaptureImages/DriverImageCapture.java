/**
 * 
 */
package CaptureImages;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

/**
 * This class is to emulate the driver that will create the create URL class
 * This class sets the following in the URL class 1. Comic name (e.g. Brewster
 * Rockit) 2. Comic code (e.g. brewsterrockit) 3. Comic Date that we are going
 * after 4. Debug level (0 thru 9)
 * 
 * @author Rich
 * 
 */
public class DriverImageCapture {

    private int intCurrentImageIndex = 0;
//    private String[] aryComicName = new String[4];
    private String[] aryDomainsToUse;
    private String strListToUse = "myList";
    private LocalDate[] aryDates = new LocalDate[2];
//    private LocalDate ldWorkingDate;
    
    private GoComicsComicStripImages GoComics;
    private DilbertComicStripImages Dilbert;
//    private OnTheFastrackComicStripImages OnTheFastrack;
    private KevinAndKellComicStripImages KevinAndKell;
//    private SafeHavensComicStripImages SafeHavens;
    private MediaZenfsComicStripImages MediaZenfs;
    private UcomicsComicStripImages Ucomics;
    private KingFeaturesComicStripImages KingFeatures;
    private ArcamaxComicStripImages Arcamax;
//    private ComicsKingdomComicStripImages ComicsKingdom;
//    private OregonianComicStripImages Oregonian;
    
//    private HtmlWebPageReader urlCreator;
    private CaptureImage captureImage;
    private UIFormCaptureImages UiFrame;
    // private ImageWebPageURL goComicsWebPageURL;
    // private ImageWebPageURL dilbertWebPageURL;
//    private ImageWebPageURL safeHavensWebPageURL;
//    private ImageWebPageURL onTheFastrackWebPageURL;
//    private ImageWebPageURL kevinAndKellWebPageURL;
    private String strFolderToSaveImg = "C:/Users/wecksr/Documents/ComicStripImages/";
    private ParseComicXml objXmlParser;
    private java.util.List<ComicStrip> ComicList = new ArrayList<>();

    private int intDebugLevel = 0;
    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
//    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String NL = "\n";

//    private DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private String strPathToXml;

    public DriverImageCapture() {
	initializeDriverImageCapture();
    }

    public DriverImageCapture(UIFormCaptureImages UIobject) {
	this.UiFrame = UIobject;
	initializeDriverImageCapture();
    }

    public DriverImageCapture(UIFormCaptureImages UIobject, int DebugLevel) {
	this.UiFrame = UIobject;
	this.intDebugLevel = DebugLevel;
	initializeDriverImageCapture();
    }

    private void initializeDriverImageCapture() {
	initializeVariables();
    }

    private void initializeVariables() {
	dbgDisplay = new DisplayDebugMessage(intDebugLevel);
	this.UiFrame = new UIFormCaptureImages(this, dbgDisplay);
	this.UiFrame.setDriverObject(this);
	this.UiFrame.setDebugDisplay(dbgDisplay);
	// ciptImage = new CaptureImage(intDebugLevel);
	captureImage = new CaptureImage(dbgDisplay);
	objXmlParser = new ParseComicXml();
	this.UiFrame.frame.setVisible(true);
    }

    private void reInitializeSpecificVariables() {
	this.GoComics = null;
	this.Dilbert = null;
//	this.OnTheFastrack = null;
	this.KevinAndKell = null;
//	this.SafeHavens = null;
	this.aryDates = UiFrame.getArrayDates();
    }

    public void CaptureSelectedImages(int intTotalImageCount, int intStartingIndex, Boolean blnOnlyThisImage) {
	reInitializeSpecificVariables();
	intDebugCode = 2;
	strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> CaptureSelectedImages (multiple paramaters) - starting";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	intDebugCode = 4;
	strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture ->Total image count: " + intTotalImageCount + " Starting index: " + intStartingIndex;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	stepThroughComicList(intTotalImageCount, intStartingIndex, blnOnlyThisImage);
	UiFrame.setMessageBoard(dbgDisplay.getAllMessageLines());
//	UiFrame.setSuccessful(dbgDisplay.getIntSuccess());
//	UiFrame.setFailure(dbgDisplay.getFailure());

    }
    
    public void setWebPageUrl (URL urlImageWebPge) {
	UiFrame.setTextWebPageUrl(urlImageWebPge.toString());
    }

    public void setImageUrl(String strImageUrl) {
	UiFrame.setTextImageUrl(strImageUrl);
    }
    
    public void setImageId(String strImageId) {
	UiFrame.setTextImageId(strImageId);
    }
    
    public void setImageDate(String strImageDate) {
	UiFrame.setTextImageDate(strImageDate);
    }
    
    public void incrementSuccesfulImageCaptureCount() {
	UiFrame.incrementSuccessfulImagageCapture();
    }
    
    public void incrementFailedImageCaptureCount() {
	UiFrame.incrementFailedImagageCapture();
    }
    
    public void setPathToXML(String PathToXml) {
	this.strPathToXml = PathToXml;
    }

    public void setPathToSaveImage(String PathToSaveImage) {
	this.strFolderToSaveImg = PathToSaveImage;
    }

    public void setArrayDates(LocalDate[] aryDates) {
	this.aryDates = aryDates;
    }

    public void setIntDebugLevel(int intDebugLevel) {
	this.intDebugLevel = intDebugLevel;
	this.dbgDisplay.setDebugLevel(intDebugLevel);
    }

    public void loadWindow() {

	intDebugCode = 2;
	strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> loadWindow() - starting";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	intDebugCode = 4;
	strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> loadWindow() Path to XMl: " + strPathToXml;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	this.aryDomainsToUse = UiFrame.getStrDomainArray();
	this.strListToUse = UiFrame.getListToUse();

	objXmlParser.setDebugDisplay(dbgDisplay);
	objXmlParser.setMyXmlFile(strPathToXml);
	objXmlParser.setAryDomainToUse(this.aryDomainsToUse);
	objXmlParser.setStrListToUse(strListToUse);
	objXmlParser.setupVariable();
	this.ComicList = null;
	ComicList = objXmlParser.getComicStripList();
	java.util.Collections.sort(ComicList, new Comparator<ComicStrip>() {

	    @Override
	    public int compare(ComicStrip o1, ComicStrip o2) {
		// TODO Auto-generated method stub
		return o1.ComicName.compareToIgnoreCase(o2.ComicName);
	    }

	    @Override
	    public Comparator<ComicStrip> reversed() {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public Comparator<ComicStrip> thenComparing(Comparator<? super ComicStrip> other) {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public <U> Comparator<ComicStrip> thenComparing(Function<? super ComicStrip, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public <U extends Comparable<? super U>> Comparator<ComicStrip> thenComparing(Function<? super ComicStrip, ? extends U> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public Comparator<ComicStrip> thenComparingInt(ToIntFunction<? super ComicStrip> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public Comparator<ComicStrip> thenComparingLong(ToLongFunction<? super ComicStrip> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public Comparator<ComicStrip> thenComparingDouble(ToDoubleFunction<? super ComicStrip> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	    }

	});

	aryDates = UiFrame.getArrayDates();

    }

    public java.util.List<ComicStrip> getComicList() {
	return this.ComicList;
    }

    private void stepThroughComicList(int intTotalImageCount, int intStartingIndex, Boolean blnOnlyThisImage) {
	intDebugCode = 2;
	strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - starting";
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	this.aryDates = UiFrame.getArrayDates();
	for (int ix = intStartingIndex; ix < intTotalImageCount; ix++) {
	    intCurrentImageIndex = UiFrame.getCurrentIndex();
	    ComicStrip csData = UiFrame.getNextComicFmChooser();
	    switch (csData.Domain) {

	    case "imgsrv.gocomics.com":
		goComicsRoutines(csData);
		break;

	    case "Dilbert.com":
		dilbertRoutines(csData);
		break;

//	    case "safehavenscomic.com":
//		safeHavensRoutines(csData);
//		break;
//
//	    case "www.onthefastrack.com":
//		onTheFastrackRoutines(csData);
//		break;
//
	    case "www.kevinandkell.com":
		kevinAndKellRoutines(csData);
		break;

	    case "media.zenfs.com":
		mediaZenfsRoutines(csData);
		break;

	    case "images.ucomics.com":
		ucomicsRoutines(csData);
		break;

	    case "arcamax":
		arcamaxRoutines(csData);
		break;

	    case "kingfeatures":
		kingFeaturesRoutines(csData);
		break;

//	    case "content.comicskingdom.net":
//		oregonianRoutines(csData);
//		break;
//
	    default:
		intDebugCode = 1;
		strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList";
		strDebugMsg += NL + "fell through to the default in the switch on Domain ";
		strDebugMsg += NL + "index: " + intCurrentImageIndex + " - ComicCode: " + csData.ComicCode + " - Domain: " + csData.Domain;
		dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

//		stepThroughDates(csData, aryDates);
		break;
	    }
	    blnOnlyThisImage = UiFrame.getJustThisComicFlag();
	    if (blnOnlyThisImage) {
		break;
	    }
	}

    }

    private void goComicsRoutines(ComicStrip csData) {
	if (GoComics == null) {
	    intDebugCode = 2;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - go Comics initializing object";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	    GoComics = new GoComicsComicStripImages(dbgDisplay);
	    GoComics.initializeClassVariables(aryDates, strFolderToSaveImg);
	    GoComics.setDriverObject(this);
	}
	GoComics.setComicStrip(csData);
	GoComics.setCurrentImageIndex(intCurrentImageIndex);
	GoComics.stepThroughDates();
    }

    private void dilbertRoutines(ComicStrip csData) {
	if (Dilbert == null) {
	    intDebugCode = 2;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - go Comics initializing object";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	    Dilbert = new DilbertComicStripImages(dbgDisplay);
	    Dilbert.initializeClassVariables(aryDates, strFolderToSaveImg);
	    Dilbert.setDriverObject(this);

	}
	Dilbert.setComicStrip(csData);
	Dilbert.setCurrentImageIndex(intCurrentImageIndex);
	Dilbert.stepThroughDates();
    }

    private void kingFeaturesRoutines(ComicStrip csData) {
	if (KingFeatures == null) {
	    intDebugCode = 2;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - KingFeatures initializing object";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	    KingFeatures = new KingFeaturesComicStripImages(dbgDisplay);
	    KingFeatures.initializeClassVariables(aryDates, strFolderToSaveImg);
	}
	KingFeatures.setComicStrip(csData);
	KingFeatures.setCurrentImageIndex(intCurrentImageIndex);
	KingFeatures.stepThroughDates();
    }

//    private void oregonianRoutines(ComicStrip csData) {
//	if (Oregonian == null) {
//	    intDebugCode = 2;
//	    strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - Comics Kingdom initializing object";
//	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
//
//	    Oregonian = new OregonianComicStripImages(dbgDisplay);
//	    Oregonian.initializeClassVariables(aryDates, strFolderToSaveImg);
//	}
//	Oregonian.setComicStrip(csData);
//	Oregonian.setCurrentImageIndex(intCurrentImageIndex);
//	Oregonian.stepThroughDates();
//    }

//    private void safeHavensRoutines(ComicStrip csData) {
//	if (SafeHavens == null) {
//	    intDebugCode = 2;
//	    strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - go Comics initializing object";
//	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
//
//	    SafeHavens = new SafeHavensComicStripImages(dbgDisplay);
//	    SafeHavens.initializeClassVariables(aryDates, strFolderToSaveImg);
//	}
//	SafeHavens.setComicStrip(csData);
//	SafeHavens.setCurrentImageIndex(intCurrentImageIndex);
//	SafeHavens.stepThroughDates();
//    }
//
//    private void onTheFastrackRoutines(ComicStrip csData) {
//	if (OnTheFastrack == null) {
//	    intDebugCode = 2;
//	    strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - go Comics initializing object";
//	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
//
//	    OnTheFastrack = new OnTheFastrackComicStripImages(dbgDisplay);
//	    OnTheFastrack.initializeClassVariables(aryDates, strFolderToSaveImg);
//	}
//	OnTheFastrack.setComicStrip(csData);
//	OnTheFastrack.setCurrentImageIndex(intCurrentImageIndex);
//	OnTheFastrack.stepThroughDates();
//    }
//
    private void kevinAndKellRoutines(ComicStrip csData) {
	if (KevinAndKell == null) {
	    intDebugCode = 2;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - go Comics initializing object";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	    KevinAndKell = new KevinAndKellComicStripImages(dbgDisplay);
	    KevinAndKell.initializeClassVariables(aryDates, strFolderToSaveImg);
	}
	KevinAndKell.setComicStrip(csData);
	KevinAndKell.setCurrentImageIndex(intCurrentImageIndex);
	KevinAndKell.stepThroughDates();
    }

    private void mediaZenfsRoutines(ComicStrip csData) {
	if (MediaZenfs == null) {
	    intDebugCode = 2;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - go Comics initializing object";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	    MediaZenfs = new MediaZenfsComicStripImages(dbgDisplay);
	    MediaZenfs.initializeClassVariables(aryDates, strFolderToSaveImg);
	}
	MediaZenfs.setComicStrip(csData);
	MediaZenfs.setCurrentImageIndex(intCurrentImageIndex);
	MediaZenfs.stepThroughDates();
    }

    private void ucomicsRoutines(ComicStrip csData) {
	if (Ucomics == null) {
	    intDebugCode = 2;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - UComics initializing object";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	    Ucomics = new UcomicsComicStripImages(dbgDisplay);
	    Ucomics.initializeClassVariables(aryDates, strFolderToSaveImg);
	}
	Ucomics.setComicStrip(csData);
	Ucomics.setCurrentImageIndex(intCurrentImageIndex);
	Ucomics.stepThroughDates();
    }

    private void arcamaxRoutines(ComicStrip csData) {
	if (Arcamax == null) {
	    intDebugCode = 2;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") DriverImageCapture -> stepThroughComicList - Arcamax initializing object";
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	    Arcamax = new ArcamaxComicStripImages(dbgDisplay);
	    Arcamax.initializeClassVariables(aryDates, strFolderToSaveImg);
	   Arcamax.setDriverObject(this);
	}
	Arcamax.setComicStrip(csData);
	Arcamax.setCurrentImageIndex(intCurrentImageIndex);
	Arcamax.stepThroughDates();
    }

}

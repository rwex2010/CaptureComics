package CaptureImages;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KingFeaturesImageWebPageURL extends ImageWebPageURL {

    private URL urlWebPageUrl;
    private String strWebPageUrl;
    private ComicStrip csData;

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String NL = "\n";

    public KingFeaturesImageWebPageURL(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

    public void setCsData(ComicStrip csData) {
	this.csData = csData;
    }

    @Override
    public void setupWebPageUrl(String strImageCode, LocalDate ldWorkingDate) {
	// sample webpage urls:
	// http://beetlebailey.com/comics/September-8-2014/
	// http://retailcomic.com/comics/September-8-2014/
	// http://tinasgroove.com/comics/September-8-2014/
	// http://zitscomics.com/comics/September-8-2014/

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-d-yyyy");
//	String strComicCode = csData.ComicCode;
	switch (strImageCode) {
	case "zits":
	    this.strWebPageUrl = "http://zitscomics.com/comics/" + ldWorkingDate.format(formatter);
	    break;

	case "retail":
	    this.strWebPageUrl = "http://retailcomic.com/comics/" + ldWorkingDate.format(formatter);
	    break;

	case "tinasgroove":
	    this.strWebPageUrl = "http://tinasgroove.com/comics/" + ldWorkingDate.format(formatter);
	    break;

	case "beetlebailey":
//	    this.strWebPageUrl = "http://beetlebailey.com/comics/" + ldWorkingDate.format(formatter);
	    this.strWebPageUrl = "http://comicskingdom.com/beetle-bailey-1/2015-05-20" ;
	    break;

	case "onthefastrack":
	    this.strWebPageUrl = "http://onthefastrack.com/comics/" + ldWorkingDate.format(formatter);
	    break;

	case "safehavenscomic":
	    this.strWebPageUrl = "http://safehavenscomic.com/comics/" + ldWorkingDate.format(formatter);
	    break;
	default:
	    break;
	}
	String tmp = strWebPageUrl;
	try {
	    urlWebPageUrl = new URL(strWebPageUrl);
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    @Override
    public URL getWebPageUrl() {
	return urlWebPageUrl;
    }

}

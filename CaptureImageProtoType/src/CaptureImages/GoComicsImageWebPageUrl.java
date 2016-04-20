package CaptureImages;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GoComicsImageWebPageUrl extends ImageWebPageURL {

    private URL urlWebPageUrl;
    private String strWebPageUrl;

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String NL = "\n";

    public GoComicsImageWebPageUrl(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

    @Override
    public void setupWebPageUrl(String strImageCode, LocalDate ldWorkingDate) {
	// for dates other than today
	// http://www.gocomics.com/brewsterrockit/2014/05/26
	LocalDate locToday = LocalDate.now();
	strWebPageUrl = "http://www.gocomics.com/" + strImageCode;
	if (!locToday.equals(ldWorkingDate)) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	    strWebPageUrl += "/" + ldWorkingDate.format(formatter);
	}

	this.intDebugCode = 4;
	this.strDebugMsg = "**GoComicsImageWebPageUrl.setupWebPageUrl** Web Page Url: " + strWebPageUrl;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	try {
	    intDebugCode = 4;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") GoComicsImageWebPageUrl -> setupWebPageUrl";
	    strDebugMsg += NL + "Web Page URL: " + strWebPageUrl;
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    urlWebPageUrl = new URL(strWebPageUrl);
	} catch (MalformedURLException e) {
	    intDebugCode = 1;
	    strDebugMsg = NL + "dbg=" + intDebugCode + ") GoComicsImageWebPageUrl -> setupWebPageUrl -- caught error";
	    strDebugMsg += NL + "error Message: " + e.getMessage();
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	}

    }

    @Override
    public URL getWebPageUrl() {
	return urlWebPageUrl;
    }

}

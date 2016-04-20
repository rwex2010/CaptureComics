package CaptureImages;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DilbertImageWebPageUrl extends ImageWebPageURL {

    private URL urlWebPageUrl;
    private String strWebPageUrl;

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;

    public DilbertImageWebPageUrl(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

    @Override
    public void setupWebPageUrl(String strImageCode, LocalDate ldWorkingDate) {
	// http://dilbert.com/2014-08-26/

	strWebPageUrl = "http://dilbert.com/strip/";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	strWebPageUrl += ldWorkingDate.format(formatter);

	this.intDebugCode = 4;
	this.strDebugMsg = "**DilbertImageWebPageUrl ->setupWebPageUrl** Web Page Url: " + strWebPageUrl;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	try {
	    urlWebPageUrl = new URL(strWebPageUrl);
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	this.intDebugCode = 1;
	this.strDebugMsg = "**DilbertImageWebPageUrl ->setupWebPageUrl** Web Page Url: " + strWebPageUrl;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    
	    e.printStackTrace();
	}

    }

    @Override
    public URL getWebPageUrl() {
	return urlWebPageUrl;
    }

}

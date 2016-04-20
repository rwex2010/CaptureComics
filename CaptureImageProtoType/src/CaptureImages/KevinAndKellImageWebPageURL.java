package CaptureImages;

import java.net.URL;
import java.time.LocalDate;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;

public class KevinAndKellImageWebPageURL extends ImageWebPageURL {
    private URL urlWebPageUrl;
    private String strWebPageUrl;

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;

    public KevinAndKellImageWebPageURL(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

    @Override
    public void setupWebPageUrl(String strImageCode, LocalDate ldWorkingDate) {
	// http://www.kevinandkell.com/
	// http://www.kevinandkell.com/2014/kk0901.html
	// http://www.kevinandkell.com/2014/strips/kk20140903.jpg -- image url
	strWebPageUrl = "http://www.kevinandkell.com";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");
	int workingYear = ldWorkingDate.getYear();
	strWebPageUrl += "/" + workingYear + "/kk" + ldWorkingDate.format(formatter) + ".html";

	this.intDebugCode = 4;
	this.strDebugMsg = "**KevinAndKellWebPageURL.setupWebPageUrl** Web Page Url: " + strWebPageUrl;
	dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);

	try {
	    urlWebPageUrl = new URL(strWebPageUrl);
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception ex) {
	    this.intDebugCode = 1;
	    this.strDebugMsg = "**SafeHavensWebPageURL.setupWebPageUrl** caught general exception";
	    this.strDebugMsg += "\nWeb Page Url: " + strWebPageUrl;
	    this.strDebugMsg += "\n error: " + ex.getMessage();
	    dbgDisplay.ShowMessage(strDebugMsg, intDebugCode);
	    // TODO: handle exception
	}

    }

    @Override
    public URL getWebPageUrl() {
	return urlWebPageUrl;
    }

}

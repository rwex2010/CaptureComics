package CaptureImages;

import java.net.URL;
import java.time.LocalDate;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;

    
public class ImageWebPageURL {
    protected String strWebPageUrl;
    protected URL urlWebPageUrl;
    
    protected String strImageCode;
    protected LocalDate ldWorkingDate;
    protected DisplayDebugMessage dbgDisplay;
    protected String strDebugMsg = "";
    protected String[] strDebugMsgArray;
    protected int intDebugCode = 512;
    
//    public ImageWebPageURL(DisplayDebugMessage dbgDisplay) {
//	this.dbgDisplay = dbgDisplay;
//    }

    public void setupWebPageUrl(String strImageCode, LocalDate ldWorkingDate) {
	
    }
    
    public URL getWebPageUrl() {
	return urlWebPageUrl;
    }

}

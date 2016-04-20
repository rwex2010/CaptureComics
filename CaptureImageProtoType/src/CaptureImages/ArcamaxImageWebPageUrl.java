package CaptureImages;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ArcamaxImageWebPageUrl extends ImageWebPageURL {

    private URL urlWebPageUrl;
    private String strWebPageUrl;

    private String strImageWebPageUrlForPreviousDay = null;

    private DisplayDebugMessage dbgDisplay;
    private String strDebugMsg = "";
    private String[] strDebugMsgArray;
    private int intDebugCode = 512;
    private String NL = "\n";

    public ArcamaxImageWebPageUrl() {
	// TODO Auto-generated constructor stub
    }

    public ArcamaxImageWebPageUrl(DisplayDebugMessage dbgDisplay) {
	this.dbgDisplay = dbgDisplay;
    }

    @Override
    public void setupWebPageUrl(String strImageCode, LocalDate ldWorkingDate) {
	// sample: http://www.arcamax.com/thefunnies/babyblues/
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM-d-yyyy");
	if (ldWorkingDate.equals(LocalDate.now())) {
	    this.strImageWebPageUrlForPreviousDay = null;
	}
	if (this.strImageWebPageUrlForPreviousDay == null) {
	    this.strWebPageUrl = "http://www.arcamax.com/thefunnies/" + strImageCode;
	} else {

	    this.strWebPageUrl = "http://www.arcamax.com" + this.strImageWebPageUrlForPreviousDay;
	}
	try {
	    this.urlWebPageUrl = new URL(strWebPageUrl);
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    @Override
    public URL getWebPageUrl() {
	return this.urlWebPageUrl;
    }

    public String getStrImageWebPageUrlForPreviousDay() {
	return strImageWebPageUrlForPreviousDay;
    }

    public void setStrImageWebPageUrlForPreviousDay(String strImageWebPageUrlForPreviousDay) {
	this.strImageWebPageUrlForPreviousDay = strImageWebPageUrlForPreviousDay;
    }

}

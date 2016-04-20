package CaptureImages;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ValidateMyUrl {
    public URL testURL;

    public ValidateMyUrl() {
    }
    
    public String validateUrl(URL urlToValidate) {
	String ReturnValue = "Did not try";
	try {
	   URLConnection connection = urlToValidate.openConnection();
	   connection.connect();
	    ReturnValue = "Valid";
	} catch (IOException ioError) {
	    ReturnValue = ioError.getMessage();
	} catch (Exception otherError) {
	    ReturnValue = otherError.getMessage();
	}
	
	return ReturnValue;
    }


}
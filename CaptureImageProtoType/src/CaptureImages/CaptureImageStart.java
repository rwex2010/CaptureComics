package CaptureImages;

import java.awt.EventQueue;


public class CaptureImageStart {

    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    DriverImageCapture driverICap = new DriverImageCapture();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

}

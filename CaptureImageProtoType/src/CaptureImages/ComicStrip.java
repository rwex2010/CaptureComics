package CaptureImages;

public class ComicStrip {
    String id;
    String ComicCode;
    String ComicName;
    String Domain;
    String DateFormat;
    String SundayExt;
    String DailyExt;
    String UrlFormat;
    String SundayAvail;
    String OneOfMyComics;
    String DaysAvailable;

    @Override
    public String toString() {
	if (DaysAvailable == null) {
	    DaysAvailable = "127";
	}
	return ComicCode + "|" + ComicName + "|" + id + "|" + Domain + "|" + SundayAvail + "|" + OneOfMyComics + "|" + DaysAvailable;
    }
}

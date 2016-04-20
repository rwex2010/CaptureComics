package CaptureImages;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    DriverImageCaptureTest.class,
    ArcamaxComicStripImagesTest.class,
    ArcamaxImageWebPageUrlTest.class,
    DilbertComicStripImagesTest.class,
    DilbertImageWebPageUrlTest.class,
    DilbertHtmlWebPageReaderTest.class,
    DilbertStepThruWebPageTagsToGetImageUrlTest.class,
    DilbertWebPageHtmlParserTest.class,
    GoComicsComicStripImagesTest.class,
    GoComicsImageWebPageUrlTest.class, 
    GoComicsHtmlWebPageReaderTest.class,
    GoComicsStepThruWebPageTagsToGetImageUrlTest.class,
    GoComicsWebPageHtmlParserTest.class,
    ProcessStartTagsTest.class,
    KingFeaturesStepThruWebPageTagsToGetImageUrlTest.class,
    KingFeaturesWebPageHtmlParserTest.class,
    KingFeaturesImageWebPageURLTest.class,
    KevinAndKellImageWebPageURLTest.class})
public class AllTests {

}

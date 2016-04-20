package CaptureImages;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    DilbertComicStripImagesTest.class,
    DilbertImageWebPageUrlTest.class,
    DilbertHtmlWebPageReaderTest.class,
    DilbertStepThruWebPageTagsToGetImageUrlTest.class,
    DilbertWebPageHtmlParserTest.class})
public class AllDilbertTests {

}

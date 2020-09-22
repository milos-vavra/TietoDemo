import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sentenceparser.Demo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class DemoTest {

    private static final String PROPER_CSV_OUTPUT =
            ", Word 1, Word 2, Word 3, Word 4, Word 5, Word 6, Word 7, Word 8, Word 9, Word 10, Word 11, Word 12, Word 13, Word 14, Word 15, Word 16, Word 17, Word 18, Word 19, Word 20" +
            ", Word 21, Word 22, Word 23, Word 24, Word 25, Word 26, Word 27, Word 28, Word 29, Word 30, Word 31, Word 32, Word 33, Word 34, Word 35, Word 36, Word 37, Word 38, Word 39" +
            ", Word 40, Word 41, Word 42, Word 43, Word 44, Word 45, Word 46, Word 47, Word 48, Word 49, Word 50\n" +
            "Sentence 1, a, demo, file, is, This\n" +
            "Sentence 2, a, can, contains, fine, It, it's, processor, see, sentence, so, test, text, the, to, we, working\n" +
            "Sentence 3, Is, it, like, sufficient, this\n" +
            "Sentence 4, see, We, will";

    private static final String PROPER_XML_OUTPUT =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<text>\n" +
                "\t<sentence>\n" +
                    "\t\t<word>a</word>\n" +
                    "\t\t<word>demo</word>\n" +
                    "\t\t<word>file</word>\n" +
                    "\t\t<word>is</word>\n" +
                    "\t\t<word>This</word>\n" +
                "\t</sentence>\n" +
                "\t<sentence>\n" +
                    "\t\t<word>a</word>\n" +
                    "\t\t<word>can</word>\n" +
                    "\t\t<word>contains</word>\n" +
                    "\t\t<word>fine</word>\n" +
                    "\t\t<word>It</word>\n" +
                    "\t\t<word>it's</word>\n" +
                    "\t\t<word>processor</word>\n" +
                    "\t\t<word>see</word>\n" +
                    "\t\t<word>sentence</word>\n" +
                    "\t\t<word>so</word>\n" +
                    "\t\t<word>test</word>\n" +
                    "\t\t<word>text</word>\n" +
                    "\t\t<word>the</word>\n" +
                    "\t\t<word>to</word>\n" +
                    "\t\t<word>we</word>\n" +
                    "\t\t<word>working</word>\n" +
                "\t</sentence>\n" +
                "\t<sentence>\n" +
                    "\t\t<word>Is</word>\n" +
                    "\t\t<word>it</word>\n" +
                    "\t\t<word>like</word>\n" +
                    "\t\t<word>sufficient</word>\n" +
                    "\t\t<word>this</word>\n" +
                "\t</sentence>\n" +
                "\t<sentence>\n" +
                    "\t\t<word>see</word>\n" +
                    "\t\t<word>We</word>\n" +
                    "\t\t<word>will</word>\n" +
                "\t</sentence>\n" +
            "</text>";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testValidXmlOutput() {
        String[] mainParams = {"xml", "src/test/resources/test_input.txt"};
        Demo.main(mainParams);
        assertEquals(PROPER_XML_OUTPUT, outContent.toString());
    }

    @Test
    public void testValidCsvOutput() {
        String[] mainParams = {"csv", "src/test/resources/test_input.txt"};
        Demo.main(mainParams);
        assertEquals(PROPER_CSV_OUTPUT, outContent.toString());
    }
}

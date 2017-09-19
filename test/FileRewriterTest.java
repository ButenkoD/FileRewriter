import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import static org.junit.Assert.assertEquals;
import com.andersenlab.file_rewriter.*;

public class FileRewriterTest {
    private final static String[] SOURCE_FILE_NAMES = new String[] {
        "test/files/1.dat",
        "test/files/2.dat",
        "test/files/3.dat"
    };
    private final static String RESULT_FILE_NAME = "test/files/r.dat";

    @Test
    public void testRewriterFirstLine() throws Exception {
        prepareResultFile();
        BufferedReader resultReader = getResultReader();
        String resultFirstLine = resultReader.readLine();
        assertEquals("aaa", resultFirstLine);
    }

    @Test
    public void testRewriterSecondLine() throws Exception {
        prepareResultFile();
        BufferedReader resultReader = getResultReader();
        resultReader.readLine();
        String resultThirdLine = resultReader.readLine();
        assertEquals("111", resultThirdLine);
    }

    @Test
    public void testRewriterThirdLine() throws Exception {
        prepareResultFile();
        BufferedReader resultReader = getResultReader();
        resultReader.readLine();
        resultReader.readLine();
        String resultThirdLine = resultReader.readLine();
        assertEquals("---", resultThirdLine);
    }

    @Test
    public void testRewriterLastLine() throws Exception {
        prepareResultFile();
        BufferedReader resultReader = getResultReader();
        String currentLine;
        String lastLine = "";
        while ((currentLine = resultReader.readLine()) != null) {
            lastLine = currentLine;
        }
        assertEquals("555", lastLine);
    }

    private void prepareResultFile() throws Exception{
        FileRewriter fileRewriter = new FileRewriter();
        fileRewriter.run(SOURCE_FILE_NAMES, RESULT_FILE_NAME);
    }

    private BufferedReader getResultReader() throws Exception {
        return new BufferedReader(new FileReader(RESULT_FILE_NAME));
    }
}


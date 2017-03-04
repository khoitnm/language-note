package tnmk.ln.test.opennlp;

import opennlp.tools.stemmer.Stemmer;
import opennlp.tools.tokenize.Tokenizer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tnmk.ln.infrastructure.LanguageConst;
import tnmk.ln.infrastructure.nlp.opennlp.OpenNLPLoader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
public class StringTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(StringTest.class);
    private static String s = "Mortals rule the desert nation of Miraji, but mythical beasts still roam the wild and remote areas, and rumor has it that somewhere, djinn still perform their magic.  For humans, it’s an unforgiving place, especially if you’re poor, orphaned, or female.";

    @Test
    public void test() {
        String regexp =
                // "\\W+";//[I, want, to, walk, my, d, og, cat, and, taran, tula, may, be, even, my, tortoise]
                "['\\s]";
        String[] words = s.split(regexp);
        LOGGER.info(Arrays.toString(words));
    }

    @Test
    public void test2() throws FileNotFoundException {
        Tokenizer tokenizer = OpenNLPLoader.getTokenizer();
        Stemmer stemmer = OpenNLPLoader.getSnowballStemmer(LanguageConst.EN);
//        Stemmer stemmer = OpenNLPLoader.getPorterStemmer();

        String[] words = tokenizer.tokenize(s);
        List<String> stemmedWords = new ArrayList<>(words.length);
        for (String word : words) {
            String stemmedWord = stemmer.stem(word).toString();
            stemmedWords.add(stemmedWord);
        }
        LOGGER.info("Original:\n" + Arrays.toString(words));
        LOGGER.info("Stemmed:\n" + stemmedWords);

    }

    public void testStemmer() {

    }
}

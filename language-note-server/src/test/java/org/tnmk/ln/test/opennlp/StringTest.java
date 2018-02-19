package org.tnmk.ln.test.opennlp;

import opennlp.tools.stemmer.Stemmer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.utils.datatype.StringUtils;
import org.tnmk.ln.app.dictionary.entity.Locale;
import org.tnmk.ln.infrastructure.nlp.opennlp.OpenNLPLoader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 3/4/17.
 */
public class StringTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(StringTest.class);
    private static String s = "Mortals rule the desert <nation> of Miraji, but mythical(s) beasts still roam the wild and remote areas, and rumor has it that somewhere, djinn still perform their magic.  For humans, it’s an unforgiving place, especially if you’re poor. [1]abc "
            + "orphaned, "
            + "or female.";

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
        Stemmer stemmer = OpenNLPLoader.getSnowballStemmer(Locale.EN_EN.getLanguage());

        String[] words = tokenizer.tokenize(s);
        List<String> stemmedWords = new ArrayList<>(words.length);
        for (String word : words) {
            String stemmedWord = stemmer.stem(word).toString();
            stemmedWords.add(stemmedWord);
        }

        String merged = StringUtils.newStringWithDelimiter(" ", words);
        LOGGER.info("Original Split:\n" + Arrays.toString(words));
        LOGGER.info("Original:\n" + s);
        LOGGER.info("Merged:\n" + merged);
        LOGGER.info("Compare:" + merged.equals(s));
        LOGGER.info("Stemmed:\n" + stemmedWords);

    }

    @Test
    public void test3() throws FileNotFoundException {
        Tokenizer tokenizer = OpenNLPLoader.getTokenizer();
        Span[] spans = tokenizer.tokenizePos(s);
        String[] words = Span.spansToStrings(spans, s);
        int i = 0;
        for (Span span : spans) {
            String wordFromSpan = s.substring(span.getStart(), span.getEnd());
            String wordFromSplit = words[i];
            String strSpan = String.format("[%s-%s] %s %s = %s", span.getStart(), span.getEnd(), wordFromSpan, wordFromSplit, wordFromSpan.equals(wordFromSplit));
            LOGGER.debug(strSpan);
            i++;
        }

    }
}

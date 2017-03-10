package tnmk.ln.test.stemming;

import org.junit.Assert;
import org.junit.Test;
import tnmk.ln.app.dictionary.entity.Locale;
import tnmk.ln.infrastructure.stemming.LemmaFindingService;
import tnmk.ln.infrastructure.stemming.LemmaSpan;
import tnmk.ln.test.PureTest;

import java.util.List;

/**
 * @author khoi.tran on 3/10/17.
 */
public class StemmingTest extends PureTest {
    LemmaFindingService wordStemmingService = new LemmaFindingService();
    private static String string = "Once upon a time there were 2 twin brothers who were not alike at all. Their names where Tim, and Tom. The twin brothers lived right across a candy shop.";

    @Test
    public void stemWord() {
        List<LemmaSpan> lemmaSpans = wordStemmingService.findByLemma(Locale.EN_EN.getLanguage(), "is", string);
        LOGGER.debug(lemmaSpans.toString());
        Assert.assertEquals(2, lemmaSpans.size());
    }
}

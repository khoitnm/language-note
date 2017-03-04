package tnmk.ln.infrastructure.nlp.opennlp;

import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.stemmer.snowball.SnowballStemmer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import tnmk.common.exception.UnexpectedException;
import tnmk.common.util.IOUtil;
import tnmk.ln.infrastructure.LanguageConst;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author khoi.tran on 3/4/17.
 */
public class OpenNLPLoader {

    public static Tokenizer getTokenizer() {
        InputStream modelIn = IOUtil.loadInputStreamFileInClassPath("/opennlp/en-token.bin");//new FileInputStream("/en-token.bin");
        TokenizerModel model = null;
        try {
            model = new TokenizerModel(modelIn);
        } catch (IOException e) {
            throw new UnexpectedException("Cannot load OpenNLP Tokenizer: " + e.getMessage(), e);
        } finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (IOException e) {
                    throw new UnexpectedException("Cannot close OpenNLP model: " + e.getMessage(), e);
                }
            }
        }
        return new TokenizerME(model);
    }

    /**
     * @param language {@link tnmk.ln.infrastructure.LanguageConst}
     */
    public static SnowballStemmer getSnowballStemmer(String language) {
        SnowballStemmer.ALGORITHM algorithm = toAlgorithm(language);
        return new SnowballStemmer(algorithm);

    }

    public static PorterStemmer getPorterStemmer() {
        return new PorterStemmer();
    }

    private static SnowballStemmer.ALGORITHM toAlgorithm(String language) {
        if (LanguageConst.EN.equals(language)) {
            return SnowballStemmer.ALGORITHM.ENGLISH;
        } else {
            return SnowballStemmer.ALGORITHM.ENGLISH;
        }
    }
}

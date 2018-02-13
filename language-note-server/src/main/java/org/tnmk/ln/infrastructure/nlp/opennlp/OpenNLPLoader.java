package org.tnmk.ln.infrastructure.nlp.opennlp;

import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.stemmer.snowball.SnowballStemmer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.tnmk.common.exception.UnexpectedException;
import org.tnmk.common.util.IOUtil;
import org.tnmk.ln.app.dictionary.entity.Locale;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author khoi.tran on 3/4/17.
 */
public class OpenNLPLoader {
    private static Tokenizer TOKENIZER = null;

    public static Tokenizer getTokenizer() {
        if (TOKENIZER != null) {
            return TOKENIZER;
        }
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
        TOKENIZER = new TokenizerME(model);
        return TOKENIZER;
    }

    /**
     * @param language {@link Locale#language}
     */
    public static SnowballStemmer getSnowballStemmer(String language) {
        SnowballStemmer.ALGORITHM algorithm = toAlgorithm(language);
        return new SnowballStemmer(algorithm);

    }

    /**
     * Should use {@link SnowballStemmer}
     *
     * @return
     */
    @Deprecated
    public static PorterStemmer getPorterStemmer() {
        return new PorterStemmer();
    }

    /**
     * @param language the value comes from {@link Locale#language}
     * @return
     */
    private static SnowballStemmer.ALGORITHM toAlgorithm(String language) {
        if (Locale.EN_EN.getLanguage().equals(language)) {
            return SnowballStemmer.ALGORITHM.ENGLISH;
        } else {
            return SnowballStemmer.ALGORITHM.ENGLISH;
        }
    }
}

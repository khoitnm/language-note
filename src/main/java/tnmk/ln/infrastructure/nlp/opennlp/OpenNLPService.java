package tnmk.ln.infrastructure.nlp.opennlp;

import opennlp.tools.stemmer.Stemmer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;
import tnmk.ln.infrastructure.stemming.LemmaSpan;

/**
 * @author khoi.tran on 3/10/17.
 */
public class OpenNLPService {
    private Stemmer getStemmer(String language) {
        return OpenNLPLoader.getSnowballStemmer(language);
    }

    /**
     * In morphology and lexicography, a lemma is the canonical form, dictionary form, or citation form of a set of words (headword) [citation needed].
     * In English, for example, run, runs, ran and running are forms of the same lexeme, with run as the lemma.
     *
     * @param text
     */
    public LemmaSpan[] analyzeLemmas(String language, String text) {
        Stemmer stemmer = getStemmer(language);
        Tokenizer tokenizer = OpenNLPLoader.getTokenizer();
        Span[] spans = tokenizer.tokenizePos(text);
        String[] spanWords = Span.spansToStrings(spans, text);
        LemmaSpan[] lemmaSpans = new LemmaSpan[spanWords.length];
        for (int i = 0; i < spanWords.length; i++) {
            Span span = spans[i];
            String word = spanWords[i];
            String lemma = stemmer.stem(word).toString();
            LemmaSpan lemmaSpan = new LemmaSpan();
            lemmaSpans[i] = lemmaSpan;

            lemmaSpan.setOriginalWord(word);
            lemmaSpan.setLemma(lemma);
            lemmaSpan.setStart(span.getStart());
            lemmaSpan.setEnd(span.getEnd());
        }
        return lemmaSpans;
    }

    public String lemma(String language, String word) {
        return getStemmer(language).stem(word).toString();
    }
}

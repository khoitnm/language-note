package tnmk.ln.infrastructure.stemming;

/**
 * In morphology and lexicography, a lemma is the canonical form, dictionary form, or citation form of a set of words (headword).
 * In English, for example, run, runs, ran and running are forms of the same lexeme, with run as the lemma.
 *
 * @author khoi.tran on 3/10/17.
 */
public class LemmaSpan {

    private String lemma;
    private String originalWord;
    /**
     * Start position of spanWord in a text.
     */
    private int start;
    /**
     * Start position of spanWord in a text.
     */
    private int end;

    @Override
    public String toString() {
        return String.format("[%s-%s] '%s' -> '%s'", start, end, originalWord, lemma);
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}

package org.tnmk.ln.app.vocabulary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.tnmk.ln.app.vocabulary.util.WordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author khoi.tran on 2/4/17.
 */
public class PhrasalVerb {

    private List<Word> words = new ArrayList<>();

    public PhrasalVerb() {
    }

    public String toExpression() {
        return WordUtils.toString(words);
    }

    public String toString() {
        return WordUtils.toString(this.words);
    }

    private Word findByType(String wordType) {
        for (Word word : words) {
            if (word.getType().equals(wordType)) {
                return word;
            }
        }
        return null;
    }

    @JsonIgnore
    public Word getVerb() {
        return findByType("verb");
    }

    @JsonIgnore
    public String getVerbValue() {
        Word verb = getVerb();
        return verb != null ? verb.getValue() : null;
    }

    @JsonIgnore
    public void setVerb(String verb) {
        Word verbWord = getVerb();
        if (verbWord == null) {
            verbWord = new Word();
        }
        verbWord.setValue(verb);
        verbWord.setType("verb");
        verbWord.setIndex(0);
        if (words.isEmpty()) {
            words.add(verbWord);
        } else {
            words.set(0, verbWord);
        }
    }

    @JsonIgnore
    public String getRemainWords() {
        return IntStream.range(1, words.size()).mapToObj(i -> words.get(i).getValue()).collect(Collectors.joining(" "));
    }

    @JsonIgnore
    public Word getPreposition() {
        return findByType("preposition");
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

}

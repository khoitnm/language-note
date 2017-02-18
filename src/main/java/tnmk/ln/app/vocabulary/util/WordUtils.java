package tnmk.ln.app.vocabulary.util;

import tnmk.ln.app.vocabulary.entity.Word;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 2/4/17.
 */
public class WordUtils {
    public static String toString(List<Word> words) {
        if (words == null) {
            return "";
        }
        return words.stream().filter(i -> i != null).map(word -> word.getValue().trim()).collect(Collectors.joining(" "));
    }
}

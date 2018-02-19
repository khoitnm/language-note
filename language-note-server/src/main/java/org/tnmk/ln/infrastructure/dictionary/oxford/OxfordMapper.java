package org.tnmk.ln.infrastructure.dictionary.oxford;

import org.tnmk.ln.app.vocabulary.entity.WordType;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.Sentence;
import org.tnmk.common.utils.datatype.StringUtils;
import org.tnmk.ln.app.vocabulary.entity.Meaning;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.Entry;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.LexicalEntry;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.Sense;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 2/24/17.
 */
public class OxfordMapper {
    public static List<Meaning> toMeanings(OxfordWord oxfordWord) {
        List<Meaning> result = new ArrayList<>();
        if (oxfordWord == null || oxfordWord.getLexicalEntries() == null) {
            return result;
        }
        for (LexicalEntry lexicalEntry : oxfordWord.getLexicalEntries()) {
            result.addAll(toMeanings(lexicalEntry));
        }
        return result;
    }

    public static List<Meaning> toMeanings(LexicalEntry lexicalEntry) {
        List<Meaning> result = new ArrayList<>();
        WordType wordType = convertLexicalCateogryToWordType(lexicalEntry.getLexicalCategory());
        List<Sentence> exampleSentences = lexicalEntry.getSentences();
        for (Entry entry : lexicalEntry.getEntries()) {
            List<Sense> senses = entry.getSenses();
            for (Sense sense : senses) {
                String explanation = StringUtils.joinNotBlankStrings(" | ", sense.getDefinitions().toArray(new String[0]));
                Meaning meaning = new Meaning();
                meaning.setWordType(wordType);
                meaning.setExplanation(explanation);
                List<String> examples = new ArrayList<>();
                if (sense.getExamples() != null) {
                    examples = sense.getExamples().stream().map(example -> example.getText()).collect(Collectors.toList());
                }
                meaning.setExamples(examples);

                for (Sentence exampleSentence : exampleSentences) {
                    if (exampleSentence.getSenseIds().contains(sense.getId())) {
                        examples.add(exampleSentence.getText());
                    }
                }

                result.add(meaning);
            }
        }
        return result;
    }

    public static WordType convertLexicalCateogryToWordType(String lexicalCategory) {
        WordType wordType;
        switch (lexicalCategory.toLowerCase().trim()) {
        case "adjective":
            wordType = WordType.ADJ;
            break;
        case "adverb":
            wordType = WordType.ADV;
            break;
        case "verb":
            wordType = WordType.VERB;
            break;
        case "noun":
            wordType = WordType.NOUN;
            break;
        case "preposition":
            wordType = WordType.PREPOSITION;
            break;
        default:
            wordType = null;
        }
        return wordType;
    }
}

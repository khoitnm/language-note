package org.tnmk.ln.app.dictionary;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.tnmk.ln.app.dictionary.entity.Example;
import org.tnmk.ln.app.dictionary.entity.Expression;
import org.tnmk.ln.app.dictionary.entity.LexicalType;
import org.tnmk.ln.app.dictionary.entity.Locale;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.Entry;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.LexicalEntry;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.Pronunciation;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.Sentence;
import org.tnmk.ln.app.dictionary.entity.SenseGroup;
import org.tnmk.ln.infrastructure.dictionary.oxford.entity.OxfordWord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author khoi.tran on 2/28/17.
 */
@Component
public class ExpressionMapper {
    public static Expression toExpression(OxfordWord oxfordWord) {
        if (oxfordWord == null) return null;
        Expression expression = new Expression();
        expression.setText(oxfordWord.getWord());
        expression.setLocale(toLocale(oxfordWord.getLanguage()));
        expression.setSenseGroups(toSenseGroups(oxfordWord.getLexicalEntries()));
        return expression;
    }

    //TODO need to migrate the pronunciation to old expression
    private static List<SenseGroup> toSenseGroups(List<LexicalEntry> oxfordLexicalEntries) {
        List<SenseGroup> senseGroups = new ArrayList<>();
        if (oxfordLexicalEntries == null) return senseGroups;
        for (LexicalEntry oxfordLexicalEntry : oxfordLexicalEntries) {
            SenseGroup senseGroup = new SenseGroup();
            senseGroup.setPronunciations(toPronunciations(oxfordLexicalEntry.getPronunciations()));
            senseGroup.setLexicalType(toLexicalType(oxfordLexicalEntry.getLexicalCategory()));
            senseGroup.setSenses(toSenses(oxfordLexicalEntry, oxfordLexicalEntry.getEntries()));
            senseGroups.add(senseGroup);
        }
        return senseGroups;
    }

    private static List<org.tnmk.ln.app.dictionary.entity.Pronunciation> toPronunciations(List<Pronunciation> pronunciations) {
        return pronunciations.stream()
            .map(pronunciation -> toPronunciation(pronunciation))
            .filter(pronunciation -> pronunciation != null)
            .collect(Collectors.toList());
    }
    private static org.tnmk.ln.app.dictionary.entity.Pronunciation toPronunciation(Pronunciation pronunciation) {
        if (pronunciation == null) {
            return null;
        }
        org.tnmk.ln.app.dictionary.entity.Pronunciation result = new org.tnmk.ln.app.dictionary.entity.Pronunciation();
        result.setAudioFile(pronunciation.getAudioFile());
        result.setDialects(pronunciation.getDialects());
        result.setPhoneticNotation(pronunciation.getPhoneticNotation());
        result.setPhoneticSpelling(pronunciation.getPhoneticSpelling());
        return result;
    }
    private static List<org.tnmk.ln.app.dictionary.entity.Sense> toSenses(LexicalEntry oxfordLexicalEntry, List<Entry> entries) {
        List<org.tnmk.ln.app.dictionary.entity.Sense> senses = new ArrayList<>();
        if (entries == null) return senses;
        for (Entry entry : entries) {
            List<org.tnmk.ln.infrastructure.dictionary.oxford.entity.Sense> entrySenses = entry.getSenses();
            for (org.tnmk.ln.infrastructure.dictionary.oxford.entity.Sense entrySense : entrySenses) {
                senses.add(toSense(oxfordLexicalEntry, entrySense));
            }
        }
        return senses;
    }

    private static org.tnmk.ln.app.dictionary.entity.Sense toSense(LexicalEntry oxfordLexicalEntry, org.tnmk.ln.infrastructure.dictionary.oxford.entity.Sense oxfordSense) {
        if (oxfordSense.getDefinitions() == null || oxfordSense.getDefinitions().isEmpty()) return null;
        org.tnmk.ln.app.dictionary.entity.Sense sense = new org.tnmk.ln.app.dictionary.entity.Sense();
        String senseExplanation = oxfordSense.getDefinitions().stream().collect(Collectors.joining(" | "));
        sense.setExplanation(senseExplanation);
        sense.setExamples(toExamples(oxfordLexicalEntry, oxfordSense));
        return sense;
    }

    private static List<Example> toExamples(LexicalEntry oxfordLexicalEntry, org.tnmk.ln.infrastructure.dictionary.oxford.entity.Sense entrySense) {
        List<org.tnmk.ln.infrastructure.dictionary.oxford.entity.Example> oxfordExamples = entrySense.getExamples();
        if (oxfordExamples == null) return new ArrayList<>();
        List<Example> examples = oxfordExamples.stream().map(iexample -> toExample(iexample)).filter(iexample -> !iexample.isEmpty()).collect(Collectors.toList());
        examples.addAll(toExamplesForSense(entrySense, oxfordLexicalEntry.getSentences()));
        return examples;
    }

    private static List<? extends Example> toExamplesForSense(org.tnmk.ln.infrastructure.dictionary.oxford.entity.Sense entrySense, List<Sentence> sentences) {
        List<Example> examples = new ArrayList<>();
        if (sentences == null) return examples;
        for (Sentence sentence : sentences) {
            if (sentence.getSenseIds() != null && sentence.getSenseIds().contains(entrySense.getId())) {
                Example example = new Example();
                example.setText(sentence.getText());
                examples.add(example);
            }
        }
        return examples;
    }

    private static Example toExample(org.tnmk.ln.infrastructure.dictionary.oxford.entity.Example oxfordExample) {
        if (oxfordExample == null || StringUtils.isBlank(oxfordExample.getText())) return null;
        Example example = new Example();
        example.setText(oxfordExample.getText());
        return example;
    }

    private static LexicalType toLexicalType(String lexicalCategory) {
        LexicalType lexicalType = null;
        if (lexicalCategory.equalsIgnoreCase("noun")) {
            return LexicalType.NOUN;
        } else if (lexicalCategory.equalsIgnoreCase("adjective")) {
            return LexicalType.ADJ;
        } else if (lexicalCategory.equalsIgnoreCase("adverb")) {
            return LexicalType.ADV;
        } else if (lexicalCategory.equalsIgnoreCase("verb")) {
            return LexicalType.VERB;
        } else if (lexicalCategory.equalsIgnoreCase("preposition")) {
            return LexicalType.PREPOSITION;
        }
        return lexicalType;
    }

    private static Locale toLocale(String oxfordWordLanguage) {
        if (StringUtils.isBlank(oxfordWordLanguage)) return null;
        if (oxfordWordLanguage.equals(Locale.CODE_EN)) {
            return Locale.EN_EN;
        }
        return null;
    }
}

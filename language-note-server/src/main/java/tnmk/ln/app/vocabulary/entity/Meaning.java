package tnmk.ln.app.vocabulary.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 1/25/17.
 */
public class Meaning {
    @NotBlank
    private String explanation;
    @Indexed
    private WordType wordType;
    private List<String> examples;
    private List<FillingQuestion> fillingQuestions;

    public Meaning() {
        examples = new ArrayList<>();
        examples.add("");//Add an empty element.
        fillingQuestions = new ArrayList<>();
    }

    public WordType getWordType() {
        return wordType;
    }

    public void setWordType(WordType wordType) {
        this.wordType = wordType;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public List<FillingQuestion> getFillingQuestions() {
        return fillingQuestions;
    }

    public void setFillingQuestions(List<FillingQuestion> fillingQuestions) {
        this.fillingQuestions = fillingQuestions;
    }
}

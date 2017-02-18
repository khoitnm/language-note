package tnmk.ln.app.dictionary.entity;

import java.util.List;

/**
 * @author khoi.tran on 2/18/17.
 */
public class Sense {
    private String explanation;
    private List<Example> examples;

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }
}

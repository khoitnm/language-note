package tnmk.ln.test.factory;

import tnmk.ln.app.dictionary.entity.Example;
import tnmk.ln.app.dictionary.entity.Sense;

import java.util.ArrayList;
import java.util.List;

/**
 * @author khoi.tran on 3/6/17.
 */
public class SenseTestFactory {
    public static Sense construct(String explanation, String... exampleStrings) {
        Sense sense = new Sense();
        sense.setExplanation(explanation);

        List<Example> exampleList = new ArrayList<>();
        for (String exampleString : exampleStrings) {
            Example example = new Example();
            example.setText(exampleString);
            exampleList.add(example);
        }
        sense.setExamples(exampleList);
        return sense;
    }
}

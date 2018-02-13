package tnmk.ln.app.dictionary;

import tnmk.ln.app.dictionary.entity.Example;

/**
 * @author khoi.tran on 3/4/17.
 */
public class ExampleFactory {
    public static Example constructSchema() {
        Example example = new Example();
        return example;
    }
}

package org.tnmk.ln.test.factory;

import org.tnmk.ln.app.dictionary.entity.LexicalType;
import org.tnmk.ln.app.dictionary.entity.SenseGroup;

import java.util.Arrays;

/**
 * @author khoi.tran on 3/6/17.
 */
public class SenseGroupTestFactory {
    public static SenseGroup constructSenseGroup(LexicalType lexicalType) {
        SenseGroup senseGroup = new SenseGroup();
        senseGroup.setLexicalType(lexicalType);
        senseGroup.setSenses(Arrays.asList(
                SenseTestFactory.construct("test_sense1", "test_exampl1.1", "test_example1.2")
                , SenseTestFactory.construct("test_sense2", "test_exampl2.1", "test_example2.2")
        ));
        return senseGroup;
    }
}

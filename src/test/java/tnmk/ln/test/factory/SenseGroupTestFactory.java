package tnmk.ln.test.factory;

import tnmk.common.util.SetUtil;
import tnmk.ln.app.dictionary.entity.LexicalType;
import tnmk.ln.app.dictionary.entity.SenseGroup;

/**
 * @author khoi.tran on 3/6/17.
 */
public class SenseGroupTestFactory {
    public static SenseGroup constructSenseGroup(LexicalType lexicalType) {
        SenseGroup senseGroup = new SenseGroup();
        senseGroup.setLexicalType(lexicalType);
        senseGroup.setSenses(SetUtil.constructSet(
                SenseTestFactory.construct("test_sense1", "test_exampl1.1", "test_example1.2")
                , SenseTestFactory.construct("test_sense2", "test_exampl2.1", "test_example2.2")
        ));
        return senseGroup;
    }
}

package tnmk.ln.app.dictionary;

import tnmk.common.util.SetUtil;
import tnmk.ln.app.dictionary.entity.SenseGroup;

/**
 * @author khoi.tran on 3/4/17.
 */
public class SenseGroupFactory {
    public static SenseGroup constructSchema() {
        SenseGroup senseGroup = new SenseGroup();
        senseGroup.setSenses(SetUtil.constructSet(SenseFactory.constructSchema()));
        return senseGroup;
    }
}

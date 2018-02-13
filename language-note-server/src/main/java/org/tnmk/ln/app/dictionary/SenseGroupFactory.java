package org.tnmk.ln.app.dictionary;

import org.tnmk.ln.app.dictionary.entity.SenseGroup;

import java.util.Arrays;

/**
 * @author khoi.tran on 3/4/17.
 */
public class SenseGroupFactory {
    public static SenseGroup constructSchema() {
        SenseGroup senseGroup = new SenseGroup();
        senseGroup.setSenses(Arrays.asList(SenseFactory.constructSchema()));
        return senseGroup;
    }
}

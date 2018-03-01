package org.tnmk.common.util.datatype;

import org.junit.Assert;
import org.junit.Test;
import org.tnmk.common.exception.BadArgumentException;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.constants.CreatureKind;
import org.tnmk.common.utils.datatype.EnumUtils;
import org.tnmk.common.utils.datatype.NumberUtils;

/**
 * @author khoi.tran on 6/7/17.
 */
public class EnumUtilsTest {

    @Test
    public void validateExistEnum_byField() {
        CreatureKind creatureKind = EnumUtils.validateExistEnum(CreatureKind.class,"displayName","Muggle");
        Assert.assertEquals(CreatureKind.MUGGLE, creatureKind);
    }

    @Test(expected = BadArgumentException.class)
    public void validateExistEnum_byField_notFound() {
        CreatureKind creatureKind = EnumUtils.validateExistEnum(CreatureKind.class,"displayName","SomeValue"+System.currentTimeMillis());
    }
    @Test
    public void validateExistEnum_byEnumValue() {
        CreatureKind creatureKind = EnumUtils.validateExistEnum(CreatureKind.class,"MUGGLE");
        Assert.assertEquals(CreatureKind.MUGGLE, creatureKind);
    }
    @Test(expected = BadArgumentException.class)
    public void validateExistEnum_byEnumValue_notFound() {
        CreatureKind creatureKind = EnumUtils.validateExistEnum(CreatureKind.class,"SomeValue"+System.currentTimeMillis());
    }
}

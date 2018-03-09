package org.tnmk.ln.test.common.utils;

import org.junit.Test;
import org.tnmk.common.utils.ToStringUtils;
import org.tnmk.ln.test.UnitBaseTest;

/**
 * @author khoi.tran on 3/10/17.
 */
public class LogUtilsTest extends UnitBaseTest {
    @Test
    public void test() {
        Object[] arr = new Object[] { 2, "a", 5l, new Long(3), new Long[] { 2l, 3l } };
//        Long arr = new Long(3);
//        Long[] arr=new Long[] {2l, 3l};
        String result = ToStringUtils.toSimpleString(arr);
        LOGGER.debug(result);
    }
}

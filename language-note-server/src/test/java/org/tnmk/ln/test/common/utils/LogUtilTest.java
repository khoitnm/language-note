package org.tnmk.ln.test.common.utils;

import org.junit.Test;
import org.tnmk.common.util.LogUtil;
import org.tnmk.ln.test.PureTest;

/**
 * @author khoi.tran on 3/10/17.
 */
public class LogUtilTest extends PureTest {
    @Test
    public void test() {
        Object[] arr = new Object[] { 2, "a", 5l, new Long(3), new Long[] { 2l, 3l } };
//        Long arr = new Long(3);
//        Long[] arr=new Long[] {2l, 3l};
        String result = LogUtil.toString(arr);
        LOGGER.debug(result);
    }
}

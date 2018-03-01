package org.tnmk.ln.test.common.utils;

import org.junit.Test;
import org.tnmk.common.utils.http.LogRequestUtils;
import org.tnmk.ln.test.PureTest;

/**
 * @author khoi.tran on 3/10/17.
 */
public class LogUtilsTest extends PureTest {
    @Test
    public void test() {
        Object[] arr = new Object[] { 2, "a", 5l, new Long(3), new Long[] { 2l, 3l } };
//        Long arr = new Long(3);
//        Long[] arr=new Long[] {2l, 3l};
        String result = LogRequestUtils.toSimpleString(arr);
        LOGGER.debug(result);
    }
}

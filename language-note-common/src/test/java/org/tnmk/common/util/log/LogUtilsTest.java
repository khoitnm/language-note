package org.tnmk.common.util.log;


import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.utils.ToStringUtils;
import org.tnmk.common.utils.log.LogUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 6/5/17.
 */
public class LogUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(LogUtilsTest.class);

    @Test
    public void logRuntime() {
        Instant startTime = Instant.now();
        doSomething();
        Instant endTime = LogUtils.logRuntime(startTime, "doSomething");
        Assert.assertFalse(endTime.isBefore(startTime));//EndTime >= startTime
    }
    private void doSomething(){
        String s="";
        for (int i = 0; i < 100; i++) {
            s+=i;
        }
    }
}

package org.tnmk.ln.test;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.test.UnitTest;

/**
 * This base class is used for testing functions which have nothing to do with Spring context
 * @author khoi.tran on 2/26/17.
 */
@Ignore
@Category(UnitTest.class)
public class UnitBaseTest{
    protected static final Logger LOGGER = LoggerFactory.getLogger(UnitBaseTest.class);
}

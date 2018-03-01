package org.tnmk.common.util.datatype;

import org.junit.Assert;
import org.junit.Test;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.utils.datatype.StringUtils;

import java.util.Arrays;

/**
 * @author khoi.tran on 6/7/17.
 */
public class StringUtilsTest {

    @Test
    public void joinNotBlankStrings() {
        Assert.assertEquals("abnullc   d ", StringUtils.joinStringWithDelimiter(null,"a", "b", null, "", "c", "  ", " d "));
        Assert.assertEquals("a##b##null####c##  ## d ", StringUtils.joinStringWithDelimiter("##", "a", "b", null, "", "c", "  ", " d "));

        Assert.assertEquals("a##b##c## d ", StringUtils.joinNotBlankStrings("##", "a", "b", null, "", "c", "  ", " d "));
        Assert.assertEquals("(a)_(b)_(c )", StringUtils.joinNotBlankStringsWithElementWrapper("_", "(", ")", "a", null, "b", " ", "c "));

    }

    @Test
    public void toWords() {
        String[] words = StringUtils.toWords("  Now,   only Trần understand  this cliché!!! ");
        Assert.assertEquals("Now",words[0]);
        Assert.assertEquals("only",words[1]);
        Assert.assertEquals("Trần",words[2]);
        Assert.assertEquals("understand",words[3]);
        Assert.assertEquals("this",words[4]);
        Assert.assertEquals("cliché",words[5]);
    }

    @Test
    public void test_toString(){
        Object nullObj = null;
        Assert.assertEquals(null,StringUtils.toString(nullObj));

        Object person = PersonFactory.createJasonBourne();
        Assert.assertNotNull(StringUtils.toString(person));

        Assert.assertEquals("1.5f",StringUtils.toString("1.5f"));
        Assert.assertEquals("1.5",StringUtils.toString(1.5f));
        Assert.assertEquals("[a, b]",StringUtils.toString(Arrays.asList("a","b")));
    }

    @Test
    public void maskEmail(){
        Assert.assertEquals(null,StringUtils.maskEmail(null));
        Assert.assertEquals("abc",StringUtils.maskEmail("abc"));
        Assert.assertEquals("     ",StringUtils.maskEmail("     "));//5 chars
        Assert.assertEquals("   **  ",StringUtils.maskEmail("       "));//7 chars
        Assert.assertEquals("abc****************om",StringUtils.maskEmail("abc.def.123@gmail.com"));
    }
}

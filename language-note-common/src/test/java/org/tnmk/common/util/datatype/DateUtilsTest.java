package org.tnmk.common.util.datatype;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.utils.collections.ArrayUtils;
import org.tnmk.common.utils.datatype.DateTimeUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

/**
 * @author khoi.tran on 6/7/17.
 */
public class DateUtilsTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(DateUtilsTest.class);

    private final LocalDate localDate =LocalDate.of(2018, 02, 28);
    private final LocalTime localTime =LocalTime.of(13, 59, 58, 999);
    private final LocalDateTime localDateTime = LocalDateTime.of(localDate,localTime);
    @Test
    public void formatLocalDateTime() {
        Assert.assertEquals("02-18-28 13-58-59", DateTimeUtils.formatLocalDateTime(localDateTime, "MM-yy-dd HH-ss-mm"));
        Assert.assertEquals("02-18-28 01-58-59", DateTimeUtils.formatLocalDateTime(localDateTime, "MM-yy-dd hh-ss-mm"));
    }

    @Test
    public void toInstant() {
        Instant instant = DateTimeUtils.toInstant(localDate);
        Assert.assertEquals("2018-02-28T00:00:00Z", instant.toString());
        Assert.assertEquals("2018-02-28", DateTimeUtils.formatInstant(instant,"yyyy-MM-dd"));
    }
    @Test
    public void toLocalDateTimePatternISO() {
        LocalDateTime startOfDate = LocalDateTime.of(localDate, LocalTime.MIN);
        Assert.assertEquals(startOfDate, DateTimeUtils.toLocalDateTimePatternISO("2018-02-28T00:00:00Z"));
    }
}

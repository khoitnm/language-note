package org.tnmk.common.utils.datatype;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author khoi.tran on 8/17/16.
 */
public final class DateTimeUtils {
    private DateTimeUtils() {
        //Utils
    }

    public static final String PATTERN_FILEPATH = "yyyyMMdd_HHmmss";

    public static Instant toInstant(LocalDate localDate) {
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }

    public static LocalDateTime toLocalDateTimePatternISO(String dateString) {
        LocalDateTime localDateTime;
        if (StringUtils.isNotBlank(dateString)) {
            localDateTime = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(dateString));
        } else {
            localDateTime = null;
        }
        return localDateTime;
    }

    public static String formatLocalDate(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDateTimeForFilePath() {
        return formatLocalDateTime(LocalDateTime.now(), PATTERN_FILEPATH);
    }

    public static String formatInstant(Instant instance, String pattern) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instance, ZoneId.of(ZoneOffset.UTC.getId()));
        return formatLocalDateTime(localDateTime, pattern);
    }
}

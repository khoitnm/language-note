package org.tnmk.common.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.common.utils.http.LogRequestUtils;

import java.time.Instant;

public final class LogUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);

    private LogUtils(){
        //Utils
    }

    public static Instant logRuntime(Instant startTime, String msg) {
        Instant now = Instant.now();
        long runTimeMilli = now.toEpochMilli() - startTime.toEpochMilli();
        double runTimeSeconds = (double) runTimeMilli / 1000;
        //TODO should change to debug
        LOGGER.trace(String.format("\n%s\n\tStart time: %s, End time: %s\n\tRuntime: %s ms ~ %.2f s", msg, startTime, now, runTimeMilli, runTimeSeconds));
        return now;
    }
}

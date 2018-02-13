package org.tnmk.common.infrastructure.data.query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.tnmk.common.util.ArrayUtil;
import org.tnmk.common.util.IOUtil;
import org.tnmk.common.util.RegularExpressions;

/**
 * @author khoi.tran on 3/12/17.
 */
@Component
public class ClassPathQueryLoader {

    //    @Override
    @Cacheable("queries")
    public static String loadQuery(String fileFullPathName, Object... params) {
        String query = IOUtil.loadTextFileInClassPath(fileFullPathName);
        query = query.replaceAll(RegularExpressions.BLOCK_COMMENTS, "");
        String filledQuery;
        if (!ArrayUtil.isEmpty(params)) {
            filledQuery = String.format(query, params);
        } else {
            filledQuery = query;
        }
        return filledQuery;
    }
}

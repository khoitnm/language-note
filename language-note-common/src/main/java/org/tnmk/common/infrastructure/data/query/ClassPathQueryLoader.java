package org.tnmk.common.infrastructure.data.query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.tnmk.common.utils.collections.ArrayUtils;
import org.tnmk.common.utils.io.IOUtils;
import org.tnmk.common.utils.RegularExpressionConstants;

/**
 * @author khoi.tran on 3/12/17.
 */
@Component
public class ClassPathQueryLoader {

    //    @Override
    @Cacheable("queries")
    public static String loadQuery(String fileFullPathName, Object... params) {
        String query = IOUtils.loadTextFileInClassPath(fileFullPathName);
        query = query.replaceAll(RegularExpressionConstants.BLOCK_COMMENTS, "");
        String filledQuery;
        if (!ArrayUtils.isEmpty(params)) {
            filledQuery = String.format(query, params);
        } else {
            filledQuery = query;
        }
        return filledQuery;
    }
}

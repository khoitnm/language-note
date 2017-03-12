package tnmk.common.infrastructure.data.query;

/**
 * @author khoi.tran on 3/12/17.
 */
public interface QueryLoader {
    String loadQuery(String fileFullPathName, Object... params);
}

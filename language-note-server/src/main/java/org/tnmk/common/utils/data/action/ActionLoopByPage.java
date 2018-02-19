package org.tnmk.common.utils.data.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * WARNING: Please careful when using this class. Otherwise, it can cause infinited loop. View more in {@link #executeAllPages(int)}.
 *
 * @author khoi.tran on 12/1/16.
 */
public abstract class ActionLoopByPage<E> {
    public static final Logger LOGGER = LoggerFactory.getLogger(ActionLoopByPage.class);

    /**
     * The name is not mandatory, only for logging.
     */
    private String name;

    public ActionLoopByPage() {
        this.name = null;
    }

    public ActionLoopByPage(String name) {
        this.name = name;
    }

    public List<E> executeSinceLastItem(int totalItems) {
        return executePages(totalItems - 1, 1, null);
    }

    public List<E> executeAllPages(int pageSize) {
        return executeAllPages(pageSize, null);
    }

    /**
     * This method will stop if there's any exception are thrown, or the data in page is empty.
     * <p>
     * If the data in page (result of {@link #executeEachPageData(Pageable)}) is never empty, then this method will loop forever, so please don't let it happen!
     * You have to make sure that there's a point which result of {@link #executeEachPageData(Pageable)} is empty.
     *
     * @param pageSize pageSize of elements.
     * @param sort     sort for elements. Can be null.
     */
    public List<E> executeAllPages(int pageSize, Sort sort) {
        return executePages(0, pageSize, sort);
    }

    public List<E> executePages(int startingPage, int pageSize, Sort sort) {
        int page = startingPage;
        List<E> all = new ArrayList<>();
        boolean isContinue = true;
        while (isContinue) {
            Pageable pageRequest = new PageRequest(page, pageSize, sort);
            LOGGER.debug("Action '{}' [{}]", name, pageRequest);
            List<E> pageData = executeEachPageData(pageRequest);
            all.addAll(pageData);
            isContinue = !pageData.isEmpty();
            page++;
        }
        LOGGER.debug("Action '{}' [finish]", name);
        return all;
    }

    /**
     * This method will be stopped if there's any exception are thrown, or the data in page is empty.
     * <p>
     * If the data in page is never empty, then this method will loop forever, so please don't let it happen!
     * You have to make sure that there's a point which result of this method is empty.
     *
     * @param pageRequest
     * @return
     */
    protected abstract List<E> executeEachPageData(Pageable pageRequest);
}

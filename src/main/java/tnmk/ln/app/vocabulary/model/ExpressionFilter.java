package tnmk.ln.app.vocabulary.model;

import java.util.Set;

/**
 * @author khoi.tran on 1/31/17.
 */
public class ExpressionFilter {
    private boolean selectAllBooks;
    private boolean selectAllLessons;
    private boolean selectAllTopics;
    private Set<String> selectedLessonIds;
    private Set<String> selectedTopicIds;
    private Set<String> selectedBookIds;

    public Set<String> getSelectedLessonIds() {
        return selectedLessonIds;
    }

    public void setSelectedLessonIds(Set<String> selectedLessonIds) {
        this.selectedLessonIds = selectedLessonIds;
    }

    public Set<String> getSelectedTopicIds() {
        return selectedTopicIds;
    }

    public void setSelectedTopicIds(Set<String> selectedTopicIds) {
        this.selectedTopicIds = selectedTopicIds;
    }

    public Set<String> getSelectedBookIds() {
        return selectedBookIds;
    }

    public void setSelectedBookIds(Set<String> selectedBookIds) {
        this.selectedBookIds = selectedBookIds;
    }

    public boolean isSelectAllBooks() {
        return selectAllBooks;
    }

    public void setSelectAllBooks(boolean selectAllBooks) {
        this.selectAllBooks = selectAllBooks;
    }

    public boolean isSelectAllLessons() {
        return selectAllLessons;
    }

    public void setSelectAllLessons(boolean selectAllLessons) {
        this.selectAllLessons = selectAllLessons;
    }

    public boolean isSelectAllTopics() {
        return selectAllTopics;
    }

    public void setSelectAllTopics(boolean selectAllTopics) {
        this.selectAllTopics = selectAllTopics;
    }
}

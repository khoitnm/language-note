package tnmk.ln.app.vocabulary.model;

import java.util.Set;

/**
 * @author khoi.tran on 1/31/17.
 */
public class ExpressionFilter {
    private boolean selectAllBooks;
    private boolean selectAllLessons;
    private boolean selectAllCategorys;
    private Set<String> selectedLessonIds;
    private Set<String> selectedCategoryIds;
    private Set<String> selectedBookIds;

    public Set<String> getSelectedLessonIds() {
        return selectedLessonIds;
    }

    public void setSelectedLessonIds(Set<String> selectedLessonIds) {
        this.selectedLessonIds = selectedLessonIds;
    }

    public Set<String> getSelectedCategoryIds() {
        return selectedCategoryIds;
    }

    public void setSelectedCategoryIds(Set<String> selectedCategoryIds) {
        this.selectedCategoryIds = selectedCategoryIds;
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

    public boolean isSelectAllCategorys() {
        return selectAllCategorys;
    }

    public void setSelectAllCategorys(boolean selectAllCategorys) {
        this.selectAllCategorys = selectAllCategorys;
    }
}

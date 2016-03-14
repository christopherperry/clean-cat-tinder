package com.github.cleancattinder.pagination;

/**
 * Paginates integers. The first call to {@link #getNextPage()}
 * will return the startPage. Subsequent calls to {@link #getNextPage()}
 * will increment by the pageStep.
 */
public class IntPaginator implements Paginator<Integer> {
    private final int pageStep;
    private int currentPage;

    public IntPaginator(int startPage, int pageStep) {
        this.pageStep = pageStep;
        this.currentPage = startPage - pageStep;
    }

    @Override
    public Integer getNextPage() {
        currentPage += pageStep;
        return currentPage;
    }
}

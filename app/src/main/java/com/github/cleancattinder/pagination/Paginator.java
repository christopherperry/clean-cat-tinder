package com.github.cleancattinder.pagination;

/**
 * Interace for paginating items.
 * @param <T> The type of items to paginate
 */
public interface Paginator<T> {
    T getNextPage();
}

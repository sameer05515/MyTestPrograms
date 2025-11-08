package com.example.vocabkhajana.model;

import java.util.Collections;
import java.util.List;

public class WordPage {
    private final List<WordEntry> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;

    public WordPage(List<WordEntry> content, int pageNumber, int pageSize, long totalElements) {
        this.content = List.copyOf(content);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    public List<WordEntry> getContent() {
        return Collections.unmodifiableList(content);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public long getTotalPages() {
        if (pageSize == 0) {
            return 0;
        }
        return (totalElements + pageSize - 1) / pageSize;
    }

    public boolean hasNext() {
        return pageNumber + 1 < getTotalPages();
    }

    public boolean hasPrevious() {
        return pageNumber > 0;
    }
}


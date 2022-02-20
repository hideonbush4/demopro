package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResultPage<T> {
    private List<T> content;
    private int pageSize;
    private int currentPage;
    private boolean first;
    private boolean last;
    private boolean empty;
    private int totalPages;
    private long total;

    public ResultPage(int currentPage, int pageSize, long total, List<T> content) {
        this.total = total;
        this.totalPages = (int)Math.ceil(1.0 * total / pageSize);
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.first = (currentPage == 1);
        this.last = (currentPage == this.totalPages);
        this.empty = content.size() < 1;
        this.content = content;
    }
}

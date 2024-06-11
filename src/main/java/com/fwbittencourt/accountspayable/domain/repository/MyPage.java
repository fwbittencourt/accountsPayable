package com.fwbittencourt.accountspayable.domain.repository;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <Filipe Bittencourt> on 10/06/24
 */
@Getter
@Setter
public class MyPage<T> {
    private List<T> content = new ArrayList<>();
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;
    private long numberOfElements;
    private int pageSize;
    private int number;


    public MyPage() {
    }

    public<R> MyPage<R> map(Function<? super T, ? extends R> mapper) {
        MyPage<R> rMyPage = new MyPage<>();
        rMyPage.content = content.stream()
            .map(mapper)
            .collect(Collectors.toList());
        rMyPage.totalPages = totalPages;
        rMyPage.totalElements = totalElements;
        rMyPage.last = last;
        rMyPage.first = first;
        rMyPage.numberOfElements = numberOfElements;
        rMyPage.pageSize = pageSize;
        rMyPage.number = number;
        return rMyPage;
    }
}

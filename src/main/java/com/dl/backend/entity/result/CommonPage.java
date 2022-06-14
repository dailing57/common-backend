package com.dl.backend.entity.result;

import java.util.ArrayList;
import java.util.List;

public class CommonPage<T> {
    public long total =0;
    public long currentPage;
    public long pageSize;
    public List<T> dataList = new ArrayList<>();
}

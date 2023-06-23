package com.aguri.captionlive.common.util;
import java.util.Comparator;

import com.aguri.captionlive.model.Request;

public class TimeComparator implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return request1.getCreatedTime().compareTo(request2.getCreatedTime());
    }
}


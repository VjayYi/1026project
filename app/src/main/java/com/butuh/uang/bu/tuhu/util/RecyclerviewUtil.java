package com.butuh.uang.bu.tuhu.util;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerviewUtil {
    public static int getVisibleItemCount(RecyclerView rv) {
        final int firstVisiblePos = getFirstVisiblePosition(rv);
        final int lastVisiblePos = getLastVisiblePosition(rv);
        return Math.max(0, lastVisiblePos - firstVisiblePos);
    }

    public static int getFirstVisiblePosition(RecyclerView rv) {
        if (rv != null) {
            final RecyclerView.LayoutManager layoutManager = rv
                    .getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager)
                        .findFirstVisibleItemPosition();
            }
        }
        return 0;
    }

    public static int getLastVisiblePosition(RecyclerView rv) {
        if (rv != null) {
            final RecyclerView.LayoutManager layoutManager = rv
                    .getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
            }
        }
        return 0;
    }
}

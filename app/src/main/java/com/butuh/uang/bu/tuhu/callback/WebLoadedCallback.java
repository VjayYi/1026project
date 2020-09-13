package com.butuh.uang.bu.tuhu.callback;

/**
 * @author leiyux
 * Create by @author at  2018/12/11  22:15
 */
public interface WebLoadedCallback {
    void onLoad(String title);

    void onProgress();

    void onFailed(String s);
}

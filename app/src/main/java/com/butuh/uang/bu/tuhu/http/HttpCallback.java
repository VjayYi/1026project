package com.butuh.uang.bu.tuhu.http;

public interface HttpCallback<T> {

    void success(T result, String message);

    void failure(String code, Throwable throwable);

}
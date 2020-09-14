package com.butuh.uang.bu.tuhu.http;

import com.butuh.uang.bu.tuhu.bean.PageTableBean;
import com.butuh.uang.bu.tuhu.bean.PrivacyBean;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.result.BaseResult;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Interface {
    // 文件上传
//    @Multipart
//    @POST(Api.UPLOAD_FILE)
//    Observable<BaseResult<FileBean>> uploadFile(@Part List<MultipartBody.Part> partList);
//

    @POST("subscriberOperation")
    Observable<BaseResult> login(@Body RequestBody body);

    @POST("memperolehProduk")
    Observable<BaseResult<PageTableBean<ProductBean>>> getProductList(@Body RequestBody body);

    @POST("asAfavorite")
    Observable<BaseResult> collection(@Body RequestBody body);

    @POST("dapatkanDetailproduk")
    Observable<BaseResult<List<ProductBean>>> productDetail(@Body RequestBody body);


    @POST("tickling")
    Observable<BaseResult> feedback(@Body RequestBody body);

    @POST("accessCatalog")
    Observable<BaseResult<PageTableBean<PrivacyBean>>> accessCatalog(@Body RequestBody body);

    @POST("happenlog")
    Observable<BaseResult> happenlog(@Body RequestBody body);
}
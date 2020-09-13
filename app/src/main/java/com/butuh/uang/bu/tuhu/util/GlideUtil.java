package com.butuh.uang.bu.tuhu.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.butuh.uang.bu.tuhu.R;

public class GlideUtil {

    public static void loadImage(Context context, String path, ImageView imageView) {
        if (TextUtils.isEmpty(path)){
            //Glide.with(context).load(R.mipmap.img_def_avatar).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView);
            return;
        }
        if (path.contains(".gif")){
            Glide.with(context).asGif().load(path).into(imageView);
        }else{
            Glide.with(context).load(path).into(imageView);
        }
    }

    public static void loadImageWithOriginSize(Context context, String path, ImageView imageView) {
        RequestOptions options=new RequestOptions();
        options.placeholder(R.mipmap.img_def_loading)
                .error(R.mipmap.img_def_loading)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(path).apply(options).into(imageView);
    }

}

package com.butuh.uang.bu.tuhu.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.util.CommonUtil;
import com.butuh.uang.bu.tuhu.util.DensityUtil;

import androidx.annotation.DrawableRes;

public class MyToast {
    //根据xml布局中的定义，在MyToast中定义这些组件
    public Toast myToast;
    public View myToastView;
    public TextView myToastMessage;

    //构造函数简单地初始化这些组件
    public MyToast(Context context) {
        myToast = new Toast(context);
        myToastView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        myToastMessage = (TextView) myToastView.findViewById(R.id.toast_message);
        ViewGroup.LayoutParams params=myToastMessage.getLayoutParams();
        params.width= DensityUtil.getScreenWidth()-DensityUtil.dp2px(10f);
        params.height=DensityUtil.dp2px(60);
        myToastMessage.setLayoutParams(params);
    }

    public void show() {
        this.myToast.show();
    }
    //仿照安卓通知的模式，定义一个Builder，个人觉得方便好用,方便重用
    //这里需要试用到安卓静态内部类

    //Builder类开始
    public static class Builder {
        //一下分别是toast的一些属性
        private String message;
        private int imageID;
        private int gravity = Gravity.CENTER;
        private boolean isShowIcon;
        private int duration = Toast.LENGTH_SHORT;
        //以下两个分别是构造toast的时候要用到的资源
        private Context context;
        private MyToast toast;
        //构造函数，主要功能是获取上下文
        public Builder(Context context) {
            this.context = context;
        }
        //分别是设置资源值的方法
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public MyToast build() {
            if (toast == null) {
                toast = new MyToast(context);
            }
            toast.myToast.setView(toast.myToastView);
            toast.myToastMessage.setText(message.isEmpty() ? "null" : message);
            toast.myToast.setDuration(duration);
            toast.myToast.setGravity(gravity, 0, 0);
            return toast;
        }

    }
    //Builder类结束
}

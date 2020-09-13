package com.butuh.uang.bu.tuhu.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Stack;

/**
 * @author chenzd
 * Activity管理类
 */
public class ProjectActivityManager {

    private Stack<Activity> activityStack;
    private volatile static ProjectActivityManager instance;

    private ProjectActivityManager() {
        activityStack = new Stack<>();
    }

    public static ProjectActivityManager getInstance() {
        if (instance == null) {
            synchronized (ProjectActivityManager.class) {
                if (instance == null) {
                    instance = new ProjectActivityManager();
                }
            }
        }
        return instance;
    }

    public int getSize() {
        return activityStack != null ? activityStack.size() : 0;
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            if (activityStack == null) {
                activityStack = new Stack<>();
            }
            activityStack.add(activity);
        }
    }

    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            if (activityStack == null) {
                activityStack = new Stack<>();
            }
            activityStack.remove(activity);
        }
    }

    private void finishAllActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    if (!activityStack.get(i).isFinishing()) {
                        activityStack.get(i).finish();
                    }
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 对于传入的View，释放其所有资源
     *
     * @param view View
     */
    public void unbindReferences(View view) {
        try {
            if (view != null) {
                view.destroyDrawingCache();
                unbindViewReferences(view);
                if (view instanceof ViewGroup) {
                    unbindViewGroupReferences((ViewGroup) view);
                }
            }
        } catch (Throwable e) {
            // whatever exception is thrown just ignore it because a crash is
            // always worse than this method not doing what it's supposed to do
        }
    }

    private void unbindViewGroupReferences(ViewGroup viewGroup) {
        int nrOfChildren = viewGroup.getChildCount();
        for (int i = 0; i < nrOfChildren; i++) {
            View view = viewGroup.getChildAt(i);
            unbindViewReferences(view);
            if (view instanceof ViewGroup)
                unbindViewGroupReferences((ViewGroup) view);
        }
        try {
            viewGroup.removeAllViews();
        } catch (Throwable mayHappen) {
            // AdapterViews, ListViews and potentially other ViewGroups don't
            // support the removeAllViews operation
        }
    }

    @SuppressWarnings("deprecation")
    private void unbindViewReferences(View view) {
        // set all listeners to null (not every view and not every API level
        // supports the methods)
        try {
            view.setOnClickListener(null);
            view.setOnCreateContextMenuListener(null);
            view.setOnFocusChangeListener(null);
            view.setOnKeyListener(null);
            view.setOnLongClickListener(null);
            view.setOnClickListener(null);
        } catch (Throwable mayHappen) {
            // do something
        }

        // set background to null
        Drawable d = view.getBackground();
        if (d != null) {
            d.setCallback(null);
        }

        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            d = imageView.getDrawable();
            if (d != null) {
                d.setCallback(null);
            }
            imageView.setImageDrawable(null);
            imageView.setBackgroundDrawable(null);
        }

        // destroy WebView
        if (view instanceof WebView) {
            WebView webview = (WebView) view;
            webview.stopLoading();
            webview.clearFormData();
            webview.clearDisappearingChildren();
            webview.setWebChromeClient(null);
            webview.setWebViewClient(null);
            webview.destroyDrawingCache();
            webview.destroy();
            webview = null;
        }

        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            try {
                listView.removeAllViewsInLayout();
            } catch (Throwable mayHappen) {
                // do something
            }
            ((ListView) view).destroyDrawingCache();
        }
    }

    @SuppressWarnings("deprecation")
    public void exit() {
        try {
            finishAllActivity();
            Activity activity = currentActivity();
            if (activity != null) {
                ActivityManager activityMgr = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
                if (activityMgr != null) {
                    activityMgr.restartPackage(activity.getPackageName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
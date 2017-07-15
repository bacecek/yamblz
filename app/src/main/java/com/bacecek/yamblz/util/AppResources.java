package com.bacecek.yamblz.util;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class AppResources {

    private Context context;

    public AppResources(Context context) {
        this.context = context.getApplicationContext();
    }

    public String getString(@StringRes int res) {
        return context.getString(res);
    }

    public String getString(@StringRes int res, Object... formatArgs) {
        return context.getString(res, formatArgs);
    }

}

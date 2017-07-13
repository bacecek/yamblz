package com.bacecek.yamblz.util;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * Created by Denis Buzmakov on 13.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class AppResources {

    private Context mContext;

    public AppResources(Context context) {
        mContext = context.getApplicationContext();
    }

    public String getString(@StringRes int res) {
        return mContext.getString(res);
    }

}

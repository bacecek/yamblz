package com.bacecek.yamblz.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Denis Buzmakov on 18.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class SystemServiceProvider {

    private Context context;

    public SystemServiceProvider(Context context) {
        this.context = context;
    }

    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public ClipboardManager getClipboardManager() {
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }
}

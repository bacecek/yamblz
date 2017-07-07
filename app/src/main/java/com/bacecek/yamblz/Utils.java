package com.bacecek.yamblz;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Denis Buzmakov on 07.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class Utils {

    public static void copyToClipboard(Context context, String text) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("text", text);
        manager.setPrimaryClip(data);
    }

}

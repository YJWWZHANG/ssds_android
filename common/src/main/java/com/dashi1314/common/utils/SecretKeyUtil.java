package com.dashi1314.common.utils;

import android.content.Context;

public class SecretKeyUtil {

    static {
        System.loadLibrary("SecretKey");
    }

    public static native String getSecretKey(Context context);
}

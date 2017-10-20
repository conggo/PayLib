package com.alvis.paylib.utils;

import android.app.Activity;

/**
 * Description:
 *
 * @author aicong
 * @since 2017/10/20
 */
public interface IPayHelper {

    void pay(Activity activity,String orderInfo, PayResultListener listener);

}

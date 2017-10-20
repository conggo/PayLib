package com.alvis.paylib;

import android.app.Activity;
import android.os.Looper;

import com.alvis.paylib.alipay.AliPayHelper;
import com.alvis.paylib.utils.PayResultListener;
import com.alvis.paylib.utils.PayType;
import com.alvis.paylib.wexinpay.WexinPayHelper;

/**
 * Description:
 *
 * @author aicong
 * @since 2017/10/20
 */
public class PayClient {

    private static final String TAG = PayClient.class.getSimpleName();

    private static AliPayHelper mAliPayHelper;
    private static WexinPayHelper mWexinPayHelper;


    private PayClient (){}

    private static class Holder{
        public static PayClient INSTANCE = new PayClient();
    }

    public static PayClient getInstance(){
        return Holder.INSTANCE;
    }


    public static AliPayHelper getAlipayHelper() {
        if (null == mAliPayHelper) {
            mAliPayHelper = new AliPayHelper();
        }
        return mAliPayHelper;
    }

    public static WexinPayHelper getWechatpayHelper() {
        if (null == mWexinPayHelper) {
            mWexinPayHelper = new WexinPayHelper();
        }
        return mWexinPayHelper;
    }


    public void pay(PayType payType, Activity activity, String orderInfo, PayResultListener listener){

        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalArgumentException(Thread.currentThread().getName() + "'. " +
                    "pay method must be called on the UI thread. ");
        }

        switch (payType){
            case ALIPAY:
                getAlipayHelper().pay(activity,orderInfo,listener);
                break;
            case WEXINPAY:
                getWechatpayHelper().pay(activity,orderInfo,listener);
                break;
            default:

                break;
        }

    }


}

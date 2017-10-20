package com.alvis.paylib.wexinpay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alvis.paylib.utils.IPayHelper;
import com.alvis.paylib.utils.PayResultListener;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Description:
 *
 * @author aicong
 * @since 2017/10/20
 */
public class WexinPayHelper implements IPayHelper {

    private static final String TAG = WexinPayHelper.class.getName();

    private static PayResultListener mListener;

    private IWXAPI mApi;

    @Override
    public void pay(Activity activity, String orderInfo, PayResultListener listener) {
        mListener = listener;
        if (null == mApi) {
            mApi = WXAPIFactory.createWXAPI(activity, null);
        }
        WexinPay pay = new Gson().fromJson(orderInfo, WexinPay.class);
        PayReq payReq = new PayReq();
        payReq.appId = pay.appid;
        payReq.partnerId = pay.partnerid;
        payReq.prepayId = pay.prepayid;
        payReq.nonceStr = pay.noncestr;
        payReq.timeStamp = pay.timestamp;
        payReq.packageValue = pay.packages;
        payReq.sign = pay.sign;
        payReq.extData = pay.extData;

        mApi.sendReq(payReq);
    }



    public static void handleonReq(BaseReq req) {
        Log.d(TAG, " ====== handleonReq =====");
    }

    public static void handleIntent(Intent intent, Context context) {

    }

    /**
     * 接收 支付回调
     */
    public static void handleOnResp(BaseResp resp) {
        Log.d(TAG, " ====  handleOnResp ,resp:" + resp.toString() + " === ");
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX && resp.errCode == 0) {
            if (null != mListener) {
                mListener.success();
            }
        } else {
            if (null != mListener) {
                mListener.failed(String.valueOf(resp.errCode),resp.errStr);
            }
        }

    }

}

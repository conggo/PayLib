package com.alvis.paylib.wexinpay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.alvis.paylib.utils.Constant;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Description:
 *
 * @author aicong
 * @since 2017/10/20
 */
public class AppRegReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context,null);
        // 将该app注册到微信
        iwxapi.registerApp(Constant.WEXIN_APP_ID);
    }
}

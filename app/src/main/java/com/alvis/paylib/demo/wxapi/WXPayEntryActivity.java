package com.alvis.paylib.demo.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alvis.paylib.utils.Constant;
import com.alvis.paylib.wexinpay.WexinPayHelper;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.WEXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        WexinPayHelper.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.d(TAG, " === wxPay onReq " + req.toString() + " === ");
        WexinPayHelper.handleonReq(req);
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, " ==== wxPay onResp ===" + resp.errStr + ";code=" + String.valueOf(resp.errCode));
        WexinPayHelper.handleOnResp(resp);
        this.finish();
    }
}
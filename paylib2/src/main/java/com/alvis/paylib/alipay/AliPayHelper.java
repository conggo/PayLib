package com.alvis.paylib.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.alvis.paylib.utils.IPayHelper;
import com.alvis.paylib.utils.PayResultListener;

import java.util.Map;

/**
 * Description:
 *
 * @author aicong
 * @since 2017/10/20
 */
public class AliPayHelper implements IPayHelper {

    private static final String TAG = AliPayHelper.class.getSimpleName();

    private Activity activity;
    private String payInfo;

    private static final int PAY_WHAT_CODE = 1;

    private Handler mHandler;


    @Override
    public void pay(final Activity activity, String orderInfo, PayResultListener listener) {
        this.activity = activity;
        this.payInfo = orderInfo;
        mHandler = new PayHanlder(listener);
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(activity);
                Map<String, String> result = payTask.payV2(payInfo, true);

                Log.i(TAG, "pay result :" + result.toString());

                Message msg = Message.obtain();
                msg.what = PAY_WHAT_CODE;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }).start();
    }



    public static class PayHanlder extends Handler{

        private PayResultListener mListener;

        public PayHanlder(PayResultListener listener){
            mListener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PAY_WHAT_CODE:
                    AlipayResponse response = new AlipayResponse((Map<String,String>)msg.obj);
                    String resultStatus = response.getResultStatus();
                    String resultMsg = response.getResult();

                    if(TextUtils.equals(resultStatus,"9000")){
                        mListener.success();
                    }else if(TextUtils.isEmpty(resultMsg)){
                        mListener.failed(resultStatus,"支付失败");
                    }else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        //支付结果确认中
                        if (TextUtils.equals(resultStatus, "8000")) {
                            mListener.failed(resultStatus, resultMsg);
                            //支付失败
                        } else {
                            mListener.failed(resultStatus, resultMsg);
                        }
                    }
                    break;
            }
        }
    }


}

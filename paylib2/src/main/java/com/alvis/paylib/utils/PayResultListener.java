package com.alvis.paylib.utils;

/**
 * Description:
 *
 * @author aicong
 * @since 2017/10/20
 */
public interface PayResultListener {

    void success();

    void failed(String code,String msg);

}

package com.yijianguanzhu.douyin.qrcode.login.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yijianguanzhu.douyin.qrcode.login.exception.ResponseParserException;
import okhttp3.Response;

import java.io.IOException;

/**
 * 响应结果工具类
 * 
 * @author yijianguanzhu 2022年10月06日
 */
public class ResponseUtil {

	public static <T> T bean( Response response, Class<T> clazz ) {
		if ( response != null && response.isSuccessful() ) {
			try {
				return JacksonUtil.bean( response.body().string(), clazz );
			}
			catch ( IOException e ) {
				throw new ResponseParserException( "响应体解析异常", e );
			}
		}
		return null;
	}

	public static <T> T bean( Response response, TypeReference<T> valueTypeRef ) {
		if ( response != null && response.isSuccessful() ) {
			try {
				return JacksonUtil.bean( response.body().string(), valueTypeRef );
			}
			catch ( IOException e ) {
				throw new ResponseParserException( "响应体解析异常", e );
			}
		}
		return null;
	}
}

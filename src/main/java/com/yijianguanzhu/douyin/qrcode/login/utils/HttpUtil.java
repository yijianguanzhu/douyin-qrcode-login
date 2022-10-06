package com.yijianguanzhu.douyin.qrcode.login.utils;

import com.yijianguanzhu.douyin.qrcode.login.exception.HttpRequestException;
import com.yijianguanzhu.douyin.qrcode.login.interceptor.BasicCommonHeadersInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.time.Duration;
import java.util.Map;

/**
 * http 工具类
 * 
 * @author yijianguanzhu 2022年10月04日
 */
@Slf4j
public class HttpUtil {

	public static MediaType JSON = MediaType.parse( "application/json; charset=utf-8" );

	public final static OkHttpClient CLIENT = new OkHttpClient.Builder()
			.connectTimeout( Duration.ofSeconds( 5 ) )
			.readTimeout( Duration.ofSeconds( 3 ) )
			.addInterceptor( new BasicCommonHeadersInterceptor() )
			.build();

	public static Response get( String url ) {
		return get( url, null );
	}

	public static Response get( String url, Map<String, String> headers ) {
		Request.Builder builder = new Request.Builder();
		if ( headers != null && !headers.isEmpty() ) {
			headers.forEach( builder::addHeader );
		}
		Request request = builder.url( url ).get().build();
		return response( request );
	}

	public static <R> Response post( String url, R body ) {
		return post( url, null, body );
	}

	public static <R> Response post( String url, Map<String, String> headers, R body ) {
		String json = JacksonUtil.json( body );
		RequestBody requestBody = RequestBody.create( JSON, json );
		Request.Builder builder = new Request.Builder();
		if ( headers != null && !headers.isEmpty() ) {
			headers.forEach( builder::addHeader );
		}
		Request request = builder.url( url ).post( requestBody ).build();
		return response( request );
	}

	protected static Response response( Request request ) {
		try {
			Response response = CLIENT.newCall( request ).execute();
			if ( response.isSuccessful() ) {
				return response;
			}
		}
		catch ( Exception e ) {
			throw new HttpRequestException( "请求错误", e );
		}
		// should't happen
		throw new HttpRequestException( "请求失败" );
	}
}

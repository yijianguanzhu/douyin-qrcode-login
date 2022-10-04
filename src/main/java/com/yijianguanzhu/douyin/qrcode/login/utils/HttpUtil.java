package com.yijianguanzhu.douyin.qrcode.login.utils;

import com.fasterxml.jackson.core.type.TypeReference;
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

	public static <T> T get( String url, Class<T> clazz ) {
		return get( url, null, clazz );
	}

	public static <T> T get( String url, Map<String, String> headers, Class<T> clazz ) {
		String response = get( url, headers );
		return JacksonUtil.bean( response, clazz );
	}

	public static <T> T get( String url, TypeReference<T> valueTypeRef ) {
		return get( url, null, valueTypeRef );
	}

	public static <T> T get( String url, Map<String, String> headers, TypeReference<T> valueTypeRef ) {
		String response = get( url, headers );
		return JacksonUtil.bean( response, valueTypeRef );
	}

	protected static String get( String url, Map<String, String> headers ) {
		Request.Builder builder = new Request.Builder();
		if ( headers != null && !headers.isEmpty() ) {
			headers.forEach( builder::addHeader );
		}
		Request request = builder.url( url ).get().build();
		return body( request );
	}

	public static <T, R> T post( String url, R body, Class<T> clazz ) {
		return post( url, null, body, clazz );
	}

	public static <T, R> T post( String url, Map<String, String> headers, R body, Class<T> clazz ) {
		String response = post( url, headers, body );
		return JacksonUtil.bean( response, clazz );
	}

	public static <T, R> T post( String url, R body, TypeReference<T> valueTypeRef ) {
		return post( url, null, body, valueTypeRef );
	}

	public static <T, R> T post( String url, Map<String, String> headers, R body, TypeReference<T> valueTypeRef ) {
		String response = post( url, headers, body );
		return JacksonUtil.bean( response, valueTypeRef );
	}

	protected static <R> String post( String url, Map<String, String> headers, R body ) {
		String json = JacksonUtil.json( body );
		RequestBody requestBody = RequestBody.create( JSON, json );
		Request.Builder builder = new Request.Builder();
		if ( headers != null && !headers.isEmpty() ) {
			headers.forEach( builder::addHeader );
		}
		Request request = builder.url( url ).post( requestBody ).build();
		return body( request );
	}

	protected static String body( Request request ) {
		try ( Response response = CLIENT.newCall( request ).execute() ) {
			if ( response.isSuccessful() ) {
				return response.body().string();
			}
		}
		catch ( Exception e ) {
			throw new HttpRequestException( "请求错误", e );
		}
		// should't happen
		throw new HttpRequestException( "请求失败" );
	}
}

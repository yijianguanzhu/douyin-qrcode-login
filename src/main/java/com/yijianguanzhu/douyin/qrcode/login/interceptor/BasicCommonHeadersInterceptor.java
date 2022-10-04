package com.yijianguanzhu.douyin.qrcode.login.interceptor;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * http request 添加统一请求头
 * 
 * @author yijianguanzhu 2022年10月04日
 */
public class BasicCommonHeadersInterceptor implements Interceptor {

	// 自定义header
	static Map<String, String> HEADERS = new LinkedHashMap<String, String>() {
		{
			this.put( "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36" );
			this.put( "Origin", "https://www.douyin.com" );
			this.put( "Referer", "https://www.douyin.com/" );
		}
	};

	/**
	 * 当不存在自定义header时才添加，存在则跳过
	 */
	@Override
	public Response intercept( Chain chain ) throws IOException {
		Headers headers = chain.request().headers();
		Request.Builder builder = chain.request().newBuilder();
		for ( String header : HEADERS.keySet() ) {
			String val = headers.get( header );
			if ( Objects.isNull( val ) ) {
				builder.addHeader( header, HEADERS.get( header ) );
			}
		}
		return chain.proceed( builder.build() );
	}
}

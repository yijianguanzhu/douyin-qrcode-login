package com.yijianguanzhu.douyin.qrcode.login.utils;

import okhttp3.Response;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * cookie 工具类
 * 
 * @author yijianguanzhu 2022年10月04日
 */
public class CookieUtil {

	public final static String EQUAL_SIGN = "=";
	public final static String SEMICOLON = ";";
	public final static String SET_COOKIE = "Set-Cookie";

	// Response to Map
	public static Map<String, String> cookies( Response response ) {
		final Map<String, String> cookies = new LinkedHashMap<>();
		List<String> headers = response.headers( SET_COOKIE );
		headers.stream().flatMap( header -> Stream.of( header.split( SEMICOLON ) ) )
				.filter( header -> header.contains( EQUAL_SIGN ) )
				.map( header -> header.split( EQUAL_SIGN ) )
				.filter( header -> header.length == 2 )
				.forEach( header -> cookies.put( header[0], header[1] ) );
		return cookies;
	}

	// Map to String
	public static String cookies( final Map<String, String> cookies ) {
		final StringBuilder builder = new StringBuilder();
		cookies.forEach( ( key, value ) -> builder.append( key ).append( EQUAL_SIGN ).append( value ).append( SEMICOLON ) );
		return builder.toString();
	}

	// String to Map
	public static Map<String, String> cookies( final String cookie ) {
		final Map<String, String> cookies = new LinkedHashMap<>();
		String[] keyValues = cookie.split( SEMICOLON );
		Stream.of( keyValues ).filter( keyValue -> keyValue.contains( EQUAL_SIGN ) )
				.map( keyValue -> keyValue.split( EQUAL_SIGN ) )
				.filter( result -> result.length == 2 )
				.forEach( result -> cookies.put( result[0], result[1] ) );
		return cookies;
	}

	// fill new cookie if putIfAbsent
	public static void fillCookies( final Map<String, String> oldCookies, final Map<String, String> newCookies ) {
		oldCookies.forEach( newCookies::putIfAbsent );
	}
}

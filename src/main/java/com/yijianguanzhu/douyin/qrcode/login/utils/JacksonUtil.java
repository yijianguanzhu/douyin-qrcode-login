package com.yijianguanzhu.douyin.qrcode.login.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * 序列化反序列化工具类
 * 
 * @author yijianguanzhu 2022年10月04日
 */
public class JacksonUtil {

	private final static ObjectMapper JACKSON_MAPPER = JsonMapper.builder()
			.serializationInclusion( JsonInclude.Include.NON_NULL )
			.configure( DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false )
			.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false )
			.configure( JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS, true )
			.configure( JsonReadFeature.ALLOW_SINGLE_QUOTES, true )
			.build();

	@SneakyThrows(IOException.class)
	public static <T> T bean( String jsonString, Class<T> clazz ) {
		return JACKSON_MAPPER.readValue( jsonString, clazz );
	}

	@SneakyThrows(IOException.class)
	public static <T> T bean( String jsonString, TypeReference<T> valueTypeRef ) {
		return JACKSON_MAPPER.readValue( jsonString, valueTypeRef );
	}

	@SneakyThrows(IOException.class)
	public static <T> String json( T obj ) {
		return JACKSON_MAPPER.writeValueAsString( obj );
	}
}

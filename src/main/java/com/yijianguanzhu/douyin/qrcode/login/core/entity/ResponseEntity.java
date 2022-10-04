package com.yijianguanzhu.douyin.qrcode.login.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@Getter
@Setter
@ToString
public class ResponseEntity<T> {

	private String description;

	/**
	 * 错误码；0代表ok
	 */
	@JsonProperty("error_code")
	private int errorCode;

	private String message;

	/**
	 * 结果集
	 */
	private T data;
}

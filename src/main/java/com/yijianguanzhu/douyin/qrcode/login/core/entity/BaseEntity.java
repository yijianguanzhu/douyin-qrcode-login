package com.yijianguanzhu.douyin.qrcode.login.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yijianguanzhu 2022年10月05日
 */
@Getter
@Setter
@ToString
public class BaseEntity {

	// 响应描述（请求错误时）
	private String description;

	// 错误描述文档链接
	@JsonProperty("desc_url")
	private String descUrl;

	private String captcha;

	/**
	 * 错误码；0代表ok
	 */
	@JsonProperty("error_code")
	private int errorCode;
}

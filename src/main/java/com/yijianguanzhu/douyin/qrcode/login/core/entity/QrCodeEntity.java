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
public class QrCodeEntity {

	@JsonProperty("app_name")
	private String appName;

	@JsonProperty("qrcode")
	private String qrCode;

	@JsonProperty("qrcode_index_url")
	private String url;

	private String token;

	@JsonProperty("web_name")
	private String webName;
}

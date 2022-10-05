package com.yijianguanzhu.douyin.qrcode.login.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yijianguanzhu.douyin.qrcode.login.enums.ScanQRCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@Getter
@Setter
@ToString
public class SuccessScanQRCodeEntity extends BaseEntity {

	// 扫码状态
	private ScanQRCodeEnum status;

	private Map<String, String> cookies;

	// 扫码成功后，下一跳url
	@JsonProperty("redirect_url")
	private String redirectUrl;

	// ======================================== 内置参数 ============================

	private String url;

	private String ticket;
}

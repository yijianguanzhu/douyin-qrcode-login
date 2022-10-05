package com.yijianguanzhu.douyin.qrcode.login.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@AllArgsConstructor
public enum ScanQRCodeEnum implements BaseCodeEnum {

	WAITING(1, "客户端未扫码"),

	PENDING(2, "客户端已扫码，等待确认"),

	SUCCEEDED(3, "客户端确认登录"),

	CANCELED(4, "客户端取消登录"),

	EXPIRED(5, "二维码过期，请重新获取");

	private int code;

	@Getter
	private String desc;

	@Override
	public int getCode() {
		return this.code;
	}

	@JsonCreator
	public static ScanQRCodeEnum from( int code ) {
		return BaseCodeEnum.codeOf( ScanQRCodeEnum.class, code );
	}
}

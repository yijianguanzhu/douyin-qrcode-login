package com.yijianguanzhu.douyin.qrcode.login.core.entity;

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
public class SuccessScanQRCodeEntity extends ResponseEntity {

	private String url;

	private ScanQRCodeEnum status = ScanQRCodeEnum.SUCCEEDED;

	private Map<String, String> cookies;
}

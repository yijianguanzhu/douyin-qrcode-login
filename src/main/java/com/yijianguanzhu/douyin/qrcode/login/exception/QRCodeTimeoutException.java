package com.yijianguanzhu.douyin.qrcode.login.exception;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@SuppressWarnings("serial")
public class QRCodeTimeoutException extends BaseException {

	public QRCodeTimeoutException( Integer code, String errMsg ) {
		super( code, errMsg );
	}

	public QRCodeTimeoutException( String errMsg ) {
		super( errMsg );
	}

	public QRCodeTimeoutException( Throwable cause ) {
		super( cause );
	}

	public QRCodeTimeoutException( String errMsg, Throwable cause ) {
		super( errMsg, cause );
	}
}

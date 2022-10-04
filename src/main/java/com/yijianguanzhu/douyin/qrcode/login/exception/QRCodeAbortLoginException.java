package com.yijianguanzhu.douyin.qrcode.login.exception;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@SuppressWarnings("serial")
public class QRCodeAbortLoginException extends BaseException {

	public QRCodeAbortLoginException( Integer code, String errMsg ) {
		super( code, errMsg );
	}

	public QRCodeAbortLoginException( String errMsg ) {
		super( errMsg );
	}

	public QRCodeAbortLoginException( Throwable cause ) {
		super( cause );
	}

	public QRCodeAbortLoginException( String errMsg, Throwable cause ) {
		super( errMsg, cause );
	}
}

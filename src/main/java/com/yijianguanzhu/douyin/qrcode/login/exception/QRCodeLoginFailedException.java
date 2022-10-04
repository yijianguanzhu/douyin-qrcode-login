package com.yijianguanzhu.douyin.qrcode.login.exception;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@SuppressWarnings("serial")
public class QRCodeLoginFailedException extends BaseException {

	public QRCodeLoginFailedException( Integer code, String errMsg ) {
		super( code, errMsg );
	}

	public QRCodeLoginFailedException( String errMsg ) {
		super( errMsg );
	}

	public QRCodeLoginFailedException( Throwable cause ) {
		super( cause );
	}

	public QRCodeLoginFailedException( String errMsg, Throwable cause ) {
		super( errMsg, cause );
	}
}

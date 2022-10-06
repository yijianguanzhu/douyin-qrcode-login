package com.yijianguanzhu.douyin.qrcode.login.exception;

/**
 * @author yijianguanzhu 2022年10月06日
 */
@SuppressWarnings("serial")
public class ResponseParserException extends BaseException {

	public ResponseParserException( Integer code, String errMsg ) {
		super( code, errMsg );
	}

	public ResponseParserException( String errMsg ) {
		super( errMsg );
	}

	public ResponseParserException( Throwable cause ) {
		super( cause );
	}

	public ResponseParserException( String errMsg, Throwable cause ) {
		super( errMsg, cause );
	}
}

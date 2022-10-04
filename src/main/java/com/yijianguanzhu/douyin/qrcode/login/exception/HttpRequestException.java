package com.yijianguanzhu.douyin.qrcode.login.exception;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@SuppressWarnings("serial")
public class HttpRequestException extends BaseException {

	public HttpRequestException( Integer code, String errMsg ) {
		super( code, errMsg );
	}

	public HttpRequestException( String errMsg ) {
		super( errMsg );
	}

	public HttpRequestException( Throwable cause ) {
		super( cause );
	}

	public HttpRequestException( String errMsg, Throwable cause ) {
		super( errMsg, cause );
	}
}

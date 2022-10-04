package com.yijianguanzhu.douyin.qrcode.login.exception;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@SuppressWarnings("serial")
public class EnumNotPresentException extends BaseException {

	public EnumNotPresentException( Integer code, String errMsg ) {
		super( code, errMsg );
	}

	public EnumNotPresentException( String errMsg ) {
		super( errMsg );
	}

	public EnumNotPresentException( Throwable cause ) {
		super( cause );
	}

	public EnumNotPresentException( String errMsg, Throwable cause ) {
		super( errMsg, cause );
	}
}

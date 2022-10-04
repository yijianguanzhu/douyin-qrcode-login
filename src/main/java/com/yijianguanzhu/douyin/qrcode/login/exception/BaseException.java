package com.yijianguanzhu.douyin.qrcode.login.exception;

import lombok.Getter;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@SuppressWarnings("serial")
@Getter
public class BaseException extends RuntimeException {

	private final Integer code;

	private final String errMsg;

	public BaseException( final Integer code, final String errMsg ) {
		super( errMsg );
		this.code = code;
		this.errMsg = errMsg;
	}

	public BaseException( final String errMsg ) {
		super( errMsg );
		this.code = null;
		this.errMsg = errMsg;
	}

	public BaseException( final Throwable cause ) {
		super( cause );
		this.errMsg = null;
		this.code = null;
	}

	public BaseException( final String errMsg, final Throwable cause ) {
		super( cause );
		this.errMsg = errMsg;
		this.code = null;
	}
}

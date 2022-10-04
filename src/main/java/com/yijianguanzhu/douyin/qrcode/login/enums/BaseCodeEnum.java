package com.yijianguanzhu.douyin.qrcode.login.enums;

import com.yijianguanzhu.douyin.qrcode.login.exception.EnumNotPresentException;

/**
 * @author yijianguanzhu 2022年10月04日
 */
public interface BaseCodeEnum {

	/**
	 * 获取code值
	 */
	int getCode();

	/**
	 * 将数值转换为枚举类
	 */
	static <E extends Enum<?> & BaseCodeEnum> E codeOf( Class<E> clazz, int code ) {
		final E[] enumConstants = clazz.getEnumConstants();
		for ( E e : enumConstants ) {
			if ( e.getCode() == code ) {
				return e;
			}
		}
		final String msg = String.format( "\"%s\"不在可选范围内", code );
		throw new EnumNotPresentException( msg );
	}
}

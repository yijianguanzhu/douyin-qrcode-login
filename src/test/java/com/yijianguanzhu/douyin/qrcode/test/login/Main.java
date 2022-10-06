package com.yijianguanzhu.douyin.qrcode.test.login;

import com.yijianguanzhu.douyin.qrcode.login.core.LoginHelper;
import com.yijianguanzhu.douyin.qrcode.login.core.entity.QrCodeEntity;
import com.yijianguanzhu.douyin.qrcode.login.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 测试
 * 
 * @author yijianguanzhu 2022年10月05日
 */
@Slf4j
public class Main {

	public static void main( String[] args ) {
		QrCodeEntity qrCode = LoginHelper.getQrCode();
		log.info( "二维码：{}", qrCode.getQrCode() );
		Map<String, String> login = LoginHelper.login( qrCode.getToken() );
		log.info( "登录成功" );
		log.info( "凭证:{}", CookieUtil.cookies( login ) );
	}
}

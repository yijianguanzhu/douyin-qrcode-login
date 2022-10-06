package com.yijianguanzhu.douyin.qrcode.login.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yijianguanzhu.douyin.qrcode.login.core.config.DefaultWaitScanQRCodeRunnable;
import com.yijianguanzhu.douyin.qrcode.login.core.entity.QrCodeEntity;
import com.yijianguanzhu.douyin.qrcode.login.core.entity.ResponseEntity;
import com.yijianguanzhu.douyin.qrcode.login.core.entity.SuccessScanQRCodeEntity;
import com.yijianguanzhu.douyin.qrcode.login.exception.BaseException;
import com.yijianguanzhu.douyin.qrcode.login.exception.QRCodeLoginFailedException;
import com.yijianguanzhu.douyin.qrcode.login.utils.CookieUtil;
import com.yijianguanzhu.douyin.qrcode.login.utils.HttpUtil;
import com.yijianguanzhu.douyin.qrcode.login.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@Slf4j
public class LoginHelper {

	// 获取登录二维码
	public static QrCodeEntity getQrCode() {
		ResponseEntity<QrCodeEntity> response = ResponseUtil.bean(
				HttpUtil.get( "https://sso.douyin.com/get_qrcode/?need_logo=true" ),
				new TypeReference<ResponseEntity<QrCodeEntity>>() {
				} );
		return response.getData();
	}

	/**
	 * 1. 主线程把等待扫码结果任务交给其他线程工作 2. 扫码成功后，主线程真正去获取cookie
	 */
	public static Map<String, String> login( String token ) {
		CompletableFuture<SuccessScanQRCodeEntity> future = waitingScanQRCode( token );
		SuccessScanQRCodeEntity succ;
		try {
			succ = future.get();
		}
		catch ( BaseException e ) {
			throw e;
		}
		catch ( Exception e ) {
			throw new RuntimeException( e );
		}
		return cookies( succ );
	}

	protected static CompletableFuture<SuccessScanQRCodeEntity> waitingScanQRCode( String token ) {
		final CompletableFuture<SuccessScanQRCodeEntity> future = new CompletableFuture<>();
		final DefaultWaitScanQRCodeRunnable runnable = new DefaultWaitScanQRCodeRunnable( future, token );
		runnable.schedule();
		return future;
	}

	protected static Map<String, String> cookies( SuccessScanQRCodeEntity succ ) {
		Map<String, String> headers = new LinkedHashMap<>();
		headers.put( CookieUtil.COOKIE, CookieUtil.cookies( succ.getCookies() ) );
		try ( Response response = HttpUtil.get( succ.getUrl(), headers ) ) {
			Map<String, String> cookies = CookieUtil.cookies( response );
			CookieUtil.fillCookies( succ.getCookies(), cookies );
			return cookies;
		}
		catch ( Exception e ) {
			log.warn( "登录失败" );
			throw new QRCodeLoginFailedException( e );
		}
	}
}

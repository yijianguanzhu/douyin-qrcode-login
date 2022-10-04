package com.yijianguanzhu.douyin.qrcode.login.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yijianguanzhu.douyin.qrcode.login.core.config.DefaultWaitScanQRCodeRunnable;
import com.yijianguanzhu.douyin.qrcode.login.core.entity.QrCodeEntity;
import com.yijianguanzhu.douyin.qrcode.login.core.entity.ResponseEntity;
import com.yijianguanzhu.douyin.qrcode.login.core.entity.SuccessScanQRCodeEntity;
import com.yijianguanzhu.douyin.qrcode.login.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@Slf4j
public class LoginHelper {

	public static QrCodeEntity getQrCode() {
		ResponseEntity<QrCodeEntity> response = HttpUtil.get( "https://sso.douyin.com/get_qrcode/?need_logo=true",
				new TypeReference<ResponseEntity<QrCodeEntity>>() {
				} );
		return response.getData();
	}

	public static Map<String, String> login( String token ) {
		CompletableFuture<SuccessScanQRCodeEntity> future = waitingScanQRCode( token );
		SuccessScanQRCodeEntity succ = null;
		try {
			succ = future.get();
		}
		catch ( Exception e ) {

		}
		return null;
	}

	protected static CompletableFuture<SuccessScanQRCodeEntity> waitingScanQRCode( String token ) {
		final CompletableFuture<SuccessScanQRCodeEntity> future = new CompletableFuture<>();
		final DefaultWaitScanQRCodeRunnable runnable = new DefaultWaitScanQRCodeRunnable( future, token );
		runnable.schedule();
		return future;
	}

	public static void main( String[] args ) {
		QrCodeEntity qrCode = getQrCode();
		log.info( "二维码：{}", qrCode.getQrCode() );
		login( qrCode.getToken() );
	}
}

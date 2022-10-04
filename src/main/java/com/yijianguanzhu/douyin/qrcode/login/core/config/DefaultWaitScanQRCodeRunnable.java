package com.yijianguanzhu.douyin.qrcode.login.core.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yijianguanzhu.douyin.qrcode.login.core.entity.ResponseEntity;
import com.yijianguanzhu.douyin.qrcode.login.core.entity.SuccessScanQRCodeEntity;
import com.yijianguanzhu.douyin.qrcode.login.enums.ScanQRCodeEnum;
import com.yijianguanzhu.douyin.qrcode.login.exception.QRCodeAbortLoginException;
import com.yijianguanzhu.douyin.qrcode.login.exception.QRCodeLoginFailedException;
import com.yijianguanzhu.douyin.qrcode.login.exception.QRCodeTimeoutException;
import com.yijianguanzhu.douyin.qrcode.login.utils.CookieUtil;
import com.yijianguanzhu.douyin.qrcode.login.utils.HttpUtil;
import com.yijianguanzhu.douyin.qrcode.login.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author yijianguanzhu 2022年10月04日
 */
@Slf4j
public class DefaultWaitScanQRCodeRunnable implements Runnable {

	final static ScheduledExecutorService EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor( 1,
			new ThreadFactory( "Waiting-Scan-QRCode" ), new ThreadPoolExecutor.CallerRunsPolicy() );

	private CompletableFuture<SuccessScanQRCodeEntity> future;
	private ScheduledFuture<?> schedule;
	private String token;

	public DefaultWaitScanQRCodeRunnable( CompletableFuture<SuccessScanQRCodeEntity> future, String token ) {
		this.future = future;
		this.token = token;
	}

	@Override
	public void run() {
		ResponseEntity<SuccessScanQRCodeEntity> entity = null;
		Map<String, String> cookies = null;
		Request request = new Request.Builder()
				.url( "https://sso.douyin.com/check_qrconnect/?token=" + token )
				.get().build();
		try ( Response response = HttpUtil.CLIENT.newCall( request ).execute() ) {
			cookies = CookieUtil.cookies( response );
			entity = JacksonUtil.bean( response.body().string(),
					new TypeReference<ResponseEntity<SuccessScanQRCodeEntity>>() {
					} );
		}
		catch ( Exception e ) {
			future.completeExceptionally( e );
			schedule.cancel( true );
			return;
		}

		SuccessScanQRCodeEntity data = entity.getData();

		// 未知异常
		if ( data.getErrorCode() != 0 ) {
			log.error( data.getDescription() );
			future.completeExceptionally( new QRCodeLoginFailedException( data.getErrorCode(), data.getDescription() ) );
			schedule.cancel( true );
			return;
		}

		ScanQRCodeEnum status = data.getStatus();
		log.info( status.getDesc() );

		// 取消登录
		if ( status == ScanQRCodeEnum.CANCELED ) {
			future.completeExceptionally( new QRCodeAbortLoginException( status.getDesc() ) );
			schedule.cancel( true );
			return;
		}

		// 二维码过期
		if ( status == ScanQRCodeEnum.EXPIRED ) {
			future.completeExceptionally( new QRCodeTimeoutException( status.getDesc() ) );
			schedule.cancel( true );
			return;
		}

		// 确认登录
		if ( status == ScanQRCodeEnum.SUCCEEDED ) {
			log.info( CookieUtil.cookies( cookies ) );
			schedule.cancel( true );
		}
	}

	public void schedule() {
		this.schedule = EXECUTOR_SERVICE.scheduleAtFixedRate( this, 3, 3, TimeUnit.SECONDS );
	}
}

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
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	private String url;
	@Setter
	private String ttwId = "ttwid=1|D3qGftHDCKKHEiI1pB0OSa7DV3iwE68_dKt4ANR-7qs|1664966072|3da3c93f99317afb365f8d82620e216977a00d845fe2c402f2c57e1d8d4842d9";
	@Setter
	private String realUrl = "https://www.douyin.com/passport/sso/login/callback/?next=https://www.douyin.com&ticket=";

	public DefaultWaitScanQRCodeRunnable( CompletableFuture<SuccessScanQRCodeEntity> future, String token ) {
		this.future = future;
		this.token = token;
		this.url = "https://sso.douyin.com/check_qrconnect/?token=" + token;
	}

	@Override
	public void run() {
		ResponseEntity<SuccessScanQRCodeEntity> entity = null;
		Request request = new Request.Builder()
				.url( url ).get().header( "Cookie", ttwId ).build();
		try ( Response response = HttpUtil.CLIENT.newCall( request ).execute() ) {
			entity = JacksonUtil.bean( response.body().string(),
					new TypeReference<ResponseEntity<SuccessScanQRCodeEntity>>() {
					} );
			entity.getData().setCookies( CookieUtil.cookies( response ) );
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
			data.setTicket( ticket( data.getRedirectUrl() ) );
			data.setUrl( realUrl + data.getTicket() );
			future.complete( data );
			schedule.cancel( true );
		}
	}

	public void schedule() {
		this.schedule = EXECUTOR_SERVICE.scheduleAtFixedRate( this, 3, 3, TimeUnit.SECONDS );
	}

	public String ticket( String url ) {
		return Stream.of( url.split( CookieUtil.AMPERSAND ) )
				.filter( val -> val.contains( "ticket" ) )
				.map( val -> val.split( CookieUtil.EQUAL_SIGN ) )
				.filter( val -> val.length == 2 )
				.map( val -> val[1] )
				.collect( Collectors.joining() );
	}
}

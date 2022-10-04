package com.yijianguanzhu.douyin.qrcode.login.core.config;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yijianguanzhu 2022年10月04日
 */
public class ThreadFactory implements java.util.concurrent.ThreadFactory {

	private final java.util.concurrent.ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
	private final AtomicInteger threadNumber = new AtomicInteger( 1 );
	private final String threadPrefix;

	public ThreadFactory( String threadPrefix ) {
		this.threadPrefix = threadPrefix;
	}

	@Override
	public Thread newThread( Runnable runnable ) {
		Thread thread = defaultThreadFactory.newThread( runnable );
		thread.setName( threadPrefix + "-" + threadNumber );
		return thread;
	}
}

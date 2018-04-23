package io.bumo.sdk.core.exec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.bumo.sdk.core.utils.concurrent.NamedThreadFactory;


/***
 * 
 * @author bumo
 *
 */
public final class ExecutorsFactory {
	
	final static PoolAttrs poolAttrs = new PoolAttrs();
	
	final static ExecutorService executorService = new ThreadPoolExecutor(
			poolAttrs.getCorePoolSize(), 
			poolAttrs.getMaximumPoolSize(), 
			poolAttrs.getKeepAliveTime(), TimeUnit.SECONDS,
	        new LinkedTransferQueue<>(), // SynchronousQueue
	        new NamedThreadFactory(poolAttrs.getThreadFactoryName(), poolAttrs.isIndexThread(), poolAttrs.isDeamon()));
	 
	final static ScheduledThreadPoolExecutor scheduCheck = new ScheduledThreadPoolExecutor(poolAttrs.getSchdCorePoolSize());
	
	public static  final ExecutorService getExecutorService() {
		return	executorService;
	}
	
	public static final ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() {
		return scheduCheck;
	}
}

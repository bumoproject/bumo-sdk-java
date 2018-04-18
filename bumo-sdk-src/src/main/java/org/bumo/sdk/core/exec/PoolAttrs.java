package org.bumo.sdk.core.exec;

/***
 * 
 * @author bumo
 *
 */
public class PoolAttrs {

	private int corePoolSize = 10;
	private int maximumPoolSize = 20000;
	private long keepAliveTime = 60L;
	private String threadFactoryName = "BUBI-SDK-MANAGER";
	private boolean indexThread = true;
	private boolean deamon = false;
	private int schdCorePoolSize = 2;

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public String getThreadFactoryName() {
		return threadFactoryName;
	}

	public void setThreadFactoryName(String threadFactoryName) {
		this.threadFactoryName = threadFactoryName;
	}

	public boolean isIndexThread() {
		return indexThread;
	}

	public void setIndexThread(boolean indexThread) {
		this.indexThread = indexThread;
	}

	public boolean isDeamon() {
		return deamon;
	}

	public void setDeamon(boolean deamon) {
		this.deamon = deamon;
	}

	public int getSchdCorePoolSize() {
		return schdCorePoolSize;
	}

	public void setSchdCorePoolSize(int schdCorePoolSize) {
		this.schdCorePoolSize = schdCorePoolSize;
	}

}

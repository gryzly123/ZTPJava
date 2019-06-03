package eu.kniedzwiecki.ztpj.lab07.jaxws;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class WorkersServiceThreadPool extends ThreadPoolExecutor
{

	private static final int pool_size = 10;
	private boolean is_paused;
	private ReentrantLock pause_lock = new ReentrantLock();
	private Condition unpaused = pause_lock.newCondition();
	
	public WorkersServiceThreadPool()
	{
		super(pool_size, // core pool size
		pool_size, // maximum pool size
		0L, // keep-alive time for idle thread
		TimeUnit.SECONDS, // time unit for keep-alive setting
		new LinkedBlockingQueue<Runnable>(pool_size)); // work queue
	}
}

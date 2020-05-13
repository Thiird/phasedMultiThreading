package actors;

import java.util.concurrent.atomic.AtomicInteger;

import main.Monitor;

public class Controller implements Runnable
{
	public static AtomicInteger toRunThreads = new AtomicInteger(0);
	public static AtomicInteger timeToken = new AtomicInteger(0);
	private int nOfThreadsPerPhase;

	public Controller(int nOfThreads)
	{
		this.nOfThreadsPerPhase = nOfThreads;
	}

	public void run()
	{
		while (true)
		{
			synchronized (Monitor.getMonitor())
			{
				getContAndIncrement();
				Monitor.nextPhase();
				Monitor.printToConsole("==============================================PHASE " + Monitor.getPhase() + " - " + Monitor.getSubPhase());
				//Monitor.printToConsole("1Now: " + toRunThreads.get());
				toRunThreads.set(nOfThreadsPerPhase);
				//Monitor.printToConsole("2Now: " + toRunThreads.get());
			}

			switch (Monitor.getPhase())
			{
				case 0:
					break;
				case 1:
					solve();
					break;
				case 2:
					solve();
					break;
				case 3:
					break;
				case 4:
					break;
			}
		}
	}

	public static void imDone()
	{
		toRunThreads.decrementAndGet();
	}

	public static int getTimeToken()
	{
		synchronized (Monitor.getMonitor())
		{
			return timeToken.get();
		}
	}

	public static int getContAndIncrement()
	{
		synchronized (Monitor.getMonitor())
		{
			if (timeToken.get() == 99999)
			{
				timeToken.set(0);
				return timeToken.get();
			}
			else
			{
				return timeToken.getAndIncrement();
			}
		}
	}

	private static void solve()
	{
		while (toRunThreads.get() != 0)
		{//Till there are threads to execute, keep releasing
			synchronized (Monitor.getMonitor())
			{
				Monitor.getMonitor().notifyAll();
			}
		}
	}
}

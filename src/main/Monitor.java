package main;

import java.util.concurrent.atomic.AtomicInteger;

public class Monitor
{
	private static volatile Monitor roadDataInstance = null;
	public static int phase = 4;
	public static int subPhase = 0;

	private Monitor()
	{

	}

	public static synchronized Monitor getMonitor()
	{//Returns reference to this object

		if (roadDataInstance == null)
		{
			roadDataInstance = new Monitor();
		}

		return roadDataInstance;
	}

	public static void release(AtomicInteger cont)
	{
		while (cont.get() != 0)
		{//Till there are threads to execute, keep releasing
			synchronized (getMonitor())
			{
				getMonitor().notifyAll();
			}
		}
	}

	public static synchronized void printToConsole(String s)
	{
		System.out.println(s);
	}

	public synchronized static int getPhase()
	{
		return phase;
	}

	public synchronized static int getSubPhase()
	{
		return subPhase;
	}

	public static void nextPhase()
	{
		synchronized (getMonitor())
		{
			switch (phase)
			{
				case 0:
					//Phase 0 has 0 subPhases
					phase++;
					break;
				case 1:
					//Phase 1 has 2 subPhases
					if (subPhase == 0) subPhase++;
					else
					{
						subPhase = 0;
						phase++;
					}

					break;
				case 2:
					//Phase 2 has 3 subPhases
					if (subPhase == 0 || subPhase == 1) subPhase++;
					else
					{
						subPhase = 0;
						phase++;
					}
					break;
				case 3:
					//Phase 3 has 2 subPhases
					if (subPhase == 0) subPhase++;
					else
					{
						subPhase = 0;
						phase++;
					}
					break;

				case 4:
					//Phase 4 has 0 subPhases
					phase = 0;
					break;
			}
		}
	}
}
package actors;

import main.Monitor;

public abstract class SlaveSkeleton implements Runnable
{
	protected int timeToken = -1;
	protected boolean executedOnce = false; //This could be modified to be an int, so that I can let the slave execute more than once per phase/subPhase

	@Override
	public void run()
	{
		while (true)
		{
			verifyCondition();

			executeJob();

			executedOnce = true;
			Controller.imDone();
		}
	}

	public abstract void verifyCondition();

	public abstract void executeJob();

	protected void waitToAct()
	{
		try
		{
			synchronized (Monitor.getMonitor())
			{
				//Monitor.printToConsole(
				//		"1-1| Eseguito: " + Boolean.toString(executed) + " _ Executed@: " + executedPhase + " _ Now@ " + Controller.getTimeToken() + " : " + Controller.toRunThreads.get());

				Monitor.getMonitor().wait();

				//Se ho eseguito, controllo se la fase è cambiata
				if (executedOnce) if (timeToken != Controller.getTimeToken()) executedOnce = false;

				//Monitor.printToConsole(
				//		"1-2| Eseguito: " + Boolean.toString(executed) + " _ Executed@: " + executedPhase + " _ Now@ " + Controller.getTimeToken() + " : " + Controller.toRunThreads.get());
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
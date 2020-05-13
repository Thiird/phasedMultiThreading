package actors;

import main.Monitor;

public class SlavePhaseOne extends SlaveSkeleton
{
	@Override
	public void executeJob()
	{
		Monitor.printToConsole("Slave 1 acting in PHASE: " + Monitor.getPhase());
	}

	@Override
	public void verifyCondition()
	{
		while (executedOnce || Monitor.getPhase() != 1)
		{
			waitToAct();
		}

		timeToken = Controller.getTimeToken();
	}
}
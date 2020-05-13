package actors;

import main.Monitor;

public class SlavePhaseTwo extends SlaveSkeleton
{
	@Override
	public void executeJob()
	{
		Monitor.printToConsole("Slave 2 acting in PHASE: " + Monitor.getPhase());
	}

	@Override
	public void verifyCondition()
	{
		while (executedOnce || Monitor.getPhase() != 2)
		{
			waitToAct();
		}

		timeToken = Controller.getTimeToken();
	}
}
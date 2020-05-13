package actors;

import main.Monitor;

public class SlavePhaseOneAndTwo extends SlaveSkeleton
{
	@Override
	public void executeJob()
	{
		Monitor.printToConsole("Slave 3 acting in PHASE: " + Monitor.getPhase());
	}

	@Override
	public void verifyCondition()
	{
		while ((Monitor.getPhase() != 1 && Monitor.getPhase() != 2) || (Monitor.getPhase() != 1 && executedOnce) || (Monitor.getPhase() != 2 && executedOnce))
		{
			waitToAct();
		}

		timeToken = Controller.getTimeToken();
	}
}
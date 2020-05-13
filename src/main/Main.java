package main;

import actors.Controller;
import actors.SlavePhaseOne;
import actors.SlavePhaseOneAndTwo;
import actors.SlavePhaseTwo;

/*
 * Phased multithreading
 * 
 * One controllers cycles between 4 phases:
 * -Phase 0 with 1 subPhase
 * -Phase 1 with 2 subPhases
 * -Phase 2 with 3 subPhases
 * -Phase 3 with 1 subPhase
 * 
 * Slaves execute their job in their phases
 * SlavePhaseOne executes in Phase 1, in all subPhases
 * SlavePhaseTWo executes in Phase 2, in all subPhases
 * SlavePhaseOneAndTwo executes in Phase 1 in all subPhases, and once per Phase2 in all subPhases
 * 
 * To set during which phase/subPhase a slave must execute in, the while boolean expression
 * in the verifyCondition() method (SlaveSkeleton class) must be modified accordingly.
 * To do so I personally manually write down a truth table in the following way:
 * 
 *  A   B   C   .. wait?
 * ___|___|___|___|___
 *  0 |   |   |   |0
 *  0 |   |   |   |1
 * .. |   |   |   |1
 * 
 * where A is
 * then I minimize the wait? expression with an online tool to get the final while condition
 */
public class Main
{
	public static void main(String[] args)
	{
		/*
		 * Slave 1 executes in phase 1
		 * Slave 2 executes in phase 2
		 * Slave 3 executes in both phase 1 and 2
		 */

		//Input 2 here because in both phase 1 and 2 there are 2 slaves executing
		Controller ct = new Controller(2);

		new Thread(new SlavePhaseOne()).start();
		new Thread(new SlavePhaseTwo()).start();
		new Thread(new SlavePhaseOneAndTwo()).start();

		new Thread(ct).start();
	}
}
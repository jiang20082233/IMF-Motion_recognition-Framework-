
package Motion.gyroMotionInterfaceImpl;

import Motion.Interfaces.GyroMotionInterface;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author CheolMin Kim
 */

public class GyroMotionImpl_Right implements GyroMotionInterface{

	/**
	 * It is ovverided method that writes an algorithm to recognize the Right motion using a list containing arrays(difference[]).
	 * @param differenceResultList
	 *  List containing arrays(difference[]) having angular difference values at  index numbers that are equal to the index number of yawRollPitchRangeList.
	 * difference[0] is Information about the order in which values were sent. 
	 * difference[1] is Information on the yaw axis difference value.
	 * difference[2] is Information on the roll axis difference value.
	 * difference[3] is Information on the pitch axis difference value.
	 * @param motionMap
	 * Map with motion names of String type as Keys , counts of Integer type as Values.
	 */
	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int rightCount=0;
		int totalRightDifference=0;
		List<double[]> factorsInRange = differenceResultList.get(1);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if (count[1] <0&&count[2]!=0) {
						rightCount++;
						totalRightDifference+=count[1];
					} 
		}
		//System.out.println("totalRightDifference :  "+totalRightDifference);
		motionMap.put("right", rightCount);
	}

}

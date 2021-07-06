import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
/**
 * 
 * @author Obinna
 *
 * Representation of Bridge and Torch problem.
 */
public class ProblemModel {
	private ArrayList<Integer> sideEnd;
	private ArrayList<Integer> sideStart;
	private int totalTime;
	private boolean torch;
	private ProblemModel predecessor;
	private int depth;
	private int cost;
	private int cost2;


	public ProblemModel(){
		this.sideEnd = new ArrayList<Integer>();
		this.sideStart = new ArrayList<Integer>();
		this.totalTime = 0;
		this.torch = true;
		this.depth = 0;
		this.cost = 0;
		this.cost2 = 0;
	}

	public ProblemModel(ArrayList<Integer> sideStart, ArrayList<Integer> sideEnd, int totalTime){
		this.sideEnd = new ArrayList<Integer>();
		this.sideStart = new ArrayList<Integer>();
		this.totalTime = 0;
		this.torch = true;
		for(Integer r: sideEnd)this.sideEnd.add(0 + r);

		for(Integer r: sideStart)this.sideStart.add(0+r);

		this.totalTime += totalTime;
	}

	public ProblemModel(ProblemModel pm){
		this(pm.getSideStart(), pm.getSideEnd(), pm.getTotalTime());
	}

	/**
	 * @return sideEnd
	 */
	public ArrayList<Integer> getSideEnd() {
		return sideEnd;
	}

	/**
	 * @param sideEnd
	 * set the end side
	 */
	public void setSideEnd(ArrayList<Integer> sideEnd) {
		this.sideEnd = sideEnd;
	}

	/**
	 * @return sideStart
	 */
	public ArrayList<Integer> getSideStart() {
		return sideStart;
	}

	/**
	 * @param sideStart
	 */
	public void setSideStart(ArrayList<Integer> sideStart) {
		this.sideStart = sideStart;
	}

	/**
	 * @return the totalTime
	 */
	public int getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime the totalTime to set
	 */
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	/**
	 * @return predecessor
	 */
	public ProblemModel getPredecessor() {
		return predecessor;
	}

	/**
	 * @param predecessor 
	 * 
	 * parent node
	 */
	public void setPredecessor(ProblemModel predecessor) {
		this.predecessor = predecessor;
	}

	/**
	 * @return depth of node
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getCost() {
		return cost;
	}

	public int getCost2() {
		return cost2;
	}

	public static void main(String args[]){
		ArrayList<Integer> Integers = new ArrayList<Integer>();
		for(int i = 4; i >1; i--)Integers.add(new Integer(i));
		Integers.add(new Integer(19));
		Integers.add(new Integer(6));
		for(Integer pm : Integers)System.out.println("" + pm);
		System.out.println("==================================================");
		Collections.sort(Integers);
		for(Integer pm : Integers)System.out.println("" + pm);

	}

	/**
	 * 
	 * @param input
	 * @return sum of the crossing times of each runner
	 */

	public int sumSpeeds(ArrayList<Integer> input){
		int sum =0;
		for(Integer i : input){
			sum+=i;
		}
		return sum;
	}

	//Production Method
	//generates all the subnodes for all the combinations of runners
	public ArrayList<ProblemModel> getSubNodes(){
		ArrayList<ProblemModel> subNodes = new ArrayList<ProblemModel>();

		if(this.torch){
			moveRightToLeft(subNodes);	
		}else {
			moveLeftToRight(subNodes);
		}	
		return subNodes;
	}

	/**
	 * generates the subnodes for all the possible combination of runners
	 * From EndSide to StartSide
	 * 
	 * @param subNodes
	 */
	private void moveLeftToRight(ArrayList<ProblemModel> subNodes) {
		//if more than 1 runners in node
		if(this.sideEnd.size() > 1){
			//cross 2 at a time then 1 at a time
			cross2EndToStart(subNodes);
			cross1EndToStart(subNodes);
		} else {
			cross1EndToStart(subNodes);
		}
	}

	/**
	 * One runner crosses at a time from end to start
	 * 
	 * @param subNodes
	 */
	private void cross1EndToStart(ArrayList<ProblemModel> subNodes) {
		for(int i = 0; i < this.sideEnd.size() ; i ++){
			ProblemModel temp = new ProblemModel(sideStart, sideEnd, totalTime);
			temp.setPredecessor(this);
			temp.setDepth(this.depth +1);
			temp.sideStart.add(temp.sideEnd.get(i));

			temp.totalTime += (temp.sideEnd.get(i));
			temp.cost += (temp.sideEnd.get(i));
			temp.cost2 += (temp.sideEnd.get(i));

			temp.sideEnd.remove(i);
			temp.torch = !this.isTorch();


			subNodes.add(temp);
		}
	}

	/**
	 * 2 runners cross at a time from end to start
	 * 
	 * @param subNodes
	 */
	private void cross2EndToStart(ArrayList<ProblemModel> subNodes) {
		for(int i = 0; i < this.sideEnd.size(); i ++){
			for(int j = i+1; j < this.sideEnd.size() ; j ++){
				ProblemModel temp = new ProblemModel(sideStart, sideEnd, totalTime);
				temp.setPredecessor(this);
				temp.setDepth(this.depth +1);
				//move two at a time to the other side of bridge forward
				temp.sideStart.add(temp.sideEnd.get(i));
				temp.sideStart.add(temp.sideEnd.get(j));
				int speedi = temp.sideEnd.get(i);
				int speedj = temp.sideEnd.get(j);

				temp.totalTime = temp.totalTime + ((speedi <= speedj) ? speedj: speedi);
				temp.cost += ((speedi <= speedj) ? speedj: speedi);
				temp.cost2 +=  speedj + speedi;
				temp.sideEnd.remove(j);
				temp.sideEnd.remove(i);
				temp.torch = !this.torch;
				subNodes.add(temp);
			}
		}
	}

	/**
	 * generates the subnodes for all the possible combination of runners
	 * From StartSide to EndSide
	 * 
	 * @param subNodes
	 */
	private void moveRightToLeft(ArrayList<ProblemModel> subNodes) {
		if(this.sideStart.size() > 1){
			cross2StartToEnd(subNodes);
			cross1StartToEnd(subNodes);
		}else {
			cross1StartToEnd(subNodes);

		}
	}

	/**
	 * 1 crosser at a time from start to end added to param subnodes
	 * 
	 * @param subNodes
	 */
	private void cross1StartToEnd(ArrayList<ProblemModel> subNodes) {
		for(int i = 0; i < this.sideStart.size() ; i ++){
			ProblemModel temp = new ProblemModel(sideStart, sideEnd, totalTime);
			temp.setPredecessor(this);
			temp.setDepth(this.depth +1);
			temp.sideEnd.add(temp.sideStart.get(i));

			temp.totalTime = temp.totalTime +  (temp.sideStart.get(i));
			temp.cost += (temp.sideStart.get(i));
			temp.cost2 += (temp.sideStart.get(i));

			temp.sideStart.remove(i);
			temp.torch = !this.isTorch();


			subNodes.add(temp);
		}
	}

	/**
	 * 2 crossers at a time from start to end added to param subnodes
	 * 
	 * @param subNodes
	 */
	private void cross2StartToEnd(ArrayList<ProblemModel> subNodes) {
		for(int i = 0; i < this.sideStart.size(); i ++){
			for(int j = i+1; j < this.sideStart.size(); j ++){
				ProblemModel temp = new ProblemModel(sideStart, sideEnd, totalTime);
				temp.setPredecessor(this);
				temp.setDepth(this.depth +1);

				//move two at a time to the other side of bridge forward
				temp.sideEnd.add(temp.sideStart.get(i));
				temp.sideEnd.add(temp.sideStart.get(j));
				int speedi = temp.sideStart.get(i);
				int speedj = temp.sideStart.get(j);

				temp.totalTime += ((speedi <= speedj) ? speedj: speedi);
				temp.cost += ((speedi <= speedj) ? speedj: speedi);
				temp.cost2 +=  speedj + speedi;

				temp.sideStart.remove(j);
				temp.sideStart.remove(i);


				temp.torch = !torch;


				subNodes.add(temp);

			}
		}
	}

	/**
	 * position of turn
	 * @return torch
	 */
	public boolean isTorch() {
		return torch;
	}


	/**
	 * Two list of runner times are equal if they have the same values in the same frequencies
	 * Example 1,1,0 and 1,0,1 would be equal
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public boolean areEqualLists(ArrayList<Integer> list1, ArrayList<Integer> list2)
	{
		//null checking
		if(list1==null && list2==null)
			return true;
		if((list1 == null && list2 != null) || (list1 != null && list2 == null))
			return false;

		if(list1.size()!=list2.size())
			return false;
		for(Object itemList1: list1)
		{
			if(!list2.contains(itemList1))
				return false;
			if(Collections.frequency(list1, itemList1) != Collections.frequency(list2, itemList1))
				return false;
		}

		return true;
	}



	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!ProblemModel.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final ProblemModel other = (ProblemModel) obj;
		if (!areEqualLists(this.sideStart, other.getSideStart())){
			return false;
		}
		if (!areEqualLists(this.sideEnd, other.getSideEnd())){
			return false;
		}
		if (this.isTorch() != other.isTorch()){
			return false;
		}

		return true;
	}




}
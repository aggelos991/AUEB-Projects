import java.util.*;

public class State{
	private ArrayList<Integer> sideEnd;
	private ArrayList<Integer> sideStart;
	private int totalTime;
	private boolean torch;//where the lamb is
	//private ProblemModel predecessor;
	private int depth;
	private int cost;//total cost
	//private int cost2;
	public State(){
		this.sideEnd = new ArrayList<Integer>();//what this list includes ?
		this.sideStart = new ArrayList<Integer>();
		this.totalTime = 0;
		this.torch = true;//the lamb is from the right size
		this.depth = 0;
		this.cost = 0;
		//this.cost2 = 0;
	}
	public State(ArrayList<Integer> sideStart, ArrayList<Integer> sideEnd, int totalTime){
		this.sideEnd = new ArrayList<Integer>();
		this.sideStart = new ArrayList<Integer>();
		this.totalTime = 0;
		this.torch = true;//the lamb is from the right side
		for(Integer r: sideEnd)this.sideEnd.add(0 + r);//?

		for(Integer r: sideStart)this.sideStart.add(0+r);//?

		this.totalTime += totalTime;//it finds the totaltime
	}
	
	//returns the SideEnd
	public ArrayList<Integer> getSideEnd() {
		return sideEnd;
	}
	//sets the SideEnd
	public void setSideEnd(ArrayList<Integer> sideEnd) {
		this.sideEnd = sideEnd;
	}
	//returns the sideStart
	public ArrayList<Integer> getSideStart() {
		return sideStart;
	}
	//sets the sideStart
	public void setSideStart(ArrayList<Integer> sideStart) {
		this.sideStart = sideStart;
	}
	//returns the totaltime
	public int getTotalTime() {
		return totalTime;
	}
	//sets the total time
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	//returns the depth
	public int getDepth() {
		return depth;
	}
	//sets the depth
	public void setDepth(int depth) {
		this.depth = depth;
	}
	//returns the cost
	public int getCost() {
		return cost;
	}
	
	
	public ArrayList<State> getSubNodes(){
		ArrayList<State> subNodes = new ArrayList<State>();

		if(this.torch){//if the lamb is at the right side
			moveRightToLeft(subNodes);	
		}else {
			moveLeftToRight(subNodes);
		}	
		return subNodes;
	}
	
	private void moveLeftToRight(ArrayList<State> subNodes) {
		
		if(this.sideEnd.size() > 1){//if the combination has two runners in node
			//cross 2 at a time then 1 at a time
			cross2EndToStart(subNodes);//cross for the second runner
			cross1EndToStart(subNodes);//cross for the first runner
		} else {//if the combination has one runner in node
			cross1EndToStart(subNodes);
		}
	}
	
	//one runner crosses the bridge
	private void cross1EndToStart(ArrayList<State> subNodes) {
		for(int i = 0; i < this.sideEnd.size() ; i ++){
			State temp = new State(sideStart, sideEnd, totalTime);
			//temp.setPredecessor(this);
			temp.setDepth(this.depth +1);
			temp.sideStart.add(temp.sideEnd.get(i));

			temp.totalTime += (temp.sideEnd.get(i));
			temp.cost += (temp.sideEnd.get(i));
			//temp.cost2 += (temp.sideEnd.get(i));

			temp.sideEnd.remove(i);
			temp.torch = !this.isTorch();


			subNodes.add(temp);
		}
	}
	
	//two runners cross the bridge
	private void cross2EndToStart(ArrayList<State> subNodes) {
		for(int i = 0; i < this.sideEnd.size(); i ++){
			for(int j = i+1; j < this.sideEnd.size() ; j ++){
				State temp = new State(sideStart, sideEnd, totalTime);
				//temp.setPredecessor(this);
				temp.setDepth(this.depth +1);
				//move two at a time to the other side of bridge forward
				temp.sideStart.add(temp.sideEnd.get(i));
				temp.sideStart.add(temp.sideEnd.get(j));
				int speedi = temp.sideEnd.get(i);
				int speedj = temp.sideEnd.get(j);

				temp.totalTime = temp.totalTime + ((speedi <= speedj) ? speedj: speedi);//it takes the player who is speeder
				temp.cost += ((speedi <= speedj) ? speedj: speedi);//it takes the lower cost
				//temp.cost2 +=  speedj + speedi;
				temp.sideEnd.remove(j);
				temp.sideEnd.remove(i);
				temp.torch = !this.torch;
				subNodes.add(temp);
			}
		}
	}
	
	
	//From startside(right) to endside(left)
	private void moveRightToLeft(ArrayList<State> subNodes) {
		if(this.sideStart.size() > 1){
			cross2StartToEnd(subNodes);
			cross1StartToEnd(subNodes);
		}else {
			cross1StartToEnd(subNodes);

		}
	}
	
	private void cross1StartToEnd(ArrayList<State> subNodes) {
		for(int i = 0; i < this.sideStart.size() ; i ++){
			State temp = new State(sideStart, sideEnd, totalTime);
			//temp.setPredecessor(this);
			temp.setDepth(this.depth +1);
			temp.sideEnd.add(temp.sideStart.get(i));

			temp.totalTime = temp.totalTime +  (temp.sideStart.get(i));
			temp.cost += (temp.sideStart.get(i));
			//temp.cost2 += (temp.sideStart.get(i));

			temp.sideStart.remove(i);
			temp.torch = !this.isTorch();


			subNodes.add(temp);
		}
	}
	
	//2 players crosses the bridge from right to left
	private void cross2StartToEnd(ArrayList<State> subNodes) {
		for(int i = 0; i < this.sideStart.size(); i ++){
			for(int j = i+1; j < this.sideStart.size(); j ++){
				State temp = new State(sideStart, sideEnd, totalTime);
				//temp.setPredecessor(this);
				temp.setDepth(this.depth +1);

				//move two at a time to the other side of bridge forward
				temp.sideEnd.add(temp.sideStart.get(i));
				temp.sideEnd.add(temp.sideStart.get(j));
				int speedi = temp.sideStart.get(i);
				int speedj = temp.sideStart.get(j);

				temp.totalTime += ((speedi <= speedj) ? speedj: speedi);
				temp.cost += ((speedi <= speedj) ? speedj: speedi);
				//temp.cost2 +=  speedj + speedi;

				temp.sideStart.remove(j);
				temp.sideStart.remove(i);


				temp.torch = !torch;


				subNodes.add(temp);

			}
		}
	}
	
	//position of the lamb
	//lamb==true(the lamb is at right)
	public boolean isTorch() {
		return torch;
	}
	
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
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!State.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final State other = (State) obj;
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
	
	
	
	public static void main(String args[]){
		ArrayList<Integer> Costs = new ArrayList<Integer>();
		Costs.add(10);
		Costs.add(3);
		Costs.add(4);
		State a=new State();
		System.out.println("the costs are:"+Costs);
		System.out.println("-----------------------------");
		System.out.println("the minimum cost is:"+a.cost);
		System.out.println("the total time that is needed is :"+a.totalTime);
	}
}
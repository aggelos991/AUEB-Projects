import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 
 * @author Obinna
 *
 * Depth First search algorithm to find answer
 */
public class DFSAI implements AI{

	private Stack<ProblemModel> unvisitedPM;
	//private Queue<Integer> unvisitedList;
	private ArrayList<ProblemModel> visited;
	private Comparator cmp;
	
	public DFSAI(ProblemModel problemModel){
		cmp = Collections.reverseOrder();
//		Collections.sort(problemModel.getSideEnd(), cmp);
//		Collections.sort(problemModel.getSideStart(), cmp);
		unvisitedPM = new Stack<ProblemModel>();
		visited = new ArrayList<ProblemModel>();
		//unvisitedList = new LinkedList<Integer>();
		
		for(ProblemModel pm : problemModel.getSubNodes()){
//			Collections.sort(pm.getSideEnd(), cmp);
//			Collections.sort(pm.getSideStart(), cmp);
			unvisitedPM.add(pm);
		}
		visited.add(problemModel);
		
	}


	@Override
	public void solve() {
		
		long startTime = System.nanoTime();
		while(!unvisitedPM.isEmpty()){
			
			ProblemModel current = unvisitedPM.pop();
					
			for(ProblemModel pm : current.getSubNodes()){
				if(!visited.contains(pm) && !unvisitedPM.contains(pm)){
//					Collections.sort(pm.getSideEnd(), cmp);
//					Collections.sort(pm.getSideStart(), cmp);
					unvisitedPM.push(pm);
				}
			
			}
			
			//end Condition
			if(current.getSideStart().isEmpty()){
				System.out.print( "\n"+ "StartSide Runners: ");
				for(Integer r: current.getSideStart()){
					System.out.print( " " +r) ;
				}
				System.out.print( "\n" + "EndSide Runners: ");
				for(Integer r: current.getSideEnd()){
					System.out.print( " " + r);
				}

				printPathTaken(current);
				System.out.print( "\n" + "------------done--------------");
				
				long endTime = System.nanoTime();
				long duration = ((endTime - startTime)/1000000);
				System.out.print( "\n" + "-DFS Time taken: " + duration + "ms");
				break;
			}
			
			visited.add(current);

		}
		
		
	}
	
	
	private void printPathTaken(ProblemModel current) {
		
		System.out.print( "\n"+ "StartSide Runners: ");
		for(Integer r: current.getSideStart()){
			System.out.print( " " +r) ;
		}
		System.out.print( "\n" + "EndSide Runners: ");
		for(Integer r: current.getSideEnd()){
			System.out.print( " " + r);
		}
		System.out.print( "\n" + "Direction: " + ((!current.isTorch())? "Forwards":"Backwards"));
		System.out.print( "\n" + "Total Time: " + current.getTotalTime());
		System.out.print( "\n" + "--------------------------");
		if(current.getPredecessor() != null)printPathTaken(current.getPredecessor());
	}
}
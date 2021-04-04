package algorithms.search;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * BFS algorithem implementation in order to find the shortest path in the Isearchable prolem
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected AbstractQueue<AState> queue; //queue of all the possible steps that available in the current isearchable.

    //CONSTRUCTOR
    public BreadthFirstSearch(Isearchable problem) {
        super(problem); //the problem
        setName("BreadthFirstSearch");
        AbstractQueue<AState> queue = new Queue();
    }

    public Solution solve(Isearchable domain){
        problem = domain;
        Solution final_solution = new Solution();
        AState start = problem.getStartState();
        AState goal = problem.getGoalState();

        queue.offer(start);
        while (queue.size() != 0){
            AState curr = queue.poll();
            if(curr.equals(goal)){
                 curr.setIsVisited(true);
                 final_solution.setSolutionPath(goal);
                 return final_solution;
            }
            ArrayList<AState> Neighbors = problem.getAllSuccessors(curr);
            for(int i = 0; i < Neighbors.size(); i++) {
                    if(!Neighbors.get(i).getIsVisited()){
                        Neighbors.get(i).setIsVisited(true);
                        Neighbors.get(i).setParent(curr);
                        queue.offer(Neighbors.get(i));
                    }

            }
        }

        //if there is no solution to the path - not suppose to happen
        return null;


    }



}

package algorithms.search;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * BFS algorithem implementation in order to find the shortest path in the Isearchable prolem
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected AbstractQueue<AState> queue; //queue of all the possible steps that available in the current isearchable.

    HashSet<AState> VisitedSet;
    //CONSTRUCTOR
    public BreadthFirstSearch() {
        super();
        setName("BreadthFirstSearch");
        AbstractQueue<AState> queue = new Queue();
        HashSet<AState> VisitedSet = new HashSet<>();// a hash set that keeps all the visited states
    }

    public Solution solve(Isearchable domain){
        problem = domain;
        Solution final_solution = new Solution();
        AState start = problem.getStartState();
        AState goal = problem.getGoalState();

        queue.offer(start);
        while (queue.size() != 0){
            AState curr = queue.poll();
            //if we reached the goal
            if(curr.equals(goal)){

                // add the goal to the visited list
                VisitedSet.add(curr);

                //NumberOfNodesEvaluated++
                this.AddsEvaluatedNode();
                 final_solution.setSolutionPath(goal);
                 return final_solution;
            }

            //checking the neighbors of the curr state if there are still
            //neighbors to visit.
            ArrayList<AState> Neighbors = problem.getAllSuccessors(curr);
            for(int i = 0; i < Neighbors.size(); i++) {

                //if we reached unvisited neighbor
                    if(!(VisitedSet.contains(Neighbors.get(i)))){
                        //add the  neighbor to the visited set and update the queue
                        VisitedSet.add(Neighbors.get(i)) ;
                        Neighbors.get(i).setParent(curr);
                        queue.offer(Neighbors.get(i));
                    }

            }
        }

        //if there is no solution to the path - not suppose to happen
        return null;


    }



}

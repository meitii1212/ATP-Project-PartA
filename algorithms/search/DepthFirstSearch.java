package algorithms.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * implementation for the iterative DFS algorithm.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    private Stack<AState> stack;
    private HashSet<AState> VisitedSet;

    /**CONSTRUCTOR*/
    public DepthFirstSearch() {
        super();
        setName("DepthFirstSearch");
        Stack<AState> stack = new Stack<>(); //the stack that collect the possibloe next steps
        HashSet<AState> VisitedSet = new HashSet<>();// a hash set that keeps all the visited states
    }

    public Solution solve(ISearchable domain){

        //creating the solution object that will be returned
        problem = domain;
        Solution final_solution = new Solution();
        AState curr = problem.getStartState();
        AState goal = problem.getGoalState();


        //pushing the StartState to the stack
        stack.push(curr);

        //loop on the stack until there are no more AStates
        while(!stack.empty()){

            //pop a AState from the stack
            curr = stack.pop();

            // if the AState is already discovered yet, ignore it
            if(VisitedSet.contains(curr)){
                continue;
            }

            //else:
            VisitedSet.add(curr);
            //NumberOfNodesEvaluated++
            this.AddsEvaluatedNode();

            //if we arrived to the goal state
            if(curr.equals(goal)) {
                //NumberOfNodesEvaluated++
                this.AddsEvaluatedNode();
                final_solution.setSolutionPath(goal);
                return final_solution;
            }
            //if its not the goal
            ArrayList<AState> Neighbors = problem.getAllSuccessors(curr);
            for (int i = 0; i < Neighbors.size(); i++) {
                if (!(VisitedSet.contains(Neighbors.get(i)))){
                    Neighbors.get(i).setParent(curr);
                    stack.push(Neighbors.get(i));
                }

            }

        }
        //if there is no solution - return empty solution - impossible.
        return final_solution;
    }


}

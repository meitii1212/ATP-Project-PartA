package algorithms.search;

import java.util.*;

/**
 * implementation for the iterative DFS algorithm.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    private Stack<AState> stack;
    private HashSet<AState> VisitedSet;
    private HashMap<AState,AState> FoundSuccessors; //all the successors we found throght the searching maaped to their parent
    /**CONSTRUCTOR*/
    public DepthFirstSearch() {
        super();
        setName("DepthFirstSearch");


    }

    public Solution solve(ISearchable domain){
        stack = new Stack<>(); //the stack that collect the possibloe next steps
        VisitedSet = new HashSet<>();// a hash set that keeps all the visited states
        FoundSuccessors = new HashMap<AState,AState>();
        //creating the solution object that will be returned
        problem = domain;
        Solution final_solution = new Solution();
        AState curr = problem.getStartState();
        AState goal = problem.getGoalState();


        //pushing the StartState to the stack
        stack.push(curr);
        FoundSuccessors.put(curr,null);

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
                goal.setParent(FoundSuccessors.get(goal));
                final_solution.BuildSolutionPath(goal);
                return final_solution;
            }
            //if its not the goal
            ArrayList<AState> Neighbors = problem.getAllSuccessors(curr);
            for (int i = 0; i < Neighbors.size(); i++) {
                if (!(VisitedSet.contains(Neighbors.get(i)))){

                    //if neighbor not in successors-> add it with the updated parent
                    if(!FoundSuccessors.containsKey(Neighbors.get(i))) {
                        Neighbors.get(i).setParent(curr);
                        FoundSuccessors.put(Neighbors.get(i),curr);

                    }
                    //if its it the table, update the correct parent
                    else{
                        Neighbors.get(i).setParent(FoundSuccessors.get(Neighbors.get(i)));

                    }

                    stack.push(Neighbors.get(i));
                }

            }

        }
        //if there is no solution - return empty solution - impossible.
        return final_solution;
    }


}

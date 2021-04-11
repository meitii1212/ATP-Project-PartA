package algorithms.search;

import java.util.*;

/**
 * BFS algorithem implementation in order to find the shortest path in the Isearchable prolem
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected AbstractQueue<AState> queue; //queue of all the possible steps that available in the current isearchable.

    HashSet<AState> VisitedSet;
    private HashMap<AState,AState> FoundSuccessors; //all the successors we found throght the searching maaped to their parent


    //CONSTRUCTOR
    public BreadthFirstSearch() throws Exception {
        super();
        setName("BreadthFirstSearch");
    }

    public Solution solve(ISearchable domain) throws Exception {
        NullArgCheck(domain);
        problem = domain;
        AbstractQueue<AState> queue = new Queue();
        HashSet<AState> VisitedSet = new HashSet<>();// a hash set that keeps all the visited states
        FoundSuccessors = new HashMap<AState,AState>(); //a hash table that map each neighbor we revealed to his parent
        Solution final_solution = new Solution();
        AState start = problem.getStartState();
        AState goal = problem.getGoalState();

        queue.offer(start);
        FoundSuccessors.put(start,null);
        while (queue.size() != 0){
            AState curr = queue.poll();
            //if we reached the goal
            if(curr.equals(goal)){

                // add the goal to the visited list and update its parent
                VisitedSet.add(curr);
                goal.setParent(FoundSuccessors.get(curr));
                //NumberOfNodesEvaluated++
                this.AddsEvaluatedNode();
                 final_solution.BuildSolutionPath(goal);
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
                        //if neighbor not in successors-> add it with the updated parent
                        if(!FoundSuccessors.containsKey(Neighbors.get(i))) {
                            Neighbors.get(i).setParent(curr);
                            FoundSuccessors.put(Neighbors.get(i), curr);
                            //NumberOfNodesEvaluated++
                            this.AddsEvaluatedNode();
                        }
                        //if its it the table, update the correct parent
                        else{
                            Neighbors.get(i).setParent(FoundSuccessors.get(Neighbors.get(i)));

                        }
                        queue.offer(Neighbors.get(i));

                    }

            }
        }

        //if there is no solution to the path - not suppose to happen return empty solution
        return final_solution;


    }



}

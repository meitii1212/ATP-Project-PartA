package algorithms.search;

import java.util.*;

/**
 * BFS algorithem implementation in order to find the shortest path in the Isearchable prolem
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected AbstractQueue<AState> queue; //queue of all the possible steps that available in the current isearchable.
    protected boolean Flag = true;
    HashSet<AState> VisitedSet;
    private HashMap<AState, AState> FoundSuccessors; //all the successors we found throght the searching maaped to their parent


    //CONSTRUCTOR
    public BreadthFirstSearch() throws Exception {
        super();
        queue = new Queue();
        setName("BreadthFirstSearch");
    }

    public Solution solve(ISearchable domain) throws Exception {
        NullArgCheck(domain);
        problem = domain;

        HashSet<AState> VisitedSet = new HashSet<>();// a hash set that keeps all the visited states
        FoundSuccessors = new HashMap<AState, AState>(); //a hash table that map each neighbor we revealed to his correct cost and parent
        Solution final_solution = new Solution();
        AState start = problem.getStartState();
        AState goal = problem.getGoalState();
        queue.offer(start);
        start.setParent(null);
        FoundSuccessors.put(start, start);
        VisitedSet.add(start); //adding the start to the visited nodes
        while (queue.size() != 0) {
            AState curr = queue.poll();
            //if we reached the goal
            if (curr.equals(goal)) {

                // add the goal to the visited list and update its parent
                VisitedSet.add(curr);
                goal.setParent((FoundSuccessors.get(curr)).getParent());
                //NumberOfNodesEvaluated++
                this.AddsEvaluatedNode();
                final_solution.BuildSolutionPath(goal);
                return final_solution;
            }

            //checking the neighbors of the curr state if there are still
            //neighbors to visit.

            int flag = 0; //flag to identify whether one of my neighbors is the goal
            ArrayList<AState> Neighbors = problem.getAllSuccessors(curr);

            // a loop to search if one of the neibors is the goal and if we are in case that need to consider the costs like BestFS
            for (int i = 0; i < Neighbors.size(); i++) {
                if (Neighbors.get(i).equals(goal)&& Flag==false) {
                    flag = 1;
                }
                //if we reached unvisited neighbor
                if (!(VisitedSet.contains(Neighbors.get(i))) || flag == 1) {

                    if (!FoundSuccessors.containsKey(Neighbors.get(i)) || flag == 1) {
                        // if one of the neighbors is the cost and we need to consider that- update the details  of the goal in the hashmap.
                        if (flag == 1 &&FoundSuccessors.containsKey(Neighbors.get(i)) ) {
                            if ((Neighbors.get(i).getCost() + curr.getCost()) < (FoundSuccessors.get(Neighbors.get(i)).getCost())) {
                                Neighbors.get(i).setCost(Neighbors.get(i).getCost() + curr.getCost());
                                Neighbors.get(i).setParent(curr);
                                FoundSuccessors.replace(Neighbors.get(i), Neighbors.get(i));

                            }
                        }
                        //if neighbor not in successors-> add it with the updated parent
                        else {
                            Neighbors.get(i).setParent(curr);
                            Neighbors.get(i).setCost(Neighbors.get(i).getCost() + curr.getCost());
                            FoundSuccessors.put(Neighbors.get(i), Neighbors.get(i));
                            //NumberOfNodesEvaluated++
                            this.AddsEvaluatedNode();
                            queue.offer(Neighbors.get(i));
                        }

                        //add the  neighbor to the visited set and update the queue
                        VisitedSet.add(Neighbors.get(i));
                        flag = 0;
                    }

                }
            }



        }
        //if there is no solution to the path - not suppose to happen return empty solution
        return final_solution;


    }
}

package algorithms.search;

import java.util.ArrayList;

public class Solution {

    private ArrayList<AState> SolutionPath; // a list that contains all the chain of AStates that are the solution by order.

    public Solution() {
        SolutionPath = new ArrayList<AState>() ;
    }

    /**
     * @param my_goal  the final goal we reached to.
     * update the SolutionPath list the solution path AStates by order.
     */
    public void BuildSolutionPath(AState my_goal) throws Exception {
            //if there is no solution to the maze
        if (my_goal==null){
            throw new Exception("Maze with no solution");  //was return
        }
        //following the parents of the AState from the goal to the StartState
            ArrayList<AState> upside_sol = new ArrayList<AState>();
            AState curr = my_goal;
            upside_sol.add(curr);
            while (curr.getParent()!=null){
                upside_sol.add(curr.getParent());
                curr = curr.parent;
             }

            //turnning upside down the path solution to be from the start to the end.
            for (int i = upside_sol.size()-1; i >=0; i--) {
                SolutionPath.add(upside_sol.get(i));

            }


    }

    public ArrayList<AState> getSolutionPath() {

        return SolutionPath;
    }
    /**
     * @param my_state the new state we want to add to the final solution list

     */
     public void AddToPath(AState my_state) throws Exception {
         NullArgCheck(my_state);
         SolutionPath.add(my_state);
    }

    protected void NullArgCheck(Object my_obj) throws Exception{
        if (my_obj==null){
            throw new Exception("Null argument received");
        }
    }
}
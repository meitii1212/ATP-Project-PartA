package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {


    //according to the interface in the lecture

    AState getStartState();

    AState getGoalState();

    ArrayList<AState> getAllSuccessors(AState my_state) throws Exception;

}

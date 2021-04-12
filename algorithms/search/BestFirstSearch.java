package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * this algorithm active the same as the BreadthFirstSearch but it usues priority queue
 * in order to find the cheepest path but it doesn't have to be the shortest path
 */
public class BestFirstSearch extends BreadthFirstSearch{

    public BestFirstSearch() throws Exception {
        super();
        setName("BestFirstSearch");
        AStateComperator my_comp = new AStateComperator(); //comparing by the cost of the AState
        queue = new PriorityQueue<AState>(my_comp);

    }





}

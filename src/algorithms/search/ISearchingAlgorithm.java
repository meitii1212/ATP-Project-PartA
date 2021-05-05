package algorithms.search;

public interface ISearchingAlgorithm {

    //according to the lecture
    Solution solve(ISearchable domain) throws Exception;
    String getName();
    int getNumberOfNodesEvaluated();
    long measureAlgorithmTimeMillis(ISearchable problem1) throws Exception;


}

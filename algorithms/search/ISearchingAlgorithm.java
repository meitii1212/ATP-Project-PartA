package algorithms.search;

public interface ISearchingAlgorithm {

    //according to the lecture
    Solution solve(ISearchable domain);
    String getName();
    int getNumberOfNodesEvaluated();


}

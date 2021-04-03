package algorithms.search;

public interface ISearchingAlgorithm {

    //according to the lecture
    Solution solve(Isearchable domain);
    String getName();
    int getNumberOfNodesEvaluated();


}

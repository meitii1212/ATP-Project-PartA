package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    private String Name; //algorithm name
    private int NumberOfNodesEvaluated; //number of visited states we pass through reaching the final solution
    private Isearchable problem;  //the specific problem we want to solve.
    //public abstract Solution solve(Isearchable domain);

    public ASearchingAlgorithm(Isearchable problem) {

        this.problem = problem;
    }


    //SETTER AND GETTER

    @Override
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return NumberOfNodesEvaluated;
    }

    public void setNumberOfNodesEvaluated(int numberOfNodesEvaluated) {
        NumberOfNodesEvaluated = numberOfNodesEvaluated;
    }

    public Isearchable getProblem() {
        return problem;
    }

    public void setProblem(Isearchable problem) {
        this.problem = problem;
    }
}

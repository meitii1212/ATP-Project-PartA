package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected String Name; //algorithm name
    protected int NumberOfNodesEvaluated; //number of visited states we pass through reaching the final solution
    protected Isearchable problem;  //the specific problem we want to solve.


    public ASearchingAlgorithm(Isearchable problem) {

        this.problem = problem;
    }

    public abstract Solution solve(Isearchable domain);

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

package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected String Name=null; //algorithm name
    protected int NumberOfNodesEvaluated=0; //number of visited states we pass through reaching the final solution
    protected ISearchable problem =null;  //the specific problem we want to solve.

    //CONSTRUCTOR
    public ASearchingAlgorithm() {
    }

    public abstract Solution solve(ISearchable problem1);


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

    // NumberOfNodesEvaluated++
    protected void AddsEvaluatedNode(){
        NumberOfNodesEvaluated = NumberOfNodesEvaluated + 1;
    }

    public ISearchable getProblem() {
        return problem;
    }

    public void setProblem(ISearchable problem) {
        this.problem = problem;
    }
}

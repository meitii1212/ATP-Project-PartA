package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected String Name=null; //algorithm name
    protected int NumberOfNodesEvaluated=0; //number of visited states we pass through reaching the final solution
    protected ISearchable problem =null;  //the specific problem we want to solve.

    //CONSTRUCTOR
    public ASearchingAlgorithm() {
    }

    public abstract Solution solve(ISearchable problem1);


    //calculating solving time
    public long measureAlgorithmTimeMillis (ISearchable problem1) {
        long first_time = System.currentTimeMillis();
        this.solve(problem1);
        long last_time = System.currentTimeMillis();
        return last_time - first_time;
    }

    protected void NullArgCheck(Object my_obj) throws Exception{
        if (my_obj==null){
            throw new Exception("Null argument received");
        }
    }
    //SETTER AND GETTER

    @Override
    public String getName(){
        return Name;
    }

    public void setName(String name){
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

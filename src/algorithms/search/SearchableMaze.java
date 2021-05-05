package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {

    //fields
    private AState StartState;
    private AState GoalState;
    private Maze originMaze;


    /**CONSTRUCTOR
     * @param origin the Maze we want to warp with the Adaptor pattern to get an Isearchable problem.
     */
    public SearchableMaze(Maze origin) throws Exception {
        NullArgCheck(origin);
        this.originMaze = origin;
        this.StartState =  new MazeState(origin.getStartPosition());
        this.GoalState = new MazeState(origin.getGoalPosition());


    }



    /**
     * @param my_state current state we want its neighbors list from the hash table
     * @return ArrayList<AState> neighbors or NULL if it doesnt have neighbors.
     */

    public ArrayList<AState> getAllSuccessors(AState my_state) throws Exception {
        NullArgCheck(my_state);
        return createlistN(((Position) my_state.getOrigin()).getRowIndex(), ((Position) my_state.getOrigin()).getColumnIndex());
    }

    //COORDINATES CHECKING FUNCTIONS

    private boolean isOnMap(int row1,int col1){
        return (row1>=0 && row1< originMaze.getRows() && col1>=0 && col1<originMaze.getColumns());
    }
    //searching zero on the right
    private boolean rightN(int row1,int col1){
        return (isOnMap(row1,col1+1)&&((this.originMaze.getMap()[row1][col1+1])==0) );
    }
    //searching zero on the left
    private boolean leftN(int row1,int col1){
        return ( isOnMap(row1,col1-1)&&((this.originMaze.getMap()[row1][col1-1])==0) );
    }

    //searching zero on the up side
    private boolean strightN(int row1,int col1){
        return ( isOnMap(row1-1,col1)&&((this.originMaze.getMap()[row1-1][col1])==0) );
    }

    //searching zero on the down side
    private boolean backN(int row1,int col1){
        return ( isOnMap(row1+1,col1)&&((this.originMaze.getMap()[row1+1][col1])==0) );
    }

    //searching zero on the up-right side
    private boolean strightrightN(int row1,int col1){
        return ( (strightN(row1,col1)&& rightN(row1-1,col1))|| (rightN(row1,col1)&&strightN(row1,col1+1)) );
    }

    //searching zero on the up-left side
    private boolean strightleftN(int row1, int col1){
        return ((strightN(row1,col1) && leftN(row1-1,col1))||(leftN(row1,col1) && strightN(row1,col1-1)));
    }

    //searching zero on the down-left side
    private boolean backleftN(int row1, int col1){
        return ((backN(row1,col1) &&leftN(row1+1,col1) )||( leftN(row1,col1)&&backN(row1,col1-1) ));
    }

    //searching zero on the down-right side
    private boolean backrightN(int row1, int col1){
        return ((backN(row1,col1) &&rightN(row1+1,col1) )||(rightN(row1,col1) &&backN(row1,col1+1) ));
    }


    /**
     * @param x the row index of a specific state
     * @param y the column index of a specific state
     * @return //final list of neighbors
     */
    private ArrayList<AState> createlistN(int x, int y) throws Exception {


        ArrayList<AState> final_list = new ArrayList<>();

        if (rightN(x,y)){
            AState right = new MazeState(new Position(x,y+1));
            right.setCost(10);
            final_list.add(right);

        }

        if (leftN(x,y)){
            AState left = new MazeState(new Position(x,y-1));
            left.setCost(10);
            final_list.add(left);
        }
        if (strightN(x,y)){
            AState stright = new MazeState(new Position(x-1,y));
            stright.setCost(10);
            final_list.add(stright);
        }
        if (backN(x,y)){
            AState back = new MazeState(new Position(x+1,y));
            back.setCost(10);
            final_list.add(back);
        }

        if (backleftN(x,y)){
            AState backleft = new MazeState(new Position(x+1,y-1));
            backleft.setCost(15);
            final_list.add(backleft);
        }

        if (strightleftN(x,y)){
            AState strightleft = new MazeState(new Position(x-1,y-1));
            strightleft.setCost(15);
            final_list.add(strightleft);
        }
        if (backrightN(x,y)){
            AState backright = new MazeState(new Position(x+1,y+1));
            backright.setCost(15);
            final_list.add(backright);
        }
        if (strightrightN(x,y)){
            AState strightright = new MazeState(new Position(x-1,y+1));
            strightright.setCost(15);
            final_list.add(strightright);
        }


        return final_list;
    }


    protected void NullArgCheck(Object my_obj) throws Exception{
        if (my_obj==null){
            throw new Exception("Null argument received");
        }
    }


    //GETTERS AND SETTERS:


    @Override
    public AState getStartState() {
        return StartState;
    }

    public void setStartState(AState startState) {
        StartState = startState;
    }

    @Override
    public AState getGoalState() {
        return GoalState;
    }

    public void setGoalState(AState goalState) throws Exception {

        NullArgCheck(goalState);
        GoalState = goalState;
    }


    public Maze getOriginMaze() {
        return originMaze;
    }

    public void setOriginMaze(Maze originMaze) throws Exception {

        NullArgCheck(originMaze);
        this.originMaze = originMaze;
    }
}

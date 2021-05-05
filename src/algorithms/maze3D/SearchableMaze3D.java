package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {

    //fields
    private AState StartState;
    private AState GoalState;
    private Maze3D originMaze;



    /**CONSTRUCTOR
     * @param origin the Maze we want to warp with the Adaptor pattern to get an Isearchable problem.
     */
    public SearchableMaze3D(Maze3D origin) throws Exception {
        NullArgCheck(origin);
        this.originMaze = origin;
        this.StartState =  new Maze3DState(origin.getStartPosition());
        this.GoalState = new Maze3DState(origin.getGoalPosition());

    }




    /**
     * @param my_state current state we want its neighbors list from the hash table
     * @return ArrayList<AState> neighbors or NULL if it doesnt have neighbors.
     */
    public ArrayList<AState> getAllSuccessors(AState my_state) throws Exception {
        NullArgCheck(my_state);
        return createlistN(((Position3D) my_state.getOrigin()).getDepthIndex(),((Position3D) my_state.getOrigin()).getRowIndex(), ((Position3D) my_state.getOrigin()).getColumnIndex());
    }

    //COORDINATES CHECKING FUNCTIONS

    private boolean isOnMap(int depth1, int row1,int col1){
        return (depth1>=0 && depth1< originMaze.getDepth() && row1>=0 && row1< originMaze.getRow() && col1>=0 && col1<originMaze.getColumn());
    }
    //searching zero on the right
    private boolean rightN(int depth1, int row1,int col1){
        return (isOnMap(depth1 ,row1,col1+1)&&((this.originMaze.getMap()[depth1][row1][col1+1])==0) );
    }
    //searching zero on the left
    private boolean leftN(int depth1, int row1,int col1){
        return ( isOnMap(depth1,row1,col1-1)&&((this.originMaze.getMap()[depth1][row1][col1-1])==0) );
    }

    //searching zero on the left
    private boolean upN(int depth1, int row1,int col1){
        return ( isOnMap(depth1+1,row1,col1)&&((this.originMaze.getMap()[depth1+1][row1][col1])==0) );
    }

    //searching zero on the left
    private boolean downN(int depth1, int row1,int col1){
        return ( isOnMap(depth1-1,row1, col1)&&((this.originMaze.getMap()[depth1-1][row1][col1])==0) );
    }

    //searching zero on the up side
    private boolean straitN(int depth1, int row1,int col1){
        return ( isOnMap(depth1,row1-1,col1)&&((this.originMaze.getMap()[depth1][row1-1][col1])==0) );
    }

    //searching zero on the down side
    private boolean backN(int depth1, int row1,int col1){
        return ( isOnMap(depth1,row1+1,col1)&&((this.originMaze.getMap()[depth1][row1+1][col1])==0) );
    }


    /**
     * @param x the row index of a specific state
     * @param y the column index of a specific state
     * @return //final list of neighbors
     */
    private ArrayList<AState> createlistN(int z, int x, int y) throws Exception {


        ArrayList<AState> final_list = new ArrayList<>();

        if (rightN(z ,x, y)){
            AState right = new Maze3DState((new Position3D(z,x,y+1)));
            final_list.add(right);

        }

        if (leftN(z ,x, y)){
            AState left = new Maze3DState((new Position3D(z ,x ,y-1)));
            final_list.add(left);
        }
        if (straitN(z ,x, y)){
            AState strait = new Maze3DState((new Position3D(z,x-1,y)));
            final_list.add(strait);
        }
        if (backN(z, x, y)){
            AState back = new Maze3DState(new Position3D(z,x+1,y));
            final_list.add(back);
        }
        if (upN(z, x, y)){
            AState up = new Maze3DState(new Position3D(z+1,x,y));
            final_list.add(up);
        }
        if (downN(z, x, y)){
            AState down = new Maze3DState(new Position3D(z-1,x,y));
            final_list.add(down);
        }

        return final_list;
    }


    protected void NullArgCheck(Object my_obj) throws Exception{
        if (my_obj==null){
            throw new Exception("Null argument received");
        }
    }

    //GETTERS AND SETTERS


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

    public Maze3D getOriginMaze() {
        return originMaze;
    }

    public void setOriginMaze(Maze3D originMaze) throws Exception {
        NullArgCheck(originMaze);
        this.originMaze = originMaze;
    }
}

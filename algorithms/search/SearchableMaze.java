package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Hashtable;

public class SearchableMaze implements Isearchable {

    //fields
    private AState StartState;
    private AState GoalState;
    private Hashtable<AState,ArrayList<AState>> hash; //an hash table that keep all the succesorrs list per a AState
    private Maze originMaze;


    /**CONSTRUCTOR
     * @param origin the Maze we want to warp with the Adaptor pattern to get an Isearchable problem.
     */
    public SearchableMaze(Maze origin) {
        this.originMaze = origin;
        this.StartState =  new MazeState(origin.getStartPosition());
        this.GoalState = new MazeState(origin.getGoalPosition());
        this.hash = initialHashTable(originMaze) ;

    }





    /**
     * @param my_maze the maze we whant to calculate the succesors for.
     * @return a hashtables (with no Null values) with mapping of AStates ArryList per each specific AState.
     */

    private Hashtable<AState,ArrayList<AState>> initialHashTable(Maze my_maze){
        Hashtable<AState,ArrayList<AState>> my_hash = new Hashtable<>();
        int rows = originMaze.getRows();
        int cols = originMaze.getColumns();

        //searching zero neighbors for each zero coordinate in the maze and adding it into the hash table


        ArrayList<AState> curr_list;

        //adding the first state neighbors list to the hash table.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if (originMaze.getMap()[i][j]==0){
                    AState curr_state = new MazeState(new Position(i,j));
                     curr_list = createlistN(i, j);
                     my_hash.put(curr_state,curr_list);

                }

            }

        }

        return my_hash;

    }


    /**
     * @param my_state current state we want its neighbors list from the hash table
     * @return ArrayList<AState> neighbors or NULL if it doesnt have neighbors.
     */
    public ArrayList<AState> getAllSuccessors(AState my_state){

        return hash.get(my_state);
    }

    //COORDINATES CHEKING FUNCTIONS

    private boolean isOnMap(int row1,int col1){
        return (row1>-1 && row1< originMaze.getRows() && col1>-1 && col1<originMaze.getColumns());
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
    private boolean upN(int row1,int col1){
        return ( isOnMap(row1+1,col1)&&((this.originMaze.getMap()[row1+1][col1])==0) );
    }

    //searching zero on the down side
    private boolean downN(int row1,int col1){
        return ( isOnMap(row1-1,col1)&&((this.originMaze.getMap()[row1-1][col1])==0) );
    }

    //searching zero on the up-right side
    private boolean uprightN(int row1,int col1){
        return ( (upN(row1,col1)&& rightN(row1-1,col1))|| (rightN(row1,col1)&&upN(row1,col1+1)) );
    }

    //searching zero on the up-left side
    private boolean upleftN(int row1, int col1){
        return ((upN(row1,col1) && leftN(row1-1,col1))||(leftN(row1,col1) && upN(row1,col1-1)));
    }

    //searching zero on the down-left side
    private boolean downleftN(int row1, int col1){
        return ((downN(row1,col1) &&leftN(row1+1,col1) )||( leftN(row1,col1)&&downN(row1,col1-1) ));
    }

    //searching zero on the down-right side
    private boolean downrightN(int row1, int col1){
        return ((downN(row1,col1) &&rightN(row1+1,col1) )||(rightN(row1,col1) &&downN(row1,col1+1) ));
    }


    /**
     * @param x the row index of a specific state
     * @param y the column index of a specific state
     * @return //final list of neighbors to the hashtable
     */
    private ArrayList<AState> createlistN(int x, int y){


        ArrayList<AState> final_list = new ArrayList<>();

        if (rightN(x,y)){
            AState right = new MazeState(new Position(x,y+1));
            right.setCost(1);
            final_list.add(right);

        }

        if (leftN(x,y)){
            AState left = new MazeState(new Position(x,y-1));
            left.setCost(1);
            final_list.add(left);
        }
        if (upN(x,y)){
            AState up = new MazeState(new Position(x-1,y));
            up.setCost(1);
            final_list.add(up);
        }
        if (downN(x,y)){
            AState down = new MazeState(new Position(x+1,y));
            down.setCost(1);
            final_list.add(down);
        }

        if (downleftN(x,y)){
            AState down = new MazeState(new Position(x+1,y-1));
            final_list.add(down);
        }

        if (upleftN(x,y)){
            AState down = new MazeState(new Position(x-1,y-1));
            final_list.add(down);
        }
        if (downrightN(x,y)){
            AState down = new MazeState(new Position(x+1,y+1));
            final_list.add(down);
        }
        if (uprightN(x,y)){
            AState down = new MazeState(new Position(x-1,y+1));
            final_list.add(down);
        }


        return final_list;
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

    public void setGoalState(AState goalState) {
        GoalState = goalState;
    }

    public Hashtable<AState, ArrayList<AState>> getHash() {
        return hash;
    }

    public void setHash(Hashtable<AState, ArrayList<AState>> hash) {
        this.hash = hash;
    }

    public Maze getOriginMaze() {
        return originMaze;
    }

    public void setOriginMaze(Maze originMaze) {
        this.originMaze = originMaze;
    }
}

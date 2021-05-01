package algorithms.mazeGenerators;

public class Maze {

    private int rows;
    private int columns;
    private int[][] map;
    private Position start; //start position
    private Position end;  //goal position


    /**
     * @param start1 - start position of the maze
     * @param end1 - goal position of the maze
     * @param map1 - 2D array that represents the maze.
     */
    public Maze(Position start1, Position end1, int[][] map1) throws Exception {
        NullArgCheck(start1);
        NullArgCheck(end1);
        NullArgCheck(map1);
        map=map1;
        start =start1;
        end=end1;

    }

    public  Maze(byte[] my_info) throws Exception {
        NullArgCheck(my_info);


    }

    /**
     * this function prints the maze matrix
     * 0-PASS
     * 1- WALL
     * S - start position
     * E- end position
     */
    public void print(){

        for(int i =0; i<this.rows;i++ ) {
            System.out.print("{ ");
            for (int j=0;j<this.columns;j++) {
                if (i==this.start.getRowIndex() && j==this.start.getColumnIndex()){
                    System.out.print("S ");
                    continue;
                }
                if (i==this.end.getRowIndex() && j==this.end.getColumnIndex()){
                    System.out.print("E ");
                    continue;
                }
                System.out.print(map[i][j] + " ");
            }

            System.out.println("}");
        }

    }


    protected void NullArgCheck(Object my_obj) throws Exception{
        if (my_obj==null){
            throw new Exception("Null argument received");
        }
    }

    //GETTERS AND SETTERS
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) throws Exception {
        if(rows <2){
            throw new Exception("Number of rows must be above 1");
        }
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) throws Exception {
        if(columns <2){
            throw new Exception("Number of columns must be above 1");
        }
        this.columns = columns;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) throws Exception {

        NullArgCheck(map);
        this.map = map;
    }

    public Position getStartPosition() {
        return start;
    }

    public void setStartPosition(Position start) throws Exception {
        NullArgCheck(start);
        this.start = start;
    }

    public Position getGoalPosition() {
        return end;
    }

    public void setGoalPosition(Position end) throws Exception {
        NullArgCheck(end);
        this.end = end;
    }

    public byte[] toByteArray(){

    }


}

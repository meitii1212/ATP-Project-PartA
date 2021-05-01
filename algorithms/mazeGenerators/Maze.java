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

    /**
     * c=counter that represents floor divition by 255
     * note that the maze starts at index 12
     * [c row , %rows , c cols, %cols, c startX, %startX, c startY, %startY, c endX, %endX, c endY, %endY,the maze bytes un compressed]
     * @return byte[] that represents all the details of the Maze
     */
    public byte[] toByteArray(){

    }


    public Maze(byte[] arr) throws Exception {
        NullArgCheck(arr);

        //set the dimentions
        this.setRows(arr[0]*255 + arr[1]);
        this.setColumns(arr[2]*255 + arr[3]);

        //set start and goal
        Position my_start = new Position(arr[4]*255 + arr[5],arr[6]*255 + arr[7]);
        Position my_end = new Position(arr[8]*255 + arr[9],arr[10]*255 + arr[11]);
        this.setStartPosition(my_start);
        this.setGoalPosition(my_end);

        //recovering the map
        int[][] my_map = new int[this.rows][this.columns];
        int index=12;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                my_map[i][j]=arr[index];
                index++;
            }
        }
        this.setMap(my_map);

    }

}

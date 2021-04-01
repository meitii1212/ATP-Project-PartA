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
    public Maze(Position start1, Position end1, int[][] map1){

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


    //GETTERS AND SETTERS
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Position getStartPosition() {
        return start;
    }

    public void setStartPosition(Position start) {
        this.start = start;
    }

    public Position getGoalPosition() {
        return end;
    }

    public void setGoalPosition(Position end) {
        this.end = end;
    }
}

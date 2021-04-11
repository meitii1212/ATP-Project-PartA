package algorithms.maze3D;

public class Maze3D {
    private int depth;
    private int row;
    private int column;
    private int[][][] map;
    private Position3D startPosition;//start position
    private Position3D goalPosition;//goal position


    public Maze3D(int[][][] map3D, Position3D start, Position3D end) throws Exception {
        NullArgCheck(map3D);
        NullArgCheck(start);
        NullArgCheck(end);
        this.map = map3D;
        this.startPosition = start;
        this.goalPosition = end;
    }
    protected void NullArgCheck(Object my_obj) throws Exception{
        if (my_obj==null){
            throw new Exception("Null argument received");
        }
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) throws Exception {
        if(depth<0){
            throw new Exception("Depth cannot be negative");
        }
        this.depth = depth;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) throws Exception {
        if(row<2){
            throw new Exception("Depth cannot be below 2 ");
        }
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int[][][] getMap() {
        return map;
    }

    public void setMap(int[][][] map) {
        this.map = map;
    }

    public Position3D getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position3D start) {
        this.startPosition = start;
    }

    public Position3D getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(Position3D end) {
        this.goalPosition = end;
    }

    public void print() {
        System.out.println("{");
        for (int depth = 0; depth < map.length; depth++) {
            for (int row = 0; row < map[0].length; row++) {
                System.out.print("{ ");
                for (int col = 0; col < map[0][0].length; col++) {
                    if (depth == startPosition.getDepthIndex() && row == startPosition.getRowIndex() && col == startPosition.getColumnIndex()) // if the position is the start - mark with S
                        System.out.print("S ");
                    else {
                        if (depth == goalPosition.getDepthIndex() && row == goalPosition.getRowIndex() && col == goalPosition.getColumnIndex()) // if the position is the goal - mark with E
                            System.out.print("E ");
                        else
                            System.out.print(map[depth][row][col] + " ");
                    }
                }
                System.out.println("}");
            }
            if (depth < map.length - 1) {
                System.out.print("---");
                for (int i = 0; i < map[0][0].length; i++)
                    System.out.print("--");
                System.out.println();
            }
        }
        System.out.println("}");
    }
}

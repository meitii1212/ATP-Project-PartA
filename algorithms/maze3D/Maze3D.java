package algorithms.maze3D;

public class Maze3D {
    private int depth;
    private int row;
    private int column;
    private int[][][] map;
    private Position3D start;//start position
    private Position3D end;//goal position


    public Maze3D(int[][][] map3D, Position3D start, Position3D end) throws Exception {
        NullArgCheck(map3D);
        NullArgCheck(start);
        NullArgCheck(end);
        this.map = map3D;
        this.start = start;
        this.end = end;
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
        return start;
    }

    public void setStartPosition(Position3D start) {
        this.start = start;
    }

    public Position3D getGoalPosition() {
        return end;
    }

    public void setGoalPosition(Position3D end) {
        this.end = end;
    }

    public void print(){
        System.out.println("{");
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j <row ; j++) {
                System.out.print("{ ");
                for (int k = 0; k < column; k++) {
                    if(i==start.getDepthIndex()&&j== start.getRowIndex()&&k==start.getColumnIndex()){
                        System.out.print("S ");
                        continue;
                    }
                    if(i==end.getDepthIndex()&&j== end.getRowIndex()&&k==end.getColumnIndex()){
                        System.out.print("E ");
                        continue;
                    }
                    System.out.print(map[i][j][k]+" ");
                }
                System.out.println("}");

            }
            System.out.println( "-".repeat(2*column+3));
        }
        System.out.println("}");
    }
}

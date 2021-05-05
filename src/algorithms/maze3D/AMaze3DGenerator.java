package algorithms.maze3D;

public abstract class AMaze3DGenerator implements IMaze3DGenerator {

    public long measureAlgorithmTimeMillis(int depth, int row, int column) throws Exception {

        long first_time = System.currentTimeMillis();
        this.generate(depth,row,column);
        long last_time = System.currentTimeMillis();
        return last_time- first_time;
    }

    /**DIMENTION LIMITS CHECKER:
     * @param depth11 - Depth value of the maze
     * @param rows11 - rows value of the maze
     * @param cols11 -  Columns value of the maze
     * @param limdep -minimal Depth value of the maze
     * @param limrow-minimal rows  value of the maze
     * @param limcol-minimal columns value of the maze
     * @throws Exception if one of the dimentions is out of range.
     */
    protected void Check3Dimentions(int depth11, int rows11, int cols11, int limdep, int limrow, int limcol) throws Exception {

            if (depth11 < limdep) {
                throw new Exception("Depth number is below required value");
            }
            if (rows11 < limrow) {
                throw new Exception("Rows number is below required value");
            }
            if (cols11 < limcol) {
                throw new Exception("Column value is below required value");
            }

    }

    protected void NullArgCheck(Object my_obj) throws Exception{
        if (my_obj==null){
            throw new Exception("Null argument received");
        }
    }
}

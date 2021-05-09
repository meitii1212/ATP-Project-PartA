package algorithms.mazeGenerators;

import java.io.Serializable;

public abstract class AMazeGenerator implements IMazeGenerator, Serializable {

        public long measureAlgorithmTimeMillis (int rows, int columns) throws Exception {
        long first_time = System.currentTimeMillis();
        this.generate(rows, columns);
        long last_time = System.currentTimeMillis();
        return last_time- first_time;
    }

/**DIMENTION LIMITS CHECKER:

 * @param rows11 - rows value of the maze
 * @param cols11 -  Columns value of the maze
 * @param limrow-minimal rows  value of the maze
 * @param limcol-minimal columns value of the maze
 * @throws Exception if one of the dimentions is  out of range.
 */
        protected void Check2Dimentions(int rows11, int cols11,int limrow, int limcol) throws Exception {

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

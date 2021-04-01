package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * @param rows number of the rows in the maze
     * @param columns number of the columns in the maze
     * @return returns a maze with simple constans solution with more random solutions
     * the constant solution is to go from the start all the way down and then all the way right.
     */
    public Maze generate(int rows, int columns) {

        int[][] my_map = new int[rows][columns];

        for (int i = 0; i <rows ; i++) {

            for (int j = 0; j < columns; j++) {

                if(j==0){
                    my_map[i][0]=0;
                    continue;
                }
                if(i==rows-1){
                    my_map[i][j]=0;
                    continue;
                }

                int rand=(int)(Math.random()*(2)+0);
                my_map[i][j]=rand;

            }

        }
        Position start_pos = new Position(0,0);
        Position end_pos = new Position(rows-1,columns-1);
        return new Maze(start_pos,end_pos,my_map);

    }
}

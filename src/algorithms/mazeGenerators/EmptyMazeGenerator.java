package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{
    /**
     * @param rows number of the rows in the maze
     * @param columns number of the columns in the maze
     * @return creating a maze of zeroes
     */
    @Override

    public Maze generate(int rows, int columns) throws Exception {
        this.Check2Dimentions(rows,columns,2,2);
        int[][] zero_map = new int[rows][columns];

        Position start_pos = new Position(0,0);
        Position end_pos = new Position(rows-1,columns-1);
        Maze my_maze = new Maze(start_pos,end_pos,zero_map);

        return my_maze;
    }
}

package algorithms.mazeGenerators;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {

    /**
     * @param rows1    number of rows of the Maze
     * @param columns1 number of columns of the Maze
     * @return return a Maze object that is created by Prim's algorithm for maze generating.
        the algurithm creates a MST - minimum spanning tree, and therefore we can approch from eache zero position
        to another zero position
        START POSITION - always {0,0}
        GOAL POSSITION - always on the bottom of the map and the exact place is demands in the diamensions of the wanted maze.
     */
    public Maze generate(int rows1, int columns1) {

        // parameters to represents wall and pass as int in the map.
        int wall = 1;
        int pass = 0;

        // initialize the maze mape full of walls.
        int[][] map = new int[rows1][columns1] ;
        for (int k = 0; k < rows1; k++) {
            Arrays.fill(map[k],1);
        }

        // a linked list that keeping all the next possible steps that available for the current maze situation.
        // the available next steps are positions that +2 Possitions from the borders of the current maze burders
        //that means that if we want to represent next step from {0,0} we can move only:
        //right to {0,2} by breaking the wall in {0,1} we'll represent that as {0,1,0,2}  (wall+potential dest)
        //down to{2,0} by breaking wall {1,0 => {1,0,2,0}

        final LinkedList<int[]> next_steps = new LinkedList<>();
        final Random random = new Random();
        int x = 0;
        int y = 0;
        next_steps.add(new int[]{x, y, x, y}); //starting from {0,0} position

        //as along as we have potential next step to do:
        while (!next_steps.isEmpty()) {
            final int[] f = next_steps.remove(random.nextInt(next_steps.size())); //we randomly choose oure next step
            //coordinations of current Position

            x = f[2];
            y = f[3];

            //if the current position is unvisited
            if (map[x][y] == wall) {
                map[f[0]][f[1]] = map[x][y] = pass; //break the wall

                //adding to the list the new possible next steps that available now and on the map and unvisited
                if (x >= 2 && map[x - 2][y] == wall)
                    next_steps.add(new int[]{x - 1, y, x - 2, y});
                if (y >= 2 && map[x][y - 2] == wall)
                    next_steps.add(new int[]{x, y - 1, x, y - 2});
                if (x < rows1 - 2 && map[x + 2][y] == wall)
                    next_steps.add(new int[]{x + 1, y, x + 2, y});
                if (y < columns1 - 2 && map[x][y + 2] == wall)
                    next_steps.add(new int[]{x, y + 1, x, y + 2});
            }
        }

        //indicator to know whether there are odd or even number of columns.
        //in the even case we want to avoid last row full of ones.

        //finding the correct case
        int flag=1; // if the columns is odd
        Position end = new Position(0, 0);
        Position start = new Position(0, 0);
        if (rows1 % 2 == 0) {
            flag=2; // if the columns is even


        }

        // finding the first zero in the correct row to set the end Position.
            for (int i = columns1 - 1; i >= 0; i--) {
                if (map[rows1 - flag][i] == pass) {  //the relevent zero will be in the row that mtches to the flag.
                    end.setRowIndex(rows1 - 1);
                    end.setColumnIndex(i);// update the end position if col even
                    break;
                }

            }


        //returning the maze
        Maze my_maze = new Maze(start,end,map);
        my_maze.setColumns(columns1);
        my_maze.setRows(rows1);
        return my_maze;
    }

}



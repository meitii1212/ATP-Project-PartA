package algorithms.maze3D;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class MyMaze3DGenerator extends AMaze3DGenerator {
    public Maze3D generate(int depth1,int row1, int column1) throws Exception {

        this.Check3Dimentions(0,2,2 ,depth1,row1,column1);
        // parameters to represents wall and pass as int in the map.
        int wall=1;
        int pass=0;

        // initialize the maze map full of walls.
        int[][][] map = new int[depth1][row1][column1] ;
        for (int i = 0; i < depth1; i++) {
            for (int k = 0; k < row1; k++) {
                Arrays.fill(map[i][k],1);
            }
        }

        // a linked list that keeping all the next possible steps that available for the current maze situation.
        // the available next steps are positions that +2 Possitions from the borders of the current maze burders
        //that means that if we want to represent next step from {0,0} we can move only:
        //right to {0,2} by breaking the wall in {0,1} we'll represent that as {0,1,0,2}  (wall+potential dest)
        //down to{2,0} by breaking wall {1,0 => {1,0,2,0}

        LinkedList<int[]> next_steps = new LinkedList<>();
        Random random = new Random();
        int z = 0;
        int x = 0;
        int y = 0;
        next_steps.add(new int[]{z, x, y, z, x, y}); //starting from {0,0} position

        //as along as we have potential next step to do:
        while (!next_steps.isEmpty()) {
            int[] f = next_steps.remove(random.nextInt(next_steps.size())); //we randomly choose oure next step
            //coordinations of current Position

            z = f[3];
            x = f[4];
            y = f[5];

            //if the current position is unvisited
            if (map[z][x][y] == wall) {
                map[f[0]][f[1]][f[2]] = map[z][x][y] = pass; //break the wall

                //adding to the list the new possible next steps that available now and on the map and unvisited
                if (z >= 2 && map[z - 2][x][y] == wall)
                    next_steps.add(new int[]{z - 1, x,y , z - 2, x,y});
                if (x >= 2 && map[z][x - 2][y] == wall)
                    next_steps.add(new int[]{z, x - 1,y, z, x - 2,y});
                if(y >=2 && map[z][x][y - 2] == wall)
                    next_steps.add(new int[]{z, x ,y - 1 , z, x ,y - 2});
                if (z < depth1 - 2 && map[z + 2][x][y] == wall)
                    next_steps.add(new int[]{z + 1, x, y, z + 2, x,y});
                if (x < row1 - 2 && map[z][x + 2][y] == wall)
                    next_steps.add(new int[]{z, x + 1, y, z ,x + 2,y});
                if (y < column1 - 2 && map[z][x][y + 2] == wall)
                    next_steps.add(new int[]{z, x , y + 1, z ,x ,y + 2});
            }
        }
        Position3D start=new Position3D(0,0,0);
        Position3D end=new Position3D(0,0,0);

        int flag=1;
        if(depth1%2==0){
            flag=2;
        }

        for (int i = row1-1; i >=0 ; i--) {
            if (map[depth1 - flag][i][i]==pass){
                if(flag==2){
                    map[depth1-1][i][i]=0;
                }
                end.setDepthIndex(depth1-1);
                end.setRowIndex(i);
                end.setColumnIndex(i);
                break;
            }
        }



        Maze3D my_maze = new Maze3D(map,start,end);
        my_maze.setDepth(depth1);
        my_maze.setRow(row1);
        my_maze.setColumn(column1);
        return my_maze;
    }


}

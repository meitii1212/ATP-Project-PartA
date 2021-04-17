package algorithms.test;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import java.util.ArrayList;


public class RunSearchOnMaze {
    public static void main(String[] args) throws Exception {
//        IMazeGenerator mg = new MyMazeGenerator();
//        Maze maze = mg.generate(20, 20);
//        SearchableMaze searchableMaze = new SearchableMaze(maze);
//        searchableMaze.getOriginMaze().print();
        int[][] map_1 = {{0,0,1,1}, {0,0,0,0}, {0,1,0,1}, {0,0,0,1}};
        Position start = new Position(0, 0);
        Position end = new Position(1, 3);
        Maze my_maze = new Maze(start, end, map_1);
        my_maze.setRows(4);
        my_maze.setColumns(4);
        SearchableMaze searchableMaze = new SearchableMaze(my_maze);
        BestFirstSearch best = new BestFirstSearch();
       // Solution solution = best.solve(searchableMaze);
        //ArrayList<AState> solutionPath = solution.getSolutionPath();
        solveProblem(searchableMaze,best);

        BreadthFirstSearch breadth = new BreadthFirstSearch();
        solveProblem(searchableMaze,breadth);
        Solution solution = best.solve(searchableMaze);
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        int cost_path=0;
        for (int i = 0; i < solutionPath.size(); i++) {
            cost_path+=solutionPath.get(i).getCost();
        }
        System.out.println(cost_path);
//
//        int[][] map_1 = {{1, 1, 1}, {1, 0,  1}, {0 ,0, 0}};
//        Position start = new Position(2, 0);
//        Position end = new Position(2, 2);
//        Maze my_maze;
//
//        {
//            try {
//                my_maze = new Maze(start, end, map_1);
//                my_maze.setRows(3);
//                my_maze.setColumns(3);
//                SearchableMaze searchableMaze = new SearchableMaze(my_maze);
//                solveProblem(searchableMaze, new BestFirstSearch());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        //solveProblem(searchableMaze, new BreadthFirstSearch());
//      //  solveProblem(searchableMaze, new DepthFirstSearch());
//        //solveProblem(searchableMaze, new BestFirstSearch());
   }
        private static void solveProblem (ISearchable domain, ISearchingAlgorithm searcher) throws Exception {
            //Solve a searching problem with a searcher
            Solution solution = searcher.solve(domain);
            System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
            //Printing Solution Path
            System.out.println("Solution path:");
            ArrayList<AState> solutionPath = solution.getSolutionPath();
            for (int i = 0; i < solutionPath.size(); i++) {
                System.out.println(String.format("%s. %s", i, solutionPath.get(i)));
            }
        }
}

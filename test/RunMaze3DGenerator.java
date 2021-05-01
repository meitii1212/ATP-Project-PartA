package test;
import java.util.ArrayList;

import algorithms.maze3D.IMaze3DGenerator;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.SearchableMaze3D;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

public class RunMaze3DGenerator {
    public static void main(String[] args) throws Exception {
        MyMaze3DGenerator gen = new MyMaze3DGenerator();
        //long time1 = gen.measureAlgorithmTimeMillis(100, 100, 100);
        //System.out.println(time1);
        Maze3D my_maze = gen.generate(2, 2, 2);
       // System.out.println(System.currentTimeMillis());
        my_maze.print();
      //  System.out.println(time1);

//        SearchableMaze3D searchableMaze = new SearchableMaze3D(my_maze);
//
//
//        //searchableMaze.getOriginMaze().print();
//        solveProblem(searchableMaze, new BreadthFirstSearch());
//        solveProblem(searchableMaze, new DepthFirstSearch());
//        solveProblem(searchableMaze, new BestFirstSearch());
    }

    private static void solveProblem(ISearchable domain, ISearchingAlgorithm
            searcher) throws Exception {
        System.out.println(System.currentTimeMillis());
        System.out.println(searcher.measureAlgorithmTimeMillis(domain));
        System.out.println(System.currentTimeMillis());
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
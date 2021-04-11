package algorithms.mazeGenerators;

public interface IMazeGenerator {

    Maze generate(int rows, int columns) throws Exception;
    long measureAlgorithmTimeMillis(int rows, int columns) throws Exception;

}

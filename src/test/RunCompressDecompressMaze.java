package test;
//import IO.MyCompressorOutputStream;
//import IO.MyDecompressorInputStream;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorOutputStream;
import IO.SimpleCompressorOutputStream;
import IO.SimpleDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import java.io.*;
import java.util.Arrays;

public class RunCompressDecompressMaze {
    public static void main(String[] args) throws Exception {
        String mazeFileName = "savedMaze1.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(100, 100); //Generate new maze
        try {
            // save maze to a file
            OutputStream out = new SimpleCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte savedMazeBytes[] = new byte[0];
        try {
            //read maze from file
            InputStream in = new SimpleDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Maze loadedMaze = new Maze(savedMazeBytes);
        boolean areMazesEquals =
                Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals));
        //maze should be equal to loadedMaze

        String mazeFileName1 = "savedMaze2.maze";
        AMazeGenerator mazeGenerator1 = new MyMazeGenerator();
        Maze maze1 = mazeGenerator.generate(4, 4); //Generate new maze
        try {
            // save maze to a file
            OutputStream out1 = new MyCompressorOutputStream((new FileOutputStream(mazeFileName1)));
            out1.write(maze1.toByteArray());
            out1.flush();
            out1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte savedMazeBytes1[] = new byte[0];
        try {
            //read maze from file
            InputStream in1 = new MyDecompressorOutputStream(new FileInputStream(mazeFileName1));
            savedMazeBytes1 = new byte[maze1.toByteArray().length];
            in1.read(savedMazeBytes1);
            in1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Maze loadedMaze1 = new Maze(savedMazeBytes1);
        boolean areMazesEquals1 =
                Arrays.equals(loadedMaze1.toByteArray(),maze1.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals1));

        System.out.println("ORIGINAL");
        maze1.print();

        System.out.println("DECOMPRESSED");
        loadedMaze1.print();
    }
}
package test;


import IO.*;
import Server.*;
import Client.*;
import algorithms.mazeGenerators.*;

import algorithms.search.*;


import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;



public class RunCommunicateWithServers
{
    public static void main(String[] args) throws IOException {
        // Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());

        // Starting servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();

        // Communicating with servers
        CommunicateWithServer_MazeGenerating();
        CommunicateWithServer_SolveSearchProblem();
        CommunicateWithServer_SolveSearchProblem();
        // Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
    }

    private static void CommunicateWithServer_MazeGenerating()
    {
        try
        {
            Client client = new Client(InetAddress.getByName("127.0.0.1"), 5400, new IClientStrategy()
            {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer)
                {
                    try
                    {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[] {50, 50};
                        toServer.writeObject(mazeDimensions);  // Send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject();  // Read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[3000 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/];  // Allocating byte[] for the decompressed maze
                        is.read(decompressedMaze);  // Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze);
                        maze.print();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }

    private static void CommunicateWithServer_SolveSearchProblem()
    {
        try
        {
            Client client = new Client(InetAddress.getByName("127.0.0.1"), 5401, new IClientStrategy()
            {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer)
                {
                    try
                    {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();
                        //Maze maze = mg.generate(50, 50);
                        Position start1 = new Position(0,0);
                        Position end1 = new Position(2,2);
                        int[][] map1= {{0,0,0},{1,1,0},{1,1,0}};
                        Maze maze = new Maze(start1,end1,map1);
                        maze.setColumns(3);
                        maze.setRows(3);
                        maze.print();
                        toServer.writeObject(maze); // Send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); // Read generated maze (compressed with MyCompressor) from server

                        //Print Maze Solution retrieved from the server
                        System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for (int i = 0; i < mazeSolutionSteps.size(); i++)
                        {
                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}
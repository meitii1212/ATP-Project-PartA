package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;

public class ServerStrategySolveSearchProblem implements IServerStrategy {


    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        //creating object streams
        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
        String tempDirectoryPath = System.getProperty("java.io.tmpdir"); //creating the directory for the solutions

        try {
            //as long as client needs service
            while (fromClient != null) {
                ASearchingAlgorithm my_algorithm = null;
                Maze maze_from_client= (Maze) fromClient.readObject();
                Thread.sleep(1000);
                byte [] byte_maze = maze_from_client.toByteArray();

                String algo_type = Configurations.getProperty("mazeSearchingAlgorithm");


                //ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );

                //MAZE solver - according to the configuration definition:
                switch(algo_type){
                    case "BreadthFirstSearch":
                        my_algorithm = new BreadthFirstSearch();
                        break;

                    case "BestFirstSearch":
                        my_algorithm = new BestFirstSearch();
                        break;

                    case "DepthFirstSearch":
                        my_algorithm = new DepthFirstSearch();
                        break;

                    default:
                        my_algorithm = new BreadthFirstSearch();
                        break;
                }

                //outputStream.write(byte_maze);

                //path to solution if exsists
                String my_path = tempDirectoryPath+"/"+ Arrays.toString(byte_maze)+".Solution";

                //SOLUTION EXSISTS
                if(new File(tempDirectoryPath,my_path).exists()){

                    FileInputStream file_stream = new FileInputStream(my_path);
                    ObjectInputStream object_stream = new ObjectInputStream(file_stream);
                    Solution my_sol = (Solution)object_stream.readObject();
                    toClient.writeObject(my_sol);
                    System.out.println("exist solution sent to client");
                }


                //CREATE A NEW SOLUTION
                else {

                    //solving the maze using ISerarchableMaze and the required algorithm.
                    SearchableMaze my_searchableMaze = new SearchableMaze(maze_from_client);
                    Solution my_sol = my_algorithm.solve(my_searchableMaze);

                    //create a new file
                    FileOutputStream file_out_stream = new FileOutputStream(my_path);
                    ObjectOutputStream objectOut = new ObjectOutputStream(file_out_stream);
                    objectOut.writeObject(my_sol);
                    objectOut.close();

                    //sending solution to client
                    toClient.writeObject(my_sol);
                    System.out.println("new solution sent to client");//TODO: delete prints
                }


            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
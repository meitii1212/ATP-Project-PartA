package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Hashtable;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    //mapping maze to his solution file
    private static Hashtable<byte[],String> my_hash = new Hashtable<byte[],String>();
    private static int file_num = 0;

    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
        //creating object streams
        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");

        try {
            //as long as client needs service
            while (fromClient != null) {
                ASearchingAlgorithm my_algorithm = null;
                Maze maze_from_client= (Maze) fromClient.readObject();
                Thread.sleep(1000);
                byte [] byte_maze = maze_from_client.toByteArray();

                String algo_type = Configurations.getProperty("mazeSearchingAlgorithm");


                byte[] a={1};
                byte[] b = {2};
                byte[] c = {3};

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );

                //MAZE solver - according to the configuration definition:
                switch(algo_type){
                    case "BreadthFirstSearch":
                        //the sighn of this algo is 1
                        outputStream.write( a );
                        my_algorithm = new BreadthFirstSearch();
                        break;

                    case "BestFirstSearch":
                        //the sighn of this algo is 2
                        outputStream.write( b );
                        my_algorithm = new BestFirstSearch();
                        break;

                    case "DepthFirstSearch":
                        //the sighn of this algo is 3
                        outputStream.write( c );
                        my_algorithm = new DepthFirstSearch();
                        break;

                    default:
                        //the sighn of this algo is 1
                        outputStream.write(a);
                        my_algorithm = new BreadthFirstSearch();
                        break;
                }
                outputStream.write(byte_maze);
                //concatenate the rest of the byte array information of the maze
                byte[] final_array = outputStream.toByteArray( );

                if(my_hash.containsKey(final_array)){
                    String sol_path = my_hash.get(final_array);
                    FileInputStream file_stream = new FileInputStream(sol_path);
                    ObjectInputStream object_stream = new ObjectInputStream(file_stream);
                    Solution my_sol = (Solution)object_stream.readObject();
                    toClient.writeObject(my_sol);
                    System.out.println("exist solution sent to client");
                }
                else {
                    SearchableMaze my_searchableMaze = new SearchableMaze(maze_from_client);
                    Solution my_sol = my_algorithm.solve(my_searchableMaze);
                    String my_path = tempDirectoryPath+"/"+file_num+".Solution";
                    file_num_plus();
                    //create a new file
                    FileOutputStream file_out_stream = new FileOutputStream(my_path);
                    ObjectOutputStream objectOut = new ObjectOutputStream(file_out_stream);
                    objectOut.writeObject(my_sol);
                    objectOut.close();


                    my_hash.put(final_array, my_path);
                    toClient.writeObject(my_sol);
                    System.out.println("new solution sent to client"+file_num);
                }



            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private synchronized int file_num_plus(){
       return file_num++;
    }
}
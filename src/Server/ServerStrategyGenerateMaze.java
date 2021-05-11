package Server;

import IO.SimpleCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{

    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {

        //creating object streams
        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

        try {
            //as long as client needs service
            //while (fromClient != null) {
                IMazeGenerator my_gen = null;

                //reading the dimentions from client
                int[] int_array_from_client = (int[]) fromClient.readObject();
                Thread.sleep(2000);

                String generatorType = Configurations.getProperty("mazeGeneratingAlgorithm");

                //MAZE GENERATING - according to the configuration definition:
                switch (generatorType) {
                    case "MyMazeGenerator":
                        my_gen = new MyMazeGenerator();
                        break;

                    case "SimpleMazeGenerator":
                        my_gen = new SimpleMazeGenerator();
                        break;

                    case "EmptyMazeGenerator":
                        my_gen = new EmptyMazeGenerator();
                        break;

                    default:
                        my_gen = new MyMazeGenerator();
                        break;

                }

                //MAZE CREATING:
                Maze my_maze = my_gen.generate(int_array_from_client[0], int_array_from_client[1]);
                byte[] maze_array = my_maze.toByteArray();
                //COMPRESSING

                //SENDING THE COMPRESSED MAZE-> to client
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                SimpleCompressorOutputStream compress = new SimpleCompressorOutputStream(byteArrayOutputStream);//>>
                compress.write(maze_array);
                compress.flush();
                toClient.writeObject(byteArrayOutputStream.toByteArray());
                System.out.println("maze byte array sent to client");
                compress.close();
                byteArrayOutputStream.close();

//                fromClient.close();
//                toClient.close();
                System.out.println("end of generate strategy");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // if the current Thread is interrupted we shutdown the threadpooland terminated the running threads
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
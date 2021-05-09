//package Server;
//
//import algorithms.mazeGenerators.Maze;
//import algorithms.search.*;
//
//import java.io.*;
//import java.util.Arrays;
//import java.util.Hashtable;
//
//public class ServerStrategySolveSearchProblem implements IServerStrategy {
//
//
//    @Override
//    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException {
//        //creating object streams
//        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
//        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
//        String tempDirectoryPath = System.getProperty("java.io.tmpdir"); //creating the directory for the solutions
//
//        try {
//            //as long as client needs service
//            while (fromClient != null) {
//                ASearchingAlgorithm my_algorithm = null;
//                Maze maze_from_client= (Maze) fromClient.readObject();
//                System.out.println("maze readed");
//                Thread.sleep(1000);
//                byte [] byte_maze = maze_from_client.toByteArray();
//
//                String algo_type = Configurations.getProperty("mazeSearchingAlgorithm");
//
//
//                //ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
//
//                //MAZE solver - according to the configuration definition:
//                switch(algo_type){
//                    case "BreadthFirstSearch":
//                        my_algorithm = new BreadthFirstSearch();
//                        break;
//
//                    case "BestFirstSearch":
//                        my_algorithm = new BestFirstSearch();
//                        break;
//
//                    case "DepthFirstSearch":
//                        my_algorithm = new DepthFirstSearch();
//                        break;
//
//                    default:
//                        my_algorithm = new BreadthFirstSearch();
//                        break;
//                }
//
//                //outputStream.write(byte_maze);
//
//                //path to solution if exsists
//                //String my_path = tempDirectoryPath+"\\"+Arrays.toString(byte_maze);
//                //String my_file_name = "maze@"+Arrays.toString(byte_maze);
//
//                String my_file_name = Arrays.toString(byte_maze)+".Maze";
//                //String my_file_name = "2.Maze";
//
//                //SOLUTION EXSISTS
//                if(new File(tempDirectoryPath,my_file_name).exists()){
//
//                    FileInputStream file_stream = new FileInputStream(tempDirectoryPath+"\\" + my_file_name);
//                    ObjectInputStream object_stream = new ObjectInputStream(file_stream);
//                    Solution my_sol = (Solution)object_stream.readObject();
//                    toClient.writeObject(my_sol);
//                    System.out.println("exist solution sent to client");
//                    return;
//                }
//
//                //CREATE A NEW SOLUTION
//                //solving the maze using ISerarchableMaze and the required algorithm.
//                SearchableMaze my_searchableMaze = new SearchableMaze(maze_from_client);
//                Solution my_sol = my_algorithm.solve(my_searchableMaze);
//                System.out.println("the problem");
//                //create a new file
//
//                FileOutputStream file_out_stream = new FileOutputStream(new File(tempDirectoryPath+"\\" + my_file_name));
//                System.out.println("the problem1");
//                ObjectOutputStream objectOut = new ObjectOutputStream(file_out_stream);
//                System.out.println("the problem2");
//                objectOut.writeObject(my_sol);
//                System.out.println("the problem3");
//                objectOut.close();
//
//                //sending solution to client
//                toClient.writeObject(my_sol);
//                System.out.println("new solution sent to client");//TODO: delete prints
//
////                SearchableMaze my_searchableMaze = new SearchableMaze(maze_from_client);
////
////                System.out.println("the problem2");
////                Solution sol = my_algorithm.solve(my_searchableMaze);
////                System.out.println("the problem3");
////                new ObjectOutputStream(new FileOutputStream(my_path)).writeObject(sol);
////                System.out.println("the problem4");
////                new ObjectOutputStream(outToClient).writeObject(sol);
////                System.out.println("the problem5");
////
//
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//    }
//}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

package Server;

        import algorithms.mazeGenerators.Maze;
        import algorithms.search.*;

        import java.io.*;
        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.Hashtable;
        import java.util.concurrent.ConcurrentHashMap;
        import java.util.concurrent.atomic.AtomicInteger;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    //mapping maze to his solution file
    private static volatile ConcurrentHashMap<byte[],String> my_hash=new ConcurrentHashMap<>();
    private static AtomicInteger file_num= new AtomicInteger();

    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        //creating object streams
        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        String hash_path = tempDirectoryPath + "/" +"MyHash.ConcurrentHashMap";
        if (new File(tempDirectoryPath, "MyHash.ConcurrentHashMap").exists()) {
            System.out.println(" i found the file of the table ");
            FileInputStream file_stream = new FileInputStream(hash_path);
            ObjectInputStream object_stream = new ObjectInputStream(file_stream);
            my_hash=(ConcurrentHashMap<byte[],String>)object_stream.readObject();
            System.out.println("the file exist");
            System.out.println(my_hash.containsValue(tempDirectoryPath+"/0"+".Solution"));
            //System.out.println(my_hash.keySet()[0]);
        }

        else{
            FileOutputStream file_out_stream1 = new FileOutputStream(hash_path);
            ObjectOutputStream objectOut1 = new ObjectOutputStream(file_out_stream1);
            objectOut1.writeObject(my_hash);
            System.out.println("creating hash file");
        }
        file_num.set(0);

        try {
            //as long as client needs service
            while (fromClient != null) {
                ASearchingAlgorithm my_algorithm = null;
                Maze maze_from_client= (Maze) fromClient.readObject();
                Thread.sleep(1000);
                byte [] byte_maze = maze_from_client.toByteArray();

                String algo_type = Configurations.getProperty("mazeSearchingAlgorithm");

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

                if(my_hash.containsKey(byte_maze)){
                    String sol_path = my_hash.get(byte_maze);
                    FileInputStream file_stream = new FileInputStream(sol_path);
                    ObjectInputStream object_stream = new ObjectInputStream(file_stream);
                    Solution my_sol = (Solution)object_stream.readObject();
                    toClient.writeObject(my_sol);
                    System.out.println("exist solution sent to client");
                    return;
                }
                else {
                    SearchableMaze my_searchableMaze = new SearchableMaze(maze_from_client);
                    Solution my_sol = my_algorithm.solve(my_searchableMaze);
                    String my_path = tempDirectoryPath+"/"+file_num.get()+".Solution";
                    file_num.incrementAndGet();
                    //create a new file
                    FileOutputStream file_out_stream = new FileOutputStream(my_path);
                    ObjectOutputStream objectOut = new ObjectOutputStream(file_out_stream);
                    objectOut.writeObject(my_sol);
                    objectOut.close();


                    my_hash.put(byte_maze, my_path);
                    //delete the content of the hashtable's file
                    PrintWriter pw = new PrintWriter("MyHash.HashMap");
                    pw.close();
//                    if(old_file.delete()){
//                        System.out.println("deleted the old table file ");
//                    }

                    //insert to the file the update hashtable
                    FileOutputStream file_out_stream1 = new FileOutputStream(hash_path);
                    ObjectOutputStream objectOut1 = new ObjectOutputStream(file_out_stream1);
                    objectOut1.writeObject(my_hash);

                    //write to client output stream the solution
                    toClient.writeObject(my_sol);
                    System.out.println("new solution sent to client"+file_num.get());
                }



            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}

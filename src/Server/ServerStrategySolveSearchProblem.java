package Server;

        import IO.MyCompressorOutputStream;
        import IO.MyDecompressorInputStream;
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
    private static volatile ConcurrentHashMap<String,String> my_hash=new ConcurrentHashMap<>();
    private static volatile AtomicInteger file_num= new AtomicInteger();

    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        //creating object streams
        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        String hash_path = tempDirectoryPath + "/" +"MyHash.ConcurrentHashMap";
        String atomic_num_path = tempDirectoryPath + "/" +"MyFileNum.int";
        if (new File(tempDirectoryPath, "MyHash.ConcurrentHashMap").exists()) {
            System.out.println(" i found the file of the table ");
            FileInputStream file_stream = new FileInputStream(hash_path);
            ObjectInputStream object_stream = new ObjectInputStream(file_stream);
            my_hash=(ConcurrentHashMap<String,String>)object_stream.readObject();
            System.out.println("the file exist");
            //System.out.println(my_hash.keySet()[0]);
        }

        else{
            FileOutputStream file_out_stream1 = new FileOutputStream(hash_path);
            ObjectOutputStream objectOut1 = new ObjectOutputStream(file_out_stream1);
            objectOut1.writeObject(my_hash);
            System.out.println("creating hash file");
        }
        if (new File(tempDirectoryPath, "MyFileNum.int").exists()) {
            FileInputStream stream_int = new FileInputStream(atomic_num_path);
            ObjectInputStream object_stream_int= new ObjectInputStream(stream_int);
            file_num.set((int)object_stream_int.readObject());
            System.out.println("the atomic integer exist");
        }
        else{
            file_num.set(0);
            FileOutputStream file_out_stream_atomic = new FileOutputStream(atomic_num_path);
            ObjectOutputStream objectOut_atomic = new ObjectOutputStream(file_out_stream_atomic);
            objectOut_atomic.writeObject(file_num.get());
            System.out.println("creating atomic num file");
        }

        try {
            //as long as client needs service
            while (fromClient != null) {
                ASearchingAlgorithm my_algorithm = null;
                Maze maze_from_client= (Maze) fromClient.readObject();
                OutputStream out1 = new ByteArrayOutputStream();

                MyCompressorOutputStream my_compress = new MyCompressorOutputStream(out1);
                my_compress.write(maze_from_client.toByteArray());
                byte[] my_byte_comp = ((ByteArrayOutputStream)(my_compress.getOut())).toByteArray();

                String comp_str = "";
                for (int i = 0; i < my_byte_comp.length ; i++) {
                    comp_str += "" + my_byte_comp[i];
                }

                Thread.sleep(1000);

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

                if(my_hash.containsKey(comp_str)){
                    String sol_path = my_hash.get(comp_str);
                    FileInputStream file_stream = new FileInputStream(sol_path);
                    ObjectInputStream object_stream = new ObjectInputStream(file_stream);
                    Solution my_sol = (Solution)object_stream.readObject();
                    toClient.writeObject(my_sol);
                    System.out.println("exist solution sent to client");
                    return;
                }
                else {
                    //solve the maze
                    SearchableMaze my_searchableMaze = new SearchableMaze(maze_from_client);
                    Solution my_sol = my_algorithm.solve(my_searchableMaze);

                    String my_path = tempDirectoryPath+"/"+file_num.get()+".Solution";
                    file_num.incrementAndGet();

                    //update in the Atomic integer file
                    PrintWriter pw_atomic_num = new PrintWriter("MyHash.HashMap");
                    pw_atomic_num.close();
                    //insert to the file the update hashtable
                    FileOutputStream file_out_stream_atomic_int = new FileOutputStream(atomic_num_path);
                    ObjectOutputStream objectOut1_atomic_int = new ObjectOutputStream(file_out_stream_atomic_int);
                    objectOut1_atomic_int.writeObject(my_hash);


                    //create a new file
                    FileOutputStream file_out_stream = new FileOutputStream(my_path);
                    ObjectOutputStream objectOut = new ObjectOutputStream(file_out_stream);
                    objectOut.writeObject(my_sol);
                    objectOut.close();


                    my_hash.put(comp_str, my_path);
                    //delete the content of the hashtable's file
                    PrintWriter pw = new PrintWriter("MyHash.HashMap");
                    pw.close();

                    //insert to the file the update hashtable
                    FileOutputStream file_out_stream1 = new FileOutputStream(hash_path);
                    ObjectOutputStream objectOut1 = new ObjectOutputStream(file_out_stream1);
                    objectOut1.writeObject(my_hash);

                    //write to client output stream the solution
                    toClient.writeObject(my_sol);
                    System.out.println("new solution sent to client "+file_num.get());
                }



            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}

package Server;

        import IO.*;

        import algorithms.mazeGenerators.*;
        import algorithms.search.*;
        import java.io.*;
        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.Hashtable;
        import java.util.concurrent.ConcurrentHashMap;
        import java.util.concurrent.atomic.AtomicInteger;
        import java.util.concurrent.locks.Lock;
        import java.util.concurrent.locks.ReentrantLock;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    //mapping maze to his solution file
    private static volatile ConcurrentHashMap<String,String> my_hash=new ConcurrentHashMap<>();
    private static volatile AtomicInteger file_num= new AtomicInteger();
    private static Lock TableLock = new ReentrantLock();
    private static Lock NumLock = new ReentrantLock();
    private static Lock WriteLock = new ReentrantLock();

    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) throws IOException, ClassNotFoundException {
        //creating object streams
        ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
        ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        String hash_path = tempDirectoryPath + "/" +"MyHash.ConcurrentHashMap";
        String atomic_num_path = tempDirectoryPath + "/" +"MyFileNum.int";

        TableLock.lock();
        //loading an existing hash table if an old server was activated before
        if (new File(tempDirectoryPath, "MyHash.ConcurrentHashMap").exists()) {
            //System.out.println(" i found the file of the table ");
            FileInputStream file_stream = new FileInputStream(hash_path);
            ObjectInputStream object_stream = new ObjectInputStream(file_stream);
            my_hash=(ConcurrentHashMap<String,String>)object_stream.readObject();
            //System.out.println("the file exist");
            object_stream.close();
            file_stream.close();

        }
        //creating a new hash table and a file to save it there
        else{
            FileOutputStream file_out_stream1 = new FileOutputStream(hash_path);
            ObjectOutputStream objectOut1 = new ObjectOutputStream(file_out_stream1);
            objectOut1.writeObject(my_hash);
            //System.out.println("creating hash file");
            objectOut1.close();
            file_out_stream1.close();
        }
        TableLock.unlock();

        NumLock.lock();
        //loading the current file num if an old server was activated before
        if (new File(tempDirectoryPath, "MyFileNum.int").exists()) {
            FileInputStream stream_int = new FileInputStream(atomic_num_path);
            ObjectInputStream object_stream_int= new ObjectInputStream(stream_int);
            file_num.set((int)object_stream_int.readObject());
            //System.out.println("the atomic integer exist");
            object_stream_int.close();
            stream_int.close();
        }

        //init the atomic integer that represents the file number and create a file to save it there
        else{
            file_num.set(0);
            FileOutputStream file_out_stream_atomic = new FileOutputStream(atomic_num_path);
            ObjectOutputStream objectOut_atomic = new ObjectOutputStream(file_out_stream_atomic);
            objectOut_atomic.writeObject(file_num.get());
            //System.out.println("creating atomic num file");
            objectOut_atomic.close();
            file_out_stream_atomic.close();
        }
        NumLock.unlock();
        try {
            //as long as client needs service
            //while (fromClient != null)
            {
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


                WriteLock.lock();
                //READING FROM HASH TABLE
                if(my_hash.containsKey(comp_str)){
                    String sol_path = my_hash.get(comp_str);
                    FileInputStream file_stream = new FileInputStream(sol_path);
                    ObjectInputStream object_stream = new ObjectInputStream(file_stream);
                    Solution my_sol = (Solution)object_stream.readObject();
                    toClient.writeObject(my_sol);
                    //System.out.println("exist solution sent to client");
                    object_stream.close();
                    file_stream.close();

                    WriteLock.unlock();
                    return;
                }
                else {
                    //WRITING NEW SOLUTION
                    //solve the maze
                    SearchableMaze my_searchableMaze = new SearchableMaze(maze_from_client);
                    Solution my_sol = my_algorithm.solve(my_searchableMaze);
                    //creating the path for the new solution
                    String my_sol_path = tempDirectoryPath+"/"+file_num.get()+".Solution";
                    file_num.incrementAndGet();

                    //update in the Atomic integer file
                    PrintWriter pw_atomic_num = new PrintWriter("MyFileNum.int");
                    pw_atomic_num.close();

                    //insert to the file the update int value of atomic int
                    FileOutputStream file_out_stream_atomic_int = new FileOutputStream(atomic_num_path);
                    ObjectOutputStream objectOut1_atomic_int = new ObjectOutputStream(file_out_stream_atomic_int);
                    objectOut1_atomic_int.writeObject(file_num.get());
                    objectOut1_atomic_int.close();
                    file_out_stream_atomic_int.close();


                    //create a new solution file
                    FileOutputStream file_out_stream = new FileOutputStream(my_sol_path);
                    ObjectOutputStream objectOut = new ObjectOutputStream(file_out_stream);
                    objectOut.writeObject(my_sol);
                    objectOut.close();
                    file_out_stream.close();

                    //updating the hash table file in directory
                    my_hash.put(comp_str, my_sol_path);
                    //delete the content of the hashtable's file
                    PrintWriter pw = new PrintWriter("MyHash.HashMap");
                    pw.close();

                    //insert to the file the update hashtable
                    FileOutputStream file_out_stream1 = new FileOutputStream(hash_path);
                    ObjectOutputStream objectOut1 = new ObjectOutputStream(file_out_stream1);
                    objectOut1.writeObject(my_hash);
                    objectOut1.close();
                    file_out_stream1.close();

                    //write to client output stream the solution
                    toClient.writeObject(my_sol);
                    //System.out.println("new solution sent to client "+file_num.get());
                    WriteLock.unlock();
                }



            }
            fromClient.close();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}

package Server;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private volatile boolean  stop;
    private ExecutorService threadPool; // Thread pool


    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) throws IOException {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.stop = false;

        // initialize thread pool with amount of threads as the user define in the config file //fff
        this.threadPool = Executors.newFixedThreadPool(Integer.parseInt(Configurations.getProperty("threadPoolSize"))); //fff
    }
    public void start(){new Thread(() -> {initServer();}).start();}


        public void initServer(){
        try {
            //INITIAL SERVER
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("Starting server at port = " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); //accepting the wainting client
                    System.out.println("Client accepted: " + clientSocket.toString());

                    // Insert the new task into the thread pool:
                    threadPool.submit(() -> {
                        handleClient(clientSocket);
                    });

                    // From previous lab:
                    // This thread will handle the new Client
                    //new Thread(() -> {
                    //    handleClient(clientSocket);
                    //}).start();

                } catch (SocketTimeoutException e){
                    System.out.println("Socket timeout");
                }
            }
            //threadPool.shutdown(); // do not allow any new tasks into the thread pool (not doing anything to the current tasks and running threads)
            threadPool.shutdownNow(); // do not allow any new tasks into the thread pool, and also interrupts all running threads (do not terminate the threads, so if they do not handle interrupts properly, they could never stop...)
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            //APPLAYING STRATEGY that recived from server-> to client
            strategy.ServerStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            System.out.println("Done handling client: " + clientSocket.toString());

            //the server close the connection with the client
            clientSocket.close();
        } catch (IOException e){
            System.out.println("IOException");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        System.out.println("Stopping server...");
        stop = true;
    }
}
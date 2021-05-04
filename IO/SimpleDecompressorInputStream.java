package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream {

    InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        //skipping the maze details
        for(int i=0; i<12; i++){
            b[i] = (byte)in.read();
        }
        int  counter =in.read();
        int index =12; //index in the out array

        //as long as the input stream is not over
        while(counter != -1 ){

            //at first add zeroes according to the counter
            while(counter != 0){
                b[index] =0;
                counter--;
                index++;
            }

            //change the state

            counter = in.read();
            //checking the input is not over
            if(counter ==-1 ){
                break;
            }
            while(counter != 0){
                b[index] =1;
                counter--;
                index++;
            }
            counter = in.read();

        }
        return 0;
    }

}
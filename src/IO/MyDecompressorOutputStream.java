package IO;

import java.io.IOException;
import java.io.InputStream;


public class MyDecompressorOutputStream extends InputStream {

    private InputStream in;

    //CONSTRUCTOR
    public MyDecompressorOutputStream(InputStream in)  {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }


    @Override
    public int read(byte[] b) throws IOException {
        //skipping the maze details

        for (int i = 0; i < 12; i++) {
            b[i] = (byte) in.read();
        }

        //calculating dimentions

        int modulu = in.read(); //index 12 just reading without writing
        int curr_compressed = in.read(); //index 13 start reading the maze
        int index = 13; //index in the out array
        int maze_length = CalcDimentions(b);

        //indexes we write in the final decompressed bytearray
        int start_index = 12;
        int end_index;

        //as long as the input stream is not over and we are in the compressed part
        for (int i = 0; i < maze_length -modulu; i++) {
            if (curr_compressed != -1) {
                TransToBinary(curr_compressed,b,start_index);
                start_index = start_index+8;
                curr_compressed= in.read();
            }
        }
        //finishing decompressing
        if (curr_compressed == -1){
            return 0;
        }

        b[start_index] = (byte) curr_compressed;
        start_index++;
        //writing the end of the byte[] that is uncompressed
        for (int i = 0; i < modulu-1; i++) {
            b[start_index] = (byte)in.read();
            start_index++;
        }

        return 0;
    }

    private void TransToBinary(int num, byte[] final_byte,int start_index){

        int k = num;

        for (int i = 0; i < 8; i++) {

            if (k != 0 ){
                final_byte[start_index] = (byte) (k%2);
                k= (byte) Math.floor(k/2); //changing k to the next divition by 2

            }

            // we reached modulu = zero therefore well put zero
            else{
                final_byte[start_index] = (byte)0;
            }

            start_index++;
        }


    }

    private int CalcDimentions(byte[] byte_arr){
        int rows = byte_arr[0]*255 + byte_arr[1];
        int cols =byte_arr[2]*255 + byte_arr[3];
        return rows*cols;
    }

}
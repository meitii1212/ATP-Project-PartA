package IO;

import java.io.IOException;
import java.io.InputStream;


public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    //CONSTRUCTOR
    public MyDecompressorInputStream(InputStream in) throws IOException {
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
        int rest =  in.read(); // readed 12 but byte is empty in 12
        int maze_length =  CalcDimentions(b);

        int curr_index =12;
        int curr_compressed;

        //as long as the input stream is not over and we are in the compressed part
        for (int i = 0; i < (maze_length-rest)/8; i++) {

            curr_compressed = in.read(); //reading the next int

            if (curr_compressed != -1) {
                TransToBinary(curr_compressed, b, curr_index);
                curr_index = curr_index + 8;
            }

            // if we finished -not seppose to heppend
            else{
                //System.out.println("finished in the for loop");
                return 0;
            }

        }


        curr_compressed = in.read(); //reading the next int

        for (int i = 0; i < rest; i++) {

            if (curr_compressed != -1) {
                b[curr_index] = (byte) curr_compressed;
                curr_index++;
                curr_compressed = in.read();
            }
        }


        return 0;

    }
    private int CalcDimentions(byte[] byte_arr){
        int rows = (byte_arr[0]*255) + byte_arr[1];
        int cols =(byte_arr[2]*255) + byte_arr[3];
        int mulp = rows*cols;
        return mulp;
    }

    private void PrintRest() throws IOException {

        int curr_compressed1 = (int) in.read();
        while (curr_compressed1 != -1) {
            System.out.println(curr_compressed1);
            curr_compressed1 = (int) in.read();

        }
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
                if(start_index <final_byte.length){
                    final_byte[start_index] = (byte)0;
                }
            }

            start_index++;
        }


    }

}
package IO;

import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public SimpleCompressorOutputStream(OutputStream os) {
        out =os;
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    @Override
    public void write(byte[] b_array) throws IOException {
        //skipping the details of the maze
        int i;
        for(i=0; i<12; i++){
            out.write(b_array[i]);
        }
        // we assume that the array is starting with zero.
        int sum =0;
        while (i < b_array.length) {
            //start to count the number of 0
            while (i < b_array.length && b_array[i] == 0) {

                //check if the number of 0 bigger than 254
                if(sum>254){
                    //if yes stop counting and count if there is more 0
                    break;
                }
                sum++;
                i++;
            }
            out.write(sum);
            sum = 0;
            //next count the number of 1
            while (i < b_array.length &&b_array[i] == 1) {
                if (sum > 254) {
                    //if yes stop counting and count if there is more 1
                    break;
                }
                sum++;
                i++;

            }
            out.write(sum);
            sum = 0;
        }

    }
}
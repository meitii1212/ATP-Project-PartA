package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream os) {
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
        int flag = 1;
        int my_decimal1=0;
        for (i = 0; i < 12; i++) {
            out.write(b_array[i]);
        }
        //write to output stream the rest of the bytearray's size - division 8
        int rest = (b_array.length - 12) % 8;
        out.write((byte) rest);


        while (i < b_array.length) {
            //start to count the number of 0
            if (i < b_array.length - rest) {
                for (int j = 0; j < 8; j++) {
                    //check if we not in the rest part of the byte array
                    if (i < b_array.length - rest) {
                        //my_str = String.valueOf((int)b_array[i]);
                        my_decimal1 += (int)b_array[i]*(Math.pow(2, j));
                        i++;
                    }
                    else {
                        flag = 0;
                        break;
                    }
                }
            }
            else{
                flag=0;
            }
            //if the for loop finish without break;
            if (flag == 1) {
                out.write((byte) my_decimal1);
                my_decimal1 = 0;
            }
            else {
                out.write(b_array[i]);
                i++;
            }
        }
    }

}
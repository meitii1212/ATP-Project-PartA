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

        // we assume that the array is starting with zero.
        int sum =0;
        int i=0;
        while (i < b_array.length) {
            while (b_array[i] == 0) {
                if()
                sum++;
                i++;
            }
            out.write(sum);
            sum = 0;
            while (b_array[i] == 1) {
                sum++;
                i++;
            }
            out.write(sum);
            sum = 0;
        }

    }
}

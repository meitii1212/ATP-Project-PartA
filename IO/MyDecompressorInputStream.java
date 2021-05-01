package IO;

import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }
}

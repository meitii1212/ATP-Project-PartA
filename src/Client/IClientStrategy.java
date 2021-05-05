package Client;

import java.io.InputStream;
import java.io.OutputStream;

public interface IClientStrategy {
    public void applyStrategy(InputStream inFromServer, OutputStream outToServer);

}
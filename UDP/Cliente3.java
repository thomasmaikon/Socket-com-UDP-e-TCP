import java.io.IOException;
import java.net.DatagramSocket;

public class Cliente3 {
    public static void main(String[] args) throws IOException {
        int portaRecebe = 3;
        int portaEnvia = 4;
        int sizeBuffer = 256;

        DatagramSocket socket = new DatagramSocket(portaRecebe);

        Cliente c = new Cliente(socket, sizeBuffer, portaEnvia, Cliente.TipoCliente.Recebe);

        Thread t = new Thread(c);

        t.start();
    }
}

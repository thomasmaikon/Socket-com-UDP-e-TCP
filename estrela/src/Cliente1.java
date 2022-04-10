import java.io.IOException;
import java.net.DatagramSocket;

public class Cliente1 {
    public static void main(String[] args) throws IOException {
        int portaRecebe = 1;
        int portaEnvia = 8081;
        int sizeBuffer = 256;

        DatagramSocket socket = new DatagramSocket(portaRecebe);

        Cliente c1 = new Cliente(socket, sizeBuffer, portaEnvia, Cliente.TipoCliente.Envia);

        Thread t = new Thread(c1);

        t.start();
    }
}

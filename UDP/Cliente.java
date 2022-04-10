import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Cliente implements Runnable{

    public static enum TipoCliente{
        Envia,
        Recebe
    };

    private int porta;
    private int size;

    private DatagramSocket socket;

    private TipoCliente cliente;

    public Cliente(DatagramSocket socket, int sizeBuffer, int portaEnvio, TipoCliente cliente){
        this.socket = socket;
        this.size = sizeBuffer;
        this.porta = portaEnvio;
        this.cliente = cliente;
    }

    public void clienteEnviaRecebe(DatagramSocket socket, int sizeBuffer, int portaEnvio) throws IOException {
        Scanner leitor = new Scanner(System.in);
        System.out.println("Envie uma mensagem: ");
        String msg = leitor.nextLine();

        envia(socket, msg, portaEnvio);
        recebe(socket, sizeBuffer);

        leitor.close();
    }

    public void clienteRecebeEnvia(DatagramSocket socket, int sizeBuffer, int portaEnvio) throws IOException {
        byte buffer[] = recebe(socket, sizeBuffer).getData();
        String msg = new String(buffer);
        msg = msg.replaceAll("[^0-9.]","");

        System.out.println( "Somando id:" + ProcessHandle.current().pid() + " com resposta -> " + msg);
        long l = ProcessHandle.current().pid() + Integer.parseInt(msg);

        envia(socket, String.valueOf(l), portaEnvio);
    }

    public void envia(DatagramSocket socket, String msg, int porta) throws IOException {
        System.out.println("Enviando msg -> " + msg);

        InetAddress address = InetAddress.getByName("localhost");

        byte buffer[] = msg.getBytes(StandardCharsets.UTF_8);

        DatagramPacket pacoteEnvio = new DatagramPacket( buffer, buffer.length, address, porta);

        socket.send(pacoteEnvio);
    }

    public DatagramPacket recebe(DatagramSocket socket, int sizeBuffer) throws IOException {
        byte bufferRecebe[] = new byte[sizeBuffer];

        DatagramPacket pacoteRecebe = new DatagramPacket(bufferRecebe, bufferRecebe.length);

        socket.receive(pacoteRecebe);

        bufferRecebe = pacoteRecebe.getData();

        String msg = new String(bufferRecebe);
        msg = msg.replaceAll("[^0-9.]","");

        System.out.println("Recebendo resposta: " + msg);

        return pacoteRecebe;
    }

    @Override
    public void run() {
        try {
            if(cliente.equals(TipoCliente.Envia)){
                clienteEnviaRecebe(socket, size, porta);
            }else{
                clienteRecebeEnvia(socket, size, porta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

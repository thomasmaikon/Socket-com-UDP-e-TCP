import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class Cliente implements Runnable{

    private long id;

    private String ip;
    private int porta;

    private String ipEnvio;
    private int portaEnvio;

    private ArrayList<Integer> msg = new ArrayList<Integer>();
    private boolean lerTeclado = false;

    private ServerSocket socketServidor;

    public Cliente(String ip, int porta, long id) throws IOException {
        this.ip = ip;
        this.porta = porta;
        this.id = id;
        socketServidor = new ServerSocket(porta);
    }

    public void setConexao(String ipEnvio, int porta) {
        this.ipEnvio = ipEnvio;
        this.portaEnvio = porta;
    }

    public void setLerTeclado(boolean lerTeclado){
        this.lerTeclado = lerTeclado;
    }

    @Override
    public void run() {
        System.out.println("Host: "+ ip + " Aguardando conexão do cliente "+ipEnvio+ " ...");

        Envia enviaDado = new Envia(ip, portaEnvio, msg, id)
                .setLerTeclado(lerTeclado);

        Recebe recebeDado = new Recebe(socketServidor, msg, id);

        Thread enviar = new Thread(enviaDado);
        Thread receber = new Thread(recebeDado);

        enviar.start();
        receber.start();


        try {
            enviar.join();
            receber.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

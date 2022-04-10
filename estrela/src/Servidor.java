import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Servidor {
    public static void main(String[] args) throws IOException {

        int sizeBuffer = 256;
        int portaConexao = 8081;

        HashMap<Integer, Integer> tabelaConexoes = new HashMap<Integer, Integer>();

        System.out.println("Iniciando servidor...");
        DatagramSocket socketServidor = new DatagramSocket(portaConexao);

        byte[] bufferRecebimento = new byte[sizeBuffer];

        DatagramPacket pacoteRecebimento = new DatagramPacket(bufferRecebimento, bufferRecebimento.length);

        while(true){
            socketServidor.receive(pacoteRecebimento);

            if(!tabelaConexoes.containsKey(pacoteRecebimento.getPort())){ // se a conexao nao existe ele cria
                System.out.println("Adicionando conexao a tabela de conexoes");
                tabelaConexoes.put(pacoteRecebimento.getPort(), pacoteRecebimento.getPort()); // eu salvo a porta proque e tudo local
            }

            bufferRecebimento = pacoteRecebimento.getData();

            String mensagem = new String(bufferRecebimento);

            mensagem =  mensagem.replaceAll("[^0-9a-zA-Z. ]","");

            if(mensagem.equals("connect")){ // tratamento para todos que querem se conectar ao servidor terem permissao
                continue;
            }else if(mensagem.contains("all")){
                for ( Integer conexao : tabelaConexoes.values()){
                    if(conexao != pacoteRecebimento.getPort()){
                        String msg = mensagem.split("all")[1];
                        envia(socketServidor, msg, conexao);
                    }
                }
                continue;
            }

            //System.out.println("Servidor recebeu a mensagem -> " + mensagem);

            String info[] = mensagem.split(" ");

            String destino = info[0];
            String conteudo = info[1];

            if(tabelaConexoes.containsKey(Integer.parseInt(destino))){
                envia(socketServidor, conteudo, tabelaConexoes.get(Integer.parseInt(destino)));
            }else{
                System.out.println("Conexao nao existe no momento");
            }
        }
    }

    public static void envia(DatagramSocket socket, String msg, int porta) throws IOException {
        System.out.println("Enviando msg para -> " + porta);

        InetAddress address = InetAddress.getByName("localhost");

        byte buffer[] = msg.getBytes(StandardCharsets.UTF_8);

        DatagramPacket pacoteEnvio = new DatagramPacket( buffer, buffer.length, address, porta);

        socket.send(pacoteEnvio);
    }

}

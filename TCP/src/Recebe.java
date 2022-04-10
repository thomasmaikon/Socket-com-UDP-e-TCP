import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class Recebe implements Runnable {

    private long id;

    private ServerSocket conexao;
    private ArrayList<Integer> msg;

    public Recebe(ServerSocket conexaoRecebeDados, ArrayList<Integer> message, long id){
        conexao  = conexaoRecebeDados;
        this.msg = message;
        this.id = id;
    }

    @Override
    public void run() {

        try {
            synchronized (msg){
                Socket connection = conexao.accept();
                System.out.println("[Rceber Dados] - Estabelecendo conexao com : " + connection.getLocalAddress().getHostAddress());

                Scanner s = new Scanner(connection.getInputStream());

                while(s.hasNext() == false);

                String mensagemRecebida = s.nextLine();

                System.out.println("[Rceber Dados] - Mensagem recebida -> " + mensagemRecebida);
                msg.add(Integer.parseInt(mensagemRecebida));
                s.close();
                connection.close();
            }

        } catch (IOException e) {
            System.out.println("Nao recebi dados...");
            e.printStackTrace();
       }
    }
}

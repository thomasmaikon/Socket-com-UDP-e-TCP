import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

public class Envia implements Runnable{

    private String ip;
    private int porta;
    private long id;
    private boolean lerTeclado;

    private ArrayList<Integer> msg ;

    public Envia(String ip, int porta, ArrayList<Integer> message, long id) {
        this.ip = ip;
        this.porta = porta;
        this.msg = message;
        this.id = id;
    }

    public Envia setLerTeclado(boolean ler){
        this.lerTeclado = ler;
        return this;
    }

    @Override
    public void run() {
        boolean aguardandoConexao = true;

        while (aguardandoConexao){
            try{
                Socket socket = new Socket(ip, porta);
                System.out.println("[Enviar Dados] - O cliente "+ ip +" conectou-se na porta " + socket.getPort());

                if(lerTeclado){
                    Scanner teclado = new Scanner(System.in);
                    PrintStream saida = new PrintStream(socket.getOutputStream());

                    System.out.println("[Enviar Dados] - Digite uma mensagem: ");
                    String mensagem = teclado.nextLine();

                    while ( isNumber(mensagem) == false){
                        System.out.println("Nao e um numero, tente novamente");
                        mensagem = teclado.nextLine();
                    }

                    saida.println(mensagem);

                    saida.close();
                    teclado.close();
                    socket.close();
                    lerTeclado = false;
                    aguardandoConexao = false;
                }else {

                    synchronized (msg) {
                        if(msg.size()>0 ){
                            System.out.println("[Enviar Dados] - Enviando mensagem:  "+ id + "+" + msg.get(0));
                            PrintStream saida = new PrintStream(socket.getOutputStream());
                            saida.println(id + msg.get(0));

                            saida.close();
                            socket.close();
                            aguardandoConexao = false;
                            lerTeclado = false;
                        }
                    }
                }

            }catch (Exception e){
                System.out.println("[Enviar Dados] - \tConexao nao funcionou, tentando novamente em alguns instantes...");
            }
        }
    }

    public boolean isNumber(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

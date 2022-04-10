import java.io.IOException;

public class MainP1 {
    public static void main(String[] args) throws IOException {
        Cliente c1 = new Cliente("127.0.0.1",11,1);

        c1.setLerTeclado(true);

        c1.setConexao(null,22);

        Thread t1 = new Thread(c1);

        t1.start();
    }
}

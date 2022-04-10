import java.io.IOException;

public class MainP4 {
    public static void main(String[] args) throws IOException {

        Cliente c4 = new Cliente("127.0.0.4",44,4);

        c4.setConexao("127.0.0.1",11);

        Thread t4 = new Thread(c4);

        t4.start();
    }
}

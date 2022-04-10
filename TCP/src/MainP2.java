import java.io.IOException;

public class MainP2 {
    public static void main(String[] args) throws IOException {

        Cliente c2 = new Cliente("127.0.0.2",22,2);

        c2.setConexao("127.0.0.1",33);

        Thread t2 = new Thread(c2);

        t2.start();
    }
}

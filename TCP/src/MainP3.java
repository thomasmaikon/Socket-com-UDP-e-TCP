import java.io.IOException;

public class MainP3 {
    public static void main(String[] args) throws IOException {

        Cliente c3 = new Cliente("127.0.0.3",33,3);

        c3.setConexao("127.0.0.1",44);

        Thread t3 = new Thread(c3);

        t3.start();
    }
}

package trabalho;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class TrataCliente {

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public TrataCliente(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Cliente " + socket.getRemoteSocketAddress() + " se conectou");
        this.in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void fechar() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Erro ao encerrar Socket");
        }

    }

    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    public Publicacao getPublicacao() throws Exception {
        try {
            return new Publicacao(in.readLine(), in.readLine());
        } catch (IOException e) {
            return null;
        }
    }

    public boolean enviarPublicacao(Publicacao publicacao) {
        out.println(publicacao.getLogin());
        out.println(publicacao.getPostagem());
        return true;
    }

}

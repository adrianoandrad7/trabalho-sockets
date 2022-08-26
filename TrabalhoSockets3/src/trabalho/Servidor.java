package trabalho;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static final int porta = 15500;
    private ServerSocket socket_servidor;
    private final List<TrataCliente> cliente = new LinkedList<>();

    public void iniciar() throws IOException {
        socket_servidor = new ServerSocket(porta);
        System.out.println("Servidor Iniciado na porta " + porta);
        clienteConexaoLoop();

    }

    private void clienteConexaoLoop() throws IOException {

        while (true) {
            TrataCliente socket_cliente = new TrataCliente(socket_servidor.accept());
            cliente.add(socket_cliente);
            new Thread(() -> {
                try {
                    clientePublicacaoLoop(socket_cliente);
                } catch (Exception ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        }
    }

    private void clientePublicacaoLoop(TrataCliente socket_cliente) throws Exception {
        try {
            Publicacao publicacao;
            while ((publicacao = socket_cliente.getPublicacao()) != null) {
                if ("sair".equalsIgnoreCase(publicacao.getLogin())) {
                    return;
                }
                System.out
                        .println("Publicação recebida de @" + publicacao.getLogin() + ": " + publicacao.getPostagem());
                Timeline(socket_cliente, publicacao);
            }
        } finally {
            socket_cliente.fechar();
        }
    }

    private void Timeline(TrataCliente sender, Publicacao publicacao) throws Exception {

        for (TrataCliente socket_cliente : cliente) {
            if (!sender.equals(socket_cliente)) {
                socket_cliente.enviarPublicacao(publicacao);
            }
        }
    }

    public static void main(String[] args) {

        try {
            Servidor servidor = new Servidor();
            servidor.iniciar();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o servidor");
        }

        System.out.println("Servidor finalizado");
    }

}

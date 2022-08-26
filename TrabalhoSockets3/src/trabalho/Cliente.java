package trabalho;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Runnable {

    private static final String endereco = "127.0.0.1";
    private TrataCliente socket_cliente;
    private final Scanner in;

    public Cliente() {
        in = new Scanner(System.in);
    }

    public void inicia() throws IOException, Exception {
        try {
            socket_cliente = new TrataCliente(new Socket(endereco, Servidor.porta));
            System.out.println("Cliente conectado ao servidor " + endereco + ":" + Servidor.porta);
            new Thread(this).start();
            envia_mensagem_loop();

        } finally {
            socket_cliente.fechar();
        }
    }

    private void envia_mensagem_loop() throws IOException, Exception {

        Publicacao publicacao = new Publicacao();
        int i =0;

        do {

            if(i == 0){
                System.out.println("Digite o seu login: ou digite 'sair' para sair");
                 publicacao.setLogin(in.nextLine());
            }

            if (publicacao.getLogin().equalsIgnoreCase("sair")) {
                break;
            }
            
            System.out.print("Postagem: ");
            publicacao.setPostagem(in.nextLine());
            socket_cliente.enviarPublicacao(publicacao);

            i++;

            if(publicacao.getPostagem().equalsIgnoreCase("sair")){
                i = 0;
            }

        } while (true);

    }
  
   @Override
    public void run() {
        Publicacao publicacao;
        try {

            while ((publicacao = socket_cliente.getPublicacao()) != null) {

                System.out.println("Nova Publicação de @" + publicacao.getLogin() + ": " + publicacao.getPostagem());
                System.out.print("Postagem: ");

            }

        } catch (Exception ex) {

            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws Exception {

        try {

            Cliente cliente = new Cliente();
            cliente.inicia();

        } catch (IOException ex) {

            System.out.println("Erro ao iniciar cliente");

        }

        System.out.println("Cliente finalizado");

    }

}


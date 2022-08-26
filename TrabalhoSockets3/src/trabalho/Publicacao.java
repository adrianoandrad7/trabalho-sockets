package trabalho;

public class Publicacao {
    
    private String login;
    private String postagem;

    Publicacao() {
    }

    public Publicacao(String login, String postagem) {
        super();
        this.login = login;
        this.postagem = postagem;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPostagem() {
        return postagem;
    }

    public void setPostagem(String postagem) {
        this.postagem = postagem;
    }

    @Override
    public String toString() {
        return "@" + login + "\n\n" + postagem;
    }
    
}

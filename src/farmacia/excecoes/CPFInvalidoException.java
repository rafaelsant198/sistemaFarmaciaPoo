package farmacia.excecoes;

public class CPFInvalidoException extends Exception {
    private static final long serialVersionUID = 1L;

    public CPFInvalidoException(String mensagem) {
        super(mensagem);
    }
}

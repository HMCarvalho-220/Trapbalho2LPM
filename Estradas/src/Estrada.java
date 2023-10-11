public class Estrada {
    private Cidade origem;
    private Cidade destino;
    private int peso;

    public Estrada(Cidade origem, Cidade destino, int peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public void setOrigem(Cidade origem) {
        this.origem = origem;
    }

    public Cidade getDestino() {
        return destino;
    }

    public void setDestino(Cidade destino) {
        this.destino = destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
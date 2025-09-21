public class Pilha {
    private Pixel[] dados;
    private int topo;

    public Pilha(int capacidade) {
        dados = new Pixel[capacidade];
        topo = -1;
    }

    //Método pra empilhar um pixel
    public void push(Pixel p) {
        dados[++topo] = p;
    }

    //Método pra desempilhar um pixel
    public Pixel pop() {
        return dados[topo--];
    }

    public boolean isEmpty() {
        return topo == -1;
    }
}
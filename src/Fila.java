public class Fila {

    private Node primeiro;
    private Node ultimo;

    private static class Node {
        Pixel valor;
        Node proximo;

        Node(Pixel valor) {
            this.valor = valor;
        }
    }

    // Adiciona um item no final da fila
    public void enqueue(Pixel valor) {
        Node novo = new Node(valor);
        if (ultimo != null) {
            ultimo.proximo = novo;
        }
        ultimo = novo;
        if (primeiro == null) {
            primeiro = ultimo;
        }
    }

    // Remove o item do início da fila
    public Pixel dequeue() {
        if (primeiro == null) return null;

        Pixel valor = primeiro.valor;
        primeiro = primeiro.proximo;

        if (primeiro == null) {
            ultimo = null;
        }

        return valor;
    }

    // Verifica se a fila está vazia
    public boolean isEmpty() {
        return primeiro == null;
    }
}
public class Fila {

    private Node primeiro; //Ponteiro pro primeiro ná da fila
    private Node ultimo; //Ponteiro pro último ná da fila

    //Classe que representa um nó da fila
    private static class Node {
        Pixel valor; 
        Node proximo; 

        //Construtor do nó que recebe um pixel como valor
        Node(Pixel valor) {
            this.valor = valor;
        }
    }

    // Adiciona um item no final da fila
    public void enqueue(Pixel valor) {
        Node novo = new Node(valor);
        if (ultimo != null) {
            ultimo.proximo = novo; // Faz o último nó apontar para o novo nó
        }
        ultimo = novo;
        if (primeiro == null) {
            primeiro = ultimo;
        }
    }

    // Remove o item do início da fila
    public Pixel dequeue() {
        if (primeiro == null) return null;

        Pixel valor = primeiro.valor; //Armazena o valor do primeiro nó pra retornar depois
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
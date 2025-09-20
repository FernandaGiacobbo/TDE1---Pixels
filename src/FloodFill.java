import java.awt.image.BufferedImage;
import java.awt.Color;

public class FloodFill {
    public static void preencherComPilha(BufferedImage img, int x, int y, Color novaCor) {
        int largura = img.getWidth();
        int altura = img.getHeight();
        Color corOriginal = new Color(img.getRGB(x, y));

        if (corOriginal.equals(novaCor)) return;

        Pilha pilha = new Pilha(10000);
        pilha.push(new Pixel(x, y));

        while (!pilha.isEmpty()) {
            Pixel p = pilha.pop();
            int px = p.getX();
            int py = p.getY();

            if (px < 0 || px >= largura || py < 0 || py >= altura) continue;

            Color corAtual = new Color(img.getRGB(px, py));

            if (corAtual.equals(corOriginal)) {
                img.setRGB(px, py, novaCor.getRGB());

                pilha.push(new Pixel(px + 1, py));
                pilha.push(new Pixel(px - 1, py));
                pilha.push(new Pixel(px, py + 1));
                pilha.push(new Pixel(px, py - 1));
            }
        }
    }


    public static void preencherComFila(BufferedImage img, int x, int y, Color novaCor) {
        int largura = img.getWidth();
        int altura = img.getHeight();
        Color corOriginal = new Color(img.getRGB(x, y));

        if (corOriginal.equals(novaCor)) return;

        Fila fila = new Fila();
        fila.enqueue(new Pixel(x, y));

        while (!fila.isEmpty()) {
            Pixel p = fila.dequeue();
            int px = p.getX();
            int py = p.getY();

            // Verifica se o pixel está dentro dos limites da imagem
            if (px < 0 || px >= largura || py < 0 || py >= altura) continue;

            Color corAtual = new Color(img.getRGB(px, py));

            // Verifica se o pixel tem a cor original
            if (corAtual.equals(corOriginal)) {
                // Pinta o pixel
                img.setRGB(px, py, novaCor.getRGB());

                // Enfileira os vizinhos (cima, baixo, esquerda, direita)
                fila.enqueue(new Pixel(px + 1, py));
                fila.enqueue(new Pixel(px - 1, py));
                fila.enqueue(new Pixel(px, py + 1));
                fila.enqueue(new Pixel(px, py - 1));
            }
        }
    }
}
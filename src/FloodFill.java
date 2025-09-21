import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;

public class FloodFill {
    // contador pra controlar os frames
    private static int contador = 0;
    
    // Método para resetar o contador
    public static void resetarContador() {
        contador = 0;
    }

    // Algoritmo que preenche usando Pilha
    public static void preencherComPilha(BufferedImage img, int x, int y, Color novaCor) {

        //Dimensões da imagem
        int largura = img.getWidth();
        int altura = img.getHeight();
        Color corOriginal = new Color(img.getRGB(x, y));

        if (corOriginal.equals(novaCor)) return;

        Pilha pilha = new Pilha(10000);
        pilha.push(new Pixel(x, y));

        //Enquanto a pilha não estiver vazia, o programa segue preenchendo os pixels
        while (!pilha.isEmpty()) {
            Pixel p = pilha.pop();
            int px = p.getX();
            int py = p.getY();

            if (px < 0 || px >= largura || py < 0 || py >= altura) continue;

            Color corAtual = new Color(img.getRGB(px, py));

            if (corAtual.equals(corOriginal)) {
                img.setRGB(px, py, novaCor.getRGB());

                //Salva pixel por pixel
                salvarFrame(img, contador);
                contador++;

                //Empilha os pixels vizinhos
                pilha.push(new Pixel(px + 1, py));
                pilha.push(new Pixel(px - 1, py));
                pilha.push(new Pixel(px, py + 1));
                pilha.push(new Pixel(px, py - 1));
            }
        }
        
        //Salva o frame final
        salvarFrame(img, contador);
    }

    //Algoritmo que preenche usando fila
    public static void preencherComFila(BufferedImage img, int x, int y, Color novaCor) {
        int largura = img.getWidth();
        int altura = img.getHeight();
        Color corOriginal = new Color(img.getRGB(x, y));

        if (corOriginal.equals(novaCor)) return;

        Fila fila = new Fila();
        fila.enqueue(new Pixel(x, y));

        //Enquanto a fila não estiver vazia, o programa segue preenchendo os pixels
        while (!fila.isEmpty()) {
            Pixel p = fila.dequeue();
            int px = p.getX();
            int py = p.getY();

            //Verifica se os pixels estão dentro dos limites da imagem
            if (px < 0 || px >= largura || py < 0 || py >= altura) continue;

            Color corAtual = new Color(img.getRGB(px, py));

            if (corAtual.equals(corOriginal)) {
                img.setRGB(px, py, novaCor.getRGB());

                //Salva pixel por pixel
                salvarFrame(img, contador);
                contador++;

                //Enfileira os pixels vizinhos
                fila.enqueue(new Pixel(px + 1, py));
                fila.enqueue(new Pixel(px - 1, py));
                fila.enqueue(new Pixel(px, py + 1));
                fila.enqueue(new Pixel(px, py - 1));
            }
        }
        
        // Salva o frame final
        salvarFrame(img, contador);
    }

    // Método para salvar cada frame
    private static void salvarFrame(BufferedImage img, int index) {
        try {
            // Cria a pasta "frames" se não existir
            File pasta = new File("frames");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            //Cria o arqiuivo com nome formatado e salva ela em formato PNG
            File output = new File(String.format("frames/frame_%05d.png", index));
            ImageIO.write(img, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Obtém valor atual dos contador de frames
    public static int getContador() {
    return contador;
}
}
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;

public class FloodFill {
    // contador global para controlar os frames
    private static int contador = 0;

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

                // salva frame a cada 100 pixels pintados
                if (contador % 100 == 0) {
                    salvarFrame(img, contador);
                }
                contador++;
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

            if (px < 0 || px >= largura || py < 0 || py >= altura) continue;

            Color corAtual = new Color(img.getRGB(px, py));

            if (corAtual.equals(corOriginal)) {
                img.setRGB(px, py, novaCor.getRGB());

                fila.enqueue(new Pixel(px + 1, py));
                fila.enqueue(new Pixel(px - 1, py));
                fila.enqueue(new Pixel(px, py + 1));
                fila.enqueue(new Pixel(px, py - 1));

                if (contador % 100 == 0) {
                    salvarFrame(img, contador);
                }
                contador++;
            }
        }
    }

    private static void salvarFrame(BufferedImage img, int index) {
        try {
            File pasta = new File("frames");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            File output = new File(String.format("frames/frame_%05d.png", index));
            ImageIO.write(img, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

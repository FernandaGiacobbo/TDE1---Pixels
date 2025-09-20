import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        BufferedImage imagem = null;
        int x = 50, y = 50;
        Color novaCor = Color.BLUE;

        try {
            // 1. Carregar imagem
            imagem = ImageIO.read(new File("entrada.png"));

            // 2. Aplicar o algoritmo Flood Fill com Fila (ou Pilha, se quiser)
            FloodFill.preencherComFila(imagem, x, y, novaCor);

            // 3. Salvar a imagem resultante
            ImageIO.write(imagem, "png", new File("saida.png"));

            System.out.println("Imagem processada com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao carregar ou salvar imagem: " + e.getMessage());
        }
    }
}

import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        BufferedImage imagem = null;
        int x = 50, y = 50; // ponto inicial
        Color novaCor = Color.BLUE; // cor para preencher

        try {

            // 1. Carregar imagem
            imagem = ImageIO.read(new File("entrada.png"));

            // 2. Aplicar o algoritmo Flood Fill (Fila ou Pilha)
            FloodFill.preencherComFila(imagem, x, y, novaCor);
            // FloodFill.preencherComPilha(imagem, x, y, novaCor);

            // 3. Salvar a imagem final
            ImageIO.write(imagem, "png", new File("saida.png"));

            System.out.println("Imagem processada com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao carregar ou salvar imagem: " + e.getMessage());
        }
    }
}

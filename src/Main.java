import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        BufferedImage imagem = null;
        int x = 50, y = 50; // ponto inicial
        Color novaCor = Color.RED; // cor para preencher

        try {
            // Limpa a pasta de frames antes de começar
            limparPastaFrames();
            
            // 1. Carregar imagem
            imagem = ImageIO.read(new File("entrada.png"));
            
            // Resetar contador antes de começar
            FloodFill.resetarContador();

            // 2. Aplicar o algoritmo Flood Fill (Fila ou Pilha)
            FloodFill.preencherComFila(imagem, x, y, novaCor);
            // FloodFill.preencherComPilha(imagem, x, y, novaCor);

            // 3. Salvar a imagem final
            ImageIO.write(imagem, "png", new File("saida.png"));

            // 4. Criar GIF com os frames
            GifCreator.criarGif("frames", "animacao.gif", 100); // 100ms entre frames
            
            System.out.println("Imagem processada e GIF criado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao carregar ou salvar imagem: " + e.getMessage());
        }
    }
    
    // Método para limpar a pasta de frames antes de começar
    private static void limparPastaFrames() {
        File pasta = new File("frames");
        if (pasta.exists() && pasta.isDirectory()) {
            File[] arquivos = pasta.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.getName().endsWith(".png")) {
                        arquivo.delete();
                    }
                }
            }
        }
    }
}
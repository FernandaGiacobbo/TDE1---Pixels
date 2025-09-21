import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage; //Manipulação de imagens em memória
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GifCreator {

    //Método para criar GIF a partir dos frames salvos
    public static void criarGif(String pastaFrames, String nomeSaida, int delayMs) {
        try {
            File dir = new File(pastaFrames);

            //Lista de arquivos png que estão na pasta
            File[] arquivos = dir.listFiles((d, name) -> name.endsWith(".png"));
            
            if (arquivos == null || arquivos.length == 0) {
                System.out.println("Nenhum frame encontrado.");
                return;
            }

            //Ordena frames pelo nome pra ter a sequencia correta
            java.util.Arrays.sort(arquivos);

            //Cria saída pro arquivo final do GIF
            ImageOutputStream output = new FileImageOutputStream(new File(nomeSaida));

            //Escritor de sequencia do GIF que usa o primeiro frame como base
            GifSequenceWriter writer = new GifSequenceWriter(output, 
                ImageIO.read(arquivos[0]).getType(), delayMs, true);

            // Adiciona todos os frames na sequência do GIF
            for (File f : arquivos) {
                BufferedImage img = ImageIO.read(f);
                writer.writeToSequence(img);
            }

            //Finaliza e fecha os recursos
            writer.close();
            output.close();

            //Captura erros de entrada e saída se ocorrer
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
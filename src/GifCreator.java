import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GifCreator {
    public static void criarGif(String pastaFrames, String nomeSaida, int delayMs) {
        try {
            File dir = new File(pastaFrames);
            File[] arquivos = dir.listFiles((d, name) -> name.endsWith(".png"));
            
            if (arquivos == null || arquivos.length == 0) {
                System.out.println("Nenhum frame encontrado.");
                return;
            }

            // Ordena frames pelo nome
            java.util.Arrays.sort(arquivos);

            // Cria saída
            ImageOutputStream output = new FileImageOutputStream(new File(nomeSaida));
            GifSequenceWriter writer = new GifSequenceWriter(output, 
                ImageIO.read(arquivos[0]).getType(), delayMs, true);

            // Adiciona todos os frames
            for (File f : arquivos) {
                BufferedImage img = ImageIO.read(f);
                writer.writeToSequence(img);
            }

            writer.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

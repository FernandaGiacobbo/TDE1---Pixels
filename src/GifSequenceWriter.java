import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;
import java.awt.image.*;
import java.io.IOException;

public class GifSequenceWriter {

    //Escritor de imagens, parâmetros de escrita e metadados
    protected ImageWriter gifWriter;
    protected ImageWriteParam imageWriteParam;
    protected IIOMetadata imageMetaData;

    //Configura o escritor de GIF com os parâmetros necessários
    public GifSequenceWriter(
            ImageOutputStream outputStream,
            int imageType,
            int timeBetweenFramesMS,
            boolean loopContinuously) throws IOException {

        //Pega o primeiro escritor de imagens que suporte GIF
        gifWriter = ImageIO.getImageWritersBySuffix("gif").next();

        //Obtem os parâmetros do padrão de escrita 
        imageWriteParam = gifWriter.getDefaultWriteParam();

        ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);

        imageMetaData = gifWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);

        String metaFormatName = imageMetaData.getNativeMetadataFormatName();

        IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);
        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");

        //Configura os atributos de controle do frame
        graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
        graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(timeBetweenFramesMS / 10));
        graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

        //Cria nós de extensão de aplicação pro controle do loop
        IIOMetadataNode appEntensionsNode = getNode(root, "ApplicationExtensions");
        IIOMetadataNode appExtensionNode = new IIOMetadataNode("ApplicationExtension");

        appExtensionNode.setAttribute("applicationID", "NETSCAPE");
        appExtensionNode.setAttribute("authenticationCode", "2.0");

        //Bytes do controle de looping
        byte[] appExtensionBytes = new byte[]{0x1, (byte) (loopContinuously ? 0 : 1), 0};

        appExtensionNode.setUserObject(appExtensionBytes);
        appEntensionsNode.appendChild(appExtensionNode);
        imageMetaData.setFromTree(metaFormatName, root);

        //Configura fluxo de saída e prepara a sequência de escrita
        gifWriter.setOutput(outputStream);
        gifWriter.prepareWriteSequence(null);
    }

    //Adiciona um frame à sequência do GIF
    public void writeToSequence(RenderedImage img) throws IOException {
        gifWriter.writeToSequence(new IIOImage(img, null, imageMetaData), imageWriteParam);
    }

    //Finaliza a sequência e fecha os recursos
    public void close() throws IOException {
        gifWriter.endWriteSequence();
    }

    //Método auxiliar para obter um nó específico ou cria-lo se não existir
    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        int nNodes = rootNode.getLength();

        //Procura pelo nó com o nome especificado
        for (int i = 0; i < nNodes; i++) {
            if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
                return (IIOMetadataNode) rootNode.item(i);
            }
        }

        //Criar um novo nó se não encontrar
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return node;
    }
}
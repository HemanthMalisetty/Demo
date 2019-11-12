package utils;

import sun.awt.image.BufferedImageGraphicsConfig;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.lang.Math;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.AlphaComposite;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Map;
import java.util.HashMap;



public class image {

    public static void resize(String inputImageFullName, String outputImageFullName, double scaleFactor)
            throws IOException {

        long scaledWidth, scaledHeight;

        // reads input image
        File inputFile = new File(inputImageFullName);
        BufferedImage inputImage = ImageIO.read(inputFile);

        int imgWidth = inputImage.getWidth(null);
        int imgHeight = inputImage.getHeight(null);

        scaledWidth = Math.round( (double) imgWidth * scaleFactor);
        scaledHeight = Math.round( (double) imgHeight * scaleFactor);

        // creates output image
        BufferedImage scaledImage = new BufferedImage((int) scaledWidth,
                (int) scaledHeight, inputImage.getType());

        // scales the input image to the output image
        // Graphics2D g2d = scaledImage.createGraphics();
        // g2d.drawImage(inputImage, 0, 0, (int) scaledWidth, (int) scaledHeight, null);
        // g2d.dispose();

        // writes to output file
        // ImageIO.write(scaledImage, "png", new File(outputImageFullName));
        ImageIO.write(resize(inputImage, (int) scaledWidth, (int) scaledHeight), "png", new File(outputImageFullName));
    }


    /**
     * High-Quality Image Resize with Java
     * http://www.componenthouse.com/article-20
     *
     * @author Hugo Teixeira


    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("c:\\picture.jpg"));
            ImageIO.write(resizeTrick(image, 24, 24), "png", new File("c:\\picture3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    private static BufferedImage resize(BufferedImage image, int width, int height) {
        int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    private static BufferedImage resizeTrick(BufferedImage image, int width, int height) {
        image = createCompatibleImage(image);
        image = resize(image, 100, 100);
        image = blurImage(image);
        return resize(image, width, height);
    }

    public static BufferedImage blurImage(BufferedImage image) {
        float ninth = 1.0f/9.0f;
        float[] blurKernel = {
                ninth, ninth, ninth,
                ninth, ninth, ninth,
                ninth, ninth, ninth
        };

        Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
        map.put(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        map.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints hints = new RenderingHints(map);
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
        return op.filter(image, null);
    }

    private static BufferedImage createCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        Graphics2D g2 = result.createGraphics();
        g2.drawRenderedImage(image, null);
        g2.dispose();
        return result;
    }



}

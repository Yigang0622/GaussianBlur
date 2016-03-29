import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        BufferedImage img = FileUtil.loadImg("1.jpg");
        assert img != null;
        System.out.println("Image Loaded: "+img.getWidth()+"x"+img.getHeight());
        new BlurEffect(2,img).getBluredImg();
        //
    }
}

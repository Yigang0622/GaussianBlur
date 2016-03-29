import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by mike on 3/27/16.
 */
public class FileUtil {

    public static BufferedImage loadImg(String fileName){
        //String path = "/Users/Mike/Desktop/";
        String path = "C:\\Users\\Mike\\Desktop\\";
        File imgFile = new File(path+fileName);
        try {
            return ImageIO.read(imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

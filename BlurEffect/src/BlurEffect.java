import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by mike on 3/27/16.
 */
public class BlurEffect {

    private int blurRadius = 1;
    private BufferedImage image;
    private double[][] weightArr;

    public BlurEffect(int blurRadius,BufferedImage image){
        this.blurRadius = blurRadius;
        this.image = image;
        weightArr = new double[blurRadius*2+1][blurRadius*2+1];
        calculateWeightMatrix();
        getFinalWeightMatrix();
    }

    private double getR(int x,int y){
        int rgb = image.getRGB(x, y);
        int r = (rgb & 0xff0000) >> 16;
        return r;
    }

    private double getG(int x,int y){
        int rgb = image.getRGB(x, y);
        int g = (rgb & 0xff00) >> 8;
        return g;
    }

    private double getB(int x,int y){
        int rgb = image.getRGB(x, y);
        int b = (rgb & 0xff);
        return b;
    }

    private double[][] getColorMatrix(int x, int y, int whichColor){

        int startX = x-blurRadius;
        int startY = y-blurRadius;
        int counter = 0;

        int length = blurRadius*2+1;
        double[][] arr = new double[length][length];

        for (int i=startX ; i<startX+length ;i++){
            for (int j = startY; j < startY+length; j++){
                if (whichColor == 1){
                    arr[counter%length][counter/length] = getR(i,j);
                }else if (whichColor == 2){
                    arr[counter%length][counter/length] = getG(i,j);
                }else if (whichColor == 3){
                    arr[counter%length][counter/length] = getB(i,j);
                }
                counter++;
            }
        }

        return arr;
    }

    private void calculateWeightMatrix(){

        //CoordinateCalc calc = new CoordinateCalc(blurRadius*2+1);

        for (int i=0;i<blurRadius*2+1;i++){
            for (int j=0;j<blurRadius*2+1;j++){

                weightArr[i][j] = getWeight(j-blurRadius,blurRadius-i);
                System.out.print(weightArr[i][j]+"\t");
            }
            System.out.println();
        }

    }

    private double getWeight(int x,int y){

        double sigma = (blurRadius*2+1)/2;
        double weight = (1/(2*Math.PI*sigma*sigma))*Math.pow(Math.E,((-(x*x+y*y))/((2*sigma)*(2*sigma))));

        return weight;
    }

    private void getFinalWeightMatrix(){

        int length = blurRadius*2+1;
        double weightSum = 0;

        for (int i = 0;i<length;i++){
            for (int j=0; j<length; j++ ){
                weightSum+=weightArr[i][j];
            }
        }


        for (int i = 0;i<length;i++){
            for (int j=0; j<length; j++ ){
                weightArr[i][j] = weightArr[i][j]/weightSum;
                System.out.print(weightArr[i][j]+"\t");
            }
            System.out.print("\n");
        }

    }

    private double getBlurColor(int x, int y,int whichColor){

        double blurGray = 0;
        double[][] colorMat = getColorMatrix(x,y,whichColor);

        int length = blurRadius*2+1;
        for (int i = 0;i<length;i++){
            for (int j=0; j<length; j++ ){
                blurGray += weightArr[i][j]*colorMat[i][j];
            }
        }

        return blurGray;
    }

    public BufferedImage getBluredImg(){

        BufferedImage bi = new BufferedImage(image.getWidth()-blurRadius*2, image.getHeight()-blurRadius*2, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                int r = (int)getBlurColor(blurRadius+x,blurRadius+y,1);
                int g = (int)getBlurColor(blurRadius+x,blurRadius+y,2);
                int b = (int)getBlurColor(blurRadius+x,blurRadius+y,3);
                Color color = new Color(r,g,b);
                bi.setRGB(x, y, color.getRGB());
               // System.out.println("Pixel:("+x+","+y+") is r:"+r+" g:"+g+" b:"+b);
            }
        }

        File file = new File("C:\\Users\\Mike\\Desktop\\out.jpg");
        try {
            ImageIO.write(bi,"jpg",file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args){
        new BlurEffect(7,null).getFinalWeightMatrix();
    }

}

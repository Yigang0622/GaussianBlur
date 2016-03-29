/**
 * Created by mike on 3/28/16.
 */

//WARN: This class should not be used any more.
public class CoordinateCalc {

    private int halfLength = 0;

    //QUADRANT Status Code
    static int QUADRANT_FIRST   = 1;
    static int QUADRANT_SECOND   = 2;
    static int QUADRANT_THIRD   = 3;
    static int QUADRANT_FOURTH  =4;
    static int QUADRANT_ON_X     = 5;
    static int QUADRANT_ON_Y     = 6;
    static int QUADRANT_ON_ORIGIN   = 0;

    public CoordinateCalc(int length){
        //Pass a odd number
        this.halfLength = (length-1)/2;
        System.out.println("Half Length: "+this.halfLength);
    }

    public int getX(int i, int j){
        int quadrantCode = getQuadrant(i,j);

        switch (quadrantCode){
            case 0:
            case 6: return 0;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: return j-halfLength;
        }
        return 0;
    }

    public int getY(int i, int j){
        int quadrantCode = getQuadrant(i,j);
        switch (quadrantCode){
            case 0:
            case 5: return 0;
            case 1:
            case 2:
            case 3:
            case 4:
            case 6: return halfLength-i;
        }
        return 0;
    }

    private int getQuadrant(int i,int j){
        if ((i == halfLength) && (j==halfLength)){
            return QUADRANT_ON_ORIGIN;
        }else if (i==halfLength && j!=halfLength){
            return QUADRANT_ON_X;
        }else if (i!=halfLength && j==halfLength){
            return QUADRANT_ON_Y;
        }else if (i<=halfLength && j>=halfLength){
            return QUADRANT_FIRST;
        }else if (i<=halfLength-1 && j<=halfLength-1){
            return QUADRANT_SECOND;
        }else if (i>halfLength && j<halfLength){
            return QUADRANT_THIRD;
        }else if (i>halfLength-1 && j>=halfLength-1){
            return QUADRANT_FOURTH;
        }
        return 0; // This is not gonna happen
    }

    //Test Code
    public static void main(String[] args){
        CoordinateCalc calc = new CoordinateCalc(5);

        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++ ){
                System.out.print("("+calc.getX(i,j)+","+calc.getY(i,j)+") ");
            }
            System.out.println();
        }
    }

}

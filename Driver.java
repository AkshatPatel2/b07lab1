import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {

    public static void main(String[] args) throws FileNotFoundException, IOException{

        double THRESHOLD = 0.0001;
        boolean check = true;

        // Checking the no argument constructor (should have empty coff/exponent arrays)
        Polynomial p1 = new Polynomial();
        if((!(p1.coff.length == 0 && p1.exp.length == 0)) || p1.evaluate(1) != 0){
            System.out.println("Test Failed: No argument constructor");
            check = false;
        }

        // Checking the two argument constructor
        double[] coff1 = {1.2, -4.0, 8.8};
        int[] exp1 = {0,6,3};
        Polynomial p2 = new Polynomial(coff1, exp1); // 1.2 - 4.0x^6 + 8.8x^3
        double desired_result = -2677.2;
        if(Math.abs(desired_result) - Math.abs(p2.evaluate(3)) > THRESHOLD){
            System.out.println("Test Failed: 2 argument constructor");
            check = false;
        }
        
        // Checking the file argument constructor
        File file1 = new File("abc.txt"); // Contains: 2.2x2-4-8x3+2x7
        double[] coff2 = {2.2, -4.0, -8.0, 2.0};
        int[] exp2 = {2,0,3,7};
        Polynomial p3 = new Polynomial(file1);
        for(int i = 0; i < 4; i++){
            if(p3.coff[i] != coff2[i] || p3.exp[i] != exp2[i] || p3.coff.length != coff2.length){
                check = false;
                System.out.println("Test Failed: File argument constructor");
                break;
            }
        }

        // Checking add
        double[] coff4 = {1.3, 1.4, -1.1};
        int[] exp4 = {1,2,3};
        double[] coff5 = {2.3, 2.4, -2.1};
        int[] exp5 = {1,3,8};
        Polynomial p4 = new Polynomial(coff4, exp4); // 1.3x+1.4x2-1.1x3
        Polynomial p5 = new Polynomial(coff5, exp5); // 2.3x+2.4x3-2.1x8
        Polynomial add_result = p4.add(p5);
        // expected: 3.6x+1.4x2+1.3x3-2.1x8
        double sum4 = p4.evaluate(2), sum5 = p5.evaluate(2), total_sum = add_result.evaluate(2);
        if(Math.abs(total_sum - sum5 - sum4) > THRESHOLD){
            check = false;
            System.out.println("Test Failed: Add Method");
        }

        // Testing multiply
        Polynomial mult_result = p4.multiply(p5);
        double total_mult = mult_result.evaluate(2);
        if(Math.abs(total_mult - (sum5*sum4)) > THRESHOLD){
            check = false;
            System.out.println("Test Failed: Multiply Method");
        }


        //testing hasRoot: p2 = 2x
        double[] coff10 = {2.0};
        int[] exp10 = {1};
        Polynomial p6 = new Polynomial(coff10, exp10);
        if(!p6.hasRoot(0)){
            check = false;
            System.out.println("Test Failed: hasRoot Method");
        }

        if(!check){
            System.out.println("Test cases failed.");
        } else{
            System.out.println("All Test Cases Passed!");
        }
    }

}
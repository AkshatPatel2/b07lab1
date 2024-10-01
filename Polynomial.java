import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class Polynomial {

    // field representing coefficients
    double[] coff;
    int[] exp;

    // no-argument constructor
    public Polynomial(){
        coff = new double[0];
        exp = new int[0];
    }

    // new constructor with 2 arguments.
    public Polynomial(double[] arr, int[] exponents){
        coff = new double[arr.length];
        exp = new int[exponents.length];
        for(int i = 0; i < arr.length; i++){
            coff[i] = arr[i];
            exp[i] = exponents[i];
        }
    }

    // new file constructor
    public Polynomial(File f) throws FileNotFoundException{
        Scanner scanner = new Scanner(f);
        if(scanner.hasNextLine() == true){
            String str = scanner.nextLine();
            String dummy_str;
            scanner.close();

            String[] split_expressions = str.split("(?=[+-])");
            int num_of_expression = split_expressions.length;
            this.coff = new double[num_of_expression];
            this.exp = new int[num_of_expression];

            for(int i = 0; i < num_of_expression; i++){
                dummy_str = split_expressions[i];
                if(dummy_str.contains("x") == true){
                    String[] split_var = dummy_str.split("x");
                    if (split_var[0].equals("")) {
                        this.coff[i] = 1;
                    }
                    else if (split_var[0].equals("-")) {
                        this.coff[i] = -1;
                    }
                    else {
                        this.coff[i] = Double.parseDouble(split_var[0]);
                    }
                    if (split_var[1].equals("")){
                        this.exp[i] = 1;
                    }
                    else {
                        this.exp[i] = Integer.parseInt(split_var[1]);
                    }
                }
                else{ //constant
                    this.coff[i] = Double.parseDouble(dummy_str);
                    this.exp[i] = 0;
                }
            }
        }
    }

    // add method
    public Polynomial add(Polynomial add_poly){
        double[] dummy_coff = new double[add_poly.coff.length + this.coff.length];
        int[] dummy_exp = new int[dummy_coff.length];

        int curr_index = 0;
        for(int i = 0; i < this.exp.length; i++){
            dummy_exp[curr_index] = this.exp[i];
            dummy_coff[curr_index] = this.coff[i];
            curr_index++;
        }

        boolean duplicate;
        for(int i = 0; i < add_poly.exp.length; i++){
            duplicate = false;
            for(int k = 0; k < curr_index; k++){
                if(dummy_exp[k] == add_poly.exp[i]){
                    duplicate = true;
                    dummy_coff[k] += add_poly.coff[i];
                    break;
                }
            }
            if(duplicate == false){
                dummy_exp[curr_index] = add_poly.exp[i];
                dummy_coff[curr_index] = add_poly.coff[i];
                curr_index++;
            }
        }

        int new_length = 0;
        for(int j = 0; j < curr_index; j++){
            if(dummy_coff[j] != 0){
               new_length++; 
            }
        }

        double[] final_coff = new double[new_length];
        int[] final_exp = new int[new_length];
        int add_index = 0;
        for(int l = 0; l < curr_index; l++){
            if(dummy_coff[l] != 0.0){
                final_coff[add_index] = dummy_coff[l];
                final_exp[add_index] = dummy_exp[l];
                add_index++;
            }
        }

        Polynomial p1 = new Polynomial(final_coff, final_exp);

        return p1;
    }

    // evaluate method
    public double evaluate(double x){
        double sum = 0;
        for(int i = 0; i < coff.length; i++){
            sum += (coff[i] * (Math.pow(x,exp[i])));
        }
        return sum;
    }

    // hasRoot method
    public boolean hasRoot(double root){
        return evaluate(root) == 0;
    }

    // multiply method
    public Polynomial multiply(Polynomial mult_poly){
        double[] dummy_coff = new double[(mult_poly.coff.length)*(this.coff.length)];
        int[] dummy_exp = new int[dummy_coff.length];
        int curr_index = 0, current_exp;
        double current_coff;
        boolean duplicate;
        for(int i = 0; i < this.exp.length; i++){
            for(int j = 0; j < mult_poly.exp.length; j++){
                current_coff = this.coff[i]*mult_poly.coff[j];
                current_exp = this.exp[i] + mult_poly.exp[j];
                duplicate = false;
                for(int k = 0; k < curr_index; k++){
                    if(dummy_exp[k] == current_exp){
                        duplicate = true;
                        dummy_coff[k] += current_coff;
                        break;
                    }
                }
                if(duplicate == false){
                    dummy_exp[curr_index] = current_exp;
                    dummy_coff[curr_index] = current_coff;
                    curr_index++;
                }
            }
        }

        int new_length = 0;
        for(int j = 0; j < curr_index; j++){
            if(dummy_coff[j] != 0){
               new_length++; 
            }
        }

        double[] final_coff = new double[new_length];
        int[] final_exp = new int[new_length];
        int add_index = 0;
        for(int l = 0; l < curr_index; l++){
            if(dummy_coff[l] != 0.0){
                final_coff[add_index] = dummy_coff[l];
                final_exp[add_index] = dummy_exp[l];
                add_index++;
            }
        }

        Polynomial p1 = new Polynomial(final_coff, final_exp);

        return p1;
    }


    //saveToFile method
    public void saveToFile(String s)  throws IOException{
        FileWriter type = new FileWriter(s);
        for (int i = 0; i < this.coff.length; i++) {
            if (this.coff[i] != 0) { // else skip
                if (this.coff[i] < 0) {
                    type.write("-");
                }
                if (this.coff[i] > 0) {
                    if(i != 0){
                        type.write("+");
                    }
                }
                if (Math.abs(this.coff[i]) != 1) {
                    type.write(String.valueOf(Math.abs(this.coff[i])));
                }
                if (this.exp[i] == 0 && Math.abs(this.coff[i]) != 1){
                    type.write(String.valueOf(Math.abs(this.coff[i])));
                }
                if (this.exp[i] != 0) {
                    type.write("x");
                    if (this.exp[i] != 1) {
                        type.write(String.valueOf(this.exp[i]));
                    } // else don't write the exponent
                }
            }
        }
        type.close();
    }

}
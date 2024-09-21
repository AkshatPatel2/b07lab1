import java.lang.Math;
public class Polynomial {

    // field representing coefficients
    double[] coff;

    // no-argument constructor
    public Polynomial(){
        coff = new double[1];
        coff[0] = 0;
    }

    // second constructor with 1 argument.
    public Polynomial(double[] arr){
        coff = new double[arr.length];
        for(int i = 0; i < arr.length; i++){
            coff[i] = arr[i];
        }
    }

    // add method
    public Polynomial add(Polynomial p1){
        int max_length = 0;
        if(coff.length <= p1.coff.length){
            max_length = p1.coff.length;
        }
        else{
            max_length = coff.length;
        }
        double[] sum_array = new double[max_length];
        for(int i = 0; i < max_length; i++){
            if(i < coff.length){
                sum_array[i] += coff[i];
            }
            if(i < p1.coff.length){
                sum_array[i] += p1.coff[i];
            }
        }
        return new Polynomial(sum_array);
    }

    // evaluate method
    public double evaluate(double x){
        double sum = 0;
        for(int i = 0; i < coff.length; i++){
            sum += (coff[i] * (Math.pow(x,i)));
        }
        return sum;
    }

    // hasRoot method
    public boolean hasRoot(double root){
        return evaluate(root) == 0;
    }

}

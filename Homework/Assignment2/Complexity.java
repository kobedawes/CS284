public class Complexity {
    static int counter = 1;
    
    public static void method1(int n){
        //quadratic growth rate n^2
        int count = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++){
                count += 1;
            }
        }
        System.out.println("With input " + n + " we iterate " + count);
    }

    public static void method2(int n){
        //cubic growth rate n^3
        int count = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++){
                for(int k = 0; k < n; k++){
                    count += 1;
                }
            }
        }
        System.out.println("With input " + n + " we iterate " + count);
    }

    public static void method3(int n){
        //logarithmic growth rate log(n)
        int count = 0;
        for(int i = 1; i < n; i *= 2) {
            count++;
        }
        System.out.println("With input " + n + " we iterate " + count);
    }

    /** 1   0   31
     *  2   16  31
     *  3   24  31
     *  4   28  31
     *  5   30  31
     *  6   31  31
     */

    /** 1   0   63
     *  2   32  63
     *  3   48  63     
     *  4   56  63
     *  5   60  63
     *  6   62  63
     *  7   63  63
     */

     /*
      The size n is related to the amount of i by 
      the formula log(n) = i, or n = 2^i 
      The time complexity of biserach is logarithmic specifically 
      log base 2 of n
      */

    public static void method4(int n){
        //n* logarithmic growth rate 5*log(n)
        int count = 0;
        for(int i= 1; i < n; i *= 2){
            for(int j = 0; j < 5; j++){

                count++;
            }
        }
        System.out.println("With input " + n + " we iterate " + count);
    }

    public static void method5(int n){
        //log(log(n)) growth rate
        int count = 0;

        double box = (double) (n);
        for(int i = 2; i < box; i *= i){
        
        count++;
        }

        System.out.println("With input " + n + " we iterate " + count);
    }

    public static void method6(int n){
        //exponential growth rate 2^n

        if (n == 1){

            counter++;
            return;
        }

        if (n > 1){

            counter++;
            method6(n-1);
            method6(n-1);
            return;
        }
    }

    public static void main(String[] args){

        method1(4);
        method2(4);
        method3(4);
        method4(4);
        method5(4);
        method5(16);
        method6(4);
        System.out.println(counter);
    }
}


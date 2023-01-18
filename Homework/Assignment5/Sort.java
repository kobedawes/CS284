import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
public class Sort{

    private static class Interval{
        //interval class that keeps track of the lower and upper bounds
        private int lower;
        private int upper;

        public Interval(int lower, int upper){
            this.lower = lower;
            this.upper = upper;
        }

        public int getLower(){
            //returns the lower bound
            return lower;
        }
        public int getUpper(){
            // returns the upper bound
            return upper;
        }

        public int getDifference(){
            //returns the difference between the indexes
            return upper - lower;
        }
        
        public boolean equals(Object o){
            // returns true if this interval and the given interval have the same lower and upper bounds
            if(o instanceof Interval){
                Interval A = (Interval)o;
                return lower == A.getLower() && upper == A.getUpper();
            }
            else{
                throw  new IllegalArgumentException("invalid object");
            }
            
        }
        public int hashCode(){
            // public int hashCode() // returns lower * lower + upper
            return lower * lower + upper;
        }
    }

    public static <E extends Comparable<E>> void bubbleSort(E[] table, int first, int last) {
    //bubble sort
        int pass = 1;
        int middle = (first+last)/2;
        int[] indTable = {first, middle, last};
        do {

            // Compare each pair of adjacent elements.
            for (int i = 0; i < indTable.length - pass; i++) {
                if (table[indTable[i]].compareTo(table[indTable[i + 1]]) > 0) { // Exchange pair.
                    E temp = table[indTable[i]];
                    table[indTable[i]] = table[indTable[i + 1]];
                    table[indTable[i + 1]] = temp; 
                } 
            }
            pass++;
        } while (pass < 3);
    }

    public static <E extends Comparable<E>> void bubbleSort2(E[] table) {
        int pass = 1;
        boolean exchanges = false;
        do {
        // Invariant: Elements after table.length-pass+1
        // are in place.
        exchanges = false;
        // Compare each pair of adjacent elements.
        for (int i = 0; i < table.length - pass; i++) {
        if (table[i].compareTo(table[i + 1]) > 0) { // Exchange pair.
        E temp = table[i];
        table[i] = table[i + 1];
        table[i + 1] = temp; exchanges = true;
        } }
        pass++;
        } while (exchanges);

    }

    public static <E extends Comparable<E>> void bubbleSort2(E[] table, int first, int length) {
        int pass = 1;
        boolean exchanges = false;
        do {
        // Invariant: Elements after table.length-pass+1
        // are in place.
        exchanges = false;
        // Compare each pair of adjacent elements.
        for (int i = first; i < length - pass; i++) {
        if (table[i].compareTo(table[i + 1]) > 0) { // Exchange pair.
        E temp = table[i];
        table[i] = table[i + 1];
        table[i + 1] = temp; exchanges = true;
        } }
        pass++;
        } while (exchanges);

    }

    public static void swap(Object[] array, int i, int j){
        //switches the items of the array with given indexes
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static <T extends Comparable<T>> int partition(T[] table, int first, int last){
        //uses the middle value as the pivot and then partions array
        int middle = (first+last)/2;
        //System.out.println("partition middle index: " + middle + " \nmiddle value: " + table[middle]);

        bubbleSort(table, first, last);
        swap(table, first, middle);
        T pivot = table[first];
        //System.out.println("partition pivot value: " + pivot);

        int up = first;
        int down = last;

        do {
            while ((up < last) && (pivot.compareTo(table[up]) >= 0)) {up++;}
            while (pivot.compareTo(table[down]) < 0) { down--;}
            if (up < down) { // if up is to the left of down
                swap(table, up, down);
            }
            } while (up < down); // Repeat while up is left of
        swap(table, first, down);
        return down;
    }

    public static <T extends Comparable<T>> void sort(T[] array){
        //does quicksort based on median partioning
        Set<Interval> intvSet = new HashSet<Interval>();
        LinkedList<Interval> order = new LinkedList<>();
        if(array.length == 2){
            if(array[0].compareTo(array[1]) > 0){
                swap(array, 0, 1);
            }
            return;
        }
        if(array.length == 3){
            bubbleSort2(array);
            return;
        }
        else{
            int pivIndex = partition(array, 0, array.length-1);

            intvSet.add(new Interval(0, pivIndex-1));
            order.addLast(new Interval(0, pivIndex-1));
            intvSet.add(new Interval(pivIndex+1, array.length -1));
            order.addLast(new Interval(pivIndex+1 , array.length -1));

            while(!intvSet.isEmpty()){
                
                Interval bounds = order.pollFirst();
                
                if(bounds.getDifference() < 0){
                    intvSet.remove(bounds);
                    bounds = order.pollFirst();
                    if(bounds == null){
                        return;
                    }
                }

                else if(bounds.getDifference() == 0){
                    intvSet.remove(bounds);
                }

                else if(bounds.getDifference() == 1){
                    if(array[bounds.getLower()].compareTo(array[bounds.getUpper()]) > 0){
                        swap(array, bounds.getLower(), bounds.getUpper());
                    }
                    intvSet.remove(bounds); 
                }

                else if(bounds.getDifference() == 2){
                   
                    bubbleSort2(array, bounds.getLower(), bounds.getUpper()+1);
                    intvSet.remove(bounds);
                }

                else{
                    
                        pivIndex = partition(array, bounds.getLower(), bounds.getUpper());                        
                        if(pivIndex -1 > 0){
                            intvSet.add(new Interval(bounds.getLower(), pivIndex-1));
                            order.addLast(new Interval(bounds.getLower(), pivIndex-1));
                        }
                        
                        if(pivIndex+1 < array.length){
                            intvSet.add(new Interval(pivIndex+1, bounds.getUpper()));
                            order.addLast(new Interval(pivIndex+1, bounds.getUpper()));
                        }
                        intvSet.remove(bounds);
                }
            }
        }
    }

    public static void main(String[] args){
        
    }
}
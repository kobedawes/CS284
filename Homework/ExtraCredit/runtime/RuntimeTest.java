package runtime;
import org.junit.Test;
import org.junit.Assert;
import java.util.EmptyStackException;



public class RuntimeTest {
    @Test 
    public void example1(){
        Runtime r = new Runtime();
        //File f = new File("eg1.pgm");
		r.readFromFile("/Users/kobe/Desktop/Academics/CS284/Homework/ExtraCredit/eg1.pgm"); // Load and parse mini-bytecode program
		r.run(); // execute program
        String result = "Pgm   : [push 5.0, push 3.4567, add, pop m0, exit]\nPc    : 6\nStack : []\nMemory: [8.4567, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]\n------------------------------------------------\n";
        Assert.assertEquals(r.toString(), result);
    }

    @Test 
    public void example2(){
        Runtime r = new Runtime();
		r.readFromFile("/Users/kobe/Desktop/Academics/CS284/Homework/ExtraCredit/eg2.pgm"); // Load and parse mini-bytecode program
		r.run(); // execute program
        String result = "Pgm   : [push 5.0, pop m0, push m0, push m0, label l2, dec, jmpz done, pop m0, push m0, mul, push m0, jmp l2, label done, pop m0, exit]\nPc    : 16\nStack : [120.0]\nMemory: [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]\n------------------------------------------------\n";
        Assert.assertEquals(r.toString(), result);
    }

    @Test(expected = EmptyStackException.class) 
    public void example3(){
        Runtime r = new Runtime();
		r.readFromFile("/Users/kobe/Desktop/Academics/CS284/Homework/ExtraCredit/eg3.pgm"); // Load and parse mini-bytecode program
		r.run(); // execute program
        //String result = "Pgm   : [push 5.0, pop m0, push m0, push m0, label l2, dec, jmpz done, pop m0, push m0, mul, push m0, jmp l2, label done, pop m0, exit]\nPc    : 16\nStack : [120.0]\nMemory: [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]\n------------------------------------------------\n";
        //Assert.assertEquals(r.toString(), result);
    }

    @Test 
    public void example4(){
        Runtime r = new Runtime();
		r.readFromFile("/Users/kobe/Desktop/Academics/CS284/Homework/ExtraCredit/eg4.pgm"); // Load and parse mini-bytecode program
		r.run(); // execute program
        String result = "Pgm   : [push 5.0, pop m0, exit]\nPc    : 4\nStack : []\nMemory: [5.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]\n------------------------------------------------\n";
        Assert.assertEquals(r.toString(), result);
    }

}

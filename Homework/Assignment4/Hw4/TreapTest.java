package Hw4;
import org.junit.Test;
import org.junit.Assert;

public class TreapTest{

    @Test
    public void add(){
      
        Treap<Integer> test = new Treap<Integer>();
        test.add(1, 50);
        test.add(2, 49);
        test.add(0, 100);
        test.add(5, 60);
        test.add(3, 40);
        test.add(4, 101);

        
        Assert.assertFalse(test.add(1));
    }

    @Test
    public void remove(){
        Treap<Integer> test = new Treap<Integer>();
        test.add(1, 50);
        test.add(2, 49);
        test.add(0, 100);
        test.add(5, 60);
        test.add(3, 40);
        test.add(4, 101);

        //Assert.assertTrue(test.remove(1));
        Assert.assertTrue(test.remove(4));
        System.out.println(test);
    }   

    @Test
    public void find(){
        Treap<Integer> test = new Treap<Integer>();
        test.add(1, 50);
        test.add(2, 49);
        test.add(0, 100);
        test.add(5, 60);
        test.add(3, 40);
        test.add(4, 101);

        Assert.assertTrue(test.find(1));
    }
}
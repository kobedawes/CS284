import org.junit.Test;

import java.util.Arrays;

import org.junit.Assert;


public class sortTest{

    @Test 
    public void sortTest1(){
        Integer[] test = {33,23,454,66,2,1,0};
        Sort.sort(test);
        Integer[] ans =  {0, 1, 2, 23, 33, 66, 454};
        String res = Arrays.toString(ans);
        Assert.assertTrue(res.equals(Arrays.toString(test)));
    }

    @Test 
    public void sortTest2(){
        Integer[] test = {1,23,454,455,25,100,0};
        Sort.sort(test);
        Integer[] ans =  {0, 1, 23, 25, 100, 454, 455};
        String res = Arrays.toString(ans);
        Assert.assertTrue(res.equals(Arrays.toString(test)));    
    }

    @Test 
    public void sortTest3(){
        Integer[] test = {101,235,454,455,25,100,0};
        Sort.sort(test);
        Integer[] ans =  {0, 25, 100, 101, 235, 454, 455};
        String res = Arrays.toString(ans);
        Assert.assertTrue(res.equals(Arrays.toString(test)));    
    }

    @Test 
    public void sortTest4(){
        Integer[] test = {1000,1,200};
        Sort.sort(test);
        Integer[] ans =  {1,200, 1000};
        String res = Arrays.toString(ans);
        Assert.assertTrue(res.equals(Arrays.toString(test))); 
    }

    @Test 
    public void sortTest5(){
        Integer[] test = {1000,200};
        Sort.sort(test);
        Integer[] ans =  {200, 1000};
        String res = Arrays.toString(ans);
        Assert.assertTrue(res.equals(Arrays.toString(test))); 
    }
}
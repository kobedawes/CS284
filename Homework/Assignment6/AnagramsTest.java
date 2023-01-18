import org.junit.Test;
import org.junit.Assert;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class AnagramsTest {
   
    @Test 
    public void testMaxEntries(){
        Anagrams a = new Anagrams();
		try {
			a.processFile("/Users/kobe/Desktop/Academics/CS284/Homework/Assignment6/words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxEntries = a.getMaxEntries();
        String testResult  = "[236204078=[alerts, alters, artels, estral, laster, lastre, rastle, ratels, relast, resalt, salter, slater, staler, stelar, talers]]";
        Assert.assertTrue(testResult.equals(maxEntries.toString()));
    }

    @Test
    public void testHashCode(){
        Anagrams a = new Anagrams();
        long hash = a.myHashCode("alters");
        Assert.assertEquals(hash, 236204078);
    }

    @Test
    public void testBuildLetters(){
        Anagrams a = new Anagrams();
        int testA = a.letterTable.get('a');
        int testZ = a.letterTable.get('z');
        Assert.assertEquals(testA, 2);
        Assert.assertEquals(testZ, 101);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void testAdd(){
        Anagrams a = new Anagrams();
		try {
			a.processFile("/Users/kobe/Desktop/Academics/CS284/Homework/Assignment6/words_alpha.txt");
            a.addWord("alters");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
}

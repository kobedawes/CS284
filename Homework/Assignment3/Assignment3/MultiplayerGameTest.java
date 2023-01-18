package Assignment3;
import org.junit.Test;
import org.junit.Assert;

public class MultiplayerGameTest{

    @Test
    public void sizetest(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool name", 100);
        Assert.assertEquals(1, testGame.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTest() throws Exception{
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool name", 100);
        testGame.addGamePiece(0, "cool name", 100);
    }

    @Test
    public void removeTest1(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool", 100);
        testGame.addGamePiece(0, "cool name", 100);
        testGame.removeGamePiece(0, "cool name");
        Assert.assertEquals(1, testGame.size());
    }

    @Test
    public void removeTest1_2(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool name", 100);
        testGame.addGamePiece(0, "cool name2", 100);
        testGame.removeGamePiece(0, "cool name");
        Assert.assertEquals(1, testGame.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeTest2() throws Exception{
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.removeGamePiece(0, "cool name");
        //Assert.assertEquals(1, testGame.size());
    }
    
    @Test
    public void haspieceTest(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool name", 100);
        Assert.assertTrue(testGame.hasGamePiece("cool name"));
    }

    @Test
    public void removeAllTest(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool name", 100);
        testGame.addGamePiece(0, "cool name2", 100);
        testGame.removeAllGamePieces(0);
        Assert.assertEquals(0, testGame.size());
    }

    @Test
    public void increaseTest(){
        MultiplayerGame testGame = new MultiplayerGame(2);
    
        testGame.addGamePiece(0, "cool name", 100);
        testGame.increaseStrength(0, -50);
        String test = "[Player0:\nGamePiece: cool name strength: 50\n]\n[Player1:\n]\n";
        Assert.assertEquals(test, testGame.toString());
    }

    @Test
    public void stringTest(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool name", 100);
        testGame.addGamePiece(1, "cool name", 100);
        String test = "[Player0:\nGamePiece: cool name strength: 100\n]\n[Player1:\nGamePiece: cool name strength: 100\n]\n";
        Assert.assertEquals(test, testGame.toString());
    }

    @Test
    public void initializeTest(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        Assert.assertEquals("Player0", testGame.currentEntityToString() );
    }

    @Test 
    public void nextPlayerTest(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool name", 100);
        testGame.addGamePiece(0, "cool name1", 100);
        testGame.nextPlayer();
        Assert.assertEquals("Player1", testGame.currentEntityToString() );
    }

    @Test 
    public void nextEntityTest(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool name", 100);
        testGame.addGamePiece(0, "cool name1", 100);
        testGame.nextEntity();
        Assert.assertEquals("GamePiece: cool name strength: 100", testGame.currentEntityToString() );
    }

    @Test
    public void prevPlayerTest(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool name", 100);
        testGame.addGamePiece(0, "cool name1", 100);
        testGame.prevPlayer();
        Assert.assertEquals("Player1", testGame.currentEntityToString() );
    }

    @Test 
    public void currentEntityTest(){
        MultiplayerGame testGame = new MultiplayerGame(2);
        testGame.addGamePiece(0, "cool name", 100);
        testGame.addGamePiece(0, "cool name1", 100);
        testGame.nextEntity();
        testGame.nextEntity();
        Assert.assertEquals("GamePiece: cool name1 strength: 100", testGame.currentEntityToString() );
    }
}

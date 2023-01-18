package Assignment3;
//import java.util.HashMap;

public class MultiplayerGame{
    private GameEntity turnTracker;
    private static GameEntity[] index;

    public MultiplayerGame(int n){
        //constructor that creates a game wiwth the indicated amount of players

        index = new GameEntity[n];

        GameEntity head = new GamePlayer(index[n-1], null, 0);
        index[0] = head;
        for(int i = 1; i < n-1; i++){
        
            head.next = new GamePlayer(head, null, i);
            index[i] = head.next;
            head = head.next;
        }
        head.next = new GamePlayer(head, index[0], n-1);
        index[n-1] = head.next;
        index[0].prev = head.next;
        initializeTurnTracker();

    }

    public int size(){
        //counts the number of pieces in the game

        int size = 0;
        for (int i = 0; i < index.length; i++){

            GameEntity head = index[i].next;
            while(head.isGamePlayer() != true){
                size += head.size();
                head = head.next;
            }
        }
        return size;
    }

    public void addGamePiece(int playerId, String name, int strength){
        //checks whether playerId is valid and whether if piece already exists
        //adds a game piece for the indicated playerId with the given variables

        if(playerId >= index.length || playerId < 0){
            throw new IllegalArgumentException("addGamePiece: no such player");
        }

        if(index[playerId].next.isGamePlayer()){
            index[playerId].next = new GamePiece(index[playerId], index[playerId].next, name, strength);
        }

        else{

            GameEntity tar = index[playerId].next;
            while(tar.isGamePlayer() != true){
                if(name == tar.getName()){
                    throw new IllegalArgumentException("addGamePiece: duplicate entity");
                }
                if(tar.next.isGamePlayer()){
                    break;
                }
                tar = tar.next;            }
            tar.next = new GamePiece(tar, tar.next, name, strength);
        }
    }

    public void removeGamePiece(int playerId, String name){
        //chekcs whether playerId is valid and removes the indicated piece for indicated player
        
        GameEntity tar = index[playerId].next;
        if(playerId >= index.length || playerId < 0){
            throw new IllegalArgumentException("removeGamePiece: no such player");
        }

        if(tar.isGamePlayer() == true){
            throw new IllegalArgumentException("removeGamePiece: entity does not exist");
        }
        
        else{
            while(tar.isGamePlayer() != true){
                if (tar.getName() == name ){
                    index[playerId].next = tar.next;
                    return;
                }
                if(name == tar.next.getName()){
                    tar.next = tar.next.next;
                    return;
                }
                tar = tar.next;
             }
             throw new IllegalArgumentException("removeGamePiece: no such piece");
       }   
     }

     public boolean hasGamePiece(String name){
        //checks the entire game to see if the indicated piece exists
        String track = index[0].getName();
        GameEntity head = index[0].next;
        while(head.getName() != track){
            if(head.getName() == name && head.isGamePlayer() != true){
                return true;
            }
        }
        return false;
     }

     public void removeAllGamePieces(int playerId){
        //removes all game pieces of the game

        if(playerId >= index.length){
            throw new IllegalArgumentException("removeGamePiece: no such player");
        }

        if(playerId == index.length - 1){
            index[playerId].next = index[0];
            index[0].prev = index[playerId];
            return;
        }
        index[playerId].next = index[playerId + 1];
        index[playerId + 1].prev = index[playerId];

     }

    public void increaseStrength(int playerId, int n){
        //checks whether id is valid and increases all of indicated player's strength by n

        if(playerId < 0 || playerId >= index.length){
            throw new IllegalArgumentException( "increaseStrength: no such player");
        }

        GameEntity head = (GamePiece) index[playerId].next; 
        while(head.isGamePlayer() != true){

            GamePiece piece = (GamePiece) head; 
            piece.updateStrength(n);
            head = head.next;
        }
    }

    public String toString(){
        //prints the player and their respective pieces

        StringBuilder res = new StringBuilder();
        for(int i = 0; i < index.length; i++){

            res.append("[" + index[i].toString() + ":\n");
            GameEntity item = index[i].next;
            while(item.isGamePlayer() != true){
                res.append(item.toString() + "\n");
                item = item.next;
            }
            res.append("]\n");
        }
        return res.toString();
    }

    public void initializeTurnTracker(){
        //sets the tracker to the first player

        turnTracker = index[0];
    }

    public void nextPlayer(){
        //goes to the next available player by searching through index

        GameEntity tar = turnTracker.next;
        while(tar.isGamePlayer() != true){
            tar = tar.next;
        }
        turnTracker = tar;
    }

    public void nextEntity(){
        //goes to the next available entity by searching through index

        turnTracker = turnTracker.next;
    }

    public void prevPlayer(){
       //goes to the previously available player by searching through index

        GameEntity tar = turnTracker.prev;
        while(tar.isGamePlayer() != true){
            tar = tar.prev;
        }
        turnTracker = tar;
    }

    public String currentEntityToString(){
         //goes to the next available entity by searching through index

        return turnTracker.toString();
    }

    public static void main(String[] args){

    }
}
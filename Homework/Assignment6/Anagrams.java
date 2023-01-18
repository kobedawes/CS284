//package anagrams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Anagrams {
	final Integer[] primes =  
			{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 
			31, 37, 41, 43, 47, 53, 59, 61, 67, 
			71, 73, 79, 83, 89, 97, 101};
	Map<Character,Integer> letterTable;
	Map<Long,ArrayList<String>> anagramTable;

	public void buildLetterTable() {
		// for every letter in the alphabet BLT assigns a
		// unique prime number to it
		letterTable = new HashMap<>();
		int prIndex = 0;
		for(char i = 'a'; i < 'a' +26; i++ ){
			letterTable.put(i, primes[prIndex]);
			prIndex++;
		}
		//System.out.println(letterTable.toString());
	}

	Anagrams() {
		buildLetterTable();
		anagramTable = new HashMap<Long,ArrayList<String>>();
	}

	public void addWord(String s) {
		// creates a hash based on the given string and adds 
		// it to anagram table if it isn't there already
		long hash = myHashCode(s);
		if(!anagramTable.containsKey(hash)){
			anagramTable.put(hash, new ArrayList<String>(Arrays.asList(s)));
		}
		else{
			if(anagramTable.get(hash).contains(s)){
				throw new IllegalArgumentException("addWord: duplicate value");
			}
			else{
				anagramTable.get(hash).add(s);
			}
		}
	}
	
	public long myHashCode(String s) {
		// returns the product of the letters in the string as 
		// the hashcode 
		if(s.isEmpty()){
			throw new IllegalArgumentException("Empty string");
		}
		long result = 1;
		for(int i =0; i < s.length(); i++){
			result*=letterTable.get(s.charAt(i));
		}
		return result;
	}
	
	public void processFile(String s) throws IOException {
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null)   {
		  this.addWord(strLine);
		}
		br.close();
	}
	
	public ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries() {
		ArrayList<Map.Entry<Long, ArrayList<String>>> result = new ArrayList<>();

	    int max = 0;
		Iterator<Map.Entry<Long,ArrayList<String>>> anagramIt = anagramTable.entrySet().iterator();
		Map.Entry<Long,ArrayList<String>> entry;
		while(anagramIt.hasNext()){
			entry = (Map.Entry<Long,ArrayList<String>>) anagramIt.next();
			int entrySize = ((ArrayList<String>) entry.getValue()).size();
			if(entrySize > max){
				max = ((ArrayList<String>) entry.getValue()).size();
			}
		}

		anagramIt = anagramTable.entrySet().iterator();
		while(anagramIt.hasNext()){
			entry = (Map.Entry<Long,ArrayList<String>>) anagramIt.next();
			int entrySize = ((ArrayList<String>) entry.getValue()).size();
			if(entrySize == max){
				result.add(entry);
			}
		}
		return result;
		
	}
	
	public static void main(String[] args) {
		Anagrams a = new Anagrams();

		final long startTime = System.nanoTime();    
		try {
			a.processFile("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long,ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime/1000000000);
		System.out.println("Time: "+ seconds);
		System.out.println("List of max anagrams: "+ maxEntries);

		
	}
}

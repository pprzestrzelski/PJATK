/**
 *
 *  @author Przestrzelski Paweł S13616
 *
 */

package zadanie2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Anagrams {

	public List<String> allWords;
	public List<List<String>> wordsToFind;
	
	public Anagrams(String allWords) throws FileNotFoundException {
		this.allWords = new ArrayList<String>();
		this.wordsToFind   = new ArrayList<List<String>>();
		
		try(Scanner sc = new Scanner(new File(allWords))) {;
			while(sc.hasNext()) 
				this.allWords.add(sc.next());
		};
	}
	
	List<List<String>> getSortedByAnQty() {
		List<List<String>> listOfLists = new ArrayList<List<String>>();
		List<String> wordsFound = new ArrayList<String>();
		List<String> copyListOfAllWords = new ArrayList<String>(allWords); 
		
		for(String wordToCompare : allWords) {
			if(copyListOfAllWords.contains(wordToCompare)) {
				wordsFound.add(wordToCompare);
				for (int i = allWords.indexOf(wordToCompare)+1; i<allWords.size(); i++) {
					if(wordToCompare.length() == allWords.get(i).length() 
							&& isAnagram(wordToCompare, allWords.get(i))) {
						wordsFound.add(allWords.get(i));
						copyListOfAllWords.remove(allWords.get(i));
					}				
				}
				listOfLists.add(new ArrayList<String>(wordsFound));
				wordsFound.clear();
			}
		}
		
		// Sortowanie zgodnie z wytycznymi z polecenia za pomoca wlasnego comparatora
		listOfLists.sort(new ListComparator());
		wordsToFind = new ArrayList<List<String>>(listOfLists);
		
		return listOfLists;
	};
	
	boolean isAnagram(String str1, String str2) {
		for (int i=0; i<str1.length(); i++) {
			if(!str2.contains(Character.toString(str1.charAt(i))) ) {
				return false;
			}
		}
		return true;
	}
	
	String getAnagramsFor(String word){
		String result = word + ": ";
		for(int i=0; i<wordsToFind.size(); i++){
			for(int j=0; j<wordsToFind.get(i).size(); j++){
				if((wordsToFind.get(i)).get(j).equals(word)){
					(wordsToFind.get(i)).remove(word);
					result+=wordsToFind.get(i);
				}
			}
		}
		if(result == word + ": "){
			result += "null";
		}
		return result;
	}
}

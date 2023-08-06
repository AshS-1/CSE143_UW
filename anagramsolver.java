//Ashwika Sharma
//8/4/2022

import java.util.*;

//This class uses a dictionary to print anagram phrases of the provided word
//    or phrase. 
public class AnagramSolver {
   
   private Map<String, LetterInventory> map;
   private List<String> dictionaryReal;   
   
   //pre: Provide a list of strings dictionary.
   //post: creates a new AnagramSolver object that uses the dictionary to create an
   //    account for the inventories each of the words in the dictionary.  
   public AnagramSolver(List<String> dictionary) {
      dictionaryReal = dictionary;
      map = new HashMap<>();
      for (int i = 0; i < dictionaryReal.size(); i++) {
         map.put(dictionaryReal.get(i), new LetterInventory(dictionaryReal.get(i)));
      }
   } 
   
   //pre: provide a string text, and an integer max. If max is less than 0, an
   //    IllegalArgumentException is thrown.
   //post: prints out all the anagrams of the string that have a length of at most max
   //    words, if max is not equal to 0. If it is equal to 0, this method prints
   //    out all the anagrams of the string with no restriction on number of words.
   public void print(String text, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
      LetterInventory letters = new LetterInventory(text);
      List<String> dict = pruneDict(text, letters);
      printHelper(letters, new Stack<String>(), max, dict);   
   }

   //pre: provide a LetterInventory letters, and stack of strings result,
   //    an integer max, and a list of strings which is the pruned dictionary.
   //post: prints out all the anagrams of the string that have a length of at most max
   //    words, if max is not equal to 0. If it is equal to 0, this method prints
   //    out all the anagrams of the string with no restriction on number of words.
   private void printHelper(LetterInventory letters, Stack<String> result,
         int max, List<String> dictionaryPruned) {
      if (letters.isEmpty()) {
         if (max == 0 || result.size() <= max) {
            System.out.println(result);
         }
      } else {
         for (int i = 0; i < dictionaryPruned.size(); i++) {
            String word = dictionaryPruned.get(i);
            if (map.get(word) != null) {
               if (letters.subtract(map.get(word)) != null) {
                  result.push(word);
                  printHelper(letters.subtract(map.get(word)), result, max, dictionaryPruned);
                  result.pop();
               }
            }
         }
      }
   }
   
   //pre: provide a string text
   //post: prunes the dictionary by removing all entries in which every letter of the word is
   //    not found in the text provided, in other words, are not relevent in finding suitable
   //    anagrams.  
   private List<String> pruneDict(String text, LetterInventory letters) {
      List<String> pruned = new LinkedList<>();
      for (int i = 0; i < dictionaryReal.size(); i++) {
         if (letters.subtract(map.get(dictionaryReal.get(i))) != null) {
             pruned.add(dictionaryReal.get(i));
         }
      }
      return pruned;  
   }

}

//Ashwika Sharma
//7/28/2022

import java.util.*;

//The class GrammarSolver uses the rules for a grammer in Backus-Naur form and
//    allows the client to generate random elements of the grammar, as well as perform
//    methods that check if the symbol is a nonterminal and print out the nonterminals
//    in it.
public class GrammarSolver {

   private Map<String, String[]> grammarMap;
   
   //pre: provide a list of BNF grammar rules. If the list is empty, an Illegal 
   //    ArgumentException is thrown. 
   //post: Initializes a new grammar based on the rules provided which consists of
   //    entries made for the nonterminals. If there are multiple entries in the grammar
   //    made for the same nonterminal, an IllegalArgumentException is thrown. 
   public GrammarSolver(List<String> rules) {
      if (rules.size() == 0) {
         throw new IllegalArgumentException();
      }
      grammarMap = new TreeMap<>();
      for (int i = 0; i < rules.size(); i++) {
         String[] next = rules.get(i).split("::=");
         if (grammarMap.containsKey(next[0])) {
            throw new IllegalArgumentException();
         }
         String[] within = next[1].split("\\|");
         String[] array = new String[within.length];
         for (int a = 0; a < within.length; a++) {
            array[a] = within[a];
         }
         grammarMap.put(next[0], array);
      } 
   }
   
   //pre: Provide a string symbol
   //post: Returns true if the symbol is a non terminal in the grammar and 
   //    otherwise returns false. 
   public boolean grammarContains(String symbol) {
      return grammarMap.containsKey(symbol);
   }
   
   //post: Returns a string containing brackets on either side with the nonterminal
   //    symbols seperated by commas.
   public String getSymbols() {
      String result = "[";
      int x = 0;
      for (String key : grammarMap.keySet()) {
         result += key;
         if (x != grammarMap.size() -1) {
            result += ", ";
         }
         x++;
      }
      result += "]";
      return result;
   }
   
   //pre: provide a string symbol and an integer number of times that the random
   //    occurance wants to be generated. If times is less than 0 or string that is
   //    passed is not a non-terminal, an IllegalArgumentException is thrown. 
   //post: Generates random occurances of the symbol provided. The number of random 
   //    occurances is determined by the integer times provided. Returns these occurances
   //    as an array of strings. 
   public String[] generate(String symbol, int times) {
      if (times < 0 || !grammarMap.containsKey(symbol)) {
         throw new IllegalArgumentException();
      }
      String [] result = new String[times];
      for (int i = 0; i < times; i++) {
         result[i] = generateHelper(symbol, "");
      }
      return result;
   }
   
   //pre: provide a string symbol and a string result.
   //post: generates the random occurances of the symbol and returns them
   //    as a string. 
   private String generateHelper(String symbol, String result) {
      if (!grammarContains(symbol)) {
         result += symbol;
      } else {
         String[] element = grammarMap.get(symbol);
         Random rand = new Random();
         int randInt = rand.nextInt(element.length);
         String[] elementNew = element[randInt].split("\\s+");
         for (String new1 : elementNew) {
            result = generateHelper(new1, result) + " ";
         }
      }
      return result.trim();     
   }

}

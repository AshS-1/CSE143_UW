//The class keeps an inventory of number of each letter included in  
//    a given string. Gets the count of letters in a string, changes
//     letter counts, and manipulates inventory in other ways
public class LetterInventory {

   private int [] elementData;
   private int size;
   
   public static final int CAPACITY = 26;          
   
   //The constructor takes a string data and creates an inventory/count of the 
   //    letters in the string. No distinction between uppercase and lowercase 
   //    and ignores non-alphabets.
   public LetterInventory(String data) {
      elementData = new int[CAPACITY];
      size = 0;
      int length = data.length();
      char c;
      for (int i = 0; i < length; i++) {
         c = data.charAt(i);
         if (Character.isLetter(c)) {
            elementData[index(c)]++;
            size++;
         }   
      }
   }
   
   //pre: Provide a letter (throws IllegalArgumentException if not a letter)
   //post: Returns count of specified letter in LetterInventory, disregard case
   public int get(char letter) {
      if (!Character.isLetter(letter)) {
         throw new IllegalArgumentException();
      }                      
      return elementData[index(letter)];
   }
   
   //pre: Give a letter of the alphabet and an integer value                 
   //    value > 0, and the character must be of the alphabet, 
   //    otherwise throws IllegalArgumentException.
   //post: Sets the count of the letter provided to the value provided.
   public void set(char letter, int value) {
      if (!Character.isLetter(letter) || value < 0) {
         throw new IllegalArgumentException();
      }
      int index = index(letter);                      
      int oriValue = elementData[index];
      elementData[index] = value;
      size += (value - oriValue);
   }
   
   //post: returns the size of the LetterInventory
   public int size() {
      return size;
   }
   
   //post: returns true if LetterInventory is empty and false if not. 
   public boolean isEmpty() {
      return size == 0;
   }
   
   //post: returns a string that prints out the values in the LetterInventory.
   public String toString() {
      String result = ""; 
      result += "[";
      size = 0;
      char letter;
      for (int i = 0; i < 26; i++) {                             
         letter = (char) (i + 97);
         for (int s = 0; s < elementData[i]; s++) {
            result += letter;
            size++;
         }
      }
      result += "]";
      return result;
   }
   
   //pre: passes a LetterInventory "other" that is being added. 
   //post: returns new LetterInventory result that is the sum of                   
   //    "this" LetterInventory and the "other" LetterInventory by adding
   //     the counts of each letter in each LetterInventory together      
   public LetterInventory add(LetterInventory other) {
      LetterInventory result = new LetterInventory("");  
      int newsize = 0; 
      int sum = 0;
      for (int i = 0; i < CAPACITY; i++) {                             
         sum = this.elementData[i] + other.elementData[i];  
         result.elementData[i] = sum;
         newsize += sum;
      }
      result.size = newsize;
      return result;
   }
   
   //pre: passes a LetterInventory "other" that is being subtracted. 
   //post: returns a new LetterInventory that is the difference between 
   //    "this" LetterInventory, and the "other" LetterInventory by subtracting
   //    their letter counts. Returns null if a letter count for "this" 
   //    LetterInventory is smaller than the same letter count for the 
   //    "other" LetterInventory.
   public LetterInventory subtract(LetterInventory other) {
      int difference = 0;   
      LetterInventory result = new LetterInventory(""); 
      this.size = 0;
      for (int i = 0; i < CAPACITY; i++) {
         if (this.elementData[i] < other.elementData[i]) {
            return null;
         }
         difference = this.elementData[i] - other.elementData[i]; 
         result.elementData[i] = difference;
         size += difference;   
      }
      result.size = size;
      return result;       
   }
   
   //pre: Provide a character c, which must be a letter
   //post: Returns the index in LetteryInventory that corresponds to the letter
   private int index(char c) {
      char cL = Character.toLowerCase(c);
      int index = Character.getNumericValue(cL) - 10;
      return index;
   }
       
}

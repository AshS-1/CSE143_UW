//Ashwika Sharma
//07/07/2022

//The class Guitar37 models a guitar that has 37 strings and implements the Guitar 
//    interface. You can pluck, tic, and play the strings, as well as get more 
//    information about the strings through other methods.
public class Guitar37 implements Guitar {
   
   private GuitarString [] strings;
   private int tics = 0;
   
   public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
   public static final int SIZE = 37;
   public static final int MAX_PITCH = 12; 
   public static final int MIN_PITCH = -24;
    
   //post: Creates an array of SIZE(37) GuitarStrings with a certain frequency based
   //      on their position in the array.
   public Guitar37() {
      strings = new GuitarString[SIZE];
      double a = 0.0;
      for (int i = 0; i < SIZE; i++) {
         double frequency = 440.0 * Math.pow(2.0, (a - 24.0) / 12.0);
         strings[i] = new GuitarString(frequency);
         System.out.println(frequency);
         a++;
      }
   }

   //pre: provides an integer pitch.
   //post: If the pitch provided is between MAX_PITCH and MIN_PITCH, inclusive, the
   //      string with that pitch is plucked.
   public void playNote(int pitch) {
      if (pitch <= MAX_PITCH && pitch >= MIN_PITCH) {   
         int index = pitch + 24;
         strings[index].pluck();
      }
   }
    
   //pre: provide a character called string
   //post: return true if the string KEYBOARD has that character, return false if 
   //      it doesn't
   public boolean hasString(char string) {
      return (KEYBOARD.indexOf(string) != -1);
   }
    
   //pre: provide a character called string. If the character is not part of the 
   //      KEYBOARD string (in other words returns false in the hasString() method) 
   //      , an IllegalArgumentException is thrown)
   //post: Plucks the string that corresponds with the character provided 
   public void pluck(char string) {
      if (!hasString(string)) {
         throw new IllegalArgumentException();
      }
      int index = KEYBOARD.indexOf(string);
      strings[index].pluck();  
   }
   
   //post: returns the sum of the first element of the ring buffers associated with 
   //      each of the 37 strings. 
   public double sample() {
      double sum = 0; 
      for (int i = 0; i < SIZE; i++) {
         sum += strings[i].sample();
      }
      return sum;
   }
   
   //post: Applies the tic() method which applies the Karplus-Strong update once on 
   //    each of the 37 strings
   public void tic() {
      for (int i = 0; i < SIZE; i++) {
         strings[i].tic();         
      }
      tics++;
   }
   
   //post: determines the number of times the tic() method is called
   public int time() {
      return tics;  
   }   

}

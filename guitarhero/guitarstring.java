//Ashwika Sharma
//07/07/2022

import java.util.*;

//The GuitarString class is a model of the a vibrating guitar string of a certain
//    frequency. The GuitarString object creates a ring buffer. Some methods in 
//    the class change the ring buffer, and others give more information about it. 
public class GuitarString {
   
   private Queue<Double> ringBuffer;
   private int n;
   
   public static final double MIN = -0.5; 
   public static final double MAX = 0.5;
   public static final double ENERGY_DECAY_FACTOR = 0.996;
   
   //pre: Provide a double frequency. If frequency <= 0 or the size of the ring
   //    buffer is less than 2 (which is determined using the frequency value) an
   //    IllegalArgumentException is thrown.
   //post: Constructs a GuitarString with a ringBuffer of a capacity determined 
   //    by the frequency provided and initializes it to all zeros.
   public GuitarString(double frequency) {
      n = (int) (Math.round(StdAudio.SAMPLE_RATE / frequency));
      if (n < 2 || frequency <= 0) {
         throw new IllegalArgumentException();
      }
      ringBuffer = new LinkedList<>();
      for (int i = 0; i < n; i++) {
         ringBuffer.add(0.0);
      } 
   }
   
   //pre: provide an array of doubles init. If the length of init is less than
   //    2, an IllegalArgumentException is thrown
   //post: Constructs a GuitarString,initializes the ring buffer with the 
   //    contents of the provided array. 
   public GuitarString(double[] init) {
      n = init.length;
      if (n < 2) {
         throw new IllegalArgumentException();
      }
      ringBuffer = new LinkedList<>();
      for (int i = 0; i < n; i++) {
         ringBuffer.add(init[i]);
      }
   }
   
   //post: replaces the values of the initial ringBuffer with random numbers 
   //    between -0.5 and 0.5 by deleting the original values and adding in 
   //    new random ones.
   public void pluck() {
      for (int i = 0; i < n; i++) {
         ringBuffer.remove();
         Random rand = new Random();
         double randomNumber = MIN + (MAX - MIN) * rand.nextDouble();           
         ringBuffer.add(randomNumber);
      }   
   }
   
   //post: applies the Karplus-Strong update once. It does this by removing the
   //    first value of the ringBuffer and seeing what the next value is, averaging  
   //    the two and multiplying by the ENERGY_DECAY_FACTOR, and adding that value 
   //    to the end of the ringBuffer.
   public void tic() {
      double first = ringBuffer.remove();
      double second = ringBuffer.peek();
      double addEnd = ((first + second) / 2) * ENERGY_DECAY_FACTOR;   
      ringBuffer.add(addEnd);
   }
   
   //post: returns the front/first value of ringBuffer. 
   public double sample() {
      return ringBuffer.peek();
   }

}

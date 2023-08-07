//Ashwika Sharma                                            
//8/19/2022

import java.io.*;
import java.util.*;

//This class creates a Huffman tree that can be used to create codes for characters in a file
//    and can decode the codes to compress and decompress files.
public class HuffmanTree {

   private HuffmanNode overallRoot;
   
   //pre: Provide an array of integers count which stores frequencies of characters
   //post: Constructs a Huffman tree using the frequencies and types of characters provided
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> q = new PriorityQueue<HuffmanNode>();
      overallRoot = construct(count, q);
   }
   
   //pre: Provide an array of integers count which stores frequencies of characters,
   //    and a HuffmanNode queue q 
   //post: Returns a HuffmanNode to put in the queue.
   private HuffmanNode construct(int[] count, Queue<HuffmanNode> q) {
      int length = count.length;
      for (int i = 0; i < length; i++) {
         if (count[i] > 0) {
            q.add(new HuffmanNode(count[i], i));
         }
      }
      q.add(new HuffmanNode(1, length));
      HuffmanNode combined = null;
      while (q.size() != 1) {
         HuffmanNode first = q.remove();
         HuffmanNode second = q.remove();
         combined = new HuffmanNode(first.count + second.count, 0, first, second);
         q.add(combined);
      }
      return combined;
   }
   
   //pre: Provide a PrintStream output                                                        
   //post: Writes the integer value of each of the letters followed by their code in the 
   //    standard format.
   public void write(PrintStream output) {
      write(output, overallRoot, "");
   }
   
   //pre: Provide a PrintStream output, a HuffmanNode root, and a string code                 
   //post: Writes the integer value of each of the letters followed by their code in the 
   //    standard format.
   private void write(PrintStream output, HuffmanNode root, String code) {
      if (root != null) {
         if (root.left == null && root.right == null) {
            output.println(root.letter);
            output.println(code);
         } else {
            write(output, root.left, code + "0");
            write(output, root.right, code + "1");
         }
      }
   }
   
   //pre: Provide a scanner input in standard format                           
   //post: Constructs a HuffmanTree using the input
   public HuffmanTree(Scanner input) {
      while (input.hasNext()) {
         int n = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = constructNode(overallRoot, n, code);
      } 
   }
   
   //pre: Provide a HuffmanNode root, an integer n, and a string code                  
   //post: Returns a HuffmanNode that makes up the HuffmanTree
    private HuffmanNode constructNode(HuffmanNode root, int n, String code) {
      if (root == null) {
         root = new HuffmanNode(0, n);
      }
      if (!code.equals("") && code.charAt(0) == '1') {
         root.right = constructNode(root.right, n, code.substring(1));
      } else if (!code.equals("") && code.charAt(0) == '0') {
         root.left = constructNode(root.left, n, code.substring(1));
      }
      return root;
   } 
   
   //pre: Provide a BitInputStream input, a PrintStream output, and an integer eof value
   //post: Decodes the values in the input stream and writes their respective characters 
   //    in the PrintStream output. It knows to stop when it reaches the eof value, which 
   //    is not printed into the output. 
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode curr = overallRoot;
      int end = 0;
      while (end != eof) {
         if (curr.left == null && curr.right == null) {
            end = curr.letter;
            if (end != eof) { 
               output.write(curr.letter);
            }
            curr = overallRoot;
         }
         int val = input.readBit();
         if (val == 0) {
            curr = curr.left;
         } else if (val == 1) {
            curr = curr.right;
         }
      }
   }

   //This class creates a single HuffmanNode                                      
   private static class HuffmanNode implements Comparable<HuffmanNode>{
      public int count;
      public int letter;
      public HuffmanNode left;
      public HuffmanNode right;
      
      //pre: Provide an integer count and an integer letter.                              
      //post: Creates a HuffmanNode with the provided count and letter values.
      public HuffmanNode(int count, int letter) {
         this.count = count;
         this.letter = letter;
      }
      
      //pre: Provide an integer count, an integer letter, a HuffmanNode left, and a HuffmanNode right.   
      //post: Creates a HuffmanNode with the provided count and letter as well as a left HuffmanNode
      //    subtree and a right HuffmanNode subtree
      public HuffmanNode(int count, int letter, HuffmanNode left, HuffmanNode right) {
         this.count = count;
         this.letter = letter;
         this.left = left;
         this.right = right;
      }
      
      //pre: Provide a HuffmanNode other
      //post: Compares this HuffmanNode and the other HuffmanNode and returns either 0 
      //    if both this HuffmanNode and the other HuffmanNode are considered equal, -1
      //    if this HuffmanNode is considered to go before the other HuffmanNode, or 1
      //    is this HuffmanNode is considered to go after the other HuffmanNode.
      public int compareTo(HuffmanNode other) {
         return this.count - other.count;
      }
   }   
}

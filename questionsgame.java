//Ashwika Sharma
//8/11/2022
import java.io.*;
import java.util.*;
//This class represents a game of 20 questions. It allows the user to play a 
//    game of 20 questions with provided questions and answers or from scratch,
//    allowing the user to provide new questions and answers if the computer 
//    gets it wrong.
public class QuestionsGame {
   
   private QuestionNode overallRoot;
   private Scanner console;
   
   //post: Initializes a new QuestionsGame representing computer
   public QuestionsGame() {
      overallRoot = new QuestionNode("computer");
      console = new Scanner(System.in);
   }
   
   //pre: Provide a scanner input. This input is in the standard format/preorder format
   //post: returns QuestionNode based on the input provided. The previously existing tree
   //    is replaced with an updated tree
   public void read(Scanner input) {
      overallRoot = readHelper(input);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
   }
   
   //pre: Provide a scanner input. This input is in the standard format/preorder format
   //post: returns QuestionNode based on the input provided. The previously existing tree
   //    is replaced with an updated tree
   private QuestionNode readHelper(Scanner input) {
      String type = "";
      String data = "";
      type = input.nextLine();
      data = input.nextLine();
      QuestionNode root = new QuestionNode(data);
      if (type.equalsIgnoreCase("a:")) {
         root.left = null;
         root.right = null;
      } else if (type.equalsIgnoreCase("q:")) {
         root.left = readHelper(input);
         root.right = readHelper(input);
      }
      return root;
   }
   
   //pre: Provide a PrintStream output. 
   //post: Stores the tree to an output file. It is written in a format
   //     that has Q: preceding each question and A: preceding each answer and is
   //     in a preorder traversal
   public void write(PrintStream output) {
      writeHelper(overallRoot, output);
   }
   
   //pre: Provide a QuestionNode root and a PrintStream output.
   //post: Stores the tree to an output file. It is written in a format
   //    that has Q: preceding each question and A: preceding each answer and is
   //    in a preorder traversal
   private void writeHelper(QuestionNode root, PrintStream output) {
      if (root.left == null && root.right == null) {
         output.println("A:");
         output.println(root.data);
      } else {
         output.println("Q:");
         output.println(root.data);
         writeHelper(root.left, output);
         writeHelper(root.right, output);
      }
   }
   
   //post: Plays the 20 questions game with the user. Asks yes/no questions until
   //    reaches an answer. If the computer wins/guesses the correct object successfuly,
   //    a message saying so is printed. Otherwise, the user is asked for the object 
   //    they were thinking of, a question to distinguish it, and whether the answer to 
   //    that question is yes or no. Updates the tree with this new information. 
   public void askQuestions() {
      overallRoot = askQuestionsHelper(overallRoot);
   }
   
   //pre: Provide a questionNode root
   //post: Plays the 20 questions game with the user. Asks yes/no questions until
   //    reaches an answer. If the computer wins/guesses the correct object successfuly,
   //    a message saying so is printed. Otherwise, the user is asked for the object 
   //    they were thinking of, a question to distinguish it, and whether the answer to 
   //    that question is yes or no. Updates the tree with this new information. 
   private QuestionNode askQuestionsHelper(QuestionNode root) {
      if (root.left != null) {
         if (yesTo(root.data)) {
            root.left = askQuestionsHelper(root.left);
         } else {
            root.right = askQuestionsHelper(root.right);
         }
      } else {
         if (yesTo("Would your object happen to be " + root.data + "?")) {
            System.out.println("Great, I got it right!");
         } else {
            System.out.print("What is the name of your object? ");
            String object = console.nextLine();
            System.out.println("Please give me a yes/no question that");
            System.out.print("distinguishes between your object\nand mine--> ");
            String question = console.nextLine();
            QuestionNode userObject = new QuestionNode(object);
            if (yesTo("And what is the answer for your object? ")) {
               root = new QuestionNode(question, userObject, root);
            } else {
               root = new QuestionNode(question, root, userObject);
            }
         }
      }
      return root;
   }
   
   // post: asks the user a question, forcing an answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   private boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
 
   //This class creates a single question node.
   private static class QuestionNode {
      public String data;
      public QuestionNode left;
      public QuestionNode right; 
      
      //pre: Provide a string data.
      //post: Creates a leaf node with the given data. 
      public QuestionNode(String data) {
         this(data, null, null);
      } 
      //pre: Provide a string data, a Question Node left, and a Question node right. 
      //post: Creates a question node with the provided data with a left QuestionNode subtree
      //    and a right QuestionNode subtree.
      public QuestionNode(String data, QuestionNode left, QuestionNode right) {
         this.data = data;
         this.left = left;
         this.right = right;
      }
   }
   
}

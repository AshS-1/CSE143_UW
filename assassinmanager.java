//Ashwika Sharma
//7/14/2022

import java.util.*;

//The AssassinManager class models a game of Assassin, keeping track of 
//    who is stalking whom in the kill ring, as well as who is killed
//    and by whom, and can also perform other methods to get more 
//    information about this class. 
public class AssassinManager {

   private AssassinNode killFront;
   private AssassinNode graveFront;

   public static final int KILLRING = 1;
   public static final int GRAVEYARD = 0;
   
   //pre: Passes through a list of string names. If the list size is empty
   //    , an IllegalArgumentException is thrown.
   //post: Creates an AssassinManager on the people whose names were provided
   //    in the list in the same order as the list had the names originally.
   public AssassinManager(List<String> names) {
      if (names.size() == 0) {
         throw new IllegalArgumentException();
      }
      killFront = new AssassinNode(names.get(0), null);
      AssassinNode current = killFront;
      for (int i = 1; i < names.size(); i++) {
         current.next = new AssassinNode(names.get(i), null);
         current = current.next;
      }
   }
   
   //post: Prints the names of the people who are alive/in the kill ring, as
   //     well as who is stalking them in the order that they are in the kill 
   //     ring. If the game is over, then the result is that the remaining 
   //     player is stalking himself/herself. 
   public void printKillRing() {
      AssassinNode current = killFront;
      String first = current.name;
      while (current != null) {
         if (current.next == null) {
            String finalName = current.name;
            System.out.println("    " + finalName + " is stalking " + first); 
         } else {
            System.out.println("    " + current.name + " is stalking " + current.next.name);
         }
         current = current.next;
      }
   }
   
   //post: Prints the names of those who have been killed/in the graveyard, as
   //    well as the person who had killed them, in opposite order of assassination 
   //    (the most recent assassination is printed first). There is no output if no
   //    one is killed/the graveyard is empty. Results are printed with an indent.
   public void printGraveyard() {
      AssassinNode current = graveFront;
      while (current != null) {
         System.out.println("    " + current.name + " was killed by " + current.killer);
         current = current.next;
      }
   } 
   
   //pre: Provide a string name.
   //post: Returns true if the kill ring contains the provided string name, otherwise
   //     returns false. The case of the provided name is ignored.
   public boolean killRingContains(String name) {
      return contains(KILLRING, name);
   } 
   
   //pre: provide a string name.
   //post: Returns true if the graveyard has the provided string name, otherwise 
   //    returns false. The case of the name is ignored.
   public boolean graveyardContains(String name) {
      return contains(GRAVEYARD, name);
   } 
   
   //post: returns true if the game is over/only one person remains alive in the
   //    kill ring, otherwise returns false.
   public boolean gameOver() {
      return (killFront.next == null); 
   }
   
   //post: returns the winner of the game if it is over, otherwise returns null 
   //    if the game is not over yet
   public String winner() {
      if (this.gameOver()) {
         return killFront.name;
      }
      return null;
   }
   
   //pre: provide a string name
   //post: If the game is over, throw IllegalStateException, or if the name is not 
   //    part of the kill ring, throw IllegalArguementException. IllegalStateException 
   //    takes precedence if both the game is over and the name is not part of the kill ring. 
   //    Performs the assassination of the person noted, and moves them from the kill ring 
   //    to the front of the graveyard without disruption to the order of the other people
   //    in both the kill ring and graveyard. Ignores case, and also updates the assassinated 
   //    person's killer to the person who assassinated them
   public void kill(String name1) {
      if (gameOver()) {
         throw new IllegalStateException();
      }
      if (!killRingContains(name1)) {
         throw new IllegalArgumentException();
      }
      AssassinNode temp = killFront;
      if (temp.name.equalsIgnoreCase(name1)) {  
         AssassinNode tempGrave = killFront;
         killFront = tempGrave.next;
         if (graveFront == null) {
            tempGrave.next = null;
         } else {
            tempGrave.next = graveFront;
         }
         graveFront = tempGrave;
         setKiller();   
      } else {                  
         while (!temp.next.name.equalsIgnoreCase(name1)) {        
            temp = temp.next;
         }
         AssassinNode temp2 = null;
         temp2 = temp.next.next;
         temp.next.killer = temp.name;
         AssassinNode tempGrave = graveFront;
         graveFront = temp.next;
         temp.next = temp2;
         if (graveFront == null) {
            graveFront.next = null;
         } else {
            graveFront.next = tempGrave;
         }
      } 
   }
   
   //post: assigns the person who had died with the name of their killer.
   private void setKiller() {
      AssassinNode current = killFront;
      if (current.next == null) {
         graveFront.killer = current.name;
      }
      while (current.next != null) {
         current = current.next;
         graveFront.killer = current.name;
      }
   }

   //pre: pass an int identifier and a string name. 
   //post: checks whether either the kill ring or the graveyard (which one 
   //    its checking is specified with the value of the identifier) has the  
   //    provided string name. 
   private boolean contains(int identifier, String name) {
      AssassinNode current;
      if (identifier == KILLRING) {
         current = killFront;
      } else {
         current = graveFront;
      }
      while (current != null) {
         if (current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }  
      return false;
   }

}

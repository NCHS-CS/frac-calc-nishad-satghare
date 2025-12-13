// Nishad
// Period 6
// Fraction Calculator Project

import java.util.*;

// TODO: Description of what this program does goes here.
public class FracCalc {

   // It is best if we have only one console object for input
   public static Scanner console = new Scanner(System.in);
   
   // This main method will loop through user input and then call the
   // correct method to execute the user's request for help, test, or
   // the mathematical operation on fractions. or, quit.
   // DO NOT CHANGE THIS METHOD!!
   public static void main(String[] args) {
   
      // initialize to false so that we start our loop
      boolean done = false;
      
      // When the user types in "quit", we are done.
      while (!done) {
         // prompt the user for input
         String input = getInput();
         
         // special case the "quit" command
         if (input.equalsIgnoreCase("quit")) {
            done = true;
         } else if (!UnitTestRunner.processCommand(input, FracCalc::processCommand)) {
        	   // We allowed the UnitTestRunner to handle the command first.
            // If the UnitTestRunner didn't handled the command, process normally.
            String result = processCommand(input);
            
            // print the result of processing the command
            System.out.println(result);
         }
      }
      
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompt the user with a simple, "Enter: " and get the line of input.
   // Return the full line that the user typed in.
   public static String getInput() {
      System.out.print("Enter: ");
      String input = console.nextLine();
      return input;
   }
   
   // processCommand will process every user command except for "quit".
   // It will return the String that should be printed to the console.
   // This method won't print anything.
   // DO NOT CHANGE THIS METHOD!!!
   public static String processCommand(String input) {

      if (input.equalsIgnoreCase("help")) {
         return provideHelp();
      }
      
      // if the command is not "help", it should be an expression.
      // Of course, this is only if the user is being nice.
      return processExpression(input);
   }
   
   // Lots work for this project is handled in here.
   // Of course, this method will call LOTS of helper methods
   // so that this method can be shorter.
   // This will calculate the expression and RETURN the answer.
   // This will NOT print anything!
   // Input: an expression to be evaluated
   //    Examples: 
   //        1/2 + 1/2
   //        2_1/4 - 0_1/8
   //        1_1/8 * 2
   // Return: the fully reduced mathematical result of the expression
   //    Value is returned as a String. Results using above examples:
   //        1
   //        2 1/8
   //        2 1/4
   public static String processExpression(String input) {
      Scanner parser = new Scanner(input);
      String firstFraction = "";
      String operator = "";
      String secondFraction = "";
      while(parser.hasNext()){
         firstFraction = parser.next();
         operator = parser.next();
         secondFraction = parser.next();
      }

      int whole1 = getWhole(firstFraction);
      int numerator1 = getNumerator(firstFraction);
      int denominator1 = getDenominator(firstFraction);

      int whole2 = getWhole(secondFraction);
      int numerator2 = getNumerator(secondFraction);
      int denominator2 = getDenominator(secondFraction);

      int impropNum1 = getImproperNumerator(whole1, numerator1, denominator1);
      int impropNum2 = getImproperNumerator(whole2, numerator2, denominator2);

      return (doMath(impropNum1, denominator1, operator, impropNum2, denominator2));
      } 

   public static int getWhole(String fraction){
      if(fraction.contains("_") && fraction.contains("/")){
         return Integer.valueOf(fraction.substring(0, fraction.indexOf("_")));
      }  
      else if(fraction.contains("/")){
         return 0;  
      }
      else if(fraction.contains("_")){
         return Integer.valueOf(fraction.substring(0, fraction.indexOf("_")));
      }
      else{
         return Integer.valueOf(fraction);
      } 
   }

   public static int getNumerator(String fraction){ 
      if(fraction.contains("_") && fraction.contains("/")){
         return Integer.valueOf(fraction.substring((fraction.indexOf("_") + 1), fraction.indexOf("/")));
      }
      else if(fraction.contains("/")){
         return Integer.valueOf(fraction.substring(0, fraction.indexOf("/")));
      }
      else{
         return 0;
      } 
   }

   public static int getDenominator(String fraction){
      if(fraction.contains("/")){
         return Integer.valueOf(fraction.substring((fraction.indexOf("/") + 1)));
      }
      else{
         return 1;
      } 
   }

   public static int getGCD(int a, int b){
      int gcd = 1;
      for(int i = 1; i <= Math.min(a, b); i++){
         if(a % i == 0 && b % i == 0){
            gcd = i;
         }
      }
      return gcd;
   }

   public static int getImproperNumerator(int whole, int numerator, int denominator){
      if(whole < 0){
         return (whole * denominator) - numerator;
      }
      return (whole * denominator) + numerator;
   }

   public static String doMath(int num1, int den1, String op, int num2, int den2){
      int finalNum = 0;
      int finalDen = 0;
      if(op.equals("+")){
         finalNum = (num1 * den2) + (num2 * den1);
         finalDen = den1 * den2;
      }
      else if(op.equals("-")){
         finalNum = (num1 * den2) - (num2 * den1);
         finalDen = den1 * den2;
      }
      else if (op.equals("*")){
         finalNum = num1 * num2;
         finalDen = den1 * den2;
      }
      else if (op.equals("/")){
         finalNum = num1 * den2;
         finalDen = den1 * num2;
      }
      return getFormattedAnswer(finalNum, finalDen);
   }

   public static String getFormattedAnswer(int numerator, int denominator){
      // checking to make sure denominator is positive
      if(denominator < 0){
         numerator *= -1;
         denominator *= -1;
      }

      // Using the Greatest Common Divisor(GCD) to reduce the fraction
      int gcd = getGCD(Math.abs(numerator), Math.abs(denominator));
      numerator /= gcd;
      denominator /= gcd;
      // Calculate whole and remainder 
      int whole = numerator / denominator;
      int remainder = numerator % denominator;

      // Formatting and returning the answer as a string
      if(remainder == 0){
         return Integer.toString(whole);
      }
      else if (whole == 0){
         return remainder + "/" + denominator;
      }
      else{
         return whole + " " + Math.abs(remainder) + "/" + denominator;

      }
   }



   
   
   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
      // TODO: Update this help text!
     
      String help = "Tech Support: \n";
      help += "Students, you need to provide actual helpful text here!";
      
      return help;
   }
}


// Nishad Satghare
// AP Computer Science A
// Period 6
// Fraction Calculator Project: 
// This project is a fraction calculator that takes in a user input with two fractions and an operator. 
// The program will return the result in the most reduced form as a mixed or whole number.
// The program is designed to handle a variety of complexities and acts as a simple, yet effective tool for fractional calculations.

// import the util package
import java.util.*;

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

      // Declaring variables for both fractions and operator
      Scanner parser = new Scanner(input);
      String firstFraction = "";
      String operator = "";
      String secondFraction = "";

      // Parsing user input into three parts: first fraction, operator, second fraction
      while(parser.hasNext()){
         firstFraction = parser.next();
         operator = parser.next();
         secondFraction = parser.next();
      }

      // Getting values for the whole, numerator, and denominator for the first fraction
      // Taking advantage of helper methods to get the values for the fractions
      int whole1 = getWhole(firstFraction);
      int numerator1 = getNumerator(firstFraction);
      int denominator1 = getDenominator(firstFraction);

      // Getting values for the whole, numerator, and denominator for the second fraction
      int whole2 = getWhole(secondFraction);
      int numerator2 = getNumerator(secondFraction);
      int denominator2 = getDenominator(secondFraction);

      // Converting both fractions into improper fractions for calculation
      int impropNum1 = getImproperNumerator(whole1, numerator1, denominator1);
      int impropNum2 = getImproperNumerator(whole2, numerator2, denominator2);

      // Performing the mathematical operation using helper methods and returning the result
      return (doMath(impropNum1, denominator1, operator, impropNum2, denominator2));
   } 

   // This method will get the whole number part of a string fraction
   // Parameter: fraction - a string value that represents the fraction input.
   // Returns: the whole number of the fraction as an integer.
   public static int getWhole(String fraction){
      // Checking for different forms of the fraction to get the whole value accordingly

      // Case 1: the fraction is a mixed number (has both "_" and "/") (example: "3_1/2")
      // The whole number is the part of the string before the "_"
      if(fraction.contains("_") && fraction.contains("/")){
         return Integer.valueOf(fraction.substring(0, fraction.indexOf("_")));
      }  
      // Case 2: the fraction is a simple fraction (just has "/") (example: "7/9")
      // Whole is zero in this case because there is no whole part to the fraction
      else if(fraction.contains("/")){
         return 0;  
      }
      // Case 3: the fraction is a whole number (example: "6")
      // Simply return the int value of that string
      else{
         return Integer.valueOf(fraction);
      } 
   }

   // This method will get the numerator part of a string fraction
   // Parameter: fraction - a string value that represents the fraction input.
   // Returns: the numerator of the fraction as an integer.
   public static int getNumerator(String fraction){ 

      // Case 1: the fraction is a mixed number (has both "_" and "/") (example: "3_1/2")
      // The numerator is in between the "_" and the "/"
      if(fraction.contains("_") && fraction.contains("/")){
         return Integer.valueOf(fraction.substring((fraction.indexOf("_") + 1), fraction.indexOf("/")));
      }
      // Case 2: the fraction is a simple fraction (just has "/") (example: "7/9")
      // The numerator is the part of the string right before the "/"
      else if(fraction.contains("/")){
         return Integer.valueOf(fraction.substring(0, fraction.indexOf("/")));
      }
      // Case 3: the fraction is a whole number (example: "6")
      // Simply return zero as there is no numerator in a whole number
      else{
         return 0;
      } 
   }

   // This method will get the denominator part of a string fraction
   // Parameter: fraction - a string value that represents the fraction input.
   // Returns: the denominator of the fraction as an integer.
   public static int getDenominator(String fraction){

      // Case 1: the fraction is either mixed or simple fraction (has "/") (example: "3_1/2" or "7/9")
      // The denominator is the part of the string after the "/"
      if(fraction.contains("/")){
         return Integer.valueOf(fraction.substring((fraction.indexOf("/") + 1)));
      }
      // Case 2: the fraction is a whole number (example: "6")
      // Since whole numbers technically have a denominator of 1, return 1
      else{
         return 1;
      }
   }

   // This method will find the Greatest Common Divisor(GCD) of two integers
   // Parameters: a and b (both integers)
   // Returns: the Greatest Common Divisor(GCD) of the two integers as an integer.
   public static int getGCD(int a, int b){
      // Start with one as thats the smallest possible GCD
      int gcd = 1;
      // Loop through all possible GCDs from 1 to the smaller of the two numbers
      for(int i = 1; i <= Math.min(a, b); i++){
         // If both numbers are divisible are that i value, then i is the GCD
         if(a % i == 0 && b % i == 0){
            // update gcd to the i value
            gcd = i;
         }
      }
      // return the final GCD
      return gcd;
   }

   // This method will get the imprper numerator of a fraction
   // Parameters: whole, numerator, and denominator (all integers)
   // Returns: the numerator of the improper fraction as an integer.
   public static int getImproperNumerator(int whole, int numerator, int denominator){
      // Case 1: Negative mixed number (example: "-3_1/2")
      // The formula for this case is: (whole * denominator) - numerator
      if(whole < 0){
         return (whole * denominator) - numerator;
      }

      // Case 2: Positive mixed number (example: "3_1/2")
      // The formula for this case is: (whole * denominator) + numerator
      else{
         return (whole * denominator) + numerator;
      }
   }

   // This method will perform the materical operations with the two fractions 
   // Parameters: numerator and denominator for both fractions, and the operator (all strings)
   // Returns: the result of the operation as a string (formatted with the use of a helper method)
   public static String doMath(int num1, int den1, String op, int num2, int den2){
      // Initializing variables for the final numerator and denominator
      int finalNum = 0;
      int finalDen = 0;
      // Performing addition
      if(op.equals("+")){
         finalNum = (num1 * den2) + (num2 * den1);
         finalDen = den1 * den2;
      }
      // Performing subtraction
      else if(op.equals("-")){
         finalNum = (num1 * den2) - (num2 * den1);
         finalDen = den1 * den2;
      }
      // Performing multiplication
      else if (op.equals("*")){
         finalNum = num1 * num2;
         finalDen = den1 * den2;
      }
      // Performing division
      else if (op.equals("/")){
         finalNum = num1 * den2;
         finalDen = den1 * num2;
      }
      // Using the helper method getFormattedAnswer to format the answer properly.
      return getFormattedAnswer(finalNum, finalDen);
   }

   // This method will format the final answer into a string
   // Parameters: numerator and denominator (both integers)
   // Returns: the final answer as a formatted string.
   public static String getFormattedAnswer(int numerator, int denominator){
      // making sure denominator is positive
      if(denominator < 0){
         numerator *= -1;
         denominator *= -1;
      }
      // Getting Greatest Common Divisor(GCD from the helper method getGCD.
      // Using the GCD) to reduce the fraction
      int gcd = getGCD(Math.abs(numerator), Math.abs(denominator));
      numerator /= gcd;
      denominator /= gcd;

      // Calculate whole and remainder 
      int whole = numerator / denominator;
      int remainder = numerator % denominator;

      // If no remainder, return the whole number
      if(remainder == 0){
         return Integer.toString(whole);
      }
      // If no whole number, return the fraction
      else if (whole == 0){
         return remainder + "/" + denominator;
      }
      // If both whole number and remainder, return the mixed number
      else{
         return whole + " " + Math.abs(remainder) + "/" + denominator;
      }
   }

   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
      // TODO: Update this help text!
     
      String help = "Tech Support: \n";
      help += "This calculator performs arithmetic operations on fractions.\n\n";
      help += "INPUT FORMAT:\n";
      help += "  - Whole numbers: 5\n";
      help += "  - Fractions: 3/4\n";
      help += "  - Mixed numbers: 2_1/4 (use underscore, NO spaces)\n";
      help += "  - Negative numbers: -2_1/4 or -3/4\n\n";
      help += "OPERATORS: + (add), - (subtract), * (multiply), / (divide)\n\n";
      help += "EXAMPLES:\n";
      help += "  1/2 + 1/4  ->  3/4\n";
      help += "  2_1/2 * 3  ->  7 1/2\n";
      help += "  5 - 1_1/4  ->  3 3/4\n\n";
      help += "Type 'quit' to exit the program.";
      return help;
   }
}


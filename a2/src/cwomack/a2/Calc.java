package cwomack.a2;

/**
 * Calc class used by Main
 * @author Carter Womack
 * @version 1.0
 */
public class Calc {
    /**
     * Static function that is used toi evaluate a mathematical equation
     * @param line String input that is a math equation
     * @return String that is the answer to given equations
     */
    public static String evaluate(String line) {
        double partA, partB;
        String[] parts = line.split(" ");

        //Make sure we have three parts to our equation
        if(parts.length != 3) {
            return "Invalid input";
        }
        //Make sure parts[0] is a double
        try {
        partA = Double.parseDouble(parts[0]);
        } catch(java.lang.NumberFormatException ex) {
            return "Invalid input";
        }
        //Make sure parts[1] is a mathematical operator
        if(!parts[1].equals("+") && !parts[1].equals("-") && !parts[1].equals("*")
                && !parts[1].equals("/")) {
            return "Invalid input";
        }
        //Make sure parts[2] is a double
        try {
            partB = Double.parseDouble(parts[2]);
        } catch(java.lang.NumberFormatException ex) {
            return "Invalid input";
        }
        //If we make it here, input is good
        switch(parts[1]){
            case "+":
                return Double.toString(partA + partB);
            case "-":
                return Double.toString(partA - partB);
            case "*":
                return Double.toString(partA * partB);
            case "/":
                return Double.toString(partA / partB);
        }
        return "Invalid input";
    }
}

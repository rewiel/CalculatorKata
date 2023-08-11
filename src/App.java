import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws Exception {
        
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        String inputString = buffer.readLine();
        String[] splitInput = inputString.split(" ");

        if (splitInput.length != 3) {
            throw new IllegalArgumentException("Has incorrect format");
        }

        String firstOperand = splitInput[0];
        String operator = splitInput[1];
        String secondOperand = splitInput[2];

        if (!isOperandValid(firstOperand) || !isOperandValid(secondOperand)) {
            throw new IllegalArgumentException("Operands must be numbers from 1 to 10");
        }
        
        boolean isArabic = isArabic(firstOperand) && isArabic(secondOperand);
        boolean isRoman = isRoman(firstOperand) && isRoman(secondOperand);

        if (!isArabic && !isRoman) {
            throw new IllegalArgumentException("Operands must be Arabic or Roman symbols");
        }

        int result;
        if (isArabic) {
            int firstNumber = Integer.parseInt(firstOperand);
            int secondNumber = Integer.parseInt(secondOperand);
            result = calculate(firstNumber, operator, secondNumber);
        } else {
            int firstNumber = romanToArabic(firstOperand);
            int secondNumber = romanToArabic(secondOperand);
            result = calculate(firstNumber, operator, secondNumber);
        }

        String output;
        if (isArabic) {
            output = String.valueOf(result);
        } else {
            output = arabicToRoman(result);
        }
        System.out.println(output);
    }
    private static boolean isOperandValid(String operand) {
        
        boolean conditionResult;
        
        try {
            int number = Integer.parseInt(operand);
            conditionResult = number >= 1 && number <= 10;
        } catch (NumberFormatException e) {
            int number = romanToArabic(operand);
            conditionResult = number >= 1 && number <= 10;
        }

        return conditionResult;
    }

    private static boolean isArabic(String operand) {
        try {
            Integer.parseInt(operand);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRoman(String operand) {
        String romanNumerals = "IVXLCDM";
        for (char c : operand.toUpperCase().toCharArray()) {
            if (!romanNumerals.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    private static int calculate(int firstOperand, String operator, int secondOperand) {
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "*":
                return firstOperand * secondOperand;
            case "/":
                return firstOperand / secondOperand;
            default:
                throw new IllegalArgumentException("Oparator is incorrect");
        }
    }

    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
            .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
            .collect(Collectors.toList());
        }
        
    }

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;
    
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
    
        int i = 0;
    
        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }
    
        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
        }
    
        return result;
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }
    
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
    
        int i = 0;
        StringBuilder sb = new StringBuilder();
    
        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
    
        return sb.toString();
    }
}

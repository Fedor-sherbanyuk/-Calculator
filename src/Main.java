import java.util.*;


public class Main {


    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            System.out.println(calc(s));
        }
    }

    public static String calc(String input) throws Exception {

        String stringNumerals = system(input);

        String[] strings = stringNumerals.split("\s");
        if (strings[0].equals("1")) {

            switch (strings[2]) {
                case "+" -> {
                    int add = Integer.parseInt(strings[1]) + Integer.parseInt(strings[3]);
                    return "" + add;
                }
                case "-" -> {
                    int subtract = Integer.parseInt(strings[1]) - Integer.parseInt(strings[3]);
                    return "" + subtract;
                }
                case "/" -> {
                    int division = (int) Math.round(Integer.parseInt(strings[1]) / Integer.parseInt(strings[3]));
                    return "" + division;
                }
                case "*" -> {
                    int multiplication = Integer.parseInt(strings[1]) * Integer.parseInt(strings[3]);
                    return "" + multiplication;
                }
            }
            return strings[2];
        } else if (strings[0].equals("I")) {

            TreeMap<String, String> roman_numerals = mapSmall();

            String suma = "";
            Integer number_1 = Integer.parseInt(roman_numerals.get(strings[1]));
            Integer number_2 = Integer.parseInt(roman_numerals.get(strings[3]));
            switch (strings[2]) {
                case "+" -> suma = String.valueOf(number_1 + number_2);
                case "-" -> suma = String.valueOf(number_1 - number_2);
                case "/" -> suma = String.valueOf((int) Math.round(number_1 / number_2));
                case "*" -> suma = String.valueOf(number_1 * number_2);
            }
            if (suma.equals("0")) {
                throw new Exception("т.к. в римской системе нет числа 0");
            }
            if (suma.matches("\\d{3}")) {
                return "C";
            } else if (suma.matches("\\d{2}") && Integer.parseInt(suma) > 10) {
                return functionBig(suma);
            } else {
                return functionSmall(suma, roman_numerals);
            }

        } else {
            throw new Exception("Ошибка, ошибка, ошибочка");
        }

    }
    public static String system(String inputSystem) throws Exception {

        if (inputSystem == null || inputSystem.equals("") || inputSystem.matches("\\s")) {
            throw new Exception("т.к. строка пустая");
        }
        List<String> arabic_numerals = new ArrayList<>();
        TreeMap<String, String> roman_numerals = mapSmall();

        for (int i = 1; i <= 10; i++) {
            arabic_numerals.add("" + i);
        }
        String[] strings = inputSystem.split("\s");

        if (strings.length < 3) {
            throw new Exception("т.к. строка не является математической операцией");
        } else if (strings.length > 3) {
            throw new Exception("т.к. формат математической операции не удовлетворяет заданию - " +
                    "два операнда и один оператор (+, -, /, *)");
        }
        if (arabic_numerals.contains(strings[0]) && arabic_numerals.contains(strings[2])) {
            return "1" + " " + inputSystem;
        }

        if (roman_numerals.containsKey(strings[0]) && roman_numerals.containsKey(strings[2])) {

            if (strings[1].equals("-") &&
                    Integer.parseInt(roman_numerals.get(strings[0])) < Integer.parseInt(roman_numerals.get(strings[2]))) {
                throw new Exception("т.к. в римской системе нет отрицательных чисел");
            }
            return "I" + " " + inputSystem;
        } else if (roman_numerals.containsKey(strings[0]) && arabic_numerals.contains(strings[2]) ||
                roman_numerals.containsKey(strings[2]) && arabic_numerals.contains(strings[0])) {
            throw new Exception("т.к. используются одновременно разные системы счисления");
        } else {
            throw new Exception("т.к. строка не является математической операцией");
        }
    }

    public static String functionBig(String s) throws Exception {
        TreeMap<String, String> roman_numerals = mapSmall();
        roman_numerals.putAll(mapBig());
        int sum = Integer.parseInt(s);
        String number = "" + (sum % 10);
        if (roman_numerals.containsValue(s)) {
            return functionSmall(s, roman_numerals);
        }
        for (int i = 11; i <= sum; i++) {
            if (i < 21 && sum == i) {
                return "X" + functionSmall(number, roman_numerals);
            } else if (i < 31 && i >= 21 && sum == i) {
                return "XX" + functionSmall(number, roman_numerals);
            } else if (i < 41 && i >= 31 && sum == i) {

                return "XXX" + functionSmall(number, roman_numerals);
            } else if (i < 51 && i >= 41 && sum == i) {

                return "XL" + functionSmall(number, roman_numerals);
            } else if (i < 61 && i >= 51 && sum == i) {

                return "L" + functionSmall(number, roman_numerals);
            } else if (i < 71 && i >= 61 && sum == i) {

                return "LX" + functionSmall(number, roman_numerals);
            } else if (i < 81 && i >= 71 && sum == i) {

                return "LXX" + functionSmall(number, roman_numerals);
            } else if (i < 91 && i >= 81 && sum == i) {

                return "LXXX" + functionSmall(number, roman_numerals);
            } else if (i < 100 && i >= 91 && sum == i) {

                return "XC" + functionSmall(number, roman_numerals);
            }
        }
        throw new Exception("Ошибка, ошибка, ошибочка");
    }
    public static String functionSmall(String s, TreeMap<String, String> roman_numerals) throws Exception {
        for (Map.Entry<String, String> o : roman_numerals.entrySet()) {
            String key = o.getKey();
            String value = o.getValue();
            if (s.equals(value)) {
                return key;
            }
        }
        throw new Exception("Ошибка, ошибка, ошибочка");
    }
    public static TreeMap<String, String> mapBig() {
        TreeMap<String, String> roman_numerals = new TreeMap<>();
        roman_numerals.put("XX", "20");
        roman_numerals.put("XXX", "30");
        roman_numerals.put("XL", "40");
        roman_numerals.put("L", "50");
        roman_numerals.put("LX", "60");
        roman_numerals.put("LXX", "70");
        roman_numerals.put("LXXX", "80");
        roman_numerals.put("XC", "90");
        return roman_numerals;
    }
    public static TreeMap<String, String> mapSmall() {
        TreeMap<String, String> roman_numerals = new TreeMap<>();
        roman_numerals.put("I", "1");
        roman_numerals.put("II", "2");
        roman_numerals.put("III", "3");
        roman_numerals.put("IV", "4");
        roman_numerals.put("V", "5");
        roman_numerals.put("VI", "6");
        roman_numerals.put("VII", "7");
        roman_numerals.put("VIII", "8");
        roman_numerals.put("IX", "9");
        roman_numerals.put("X", "10");
        return roman_numerals;
    }
}
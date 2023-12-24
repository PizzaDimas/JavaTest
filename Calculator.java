import java.util.Scanner;

/**
 * @author pizza
 * 24.12.2023
 */
public class Calculator {
    public static void main(String[] args) throws Exception {
        System.out.println(calc(checkData(enterInput())));
    }
    public static String calc(String input){                                                                            // вычисляет значение
        try {
            String[] arraySymbol = input.split(" ");
            switch (arraySymbol[1]) {
                case "+":
                    return Integer.toString(Integer.parseInt(arraySymbol[0]) + Integer.parseInt(arraySymbol[2]));
                case "*":
                    return Integer.toString(Integer.parseInt(arraySymbol[0]) * Integer.parseInt(arraySymbol[2]));
                case "-":
                    return Integer.toString(Integer.parseInt(arraySymbol[0]) - Integer.parseInt(arraySymbol[2]));
                case "/":
                    return Integer.toString(Integer.parseInt(arraySymbol[0]) / Integer.parseInt(arraySymbol[2]));
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String enterInput(){                                                                                  // возвращает данные с консоли
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        return input;
}
    private static String checkData(String input) throws Exception{                                                      // проверяет строку на ограничения
        try{
            String[] arraySymbol = input.split(" ");
            if (arraySymbol.length != 3) {
                throw new Exception();
            }
            if (Integer.parseInt(arraySymbol[0]) > 10 || Integer.parseInt(arraySymbol[0]) < 0){
                throw new Exception();
            }
            if (Integer.parseInt(arraySymbol[2]) > 10 || Integer.parseInt(arraySymbol[2]) < 0){
                throw new Exception();
            }
            if (!"*/+-".contains(arraySymbol[1])){
                throw new Exception();
            }
            return input;
        }
        catch (Exception e){
            throw new Exception();
        }
    }
}

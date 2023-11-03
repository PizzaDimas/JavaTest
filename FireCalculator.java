package fire;

/**
 * @author pizza
 * @date 02.11.2023 - 14:10
 */
public class FireCalculator {

    public static void main(String[] args) throws Exception {

        Capital capital = new Capital();                        // создаем объект типа капитал, чтобы узнать каков будет максимальный процент изъятия капитала.
        capital.enterYearAndCalculate();                        // вызываем метод ввода года и расчет макс процентной ставки

    }

}

package fire;

import java.util.Scanner;

/**
 * @author pizza
 * @date 02.11.2023 - 14:30
 */
public class Capital {
    /*
     * Основная идея состоит в том чтобы циклом перебрать все возможные процентные ставки (с шагом 0.5) начиная
     * с 0.5 и до максимальной, процент ставки после которой гарантированно приводит к полному истощению капитала.
     *
     * После ввода с консоли происходит проверка на тип и соответсвие интервалу возможных значений.
     *
     */
    /**
     * При вводе в консоль года в интервале от 2002 до 2021 включительно калькулятор посчитает
     * максимальную процентную ставку изъятия капитала, при которой владелец капитала гарантированно
     * сможет покрывать свои базовые потребности до 2022 года.
     */

    private int startYear;                                                                                      // первый год жизни с капиталом
    private int leftYearInterval = 2001;                                                                        // левая невключенная граница интервала значений стартового года
    private int rightYearInterval = 2022;                                                                       // правая невключенная граница интервала значений стартового года


    private int getStartYear() throws Exception {
        // здесь идет реализация ввода года начала жизни на проценты с консоли
        try {
            Scanner in = new Scanner(System.in);
            startYear = in.nextInt();
            return startYear;
        } catch (Exception e) {
            throw new Exception("acceptable interval: [2002-2021]");
        }
    }

    private boolean checkYear(int startYear) {
        // здесь идет реализация статуса соотвествия введенного года начала на соответсвие допустимому интервалу.
        return ((startYear < rightYearInterval) && (startYear > leftYearInterval));
    }

    public void enterYearAndCalculate() throws Exception {
        // здесь идет проверка статуса соответсвия введенного года, а также печать результатов главного метода
        try {
            if (!checkYear(getStartYear())) {
                throw new Exception("acceptable interval: [2002-2021]");
            }
            System.out.println(findMaxPercent());
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage());
        }
    }

    private double passCapitalThroughYear(double amountOfCapital, double baseExpenses, int j) {
        int nextYear = startYear - leftYearInterval + j;                                                         // индекс массива для значения индекса биржи  будущего года
        int thatYear = startYear - leftYearInterval - 1 + j;                                                     // индекс массива для значения индекса уходящего года
        amountOfCapital = amountOfCapital - baseExpenses;                                                        // В начале года происходит списание расходов на год
        amountOfCapital = amountOfCapital                                                                        // Индексация капитала на индекс биржи
                * (Constants.MOEX_RATE[nextYear]
                / Constants.MOEX_RATE[thatYear]);
        return amountOfCapital;
    }

    private double indexBaseExpenses(double baseExpenses, int j) {
        int thatYear = startYear - leftYearInterval - 1 + j;                                                    // индекс массива для значения инфляции уходящего года
        return (baseExpenses * (1 + (Constants.INFLATION_RATE[thatYear]) / 100));                               // Индексация базовых расходов на инфлюцию в конце года
    }

    private double findMaxPercent() {
        // здесь реализован главный метод класса, поиск максимальной процентной ставки
        double percent;                                                                                         // Процентная ставка
        double amountOfCapital;                                                                                 // остаток капитала
        double baseExpenses;                                                                                    // базовые расходы
        double step = 0.5;                                                                                      // шаг изыскания
        double decimalPercent;                                                                                  // десятичный процент
        // цикл увеличивает процентную ставку на 0.5 в случае успешной итерации
        for (percent = step; (true); percent += step) {
            // условия для итерации с новой процентной ставкой
            amountOfCapital = 100;
            decimalPercent = percent / 100;
            baseExpenses = decimalPercent * amountOfCapital;                                                    // Базовые расходы, которые составляют изначально 4% от капитала
            // цикл уменьшает каждый год капитал на размер базовых расходов, проводит индексацию
            for (int j = 0; j < (rightYearInterval - startYear); j++) {
                amountOfCapital = passCapitalThroughYear(amountOfCapital, baseExpenses, j);
                baseExpenses = indexBaseExpenses(baseExpenses, j);
                if (amountOfCapital < 0) {
                    return (percent - step);                                                                    // При остановке цикла изза истощения капитала возвращается предыдущее значение ставки
                }
            }
        }
    }
}

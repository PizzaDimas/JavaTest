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

    private int startYear;                                         // первый год жизни с капиталом

    public int getStartYear() throws Exception {
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
        return ((startYear < 2022) && (startYear > 2001));
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

    private double passCapitalThroughYear(double amountOfCapital, double baseExpenses, int j){
        amountOfCapital = amountOfCapital - baseExpenses;                                              // В начале года происходит списание расходов на год
        amountOfCapital = amountOfCapital                                                              // Индексация капитала на индекс биржи
                * (Constants.MOEX_RATE[startYear - 2002 + 1 + j]
                / Constants.MOEX_RATE[startYear - 2002 + j]);
        return amountOfCapital;
    }

    private double indexBaseExpenses(double baseExpenses, int j){
        return (baseExpenses * (1 + (Constants.INFLATION_RATE[startYear - 2002 + j]) / 100));          // Индексация базовых расходов на инфлюцию в конце года
    }
    public double findMaxPercent() {
        // здесь реализован главный метод класса, поиск максимальной процентной ставки
        double percent;                                                                                // Процентная ставка
        double amountOfCapital;
        double baseExpenses;
        // цикл увеличивает процентную ставку на 0.5 в случае успешной итерации
        for (percent = 0.5; (true); percent += 0.5) {
            // условия для итерации с новой процентной ставкой
            amountOfCapital = 100;
            baseExpenses = percent / 100 * amountOfCapital;                                            // Базовые расходы, которые составляют изначально 4% от капитала
            // цикл уменьшает каждый год капитал на размер базовых расходов, проводит индексацию
            for (int j = 0; j < (2022 - startYear); j++) {
                amountOfCapital = passCapitalThroughYear(amountOfCapital, baseExpenses, j);
                baseExpenses = indexBaseExpenses(baseExpenses, j);
                if (amountOfCapital < 0) {
                    return (percent - 0.5);                                                             // При остановке цикла изза истощения капитала возвращается предыдущее значение ставки
                }
            }
        }
    }
}

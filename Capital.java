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

    private int startYear;
    final private int LEFT_INTERVAL_BORDER = 2001;                                                 // левая невключенная граница интервала значений стартового года
    final private int RIGHT_INTERVAL_BORDER = 2022;                                                // правая невключенная граница интервала значений стартового года
    final private double STEP = 0.5;

    private int getStartYear() throws Exception {
        try {
            Scanner in = new Scanner(System.in);
            startYear = in.nextInt();
            return startYear;
        } catch (Exception e) {
            throw new Exception("Invalid type of value entered. Acceptable interval: [2002-2021]");
        }
    }

    private boolean checkYear(int startYear) {
        return ((startYear < RIGHT_INTERVAL_BORDER) && (startYear > LEFT_INTERVAL_BORDER));
    }

    public void enterYearAndCalculate() throws Exception {
        if (!checkYear(getStartYear())) {
            throw new Exception("Value out of range. Acceptable interval: [2002-2021]");
        }
        System.out.println(findMaxPercent());
    }

    private double passCapitalThroughYear(double amountOfCapital, double baseExpenses, int j) {
        int nextYearIndex = startYear - LEFT_INTERVAL_BORDER + j;
        int thatYearIndex = startYear - LEFT_INTERVAL_BORDER - 1 + j;
        amountOfCapital = amountOfCapital - baseExpenses;
        amountOfCapital = amountOfCapital * (Constants.MOEX_RATE[nextYearIndex] / Constants.MOEX_RATE[thatYearIndex]);
        return amountOfCapital;
    }

    private double indexBaseExpenses(double baseExpenses, int j) {
        int thatYearIndex = startYear - LEFT_INTERVAL_BORDER - 1 + j;
        return (baseExpenses * (1 + (Constants.INFLATION_RATE[thatYearIndex]) / 100));             // индексация на инфляцию базовых доходов
    }

    private double findMaxPercent() {
        double percent;
        double amountOfCapital;
        double baseExpenses;
        double decimalPercent;
        for (percent = STEP; (true); percent += STEP) {
            // условия для итерации с новой процентной ставкой
            amountOfCapital = 100;
            decimalPercent = percent / 100;
            baseExpenses = decimalPercent * amountOfCapital;
            for (int j = 0; j < (RIGHT_INTERVAL_BORDER - startYear); j++) {                        // цикл уменьшает каждый год капитал на размер базовых расходов, проводит индексацию капитала
                amountOfCapital = passCapitalThroughYear(amountOfCapital, baseExpenses, j);
                baseExpenses = indexBaseExpenses(baseExpenses, j);
                if (amountOfCapital < 0) {
                    return (percent - STEP);                                                       // При остановке цикла изза истощения капитала возвращается предыдущее значение ставки
                }
            }
        }
    }
}

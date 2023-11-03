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
    private double amountOfCapital = 100;                          // размер капитала
    private int startYear;                                         // первый год жизни с капиталом

    public Capital(int startYear) {
        this.startYear = startYear;
    }

    public Capital() {
    }

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

    public boolean checkYear(int startYear) {
        // здесь идет реализация статуса соотвествия введенного года начала на соответсвие допустимому интервалу.
        return (((startYear < 2022) && (startYear > 2001)) ? true : false);
    }

    public void enterYearAndCalculate() throws Exception {
        // здесь идет проверка статуса соответсвия введенного года, а также печать результатов главного метода
        try {
            if (!checkYear(getStartYear())) throw new Exception("acceptable interval: [2002-2021]");
            System.out.println(findMaxPercent());
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage());
        }
    }

    public double findMaxPercent() {
        // здесь реализован главный метод класса, поиск максимальной процентной ставки для соответсвия условиям задачи
        double percent = 0.5;                                                                                  // Процентная ставка
        double baseExpenses = percent / 100 * amountOfCapital;                                                 // Базовые расходы, которые составляют изначально 4% от капитала
        // цикл увеличивает процентную ставку на 0.5 в случае успешной итерации
        for (int i = 0; (amountOfCapital > 0); i++) {

//           System.out.println(" 11111 remained start year " + amountOfCapital
//                                                            + " "
//                                                            + baseExpenses);

            // цикл уменьшает каждый год капитал на размер базовых расходов, проводит индексацию по индексу мос биржи и инфляции
            for (int j = 0; j < (2022 - startYear); j++) {

//                 System.out.println("Start year " + amountOfCapital
//                                                  + " - expenses ("
//                                                  + baseExpenses
//                                                  + ") = "
//                                                  + (amountOfCapital - baseExpenses));

                amountOfCapital = amountOfCapital - baseExpenses;                                             // В начале года происходит списание расходов на год
                // Так как данных за 2002 год по индексу мос биржи недостаточно для понимания динамики изменения капитала, предполагаем что за 2002 нет изменения рынка.
                if ((startYear == 2002) && (j == 0)) {
                    amountOfCapital = amountOfCapital
                            * 1
                            * (1 - (Constants.INFLATION_RATE[startYear - 2002 + j] / 100));

//                    System.out.println("Percents and inflation remained " + (j + startYear)
//                                                                          + " year = "
//                                                                          + amountOfCapital);
                } else {
                    amountOfCapital = amountOfCapital
                            * (Constants.MOEX_RATE[startYear - 2002 + j]
                            / Constants.MOEX_RATE[startYear - 2002 - 1 + j]
                            * (1 - (Constants.INFLATION_RATE[startYear - 2002 + j] / 100)));

//                    System.out.println("Percents and inflation remained " + (j + startYear)
//                                                                          + " year = "
//                                                                          + amountOfCapital);

                }
                baseExpenses = baseExpenses * (1 + (Constants.INFLATION_RATE[startYear - 2002 + j]) / 100);     // Индексация базовых расходов на инфлюцию в конце года
                if (amountOfCapital < 0) return (percent - 0.5);                                                // При остановке цикла изза истощения капитала возвращается предыдущее значение ставки
            }
            // условия для итерации с новой процентной ставкой
            amountOfCapital = 100;
            percent += 0.5;
            baseExpenses = percent / 100 * amountOfCapital;
        }
        return percent;
    }
}

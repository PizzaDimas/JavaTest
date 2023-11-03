package fire;

/**
 * Вспомогательный класс с данными, на основе которых нужно производить расчет
 * Данные могут отличаться от реальных (эксраполированы или округлены)
 */
public class Constants {

    //Индекс мос биржи с 2002 по 2022 сверху вниз
    public static final double[] MOEX_RATE = {
            417.42,
            673.72,
            722.81,
            1323.32,
            2216.63,
            2472.38,        // 2007
            810.922,        // 2008
            1793.24,        // 2009
            2209.46,        // 2010
            1835.14,        // 2011
            1934.43,        // 2012
            1967.83,        // 2013
            1828.06,        // 2014
            2305.50,        // 2015
            3042.00,        // 2016
            3015.71,
            3564.05,
            4887.25,
            5567.28,
            6731.43,
            4170.35
    };

    //Инфляция с 2002 по 2022 сверху вниз
    public static final double[] INFLATION_RATE = {
            15.06,
            11.99,
            11.74,
            10.91,
            9.00,
            11.87,  // 2007
            13.28,  // 2008
            8.80,   // 2009
            8.78,   // 2010
            6.10,   // 2011
            6.58,   // 2012
            6.45,   // 2013
            11.36,  // 2014
            12.91,
            5.38,
            2.52,
            4.27,
            3.05,
            4.91,
            8.39,
            11.92,
    };

}
package com.rodrigo.cashflowapi.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Calc {


    public static double fixed(double valor){

        BigDecimal valorBigDecimal = new BigDecimal(valor);
        valorBigDecimal = valorBigDecimal.setScale(2, RoundingMode.HALF_UP);

        double valorFormatado = valorBigDecimal.doubleValue();

        return valorFormatado;
    }
}

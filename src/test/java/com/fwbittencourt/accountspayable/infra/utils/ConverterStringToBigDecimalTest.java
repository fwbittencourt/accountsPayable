package com.fwbittencourt.accountspayable.infra.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * @autor Filipe Bittencourt on 10/06/24
 */
class ConverterStringToBigDecimalTest {

    /*Exemplo de casos em R$ 1.000,00*/
    @Test
    void testConvertToBigDecimal00vZZ() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("01,00");
        Assertions.assertEquals(new BigDecimal("1"), result);
    }

    @Test
    void testConvertToBigDecimal00v00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("01,05");
        Assertions.assertEquals(new BigDecimal("1.05"), result);
    }

    @Test
    void testConvertToBigDecimal0p000() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1.001");
        Assertions.assertEquals(new BigDecimal("1001"), result);
    }

    @Test
    void testConvertToBigDecimal0p000v0() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1.000,5");
        Assertions.assertEquals(new BigDecimal("1000.5"), result);
    }

    @Test
    void testConvertToBigDecimal0p000v0D() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1.234,50");
        Assertions.assertEquals(new BigDecimal("1234.5"), result);
    }

    @Test
    void testConvertToBigDecimal0p000v00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("5.678,54");
        Assertions.assertEquals(new BigDecimal("5678.54"), result);
    }

    @Test
    void testConvertToBigDecimal0p000p000v00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1.123.456,53");
        Assertions.assertEquals(new BigDecimal("1123456.53"), result);
    }


    /*Exemplo de casos em R$ 1,000.00*/
    @Test
    void testConvertToBigDecimal00pZZ() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("01.00");
        Assertions.assertEquals(new BigDecimal("1"), result);
    }

    @Test
    void testConvertToBigDecimal0V00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1.05");
        Assertions.assertEquals(new BigDecimal("1.05"), result);
    }

    @Test
    void testConvertToBigDecimal00V00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("01.05");
        Assertions.assertEquals(new BigDecimal("1.05"), result);
    }

    @Test
    void testConvertToBigDecimal0v000() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1,000");
        Assertions.assertEquals(0, result.compareTo(new BigDecimal("1000")));
    }

    @Test
    void testConvertToBigDecimal0v000p0() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1,000.5");
        Assertions.assertEquals(new BigDecimal("1000.5"), result);
    }

    @Test
    void testConvertToBigDecimal0v000p00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1,000.54");
        Assertions.assertEquals(new BigDecimal("1000.54"), result);
    }

    @Test
    void testConvertToBigDecimal0v000p0D() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1,000.50");
        Assertions.assertEquals(new BigDecimal("1000.5"), result);
    }

    @Test
    void testConvertToBigDecimal0v000v000p00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1,123,000.53");
        Assertions.assertEquals(new BigDecimal("1123000.53"), result);
    }

    /*Exemplo de casos em R$ 1000.00*/
    @Test
    void testConvertToBigDecimal0p00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("105");
        Assertions.assertEquals(new BigDecimal("105"), result);
    }

    @Test
    void testConvertToBigDecimal0000p0() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1000.5");
        Assertions.assertEquals(new BigDecimal("1000.5"), result);
    }

    @Test
    void testConvertToBigDecimal0000p00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1000.54");
        Assertions.assertEquals(new BigDecimal("1000.54"), result);
    }

    @Test
    void testConvertToBigDecimal0000p0D() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1000.50");
        Assertions.assertEquals(new BigDecimal("1000.5"), result);
    }

    @Test
    void testConvertToBigDecimal0000000p0D() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1123000.53");
        Assertions.assertEquals(new BigDecimal("1123000.53"), result);
    }

    /*Exemplo de casos em R$ 1000,00*/
    @Test
    void testConvertToBigDecimal0000v0() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1000,5");
        Assertions.assertEquals(new BigDecimal("1000.5"), result);
    }

    @Test
    void testConvertToBigDecimal0000v00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1000,54");
        Assertions.assertEquals(new BigDecimal("1000.54"), result);
    }

    @Test
    void testConvertToBigDecimal0000v0D() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1000,50");
        Assertions.assertEquals(new BigDecimal("1000.5"), result);
    }

    @Test
    void testConvertToBigDecimal0000000v0D() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1123000,53");
        Assertions.assertEquals(new BigDecimal("1123000.53"), result);
    }

    @Test
    void testConvertToBigDecimal000v000() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("123,456");
        Assertions.assertEquals(new BigDecimal("123456"), result);
    }

    @Test
    void testConvertToBigDecimal0p000p00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1,234,31");
        Assertions.assertEquals(new BigDecimal("1234.31"), result);
    }

    @Test
    void testConvertToBigDecimal0v000v00() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("1.234.31");
        Assertions.assertEquals(new BigDecimal("1234.31"), result);
    }

    @Test
    void testConvertToBigDecimal000v000v() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("112,300,");
        Assertions.assertEquals(0, result.compareTo(new BigDecimal("112300")));
    }

    @Test
    void testConvertToBigDecimal000p000p() throws ParseException {
        BigDecimal result = ConverterStringToBigDecimal.convertToBigDecimal("112.300.");
        Assertions.assertEquals(0, result.compareTo(new BigDecimal("112300")));
    }
}

package com.fwbittencourt.accountspayable.infra.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Valores monetários <br>
 * Separador milhar: {@code . ou ,} <br>
 * Separador decimal: {@code , ou .} <br>
 * A parte decimal é opcional, pode ter 1 ou 2 dígitos. <br>
 * O separador de milhar é opcional, se tiver, tem que ser a cada 3 dígitos. <br>
 * Exemplos:
 *
 * <pre>
 * 1
 * 1,5
 * 1,05
 * 3.195,12
 * </pre>
 * <pre>
 * 1.5
 * 1.05
 * 3,195.12
 * </pre>
 * <pre>
 * 105
 * 1050,5
 * 3195.12
 * 1,050,5
 * 3.195.12
 * </pre>
 * @autor Filipe Bittencourt on 10/06/24
 */
public class ConverterStringToBigDecimal {

    private static final String COMMA = ",";
    private static final String PERIOD = "\\.";
    private static final String US_REGEX_PATTERN = "^\\d{1,3}(,\\d{3})*(\\.\\d{1,2})?$";
    private static final String BRAZILIAN_REGEX_PATTERN = "^\\d{1,3}(\\.\\d{3})*(,\\d{1,2})?$";

    private ConverterStringToBigDecimal() {
    }


    public static BigDecimal convertToBigDecimal(final String value) throws ParseException {
        String cleanedValue = prepareValueToProcess(value);
        String formatType = detectFormat(value);
        final NumberFormat format;

        switch (formatType) {
            case "BR":
                format = NumberFormat.getNumberInstance(Locale.forLanguageTag("pt-BR"));
                break;
            case "US":
                format = NumberFormat.getNumberInstance(Locale.US);
                break;
            default:
                format = getPersonalizedFormat(cleanedValue);
                break;
        }

        final Number parsedNumber = format.parse(cleanedValue);
        return new BigDecimal(parsedNumber.toString()).stripTrailingZeros();
    }

    private static String detectFormat(final String value) {
        if (Pattern.matches(US_REGEX_PATTERN, value)) {
            return "US";
        } else if (Pattern.matches(BRAZILIAN_REGEX_PATTERN, value)) {
            return "BR";
        } else {
            return "Unknown";
        }
    }

    private static NumberFormat getPersonalizedFormat(final String value) {
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        if (value.contains(COMMA)) {
            symbols.setDecimalSeparator(',');
        } else {
            symbols.setDecimalSeparator('.');
        }
        final DecimalFormat df = new DecimalFormat("####.#", symbols);
        df.setParseBigDecimal(true);
        return df;
    }

    private static String prepareValueToProcess(String value) {
        String newValue = value;
        if (countOccurrences(value, COMMA) >= 2) {
            newValue = value.replaceFirst(COMMA, "");
            prepareValueToProcess(newValue);
        }
        if (countOccurrences(value, PERIOD) >= 2) {
            newValue = value.replaceFirst(PERIOD, "");
            prepareValueToProcess(newValue);
        }
        return newValue;
    }

    private static int countOccurrences(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}

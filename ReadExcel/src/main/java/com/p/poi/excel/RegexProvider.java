package com.p.poi.excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Class provides list of RegEx patterns.
 */
public class RegexProvider {

    /**
     * Map stores locale based months and days name RegEx patterns
     */
    private final static Map<String, ArrayList<String>> LOCALE_MONTHS_DAYS = Collections
            .unmodifiableMap(new HashMap<String, ArrayList<String>>() {
                /** serialVersionUID */
                private static final long serialVersionUID = 6613904493602382401L;

                {
                    put("en", new ArrayList<>(Arrays.asList(
                            "Jan(uary)?|Feb(ruary)?|Mar(ch)?|Apr(il)?|May|Jun(e)?|Jul(y)?|Aug(ust)?|Sep(t|tember)?|Oct(ober)?|Nov(ember)?|Dec(ember)?",
                            "(Sun|Mon|(T(ues|hurs))|Fri)(day|\\.)?$|Wed(\\.|nesday)?$|Sat(\\.|urday)?$|T((ue?)|(hu?r?))\\.?")));

                    put("fr", new ArrayList<>(Arrays.asList(
                            "janv(ier)?|févr(ier)?|mars|avril|mai|juin|juil(let)?|août|sept(embre)?|oct(obre)?|nov(embre)?|déc(embre)?",
                            "lundi|mardi|mercredi|jeudi|vendredi|samedi|dimanche")));

                    put("de", new ArrayList<>(Arrays.asList(
                            "J(a|ä)n(uar)?|Feb(ruar)?|März|Apr(il)?|Mai|Juni|Juli|Aug(ust)?|Sept(ember)?|Okt(ober)?|Nov(ember)?|Dez(ember)?",
                            "Montag|Dienstag|Mittwoch|Donnerstag|Freitag|Samstag|Sonntag")));

                    put("es", new ArrayList<>(Arrays.asList(
                            "enero|feb(rero)?|marzo|abr(il)?|mayo|jun(io)?|jul(io)?|agosto|se(t|pt|ptiembre)|oct(ubre)?|nov(iembre)?|dic(iembre)?",
                            "lunes|martes|miércoles|jueves|viernes|sábado|domingo")));

                    put("it", new ArrayList<>(Arrays.asList(
                            "gen(naio)?|febbr(aio)?|mar(zo)?|apr(ile)?|magg(io)?|giugno|luglio|ag(osto)?|sett(embre)?|ott(obre)?|nov(embre)?|dic(embre)?",
                            "lunedì|martedì|mercoledì|giovedì|venerdì|sabato|domenica")));

                    put("nl", new ArrayList<>(Arrays.asList(
                            "jan(uari)?|feb(ruari)?|maart|apr(il)?|mei|juni|juli|aug(ustus)?|sept(ember)|okt(ober)?|nov(ember)?|dec(ember)?",
                            "maandag|dinsdag|woensdag|donderdag|vrijdag|zaterdag|zondag")));

                    put("sv", new ArrayList<>(Arrays.asList(
                            "jan(uari)?|febr(uari)?|mars|april|maj|juni|juli|aug(usti)?|sept(ember)?|okt(ober)?|nov(ember)?|dec(ember)?",
                            "måndag|tisdag|onsdag|torsdag|fredag|lördag|söndag")));

                    put("fi", new ArrayList<>(Arrays.asList(
                            "tammikuu|helmikuu|maaliskuu|huhtikuu|toukokuu|kesäkuu|heinäkuu|elokuu|syyskuu|lokakuu|marraskuu|joulukuu",
                            "maanantai|tiistai|keskiviikko|torstai|perjantai|lauantai|sunnuntai")));

                    put("da", new ArrayList<>(Arrays.asList(
                            "jan(uar)?|feb(ruar)?|marts|april|maj|juni|juli|aug(ust)?|sept(ember)?|okt(ober)?|nov(ember)?|dec(ember)?",
                            "mandag|tirsdag|onsdag|torsdag|fredag|lørdag|søndag")));

                }
            });

    // ==================================================================================================================================//
    // Amount RegEx
    // ==================================================================================================================================//

    // Amount Format : starting or trailing any currency symbol
    // format for decimal is fractional separator, comma is thousand separator
    // format for comma is fractional separator, decimal is thousand separator
    private static String AMOUNT_SIMPLE = "((\\d{1,3}[\\,\\.]{0,1}){0,4}\\d{1,3})?([\\.\\,]\\d{1,2})";
    private static String AMOUNT_WITH_SYMBOL = "\\p{Sc}?\\s*((\\d{1,3}[\\,\\.]{0,1}){0,4}\\d{1,3})?([\\.\\,]\\d{1,2})\\s*\\p{Sc}?";
    private static String AMOUNT_COMPLEX = "(\\p{L}+)?\\p{Sc}((\\d{1,3}[\\,\\.]{0,1}){0,4}\\d{1,3})?([\\.\\,]\\d{1,2})";

    // ==================================================================================================================================//
    // Date RegEx
    // ==================================================================================================================================//

    // NUMERIC_DATE_FORMAT: This RegEx will match dates with numeric format
    // DD-MM-YY(YY) or DD/MM/YY(YY) or DD.MM.YY(YY) or DD\MM\YY(YY) or DD MM YY(YY)
    // MM-DD-YY(YY) or MM/DD/YY(YY) or MM.DD.YY(YY) or MM\DD\YY(YY) or or MM DD
    // YY(YY)
    // (YY)YY-MM-DD or (YY)YY/MM/DD or (YY)YY.MM.DD or (YY)YY\MM\DD or (YY)YY MM DD
    // (YY)YY-DD-MM or (YY)YY/DD/MM or (YY)YY.DD.MM or (YY)YY\DD\MM or (YY)YY MM MM
    private static String NUMERIC_DATE_FORMAT = "(\\d{1,2}%1$s\\d{1,2}%1$s\\d{2,4})|\\d{4}%1$s\\d{1,2}%1$s\\d{1,2}";

    // TEXT DATE FORMAT: This RegEx will match dates with month names in their long
    // or short hand formats (case insensitive)
    // DD-MONTH-YY(YY) or DD/MONTH/YY(YY) or DD.MONTH.YY(YY) or DD\MONTH\YY(YY) or
    // DD MONTH YY(YY)
    // DD. MONTH YY(YY) or DD, MONTH YY(YY)
    // MONTH-DD-YY(YY) or MONTH/DD/YY(YY) or MONTH.DD.YY(YY) or MONTH\DD\YY(YY) or
    // MONTH DD YY(YY)
    // MONTH-DD, YYYY or MONTH DD, YYYY
    private static String TEXT_DATE_FORMAT = "(\\d{1,2}%1$s?(([\\.\\,]\\s)|%2$s)(?i:%3$s)%2$s\\d{2,4}|(?i:%3$s)%2$s\\d{1,2}%1$s?%2$s\\d{2,4})|(?i:%3$s)((-|\\s)\\d{2,4}\\,?\\s?\\d{4})";

    // DATE TIME: This RegEx will match various time formats
    // HH:MM:SS or HH:MM:SS AM|PM or HH:MM or HH:MM AM|PM
    private static String TIME_FORMAT = "([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?(\\s?[AaPp][Mm])?";

    // DAY OF THE WEEK: This RegEx will match the days of the week in their long or
    // short hand formats (case insensitive)
    private static String DAYS = "(?i:%s)";

    // MONTHS: This RegEx will match month names in their long or short hand formats
    // (case insensitive)
    private static String MONTHS = "(?i:%s)";

    // DATE_SEPARATORS: This RegEx will match any of the ordinal indicators
    private static String DATE_ORDINAL_INDICATORS = "(th|rd|st|nd)";

    // DATE_SEPARATORS: This RegEx will match any of the separators
    // whitespace, hyphen, forward slash, backward slash
    private static String DATE_SEPARATORS = "[\\s\\-\\/\\.\\\\]";

    // ==================================================================================================================================//
    // Generic RegEx
    // ==================================================================================================================================//

    // Single word : number
    public static final String SINGLE_NUMBER = "\\d+";

    // Single word : number with characters '_', '-', ':', ',', '#'
    public static final String SINGLE_NUMBER_COMPLEX = "[\\d\\-\\:\\_\\,\\#]+";

    // Single word : alphabetic word
    public static final String SINGLE_WORD_ALPHA = "\\p{L}+";

    // Single word : alphabetic word with characters '_', '-', ':', ',', '#'
    public static final String SINGLE_WORD_ALPHA_COMPLEX = "[\\p{L}\\-\\:\\_\\,\\#]+";

    // Single word : without any digits (only word and special chars)
    public static final String STRING_NON_NUMERIC = "[^\\d]+";

    // Single word : alphanumeric
    public static final String SINGLE_ALPHANUMERIC_WORD = "\\w+";

    // String : digits
    public static final String STRING_DIGIT = "[\\d\\s]+";

    // String : Alpha numeric
    public static final String STRING_ALPHA_NUMERIC = "[\\w\\s]+";

    // String : Alpha numeric with characters '_', '-', ':'
    public static final String STRING_ALPHA_NUMERIC_COMPLEX = "[\\w\\-\\:\\,\\#\\s]+";

    public static final String ALPHA_NUMERIC_STRING_WITH_ALL_SPECIAL_CHARACTERS = "^([\\p{L}0-9\\s-\\:]+)(.*)$";
    public static final String ALPHA_NUMERIC_STRING_STARTING_WITH_LETTER = "^([\\p{L}\\s\\-\\:]+)([\\p{L}0-9\\s\\-\\:]+)(.*)$";

    // Generic Regex
    public static final String GENERIC_REGEX = ".+";

    // Matches text with alphabets and optionally ending with characters '-', ':',
    // '#', 'currency symbol'
    public static final String SINGLE_WORD_OPT_SPC_CHARS = "\\p{L}+([\\-\\:\\#\\p{Sc}]$)?";

    // Matches text starting with alphabetic chars
    public static final String START_WITH_ALPHA_WORD = "^\\p{L}+";

    // Matches text ending with alphabetic chars
    public static final String END_WITH_ALPHA_WORD = "\\p{L}+$";

    // Matches any whitespace character (equivalent to [\r\n\t\f\v ])
    public static final String WHITE_SPACE_REGEX = "\\s";

    // Matches any numeric/alphabet character
    public static final String NUMBER_OR_ALPHABET_REGEX = "[\\w\\d]";

    // Assert position at a word boundary
    public static final String WORD_BOUNDARY_REGEX = "\\b";

    // Case insensitive match (ignores case of [a-zA-Z])
    public static final String CASE_INSENSITIVE_REGEX = "(?i)";

    // Regex to extract the page number from Image Name
    public static final String EXTRACT_PAGE_NUMBER_REGEX = "(?<=_PG)\\d+";


    /**
     * This method returns a map of regex pool.
     *
     * @return
     */
    public static Map<String, Integer> getRegexMap() {
        // Months list at index '0'
        String monthsName = LOCALE_MONTHS_DAYS.get("en").get(0);
        // Days list at index '1'
        String daysName = LOCALE_MONTHS_DAYS.get("en").get(1);

        Map<String, Integer> regexMap = new LinkedHashMap<>();
        regexMap.put(AMOUNT_SIMPLE, 0);
        regexMap.put(AMOUNT_WITH_SYMBOL, 0);
        regexMap.put(AMOUNT_COMPLEX, 0);
        regexMap.put(String.format(NUMERIC_DATE_FORMAT, DATE_SEPARATORS), 0);
        regexMap.put(String.format(TEXT_DATE_FORMAT, DATE_ORDINAL_INDICATORS, DATE_SEPARATORS, monthsName), 0);
        regexMap.put(TIME_FORMAT, 0);
        regexMap.put(String.format(MONTHS, monthsName), 0);
        regexMap.put(String.format(DAYS, daysName), 0);
        regexMap.put(SINGLE_NUMBER, 0);
        regexMap.put(SINGLE_NUMBER_COMPLEX, 0);
        regexMap.put(SINGLE_WORD_ALPHA, 0);
        regexMap.put(SINGLE_WORD_ALPHA_COMPLEX, 0);
        regexMap.put(STRING_NON_NUMERIC, 0);
        regexMap.put(STRING_DIGIT, 0);
        regexMap.put(STRING_ALPHA_NUMERIC, 0);
        regexMap.put(STRING_ALPHA_NUMERIC_COMPLEX, 0);

        return regexMap;
    }
}


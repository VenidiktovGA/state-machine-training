package ru.venidiktov.state.machine.training.parser.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс достает из html строки конкретные символы
 */
@Slf4j
public class ContentParser {

    private final static Pattern PATTERN_DATE = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
    private final static Pattern PATTERN_URL = Pattern.compile("a  href=\"([a-zA-Z0-9/\\-]*)\"");
    private final static Pattern PATTERN_TITLE = Pattern.compile("\">(.*)</a>");
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public LocalDate parseDate(String line) {
        var matcher = PATTERN_DATE.matcher(line);
        if (matcher.find()) {
            var dateAsString = matcher.group(0);
            var date = LocalDate.parse(dateAsString, DATE_FORMATTER);
            log.info("date:{}", date);
            return date;
        }
        return null;
    }

    public String parseUrl(String line) {
        var matcher = PATTERN_URL.matcher(line);
        if (matcher.find()) {
            var url = matcher.group(1);
            log.info("url:{}", url);
            return url;
        }
        return null;
    }

    public String parseTitle(String line) {
        var matcher = PATTERN_TITLE.matcher(line);
        if (matcher.find()) {
            var title = matcher.group(1);
            log.info("title:{}", title);
            return title;
        }
        return null;
    }
}

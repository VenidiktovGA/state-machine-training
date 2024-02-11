package ru.venidiktov.state.machine.training.parser.simple;

import lombok.extern.slf4j.Slf4j;
import ru.venidiktov.state.machine.training.parser.Parser;
import ru.venidiktov.state.machine.training.parser.model.Content;
import ru.venidiktov.state.machine.training.parser.model.ListNews;
import ru.venidiktov.state.machine.training.parser.sm.State;
import ru.venidiktov.state.machine.training.parser.util.ContentParser;

/**
 * HTML сам по себе регулярным языком не является по этому конечным автоматом его не распарсить,
 * однако мы в рамках задачи мы работаем не с html а с ограниченным набором символов, которые
 * будем отслеживать в виде строк файла. Поэтому мы будим считать что язык с которам мы работаем регулярный
 * и на основании этого мы применяем конечный детерминированный автомат.
 */
@Slf4j
public class ParserSimpleStateMachine implements Parser {

    private State currentState = State.BEGIN; // Задаем первоначальное состояние

    private final ContentParser contentParser = new ContentParser();

    @Override
    public ListNews parse(Content content) {
        log.info("content for parser:{}", content);
        var lines = content.value().split("\n");
        var result = new ListNews();
        for (var line : lines) {
            switch (currentState) {
                case BEGIN -> {
                    /**
                     * Для перехода из начального состояния в следующее нам нужно найти фрагмент в html строке который мы будем парсить
                     */
                    if (line.contains("id=\"cont_wrap\"")) currentState = State.CONT_WRAP;
                }
                case CONT_WRAP -> {
                    if (line.contains("class=\"time\"")) currentState = State.TIME; // Нашли div с class="time"
                    if (line.contains("</div>"))
                        currentState = State.BEGIN; // Конец div и в нем нет интересующих нас элементов
                }
                case TIME -> {
                    if (line.contains("class=\"listitem\""))
                        currentState = State.LIST_ITEM; // Далее надо будет извлекать название новости
                    if (line.contains("class=\"data\"")) { // В строке есть дата
                        var currentNews = result.addAndGetNew();
                        currentNews.data(contentParser.parseDate(line));
                    }
                }
                case LIST_ITEM -> {
                    log.info("newsLine:{}", line);
                    var currentNews = result.getLast().orElseThrow(() -> new IllegalStateException("currentNews can't be null"));
                    currentNews.url(contentParser.parseUrl(line));
                    currentNews.title(contentParser.parseTitle(line));
                }
                case LIST_ITEM_END -> {
                    if (line.contains("</div>")) currentState = State.CONT_WRAP;
                }
            }
        }
        return result;
    }
}

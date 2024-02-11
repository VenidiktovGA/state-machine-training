package ru.venidiktov.state.machine.training.parser.impl.complex;

import lombok.extern.slf4j.Slf4j;
import ru.venidiktov.state.machine.training.parser.impl.Parser;
import ru.venidiktov.state.machine.training.parser.model.Content;
import ru.venidiktov.state.machine.training.parser.model.ListNews;
import ru.venidiktov.state.machine.training.parser.util.ContentParser;
import ru.venidiktov.state.machine.training.sm.lib.State;
import ru.venidiktov.state.machine.training.sm.lib.StateMachineEngin;

@Slf4j
public class ParserComplexStateMachine implements Parser {

    private final ContentParser contentParser = new ContentParser();

    // Возможное состояния конечного автомата
    private final State<String, ListNews> begin = new State<>("begin");
    private final State<String, ListNews> contWrap = new State<>("contWrap");
    private final State<String, ListNews> time = new State<>("time");
    private final State<String, ListNews> listItem = new State<>("listItem");
    private final State<String, ListNews> listItemEnd = new State<>("listItemEnd");

    @Override
    public ListNews parse(Content content) {
        StateMachineEngin<String, ListNews> parserComplexStateMachine = new StateMachineEngin<>(
                begin, new ListNews(), new DataSourceContent(content)
        );
        return parserComplexStateMachine.doJob();
    }

    public ParserComplexStateMachine() {
        // Задаем функции перехода
        begin.setFunctionNextState(line -> {
            if (line.contains("id=\"cont_wrap\"")) return contWrap;
            return null;
        });
        contWrap.setFunctionNextState(line -> {
            if (line.contains("class=\"time\"")) return time; // Нашли div с class="time"
            if (line.contains("</div>")) return begin; // Конец div и в нем нет интересующих нас элементов
            return null;
        });
        time.setFunctionNextState(line -> {
            if (line.contains("class=\"listitem\"")) return listItem; // Далее надо будет извлекать название новости
            return null;
        });
        listItem.setFunctionNextState(line -> listItemEnd);
        listItemEnd.setFunctionNextState(line -> {
            if (line.contains("</div>")) return contWrap;
            return null;
        });

        // Задаем действия выполняемые на состояниях
        time.setAction((line, currentResult) -> {
            if (line.contains("class=\"data\"")) { // В строке есть дата
                var currentNews = currentResult.addAndGetNew();
                currentNews.data(contentParser.parseDate(line));
            }
        });
        listItem.setAction((line, currentResult) -> {
            log.info("newsLine:{}", line);
            var currentNews = currentResult.getLast().orElseThrow(() -> new IllegalStateException("currentNews can't be null"));
            currentNews.url(contentParser.parseUrl(line));
            currentNews.title(contentParser.parseTitle(line));
        });
    }
}

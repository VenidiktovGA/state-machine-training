package ru.venidiktov.state.machine.training.parser.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListNews {

    private final List<News> list = new ArrayList<>();

    public News addAndGetNew() {
        var news = new News();
        list.add(news);
        return news;
    }

    public Optional<News> getLast() {
        return Optional.ofNullable(list.getLast());
    }
}

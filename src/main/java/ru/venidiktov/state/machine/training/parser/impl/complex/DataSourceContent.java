package ru.venidiktov.state.machine.training.parser.impl.complex;

import ru.venidiktov.state.machine.training.parser.model.Content;
import ru.venidiktov.state.machine.training.sm.lib.DataSource;

public class DataSourceContent implements DataSource<String> {

    private final String[] lines;

    private int currentIdx = 0;

    public DataSourceContent(Content content) {
        lines = content.value().split("\n");
    }

    @Override
    public boolean hasNext() {
        return currentIdx < lines.length;
    }

    @Override
    public String next() {
        return lines[currentIdx++];
    }
}

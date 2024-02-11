package ru.venidiktov.state.machine.training.parser.impl;

import ru.venidiktov.state.machine.training.parser.model.Content;
import ru.venidiktov.state.machine.training.parser.model.ListNews;

public interface Parser {

    ListNews parse(Content content);
}

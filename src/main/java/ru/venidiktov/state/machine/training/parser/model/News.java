package ru.venidiktov.state.machine.training.parser.model;

import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class News {

    private LocalDate data;
    private String url;
    private String title;
}

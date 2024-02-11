package ru.venidiktov.state.machine.training.sm.lib;

/**
 * Источник данных
 *
 * @param <T>
 */
public interface DataSource<T> {
    boolean hasNext();

    T next();
}

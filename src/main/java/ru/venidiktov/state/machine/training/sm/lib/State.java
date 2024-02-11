package ru.venidiktov.state.machine.training.sm.lib;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Состояние конечного автомата
 *
 * @param <T>
 * @param <R>
 */
@Setter
@Getter
@RequiredArgsConstructor
public class State<T, R> {

    private final String name; // Имя состояния

    private Function<T, State<T, R>> functionNextState = null; // Функция перехода в следующее состояние

    private BiConsumer<T, R> action = null; // Действие выполняемое на этом состоянии

    public Optional<State<T, R>> nextState(T inDate) {
        // Если есть функция перехода в новое состояние вызываем ее, если ее нет, то ничего не далаем
        return Optional.ofNullable(functionNextState.apply(inDate));
    }

    public void doAction(T inData, R result) {
        if (action != null) {
            action.accept(inData, result);
        }
    }
}

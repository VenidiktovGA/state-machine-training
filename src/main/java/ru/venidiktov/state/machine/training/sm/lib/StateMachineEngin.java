package ru.venidiktov.state.machine.training.sm.lib;

import lombok.AllArgsConstructor;

/**
 * Движок конечного автомата выполняющий типовые действия, такие как:
 * выполнение действия на состоянии, переход в состояние
 *
 * @param <T> - тип данных который принимает конечный автомат
 * @param <R> - результат который получится по срабатыванию конечного автомата
 */
@AllArgsConstructor
public class StateMachineEngin<T, R> {

    private State<T, R> currentState; // Текущее состояние

    private final R result; // Результат который должен получится в результате срабатывания конечного автомата

    private final DataSource<T> dataSource; // Источник данных или входных сигналов

    /**
     * Работа конечного автомата пока в источнике данных есть данные
     *
     * @return
     */
    public R doJob() {
        while (dataSource.hasNext()) {
            var inData = dataSource.next(); // Достаем единицу данных
            currentState.doAction(inData, result); // Выполняем текущее действие и передаем ему единицу данных
            currentState = currentState.nextState(inData).orElse(currentState); // Изменить или нет статус на основании единицы данных
        }
        return result;
    }
}

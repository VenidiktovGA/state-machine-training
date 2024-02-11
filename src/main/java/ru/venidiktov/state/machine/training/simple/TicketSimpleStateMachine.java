package ru.venidiktov.state.machine.training.simple;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class TicketSimpleStateMachine {

    private STATE currentState = STATE.BEGIN; // Первоначальное состояние билетного аппарата BEGIN

    /**
     * При установке билетный аппарат начинает свою работу с начального действия
     */
    public TicketSimpleStateMachine() {
        currentState.doAction();
    }

    /**
     * Логика работы конечного автомата для билетного аппарата
     * Не валидные команды для состояний игнорируются!
     * В том что конечный автомат не реагирует на не валидные команды заключается одна из парадигм конечных автоматов,
     * так как мы не пытаемся определить все реакции нашей системы на все возможные варианты, у нас есть диаграмма и
     * мы по ней работам.
     *
     * @param cmd - входной символы, команды пользователя
     */
    public void action(CMD cmd) {
        log.info("cmd:{}", cmd);
        switch (currentState) {
            case BEGIN -> {
                if (CMD.COIN == cmd) currentState = STATE.COIN_5;
                currentState.doAction();
            }
            case COIN_5 -> {
                if (CMD.COIN == cmd) currentState = STATE.COIN_10;
                if (CMD.CANCEL == cmd) currentState = STATE.BEGIN;
                currentState.doAction();
            }
            case COIN_10 -> {
                if (CMD.BEGIN == cmd) currentState = STATE.BEGIN;
                currentState.doAction();
            }
        }
    }

    /**
     * Команда которые билетный аппарат может получить из вне
     */
    public enum CMD {BEGIN, COIN, CANCEL}

    /**
     * Состояния в которых билетный аппарат может находится
     * При выполнении действия состояния мы просто печатаем его в лог, но можно например
     * хранить состояние счетчика монет и рпи переходах между состояниями его обновлять
     */
    @RequiredArgsConstructor
    public enum STATE {
        BEGIN(() -> log.info("BEGIN. Waiting for coin")),
        COIN_5(() -> log.info("COIN_5. Waiting for one more coin")),
        COIN_10(() -> log.info("COIN_10. Ticket"));

        private final Runnable action;

        public void doAction() {
            action.run();
        }
    }
}

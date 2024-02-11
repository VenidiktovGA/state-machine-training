package ru.venidiktov.state.machine.training.simple;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class TicketSimpleStateMachineTest {

    @Test
    void ignoreNotValidCommand() {
        var stateMachine = new TicketSimpleStateMachine();

        stateMachine.action(TicketSimpleStateMachine.CMD.COIN);
        stateMachine.action(TicketSimpleStateMachine.CMD.BEGIN);

        assertThat(stateMachine.getCurrentState()).isEqualTo(TicketSimpleStateMachine.STATE.COIN_5);
    }

    @Test
    void successScenarioToCoin10() {
        var stateMachine = new TicketSimpleStateMachine();

        stateMachine.action(TicketSimpleStateMachine.CMD.COIN);
        stateMachine.action(TicketSimpleStateMachine.CMD.COIN);

        assertThat(stateMachine.getCurrentState()).isEqualTo(TicketSimpleStateMachine.STATE.COIN_10);
    }

    @Test
    void successScenarioToBeginThrowCanselCommand() {
        var stateMachine = new TicketSimpleStateMachine();

        stateMachine.action(TicketSimpleStateMachine.CMD.COIN);
        stateMachine.action(TicketSimpleStateMachine.CMD.CANCEL);

        assertThat(stateMachine.getCurrentState()).isEqualTo(TicketSimpleStateMachine.STATE.BEGIN);
    }

    @Test
    void successScenarioToBegin() {
        var stateMachine = new TicketSimpleStateMachine();

        stateMachine.action(TicketSimpleStateMachine.CMD.COIN);
        stateMachine.action(TicketSimpleStateMachine.CMD.COIN);
        stateMachine.action(TicketSimpleStateMachine.CMD.BEGIN);

        assertThat(stateMachine.getCurrentState()).isEqualTo(TicketSimpleStateMachine.STATE.BEGIN);
    }

}
package ru.venidiktov.state.machine.training.manual;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class TicketManualStateMachineTest {

    @Test
    void ignoreNotValidCommand() {
        var stateMachine = new TicketManualStateMachine();

        stateMachine.action(TicketManualStateMachine.CMD.COIN);
        stateMachine.action(TicketManualStateMachine.CMD.BEGIN);

        assertThat(stateMachine.getCurrentState()).isEqualTo(TicketManualStateMachine.STATE.COIN_5);
    }

    @Test
    void successScenarioToCoin10() {
        var stateMachine = new TicketManualStateMachine();

        stateMachine.action(TicketManualStateMachine.CMD.COIN);
        stateMachine.action(TicketManualStateMachine.CMD.COIN);

        assertThat(stateMachine.getCurrentState()).isEqualTo(TicketManualStateMachine.STATE.COIN_10);
    }

    @Test
    void successScenarioToBeginThrowCanselCommand() {
        var stateMachine = new TicketManualStateMachine();

        stateMachine.action(TicketManualStateMachine.CMD.COIN);
        stateMachine.action(TicketManualStateMachine.CMD.CANCEL);

        assertThat(stateMachine.getCurrentState()).isEqualTo(TicketManualStateMachine.STATE.BEGIN);
    }

    @Test
    void successScenarioToBegin() {
        var stateMachine = new TicketManualStateMachine();

        stateMachine.action(TicketManualStateMachine.CMD.COIN);
        stateMachine.action(TicketManualStateMachine.CMD.COIN);
        stateMachine.action(TicketManualStateMachine.CMD.BEGIN);

        assertThat(stateMachine.getCurrentState()).isEqualTo(TicketManualStateMachine.STATE.BEGIN);
    }

}
package bankomatEmulator.command;

import bankomatEmulator.exception.InterruptOperationException;

interface Command {
    void execute() throws InterruptOperationException;
}

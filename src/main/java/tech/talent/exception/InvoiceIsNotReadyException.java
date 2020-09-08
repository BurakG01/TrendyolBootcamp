package tech.talent.exception;

public class InvoiceIsNotReadyException extends RuntimeException {
    public InvoiceIsNotReadyException() { super("One month must expire to pay the bill");
    }
}

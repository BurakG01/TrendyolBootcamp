package tech.talent.exception;

public abstract class LockedException extends RuntimeException {
    protected LockedException(String message){super(message);}
}

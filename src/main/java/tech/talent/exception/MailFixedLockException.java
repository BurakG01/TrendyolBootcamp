package tech.talent.exception;

public class MailFixedLockException extends LockedException{
    public MailFixedLockException(String message) {
        super(message);
    }
}

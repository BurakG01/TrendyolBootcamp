package tech.talent.exception;

public class MailLockException extends LockedException{
    public MailLockException() {
        super("Mail");
    }
}

package tech.talent.exception;

public class MailFixedExpiredException extends  ExpiredException{
    public MailFixedExpiredException(String message) {
        super(message);
    }
}

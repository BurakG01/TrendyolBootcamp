package tech.talent.exception;

public class SmsFixedExpiredException extends ExpiredException {

    public SmsFixedExpiredException(String message) {
        super(message);
    }
}

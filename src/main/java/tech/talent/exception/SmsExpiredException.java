package tech.talent.exception;

public class SmsExpiredException extends ExpiredException {

    public SmsExpiredException() {
        super("SMS");
    }
}

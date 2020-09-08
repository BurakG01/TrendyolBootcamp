package tech.talent.exception;

public class SmsLockException extends LockedException{
    public SmsLockException(){super("SMS");}
}

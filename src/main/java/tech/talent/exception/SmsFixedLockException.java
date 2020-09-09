package tech.talent.exception;

public class SmsFixedLockException extends LockedException{
    public SmsFixedLockException(String message){super(message);}
}

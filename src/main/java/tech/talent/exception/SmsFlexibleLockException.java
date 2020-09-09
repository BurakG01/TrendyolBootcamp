package tech.talent.exception;

public class SmsFlexibleLockException extends LockedException {
    public SmsFlexibleLockException(String message) {
        super(message);
    }
}

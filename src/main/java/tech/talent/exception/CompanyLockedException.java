package tech.talent.exception;

public class CompanyLockedException extends  RuntimeException{
    public CompanyLockedException(String message) { super(message);
    }
}

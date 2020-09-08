package tech.talent.language;

public class English implements Language {
    @Override
    public String getLockMessage() {
        return "  Package has not paid, you are in black list";
    }

    @Override
    public String getExpiredMessage() {
        return "  Package was expired , message could not send";
    }
}

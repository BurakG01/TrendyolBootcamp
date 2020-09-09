package tech.talent.language;


public class English implements Language {
    @Override
    public String getSmsFixedLockMessage() {
        return "You did not pay sms fixed pack invoice for two month and you are in black list";
    }

    @Override
    public String getMailFixedLockMessage() {
        return "You did not pay mail fixed pack invoice for two month and you are in black list";
    }

    @Override
    public String getMailFlexibleLockMessage() {
        return "You did not pay mail flexible pack invoice for two month and you are in black list";
    }

    @Override
    public String getSmsFlexibleLockMessage() {
        return "Sms flexible pack has expired you have to buy new one";
    }

    @Override
    public String getSmsFixedExpiredMessage() {
        return "Sms fixed pack has expired you have to buy new one";
    }

    @Override
    public String getMailFixedExpiredMessage() {
        return "Mail fixed pack has expired you have to buy new one";
    }

    @Override
    public String getMailFlexibleExpiredMessage() {
        return "Mail flexible pack has expired you have to buy new one";
    }

    @Override
    public String getSmsFlexibleExpiredMessage() {
        return "Sms flexible pack has expired you have to buy new one";
    }

    @Override
    public String getMailReceiverNotValidMessage() {
        return "Receiver you try to send mail is not valid";
    }

    @Override
    public String getSmsReceiverNotValidMessage() {
        return "Receiver you try to send message is not valid";
    }

    @Override
    public String getInvoiceIsNotReadyMessage() {
        return "Invoice is not ready in this time";
    }

    @Override
    public String PrintMessage(String message, String receiverName) {
        return "Message : " + message + " was sent to " + receiverName;
    }


}

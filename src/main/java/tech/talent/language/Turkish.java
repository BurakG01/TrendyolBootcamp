package tech.talent.language;

public class Turkish implements Language {

    private static final String LOCK_MESSAGE = "paketinin faturasını 2 ay ödemediğiniz için kara listedesiniz diğer hizmetlerimizden de yararlanamayacaksınız";
private static final String EXPIRE_MESSAGE="paketinin tarihi sona ermiştir lütfen yeni paket satın alınız ";
    @Override
    public String getSmsFixedLockMessage() {
        return "Sabit Kotalı Sms " + LOCK_MESSAGE;
    }

    @Override
    public String getMailFixedLockMessage() {
        return "Sabit Kotalı Email " +LOCK_MESSAGE;
    }

    @Override
    public String getMailFlexibleLockMessage() {
        return "Esnek Email " +LOCK_MESSAGE;
    }

    @Override
    public String getSmsFlexibleLockMessage() {
        return "Esnek Sms " +LOCK_MESSAGE;
    }

    @Override
    public String getSmsFixedExpiredMessage() {
        return "Sabit Kotalı Sms " + EXPIRE_MESSAGE;
    }

    @Override
    public String getMailFixedExpiredMessage() {
        return "Sabit Kotalı Email " +EXPIRE_MESSAGE;
    }

    @Override
    public String getMailFlexibleExpiredMessage() {
        return "Esnek Email " +EXPIRE_MESSAGE;
    }

    @Override
    public String getSmsFlexibleExpiredMessage() {
        return "Esnek Sms " +EXPIRE_MESSAGE;
    }

    @Override
    public String getMailReceiverNotValidMessage() {
        return "Email alıcı bilgileri geçerli değildir";
    }

    @Override
    public String getSmsReceiverNotValidMessage() {
        return "Sms alıcı bilgileri geçerli değildir";
    }

    @Override
    public String getInvoiceIsNotReadyMessage() {
        return "Fatura ödemesi için bir ay dolması gerekmektedir";
    }

    @Override
    public String PrintMessage(String message, String receiverName) {
        return " mesajınız : " + message + "başarıyla gönderilmiştir" + "alıcı adı : "
                +receiverName;
    }

}

package tech.talent.language;

import tech.talent.model.ReceiverDto;

public interface Language {
    String getSmsFixedLockMessage();

    String getMailFixedLockMessage();

    String getMailFlexibleLockMessage();

    String getSmsFlexibleLockMessage();

    String getSmsFixedExpiredMessage();

    String getMailFixedExpiredMessage();

    String getMailFlexibleExpiredMessage();

    String getSmsFlexibleExpiredMessage();

    String getMailReceiverNotValidMessage();

    String getSmsReceiverNotValidMessage();
    String getInvoiceIsNotReadyMessage();

    String PrintMessage(String message, String receiverName);


}

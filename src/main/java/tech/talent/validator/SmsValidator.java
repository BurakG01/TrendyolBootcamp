package tech.talent.validator;

import tech.talent.exception.ReceiverNotValidException;
import tech.talent.model.ReceiverDto;

public class SmsValidator implements Validator {
    @Override
    public boolean isValid(ReceiverDto receiverDto) throws ReceiverNotValidException {
        if (receiverDto.getName() == null || receiverDto.getPhoneNumber() == null) {
            throw new ReceiverNotValidException("Receiver name or phone number cannot be null ");
        }
        return true;
    }
}

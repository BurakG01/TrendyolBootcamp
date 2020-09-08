package tech.talent.validator;

import tech.talent.exception.ReceiverNotValidException;
import tech.talent.model.ReceiverDto;

public class MailValidator implements Validator {
    @Override
    public boolean isValid(ReceiverDto receiverDto) throws ReceiverNotValidException {
        if (receiverDto.getEmail() == null || receiverDto.getName() == null) {
            throw new ReceiverNotValidException("Receiver name or email cannot be null");
        }
        return true;
    }
}

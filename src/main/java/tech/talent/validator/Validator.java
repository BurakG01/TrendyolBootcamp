package tech.talent.validator;

import tech.talent.exception.ReceiverNotValidException;
import tech.talent.model.ReceiverDto;

public interface Validator {
   boolean isValid(ReceiverDto receiverDto) throws ReceiverNotValidException;
}

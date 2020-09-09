package tech.talent.validator;

import tech.talent.exception.ReceiverNotValidException;
import tech.talent.language.Language;
import tech.talent.model.ReceiverDto;

public interface Validator {
   void setCompanyLanguage(Language language);
   boolean isValid(ReceiverDto receiverDto) throws ReceiverNotValidException;
}

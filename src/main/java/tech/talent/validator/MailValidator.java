package tech.talent.validator;

import tech.talent.exception.ReceiverNotValidException;
import tech.talent.language.Language;
import tech.talent.model.ReceiverDto;

public class MailValidator implements Validator {
    private Language language;
    @Override
    public void setCompanyLanguage(Language language) {
        this.language=language;
    }

    @Override
    public boolean isValid(ReceiverDto receiverDto) throws ReceiverNotValidException {
        if (receiverDto.getEmail() == null || receiverDto.getName() == null) {
            throw new ReceiverNotValidException(language.getMailReceiverNotValidMessage());
        }
        return true;
    }
}

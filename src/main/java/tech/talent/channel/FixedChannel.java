package tech.talent.channel;

import tech.talent.exception.ExpiredException;
import tech.talent.exception.LockedException;
import tech.talent.exception.ReceiverNotValidException;
import tech.talent.language.Language;
import tech.talent.model.ReceiverDto;
import tech.talent.pack.FixedPackage;
import tech.talent.validator.Validator;

import java.util.Calendar;
import java.util.List;

public class FixedChannel implements Channel {
    private FixedPackage fixedPackage;
    private Validator validator;
    private List<ReceiverDto> receivers;


    public FixedChannel(FixedPackage fixedPackage, Validator validator, List<ReceiverDto> receivers) {
        this.fixedPackage = fixedPackage;
        this.validator = validator;
        this.receivers = receivers;
    }

    @Override
    public void send(String message, Language companyLanguage) throws LockedException, ExpiredException {
        setCompanyLanguage(companyLanguage);
        for (ReceiverDto receiver : receivers) {
            // todo : check message if invalid throw exception
            if (!fixedPackage.isLocked(Calendar.getInstance()) && !fixedPackage.isExpired(Calendar.getInstance())) {
                try {
                    if (validator.isValid(receiver)) {

                        System.out.println(companyLanguage.PrintMessage(message,receiver.getName()));

                        fixedPackage.increaseCount();

                        if (fixedPackage.isExceed()) {
                            fixedPackage.addFixedPrice();
                            fixedPackage.resetCount();
                        }
                    }
                } catch (ReceiverNotValidException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    private void setCompanyLanguage(Language companyLanguage) {
        fixedPackage.setCompanyLanguage(companyLanguage);
        validator.setCompanyLanguage(companyLanguage);
    }


}

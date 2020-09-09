package tech.talent.channel;

import tech.talent.exception.ExpiredException;
import tech.talent.exception.LockedException;
import tech.talent.exception.ReceiverNotValidException;
import tech.talent.language.Language;
import tech.talent.model.ReceiverDto;
import tech.talent.pack.FlexiblePackage;
import tech.talent.validator.Validator;

import java.util.Calendar;
import java.util.List;

public class FlexibleChannel implements Channel {
    private FlexiblePackage flexiblePackage;
    private Validator validator;
    private List<ReceiverDto> receivers;

    public FlexibleChannel(FlexiblePackage flexiblePackage, Validator validator, List<ReceiverDto> receivers) {
        this.flexiblePackage = flexiblePackage;
        this.validator = validator;
        this.receivers = receivers;
    }


    @Override
    public void send(String message, Language companyLanguage) throws LockedException, ExpiredException {
        setCompanyLanguage(companyLanguage);
        for (ReceiverDto receiver : receivers) {

            if (!flexiblePackage.isLocked(Calendar.getInstance()) && !flexiblePackage.isExpired(Calendar.getInstance())) {
                try {
                    if (validator.isValid(receiver)) {
                        System.out.println(" Message : " + message + " sent to " + receiver.getName() + "by fixed price pack");

                        flexiblePackage.increaseCount();

                        if (flexiblePackage.isExceed()) {
                            flexiblePackage.increasePrice();
                        }
                    }
                } catch (ReceiverNotValidException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    private void setCompanyLanguage(Language companyLanguage) {
        flexiblePackage.setCompanyLanguage(companyLanguage);
        validator.setCompanyLanguage(companyLanguage);
    }
}

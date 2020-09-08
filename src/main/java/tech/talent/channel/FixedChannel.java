package tech.talent.channel;

import tech.talent.exception.ExpiredException;
import tech.talent.exception.LockedException;
import tech.talent.exception.ReceiverNotValidException;
import tech.talent.model.ReceiverDto;
import tech.talent.pack.FixedPackage;
import tech.talent.validator.Validator;

import java.util.List;

public class FixedChannel  implements  Channel{
    private FixedPackage fixedPackage;
    private Validator validator;
    private List<ReceiverDto> receivers;

    public FixedChannel(FixedPackage fixedPackage, Validator validator, List<ReceiverDto> receivers) {
        this.fixedPackage = fixedPackage;
        this.validator = validator;
        this.receivers = receivers;
    }

    @Override
    public void send(String message) throws LockedException, ExpiredException {
        for (ReceiverDto receiver : receivers) {

            if (!fixedPackage.isLocked() && !fixedPackage.isExpired()) {
                try {
                    if (validator.isValid(receiver)) {
                        System.out.println(" Message : " + message + " sent to " + receiver.getName() + "by fixed price pack");

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
}

package tech.talent.service;

import tech.talent.channel.Channel;
import tech.talent.exception.CompanyLockedException;
import tech.talent.exception.ExpiredException;
import tech.talent.exception.LockedException;
import tech.talent.model.CompanyDto;

public class NotificationService {

    private CompanyDto company;

    public NotificationService(CompanyDto company) {
        this.company = company;
    }

    public void send(String message) throws CompanyLockedException {
        if (company.isLocked()) {
            throw new CompanyLockedException(company.getLockReason());
        }
        for (Channel channel : company.getChannels()) {
            try {
                channel.send(message);
            } catch (LockedException exception) {
                String lockReason = exception.getMessage() + company.getLanguage().getLockMessage();
                company.setLocked(true);
                company.setLockReason(lockReason);
                System.out.println(lockReason);


            } catch (ExpiredException exception) {

                System.out.println(exception.getMessage() + company.getLanguage().getExpiredMessage());
            }
        }

    }
}

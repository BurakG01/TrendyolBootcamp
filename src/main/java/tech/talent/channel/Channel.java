package tech.talent.channel;

import tech.talent.exception.ExpiredException;
import tech.talent.exception.LockedException;
import tech.talent.language.Language;

public interface Channel {
    void send(String message, Language companyLanguage) throws LockedException , ExpiredException;
}

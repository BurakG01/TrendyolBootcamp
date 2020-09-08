package tech.talent.channel;

import tech.talent.exception.ExpiredException;
import tech.talent.exception.LockedException;

public interface Channel {
    void send(String message) throws LockedException , ExpiredException;
}

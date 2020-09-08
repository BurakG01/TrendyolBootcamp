package tech.talent.pack;

import java.math.BigDecimal;

public abstract class FlexiblePackage extends Package {
    protected FlexiblePackage(int limit, BigDecimal price) {
        super(limit, price);
    }

   public abstract void increasePrice();
}

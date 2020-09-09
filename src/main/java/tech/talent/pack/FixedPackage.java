package tech.talent.pack;

import java.math.BigDecimal;

public abstract class FixedPackage extends Package {
    protected FixedPackage(int limit, BigDecimal price) {
        super(limit, price);
    }

    public int getCount() {
        return count;
    }

    public void resetCount() {
        this.count = INITIAL_COUNT;
    }

    public void addFixedPrice() {
        this.price = price.add(price);
    }
}

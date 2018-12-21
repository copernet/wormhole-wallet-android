package org.bouncycastle.math.field;

import core.util.BigInteger;

public interface FiniteField
{
    BigInteger getCharacteristic();

    int getDimension();
}

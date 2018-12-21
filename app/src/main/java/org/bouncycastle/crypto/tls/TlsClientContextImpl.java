package org.bouncycastle.crypto.tls;

import core.java.security.SecureRandom;

class TlsClientContextImpl
    extends AbstractTlsContext
    implements TlsClientContext
{
    TlsClientContextImpl(SecureRandom secureRandom, SecurityParameters securityParameters)
    {
        super(secureRandom, securityParameters);
    }

    public boolean isServer()
    {
        return false;
    }
}

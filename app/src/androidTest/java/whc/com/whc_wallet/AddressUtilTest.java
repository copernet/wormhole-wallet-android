package whc.com.whc_wallet;

import org.junit.Test;

import core.core.Bitcoin;

import static core.core.Bitcoin.CKD_priv;
import static junit.framework.Assert.assertEquals;

/**
 * Created by chuanbei.qiao on 2018/9/21.
 */

public class AddressUtilTest {
    @Test
    public void bip32_correct() throws Exception {
        String xprv = "xprv9s21ZrQH143K31xYSDQpPDxsXRTUcvj2iNHm5NUtrGiGG5e2DtALGdso3pGz6ssrdK4PFmM8NSpSBHNqPqm55Qn3LqFtT2emdEXVYsCzC2U";
        String deserializedXprvPieces[] = Bitcoin.deserialize_xkey(xprv, true);
        String xprv_c = deserializedXprvPieces[4];
        String xprv_k = deserializedXprvPieces[5];

        String[] initial = CKD_priv(xprv_k, xprv_c, 0);

        String[] d1 = Bitcoin.deserialize_xkey("xprv9vHkqa6EV4sPZHYqZznhT2NPtPCjKuDKGY38FBWLvgaDx45zo9WQRUT3dKYnjwih2yJD9mkrocEZXo1ex8G81dwSM1fwqWpWkeS3v86pgKt", true);

        assertEquals(d1[4], initial[1]);

        String xprv2 = "xprv9s21ZrQH143K3QTDL4LXw2F7HEK3wJUD2nW2nRk4stbPy6cq3jPPqjiChkVvvNKmPGJxWUtg6LnF5kejMRNNU3TGtRBeJgk33yuGBxrMPHi";
        String deserializedXprvPieces2[] = Bitcoin.deserialize_xkey(xprv2, true);
        String xprv_c2 = deserializedXprvPieces2[4];
        String xprv_k2 = deserializedXprvPieces2[5];

        String[] initial2 = CKD_priv(xprv_k2, xprv_c2, 0 | 0x80000000);

        String[] d12 = Bitcoin.deserialize_xkey("xprv9uHRZZhk6KAJC1avXpDAp4MDc3sQKNxDiPvvkX8Br5ngLNv1TxvUxt4cV1rGL5hj6KCesnDYUhd7oWgT11eZG7XnxHrnYeSvkzY7d2bhkJ7", true);

        assertEquals(d12[4], initial2[1]);
    }
}

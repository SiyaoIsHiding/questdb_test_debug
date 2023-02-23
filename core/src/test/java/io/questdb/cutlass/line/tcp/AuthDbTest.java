/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2023 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.cutlass.line.tcp;

import org.junit.Assert;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class AuthDbTest {
    @Test
    public void testCryptoAlgorithm() throws Exception {
        // Generated by LineTcpReceiverTest.generateKeys
        // s: NgdiOWDoQNUP18WOnb1xkkEG5TzPYMda5SiUOvT1K0U=
        // testUser1	ec-p-256-sha256	Vs4e-cOLsVCntsMrZiAGAZtrkPXO00uoRLuA3d7gEcI=	ANhR2AZSs4ar9urE5AZrJqu469X0r7gZ1BBEdcrAuL_6

        PrivateKey privateKey = AuthDb.importPrivateKey("NgdiOWDoQNUP18WOnb1xkkEG5TzPYMda5SiUOvT1K0U=");
        PublicKey publicKey = AuthDb.importPublicKey("Vs4e-cOLsVCntsMrZiAGAZtrkPXO00uoRLuA3d7gEcI=", "ANhR2AZSs4ar9urE5AZrJqu469X0r7gZ1BBEdcrAuL_6");
        String challengeString = "XV54KEZwck9bNThoTlMpJnVNO216b2RAWlFneF1YVltYKi91PlItd1d7OEAuSVRQVysjQ09bMn4wSkVaWklcMlcoJHVwRGR7OiZzOUM2ZGkhdy9aKkEuPXJnPyQjMEtPQD1uVCBMLV4wWCs6ekx5NnwkdDF7dVxhO0goJy1uV2lhdSF2cjktWUlZVCA8M0EmWypCaFMhP0U7VFV5TyQgY2JWLyxJSkJvI1M8c0lUaXU6OzIlKVRGcEh4IVJHP0Bgdmkjfi42fVpTdiw8ZCo5Y3RhXFV0W2MwOm5Od0M4Tj0vWER3MGJiUEVBUlwyMS5RfEAvNWNwZnt7QzpReHN2SCdUZzR+MjYqeS51Lz5xXkVwXztUNyJtJE1NMCJcSDlZTSMzazxnUHksN3dGbFVsN15kPG5AUXNiSTY3Y20pXzFRXnhzZj5CM3EoI2dsVnpVZT41L25Se2wobChwSENHVkRxQyUlU3ExOUJIb3wgdGwweURoQnhkKlM7O0k1M2dwRU9Qe2NqaUwvey1wXFFJW08uWHIpM11PTFRETX1jK2JWU2MmJTBWZDk7YVZpI1A8THZaLykgVyFhe2x9O0xzICVGKGx3TD8tMz1bKUV+YUsmVyZvYXxiWTN5KT9DS2UlKVh+SFxUeTtXOnlidV0lejl6MFN9KH43Sn5+eEM3fXcK";
        byte[] challenge = challengeString.getBytes();
        Signature sig = Signature.getInstance(AuthDb.SIGNATURE_TYPE_DER);

        sig.initSign(privateKey);
        sig.update(challenge);
        byte[] sig2 = sig.sign();

        sig.initVerify(publicKey);
        sig.update(challenge);
        boolean verified = sig.verify(sig2);
        Assert.assertTrue(verified);
    }

    @Test
    public void testCryptoAlgorithmGeneratedInPython() throws Exception {
        // Generated by the following Python code:
        // JsonWebKey.generate_key("EC", "P-256", is_private=True)
        //
        // The keys are problematic since they were leading to negative
        // BigInteger values being read from raw bytes.

        PrivateKey privateKey = AuthDb.importPrivateKey("l_g_pe3JzuEFWx9i8-8aHpPx2hdE3bxLQWB5WldsR8c");
        PublicKey publicKey = AuthDb.importPublicKey("V0YHrzmg8Szrcj9PlvzEmPiwtp1Ldo5oqjRUatRzpHs", "xWuN9boVCRgmQp9WoMXZXjwOA1BhIj1T8IwLjAys2xk");
        String challengeString = "3q%EqIAwbOP^]~}9AyzK_M#XC2N$\"kF]1!#R7$}g+Z6aZicwUbOg#FIIf7C+=aQMg8lN0.-Z}24n>dU@IC7z[?4gTM/xb5)Wr6+0c)Jv(W4Li}Kv.i9L[.q_{0$cd3.?biu6NF\\!}}SX2E$o=^ N4TA 0P{cH3eaH})%iC6Ay~z5f[ahZbGYI%(Y-E~y>wPe~{*\">m=la5`'`jS>9(YSr?&>Dq{pe]u~om,JC0SpSEOd+U,5QuC]'ae%:-*o1~[c?%Cxxxfj/(x<&wt*OX`:mRB;-Y<N_c1?`f8=Q{!@g]7MVboh{C^FL4p+3v$Ux|]V(zVFa%Xz8cdlbyD=b~G5*4+o0<WSC;kCloyw$8$c%gi`/Pqm3)qr}7v_@j}(]Kd`sl`s'a8('5D4O5g=!4qh4Q.Clp$Yr9q3k;}t>4OF1@i^qmdrQS]%@Qo0[6lX6J?bu~r9i::3%A:fFebl7N!JU<[fms;&;&t9tUn8%hPO]|4aU3-RFx1X}Q!Esy_|uP:\\";
        byte[] challenge = challengeString.getBytes();
        Signature sig = Signature.getInstance(AuthDb.SIGNATURE_TYPE_DER);

        sig.initSign(privateKey);
        sig.update(challenge);
        byte[] sig2 = sig.sign();

        sig.initVerify(publicKey);
        sig.update(challenge);
        boolean verified = sig.verify(sig2);
        Assert.assertTrue(verified);
    }
}

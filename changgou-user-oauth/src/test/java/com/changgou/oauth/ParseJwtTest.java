package com.changgou.oauth;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class ParseJwtTest {

    @Test
    public void parseJwt(){
        //基于公钥去解析jwt
        String jwt ="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU4NjMwMTM1NCwiYXV0aG9yaXRpZXMiOlsic2Vja2lsbF9saXN0IiwiZ29vZHNfbGlzdCJdLCJqdGkiOiI3ZWMwZDkxMC0yZWI4LTRkNGItODNmYy05YzE5MzNkN2VmZmUiLCJjbGllbnRfaWQiOiJjaGFuZ2dvdSIsInVzZXJuYW1lIjoiaGVpbWEifQ.oXKK15Lx3tyw7lFH4GNdTUfHwLyv5q3r9PHdZBJFGNWQzypXYM-g_mloxOzaFHL68SCwIvE-pSa9ZdPrMiOxrWuRooAZjs8diU342WRMOrwY2rP-XStXwUxD415mHcJRqNwCdoanhXOTdFFDwLzahNo9y4d5lf5_39dJus6H6CFYjt6WwvQU5Ao9P1X1ncO0pBtQwdlMg_p5TuQFRn_iW5k89tkXqs1_K01mY_KfSIwNO_0XzfXpPdAsCYJX1QHdRZerr-YrE7gdZ3Xg3WtxSiU3hOwdD6PnAXJG0dUXb_bdgRqHjok27kKSnQ9xmx5FyxHKtBXCNw2Gy74AuG5ZBw";

        String publicKey ="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvFsEiaLvij9C1Mz+oyAmt47whAaRkRu/8kePM+X8760UGU0RMwGti6Z9y3LQ0RvK6I0brXmbGB/RsN38PVnhcP8ZfxGUH26kX0RK+tlrxcrG+HkPYOH4XPAL8Q1lu1n9x3tLcIPxq8ZZtuIyKYEmoLKyMsvTviG5flTpDprT25unWgE4md1kthRWXOnfWHATVY7Y/r4obiOL1mS5bEa/iNKotQNnvIAKtjBM4RlIDWMa6dmz+lHtLtqDD2LF1qwoiSIHI75LQZ/CNYaHCfZSxtOydpNKq8eb1/PGiLNolD4La2zf0/1dlcr5mkesV570NxRmU1tFm8Zd3MZlZmyv9QIDAQAB-----END PUBLIC KEY-----";

        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));
        String claims = token.getClaims();
        System.out.println(claims);
    }
}

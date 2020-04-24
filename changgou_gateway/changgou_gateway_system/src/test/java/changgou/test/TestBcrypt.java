package changgou.test;

import org.springframework.security.crypto.bcrypt.BCrypt;


/**
 * @author cxl
 * @date 2020-03-25 22:03
 */

public class TestBcrypt {
    public static void main(String[] args) {
        //获取盐
        String gensalt = BCrypt.gensalt();
        System.out.println("盐:"+gensalt);
        //基于当前的盐对 密码进行加密
        String saltPassword = BCrypt.hashpw("123456", gensalt);
        System.out.println("加密后的密文:"+saltPassword);

        //解密
        boolean checkpw = BCrypt.checkpw("123456", saltPassword);
        System.out.println("密文的校验结果:"+checkpw);

    }

}

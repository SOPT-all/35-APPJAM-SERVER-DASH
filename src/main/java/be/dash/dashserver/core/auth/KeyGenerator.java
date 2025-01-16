package be.dash.dashserver.core.auth;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;


@Component
public class KeyGenerator {

    private static final String HMACSHA256 = "HmacSHA256";

    public Key getKeyFromString(String keyString) {
        byte[] keyBytes = Decoders.BASE64.decode(keyString);
        return new SecretKeySpec(keyBytes, HMACSHA256);
    }
}

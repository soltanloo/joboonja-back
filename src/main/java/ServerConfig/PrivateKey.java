package ServerConfig;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class PrivateKey {
    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static Key getKey() {
        return key;
    }
}

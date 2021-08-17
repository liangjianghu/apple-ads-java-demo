package com.liangjianghu.asa;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.interfaces.ECKey;

import java.util.Date;
import java.util.HashMap;

/**
 * apple ads java demo
 */
public class ASAToken {

    private static String client_id = "SEARCHADS.27478e71-3bb0-4588-998c-182e2b405577";
    private static String team_id = "SEARCHADS.27478e71-3bb0-4588-998c-182e2b405577";
    private static String key_id = "bacaebda-e219-41ee-a907-e2c25b24d1b2";
    private static String aud = "https://appleid.apple.com";
    private static String alg = "ES256";

    // openssl pkcs8 -topk8 -inform pem -in private-key.pem -outform pem -nocrypt -out private-key-new.pem
    private static final String PRIVATE_KEY_FILE_256 = "/Users/wd/Downloads/ASA/private-key-new.pem";

    public static void main(String[] args) {
        try {
            String clientSecret = createClientSecret(PRIVATE_KEY_FILE_256);
            System.err.println(clientSecret);
        } catch (Exception e) {
            System.out.println("createClientSecret error");
        }
    }
    /**
     *
     * Create a Client Secret 
     *
     * @return client secret
     */
    public static String createClientSecret(String privateKeyPath) throws Exception {
        Algorithm algorithm = Algorithm.ECDSA256((ECKey) PemUtils.readPrivateKeyFromFile(PRIVATE_KEY_FILE_256, "EC"));
        return JWT.create()
                .withIssuer(team_id)
                .withHeader(new HashMap() {{
                    put("alg",alg);
                    put("kid",key_id);
                }})
                .withSubject(client_id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400*180*1000L))
                .sign(algorithm);
    }
}
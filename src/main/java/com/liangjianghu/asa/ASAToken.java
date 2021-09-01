package com.liangjianghu.asa;

import java.security.interfaces.ECKey;

import java.util.Date;
import java.util.HashMap;

import java.io.Reader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.alibaba.fastjson.JSONObject;

/**
 * apple ads java demo
 */
public class ASAToken {

    private static String client_id = "SEARCHADS.27478e71-3bb0-1106-998c-ljh182405577";
    private static String team_id = "SEARCHADS.27478e71-3bb0-1106-998c-ljh182405577";
    private static String key_id = "bacaebda-e219-1106-a907-ljh182405577";
    private static String aud = "https://appleid.apple.com";
    private static String alg = "ES256";

    // openssl pkcs8 -topk8 -inform pem -in private-key.pem -outform pem -nocrypt -out private-key-new.pem
    private static final String PRIVATE_KEY_FILE_256 = "/Users/wd/Downloads/ASA/private-key-new.pem";

    public static void main(String[] args) {
        try {
            String clientSecret = createClientSecret(PRIVATE_KEY_FILE_256);
            System.out.println("clientSecret 建议保存，有效期可设置最长 180 天");
            System.out.println(clientSecret);

            String url            = "https://appleid.apple.com/auth/oauth2/token";
            String urlParameters  = "grant_type=client_credentials&scope=searchadsorg&agency=ljh&client_id=" + client_id + "&client_secret=" + clientSecret;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setDoOutput( true );
            con.setRequestMethod( "POST" );
            con.setRequestProperty( "charset", "utf-8" );
            con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            con.setRequestProperty( "Host", "appleid.apple.com" );
            con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
            con.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
               wr.write( postData );
            }
            if (con.getResponseCode() == 200) {
                String result = IOUtils.toString(con.getInputStream(), StandardCharsets.UTF_8);
                JSONObject jsonObject = JSONObject.parseObject(result);
                System.out.println("access_token 有效期1个小时");
                System.out.println(jsonObject.getString("access_token"));
            }
        } catch (Exception e) {
            System.err.println("error");
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
                .withAudience(aud)
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
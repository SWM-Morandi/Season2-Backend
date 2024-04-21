package kr.co.morandi.backend.member_management.infrastructure.config.jwt.utils;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@Getter
public class SecretKeyProvider {

    private final PublicKey publicKey;

    private final PrivateKey privateKey;

    public SecretKeyProvider(@Value("${security.publicKey}") String publicKey,
                             @Value("${security.privateKey}") String privateKey) {
        this.publicKey = convertPEMToPublicKey(decoding(publicKey));
        this.privateKey = convertPEMToPrivateKey(decoding(privateKey));
    }
    private String decoding(String key) {
        byte[] decoded = Base64.getDecoder().decode(key);
        return new String(decoded, StandardCharsets.UTF_8);
    }
    public PublicKey convertPEMToPublicKey(String publicKeyPemFile) {
        String publicKeyPEM = extractPublicPemKeyContent(publicKeyPemFile);
        byte[] encodedKey = Base64.getDecoder().decode(publicKeyPEM);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
            return keyFactory.generatePublic(keySpec);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    private PrivateKey convertPEMToPrivateKey(String privateKeyPemFile) {
        String privateKeyPEM = extractPrivatePemKeyContent(privateKeyPemFile);
        byte[] encodedKey = Base64.getDecoder().decode(privateKeyPEM);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
            return keyFactory.generatePrivate(keySpec);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    private String extractPublicPemKeyContent(String pemKey) {
        return pemKey.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
    }

    private String extractPrivatePemKeyContent(String pemKey) {
        return pemKey.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
    }
}


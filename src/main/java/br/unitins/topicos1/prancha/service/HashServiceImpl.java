package br.unitins.topicos1.prancha.service;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashServiceImpl implements HashService {

    private String salt = "#$127732&"; // valor misturado com a senha para gerar o hash
    private Integer iterationCount = 403; // número de vezes que o algoritmo repete o processo (deixa mais seguro)
    private Integer keyLength = 512; // tamanho do hash

    // método para gerar o hash
    @Override
    public String getHashSenha(String senha) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512") // método para armazenar senhas
                    .generateSecret(new PBEKeySpec(senha.toCharArray(), // cria o especificador da senha, esse especificador gera o hash
                            salt.getBytes(),
                            iterationCount,
                            keyLength))
                    .getEncoded();

            return Base64.getEncoder().encodeToString(result); // converte o hash de bytes para a Base64

        // tratando erros
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao gerar o hash");
        } catch (NoSuchAlgorithmException e) {
             throw new RuntimeException("Erro ao gerar o hash");
        }
    }

    // método que vai gerar o hash manualmente
    public static void main(String[] args) {
        HashService hash = new HashServiceImpl();
        System.out.println(hash.getHashSenha("123456"));
    }

}
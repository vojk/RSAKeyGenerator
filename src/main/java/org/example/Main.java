package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What Key do you want to generate? *[A]ll [Pr]ivate [Pu]blic");
        System.out.printf("Your choice: ");
        String choice = scanner.nextLine().toLowerCase();
        GeneratePublicAndPrivateKeys(choice);
    }

    private static String convertToBase64(Key key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    private static void generateFile(String Base64Key, Path pathToFile) {
        try {
            Files.writeString(pathToFile, Base64Key);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void GeneratePublicAndPrivateKeys(String choice) {

        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(4096);
            KeyPair kp = kpg.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) kp.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) kp.getPrivate();
            Path privatePath = Paths.get("rsaPrivateKey.pem");
            Path publicPath = Paths.get("rsaPublicKey.pem");
            switch (choice) {
                case "pr":
                    generateFile(convertToBase64(rsaPrivateKey), privatePath);
                    break;
                case "pu":
                    generateFile(convertToBase64(rsaPublicKey), publicPath);
                    break;
                default:
                    generateFile(convertToBase64(rsaPrivateKey), privatePath);
                    generateFile(convertToBase64(rsaPublicKey), publicPath);
                    break;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

package com.telconovaP7F22025.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Small utility to print a BCrypt hash of a given plaintext.
 * Run with Maven: mvn -DskipTests exec:java -Dexec.mainClass=com.telconovaP7F22025.demo.util.PasswordHashGenerator
 */
public class PasswordHashGenerator {
    private static final Logger logger = LoggerFactory.getLogger(PasswordHashGenerator.class);

    public static void main(String[] args) {
        String plain = "secret";
        if (args != null && args.length > 0) {
            plain = args[0];
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashed = encoder.encode(plain);  
        logger.info(hashed);                     
    }
}

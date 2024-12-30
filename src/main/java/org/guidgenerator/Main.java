package org.guidgenerator;

import static org.guidgenerator.GuidHostnameGenerator.generateKey;

public class Main {
    public static void main(String[] args) {
        // Example: Generate 5 unique keys
        for (int i = 0; i < 5; i++) {
            String uniqueKey = generateKey();
            System.out.println("Generated Unique Key: " + uniqueKey);
        }
    }
}
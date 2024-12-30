package com.example.guidhostname;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class GuidHostnameGeneratorTest {

    @Test
    void testGenerateKeysForHighThroughput() throws InterruptedException {
        // Number of concurrent requests we want to simulate
        final int numberOfRequests = 10_000;

        // A thread-safe Set (use Collections.synchronizedSet) to store generated keys
        Set<String> generatedKeys = Collections.synchronizedSet(new HashSet<>());

        // Create a thread pool (e.g., 10 threads)
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Submit tasks to generate keys in parallel
        for (int i = 0; i < numberOfRequests; i++) {
            executorService.submit(() -> {
                String key = GuidHostnameGenerator.generateKey();

                // Check for duplicates; if found, fail the test immediately
                synchronized (generatedKeys) {
                    assertFalse(generatedKeys.contains(key), "Duplicate key found: " + key);
                    generatedKeys.add(key);
                }
            });
        }

        // Shut down the executor and wait for all tasks to finish
        executorService.shutdown();
        boolean finished = executorService.awaitTermination(10, TimeUnit.SECONDS);

        // Ensure all tasks completed successfully
        assertTrue(finished, "Not all key generation tasks completed in time");

        // Verify the total number of unique keys generated matches the number of requests
        assertEquals(numberOfRequests, generatedKeys.size(),
                "Number of generated keys does not match the number of requests");
    }
}

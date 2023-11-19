package com.example.avito;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:5173")
public class AvitoApplication {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SpringApplication.run(AvitoApplication.class, args);

//        int numThreads = 100_00;
//        int numRequestsPerThread = 1;
//
//        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        JwtRequestDto requestDto = new JwtRequestDto();
//        requestDto.setEmail("q@gmail.com");
//        requestDto.setPassword("q");
//
//        HttpEntity<JwtRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);
//
//        for (int i = 0; i < numThreads; i++) {
//            executorService.execute(() -> {
//                for (int j = 0; j < numRequestsPerThread; j++) {
//                    Instant start = Instant.now();
//
//                    ResponseEntity<?> responseEntity = restTemplate.exchange(
//                            "http://localhost:8080/auth/token",
//                            HttpMethod.POST,
//                            requestEntity,
//                            String.class
//                    );
//
//                    Instant end = Instant.now();
//                    Duration duration = Duration.between(start, end);
//                    long milliseconds = duration.toMillis();
//
//                    if (responseEntity.getStatusCode().is2xxSuccessful()) {
//                        String responseBody = (String) responseEntity.getBody();
//                        System.out.println("Response: " + responseBody);
//                        System.out.println("Time taken: " + milliseconds + " milliseconds");
//                    } else {
//                        System.out.println("Request failed with status code: " + responseEntity.getStatusCodeValue());
//                    }
//                }
//            });
//        }
//
//        executorService.shutdown();
    }


}

package com.example.salaryservice.controller;

import com.example.salaryservice.dto.SalaryDTO;
import com.example.salaryservice.dto.JwtResponseDTO;
import com.example.salaryservice.dto.LoginDTO;
import com.example.salaryservice.model.Salary;
import com.example.salaryservice.model.User;
import com.example.salaryservice.service.JwtService;
import com.example.salaryservice.service.SalaryService;
import com.example.salaryservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/salary")
public class UserController {

    @Value("${auth.service.url}")
    private String authServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            ResponseEntity<JwtResponseDTO> response = restTemplate.postForEntity(authServiceUrl + "/auth/login", loginDTO, JwtResponseDTO.class);
            JwtResponseDTO responseBody = response.getBody();

            if (responseBody != null) {
                System.out.println("Token received: " + responseBody.getJwt());
                return ResponseEntity.ok(responseBody);
            } else {
                System.out.println("Response body is null");
                return ResponseEntity.status(500).body(null);
            }
        } catch (HttpClientErrorException.Forbidden e) {
            return ResponseEntity.status(403).body(null);  // Custom handling for 403
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/details")
    public ResponseEntity<SalaryDTO> getSalaryDetails(@RequestHeader("Authorization") String token, @RequestBody SalaryDTO salaryDTO) {
        String actualToken = token.replace("Bearer ", "");
        String username = jwtService.extractUsername(actualToken);
        if (!jwtService.isTokenValidForService(actualToken, "salary")) {
            return ResponseEntity.status(403).build();
        }

        User user = userService.findByUsername(username);
        Optional<Salary> salaryOptional = salaryService.getSalaryDetailsByUser(user);
        if (salaryOptional.isPresent()) {
            Salary salary = salaryOptional.get();
            SalaryDTO responseDTO = new SalaryDTO();
            responseDTO.setId(salary.getId());
            responseDTO.setUsername(salary.getUser().getUsername());
            responseDTO.setAmount(salary.getAmount());
            responseDTO.setCurrency(salary.getCurrency());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<SalaryDTO> registerSalary(@RequestHeader("Authorization") String token, @RequestBody SalaryDTO salaryDTO) {
        String actualToken = token.replace("Bearer ", "");
        String username = jwtService.extractUsername(actualToken);
        if (!jwtService.isTokenValidForService(actualToken, "salary")) {
            return ResponseEntity.status(403).build();
        }

        User user = userService.findByUsername(username);
        Optional<Salary> salaryOptional = salaryService.registerSalary(salaryDTO, user);
        if (salaryOptional.isPresent()) {
            Salary salary = salaryOptional.get();
            SalaryDTO responseDTO = new SalaryDTO();
            responseDTO.setId(salary.getId());
            responseDTO.setUsername(salary.getUser().getUsername());
            responseDTO.setAmount(salary.getAmount());
            responseDTO.setCurrency(salary.getCurrency());
            responseDTO.setMessage("Salary registered successfully");
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String actualToken = token.replace("Bearer ", "");
        jwtService.logoutToken(actualToken, "salary");
        return ResponseEntity.ok("User logged out successfully");
    }
    
    @PostMapping("/sso-login")
    public ResponseEntity<JwtResponseDTO> ssoLogin(@RequestHeader("Authorization") String token) {
        String actualToken = token.replace("Bearer ", "");
        String username = jwtService.extractUsername(actualToken);
        if (jwtService.isTokenValidForService(actualToken, "salary")) {
            return ResponseEntity.ok(new JwtResponseDTO(actualToken));
        } else {
            return ResponseEntity.status(403).body(null);
        }
    }

}

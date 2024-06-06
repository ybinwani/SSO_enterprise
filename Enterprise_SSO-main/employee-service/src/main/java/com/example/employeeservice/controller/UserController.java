package com.example.employeeservice.controller;

import com.example.employeeservice.dto.EmployeeDTO;
import com.example.employeeservice.dto.JwtResponseDTO;
import com.example.employeeservice.dto.LoginDTO;
import com.example.employeeservice.dto.UserDTO;
import com.example.employeeservice.model.Employee;
import com.example.employeeservice.model.User;
import com.example.employeeservice.repository.UserRepository;
import com.example.employeeservice.service.EmployeeService;
import com.example.employeeservice.service.JwtService;
import com.example.employeeservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class UserController {

    @Value("${auth.service.url}")
    private String authServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private EmployeeService employeeService;

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

//    @PostMapping("/details")
//    public ResponseEntity<EmployeeDTO> getEmployeeDetails(@RequestHeader("Authorization") String token, @RequestBody EmployeeDTO employeeDTO) {
//        String actualToken = token.replace("Bearer ", "");
//        String username = jwtService.extractUsername(actualToken);
//        if (!jwtService.isTokenValidForService(actualToken, "employee")) {
//            return ResponseEntity.status(403).build();
//        }
//
//        User user = userService.findByUsername(username);
//        Optional<Employee> employeeOptional = employeeService.getEmployeeDetailsByUser(user);
//        if (employeeOptional.isPresent()) {
//            Employee employee = employeeOptional.get();
//            EmployeeDTO responseDTO = new EmployeeDTO();
//            responseDTO.setId(employee.getId());
//            responseDTO.setUsername(employee.getUser().getUsername());
//            responseDTO.setExperience(employee.getExperience());
////            responseDTO.setFileData(employee.getFileData()); 
//            responseDTO.setDepartment(employee.getDepartment());
//            return ResponseEntity.ok(responseDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<EmployeeDTO> registerEmployee(@RequestHeader("Authorization") String token, @RequestBody EmployeeDTO employeeDTO) {
//        String actualToken = token.replace("Bearer ", "");
//        String username = jwtService.extractUsername(actualToken);
//        if (!jwtService.isTokenValidForService(actualToken, "employee")) {
//            return ResponseEntity.status(403).build();
//        }
//
//        User user = userService.findByUsername(username);
//        Optional<Employee> employeeOptional = employeeService.registerEmployee(employeeDTO, user);
//        if (employeeOptional.isPresent()) {
//            Employee employee = employeeOptional.get();
//            EmployeeDTO responseDTO = new EmployeeDTO();
//            responseDTO.setId(employee.getId());
//            responseDTO.setUsername(employee.getUser().getUsername());
////            responseDTO.setFirstName(employee.getFirstName());
////            responseDTO.setLastName(employee.getLastName());
//            responseDTO.setDepartment(employee.getDepartment());
//            responseDTO.setMessage("Employee registered successfully");
//            return ResponseEntity.ok(responseDTO);
//        } else {
//            return ResponseEntity.status(500).build();
//        }
//    }
    

//    @PostMapping(value = "/register", consumes = "multipart/form-data")
//    public ResponseEntity<EmployeeDTO> registerEmployee(
//            @RequestHeader("Authorization") String token,
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("employee") String employeeJson) {
//
//        String actualToken = token.replace("Bearer ", "");
//        String jwtUsername = jwtService.extractUsername(actualToken);
//        if (!jwtService.isTokenValidForService(actualToken, "employee")) {
//            return ResponseEntity.status(403).build();
//        }
//
//        User user = userService.findByUsername(jwtUsername);
//        EmployeeDTO employeeDTO;
//        try {
//            employeeDTO = new ObjectMapper().readValue(employeeJson, EmployeeDTO.class);
//        } catch (JsonProcessingException e) {
//            return ResponseEntity.status(400).body(new EmployeeDTO());
//        }
//
//        try {
//            Optional<Employee> employeeOptional = employeeService.registerEmployee(employeeDTO, user, file);
//            if (employeeOptional.isPresent()) {
//                Employee employee = employeeOptional.get();
//                EmployeeDTO responseDTO = new EmployeeDTO();
//                responseDTO.setId(employee.getId());
//                responseDTO.setUsername(employee.getUser().getUsername());
//                responseDTO.setId(employee.getId());
//                responseDTO.setExperience(employee.getExperience());
//                responseDTO.setDepartment(employee.getDepartment());
//                responseDTO.setFileData(employee.getFileData());
//                responseDTO.setMessage("Employee registered successfully");
//                return ResponseEntity.ok(responseDTO);
//            } else {
//                return ResponseEntity.status(500).build();
//            }
//        } catch (IOException e) {
//            EmployeeDTO errorDTO = new EmployeeDTO();
//            errorDTO.setMessage("Failed to store file");
//            return ResponseEntity.status(500).body(errorDTO);
//        }
//    }
//
//    
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserDTO> finduserbyID(@RequestHeader("Authorization") String token,@PathVariable("userId") Long userId){
//    	Optional<User> userOptional = userService.findByUserId(userId);
//        if (userOptional.isPresent()) {
//            UserDTO userDTO = userService.convertToDTO(userOptional.get());
//            return ResponseEntity.ok(userDTO);
//        } else {
//            return ResponseEntity.status(404).build();
//        }
//    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String actualToken = token.replace("Bearer ", "");
        jwtService.logoutToken(actualToken, "employee");
        return ResponseEntity.ok("User logged out successfully");
    }
    
    @PostMapping("/sso-login")
    public ResponseEntity<JwtResponseDTO> ssoLogin(@RequestHeader("Authorization") String token) {
        String actualToken = token.replace("Bearer ", "");
        String username = jwtService.extractUsername(actualToken);
        if (jwtService.isTokenValidForService(actualToken, "employee")) {
            return ResponseEntity.ok(new JwtResponseDTO(actualToken));
        } else {
            return ResponseEntity.status(403).body(null);
        }
    }
    
    
    @PostMapping("/details")
    public ResponseEntity<EmployeeDTO> getEmployeeDetails(@RequestHeader("Authorization") String token, @RequestBody EmployeeDTO employeeDTO) {
        String actualToken = token.replace("Bearer ", "");
        String username = jwtService.extractUsername(actualToken);
        if (!jwtService.isTokenValidForService(actualToken, "employee")) {
            return ResponseEntity.status(403).build();
        }

        User user = userService.findByUsername(username);
        Optional<Employee> employeeOptional = employeeService.getEmployeeDetailsByUser(user);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            EmployeeDTO responseDTO = new EmployeeDTO();
            responseDTO.setId(employee.getId());
            responseDTO.setUsername(employee.getUser().getUsername());
            responseDTO.setExperience(employee.getExperience());
            responseDTO.setDepartment(employee.getDepartment());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> registerEmployee(@RequestHeader("Authorization") String token, @RequestBody EmployeeDTO employeeDTO) {
        String actualToken = token.replace("Bearer ", "");
        String username = jwtService.extractUsername(actualToken);
        if (!jwtService.isTokenValidForService(actualToken, "employee")) {
            return ResponseEntity.status(403).build();
        }

        User user = userService.findByUsername(username);
        Optional<Employee> employeeOptional = employeeService.registerEmployee(employeeDTO, user);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            EmployeeDTO responseDTO = new EmployeeDTO();
            responseDTO.setId(employee.getId());
            responseDTO.setUsername(employee.getUser().getUsername());
            responseDTO.setExperience(employee.getExperience());
            responseDTO.setDepartment(employee.getDepartment());
            responseDTO.setMessage("Employee registered successfully");
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

}

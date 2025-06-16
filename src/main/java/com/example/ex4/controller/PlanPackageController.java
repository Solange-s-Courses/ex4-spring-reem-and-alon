//package com.example.ex4.controller;
//import com.example.ex4.dto.PlanPackageDTO;
//import com.example.ex4.service.PlanPackageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//import java.security.Principal;
//
//@RestController
//@RequestMapping("/package")
//public class PlanPackageController {
//
//    @Autowired
//    private PlanPackageService service;
//
//    @PostMapping(value="/add", consumes = "application/json")
//    public ResponseEntity<?> addPackage(@RequestBody PlanPackageDTO planPackage, Principal principal) {
//        service.saveNewPackage(principal.getName(), planPackage);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping(value="/add", consumes = "application/json")
//    public ResponseEntity<?> addPackage(@RequestBody PlanPackageDTO planPackage, Principal principal) {
//        service.saveNewPackage(principal.getName(), planPackage);
//        return ResponseEntity.ok().build();
//    }
//
//    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
//    public ResponseEntity<String> handleInvalidRequest(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid request: " + ex.getMessage());
//    }
//
//}

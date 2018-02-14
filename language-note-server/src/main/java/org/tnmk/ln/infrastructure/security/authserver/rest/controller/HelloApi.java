package org.tnmk.ln.infrastructure.security.authserver.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApi {


    @GetMapping("/api/hello")
    public ResponseEntity<?> hello() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String msg = String.format("Hello %s", name);
        return new ResponseEntity<Object>(msg, HttpStatus.OK);
    }
//
//    @GetMapping(path = "/api/me", produces = "application/json" )
//    public Account me() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        return accountService.findAccountByUsername(username);
//    }


}

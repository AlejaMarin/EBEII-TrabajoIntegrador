package com.msbills.repositories;

import com.msbills.configuration.feign.FeignInterceptor;
import com.msbills.models.BillUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "users-service", url = "http://localhost:8087", configuration = FeignInterceptor.class)
public interface FeignRepository {

    @GetMapping("/users/public/{username}")
    ResponseEntity<List<BillUserResponse.User>> findByUsernamePublic(@PathVariable String username);
}

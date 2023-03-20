package com.msbills.controller;

import com.msbills.models.Bill;
import com.msbills.models.BillUserResponse;
import com.msbills.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService service;

    //@PreAuthorize("hasAuthority('GROUP_admin')")
    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAll() {

        return ResponseEntity.ok().body(service.getAllBill());
    }


    @PreAuthorize("hasAuthority('GROUP_provider')")
    @PostMapping()
    public ResponseEntity<Bill> saveBill(@RequestBody Bill bill) {

        return ResponseEntity.ok().body(service.saveBill(bill));
    }

    //@PreAuthorize("hasAuthority('GROUP_admin')")
    @GetMapping("/findBy")
    public ResponseEntity<Bill> findByCustomer(@RequestParam String customer) {

        Bill bill = service.findByCustomer(customer);
        if (bill != null) {
            return ResponseEntity.ok().body(bill);
        }
        return ResponseEntity.notFound().build();
    }

    //@PreAuthorize("hasAuthority('GROUP_client')")
    @GetMapping("/detail/{username}")
    public ResponseEntity<BillUserResponse> detailBill(@PathVariable String username) {

        BillUserResponse detailBill = service.detailBill(username);
        if (detailBill != null) {
            return ResponseEntity.ok().body(detailBill);
        }
        return ResponseEntity.notFound().build();
    }

}

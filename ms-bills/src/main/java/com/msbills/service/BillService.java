package com.msbills.service;

import com.msbills.models.Bill;
import com.msbills.models.BillUserResponse;
import com.msbills.repositories.BillRepository;
import com.msbills.repositories.FeignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository repository;
    private final FeignRepository feignRepository;

    public List<Bill> getAllBill() {

        return repository.findAll();
    }

    public Bill saveBill(Bill bill) {

        return repository.save(bill);
    }

    public Bill findByCustomer(String customer) {

        return repository.findByCustomerBill(customer).orElse(null);
    }

    public BillUserResponse detailBill(String username) {

        List<BillUserResponse.User> user = feignRepository.findByUsernamePublic(username).getBody();
        Bill bill = findByCustomer(user.get(0).getFirstName());
        BillUserResponse response = null;
        if (bill != null) {
            response = new BillUserResponse(bill.getIdBill(), bill.getBillingDate(), bill.getTotalPrice(), user.get(0));
        }
        return response;
    }

}

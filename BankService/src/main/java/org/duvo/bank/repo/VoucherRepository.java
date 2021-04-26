package org.duvo.bank.repo;

import java.util.List;

import org.duvo.bank.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    List<Voucher> findByPhoneNumber(String phoneNumber);
    
    List<Voucher> findByProcessedFalse();
}

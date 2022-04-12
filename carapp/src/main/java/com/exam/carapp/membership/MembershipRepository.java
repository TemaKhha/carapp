package com.exam.carapp.membership;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {
    List<Membership> getByCarId(Integer carId);
}

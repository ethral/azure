package com.tefloncode.azure.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tefloncode.azure.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	@Query(value = "select * from azure.order where order_tmstmp >= ?1 and order_tmstmp< ?2", nativeQuery = true)
	//@Query(value = "select * from sbat.booking where booking_slot >= '2020-09-28 12:01:01.0' and booking_slot < '2020-09-29 12:01:01.0'",nativeQuery = true)
	List<Order> findByOrderTs(Timestamp orderts_start, Timestamp orderts_end);

}

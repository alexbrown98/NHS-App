package com.example.demo.repositories;

import com.example.demo.entities.Booking;
import com.example.demo.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBySession(Session session);
}

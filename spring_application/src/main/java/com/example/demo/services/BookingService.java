package com.example.demo.services;

import com.example.demo.entities.Booking;
import com.example.demo.entities.Session;
import com.example.demo.entities.Student;
import com.example.demo.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Optional<Booking> getBooking(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getAllSessionBookings(Session session) {
        return bookingRepository.findBySession(session);
    }

    public void insert(Booking booking) {
        bookingRepository.save(booking);
    }
}

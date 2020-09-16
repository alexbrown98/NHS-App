package com.example.demo.services;

import com.example.demo.entities.Booking;
import com.example.demo.entities.Session;
import com.example.demo.entities.Feedback;
import com.example.demo.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Optional<Feedback> getFeedback(Long id) {
        return feedbackRepository.findById(id);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getAllSessionFeedback(Session session) {
        return feedbackRepository.findBySession(session);
    }

    public void insert(Feedback feedback) {
        feedbackRepository.save(feedback);
    }
}

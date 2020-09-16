package com.example.demo.repositories;

import com.example.demo.entities.Session;
import com.example.demo.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findBySession(Session session);
}

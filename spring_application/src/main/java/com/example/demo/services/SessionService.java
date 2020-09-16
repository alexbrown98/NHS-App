package com.example.demo.services;

import com.example.demo.entities.Session;
import com.example.demo.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Optional<Session> getSession(Long id) {
        return sessionRepository.findById(id);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    public void saveSession(Session session){
        sessionRepository.save(session);
    }

    public void insert(Session session) {
        sessionRepository.save(session);
    }

    public List<Session> getCompletedSessions() {
        return getAllSessions().stream()
                .filter(session -> session.isCompleted())
                .collect(Collectors.toList());
    }

    public List<Session> getNextSessions() {
        return getAllSessions().stream()
                .filter(session -> !session.isCompleted())
                .collect(Collectors.toList());
    }
}

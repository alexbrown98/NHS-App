package com.example.demo.controllers;

import com.example.demo.entities.Doctor;
import com.example.demo.entities.Session;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.services.DoctorService;
import com.example.demo.services.SessionService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private DoctorService doctorService;

    public static final Logger logger = LoggerFactory.getLogger(AppController.class);

    //        --------- RETRIEVE ALL SESSIONS-------------
    @CrossOrigin
    @RequestMapping(value = "/api/allsessions", method = RequestMethod.GET)
    public ResponseEntity<List<Session>> listAllSessions() {
        List<Session> sessions = sessionService.getAllSessions();
        return new ResponseEntity<List<Session>>(sessions, HttpStatus.OK);
    }

    //        --------- RETRIEVE COMPLETED SESSIONS-------------
    @CrossOrigin
    @RequestMapping(value = "/api/completedsessions", method = RequestMethod.GET)
    public ResponseEntity<List<Session>> listCompletedSessions() {
        List<Session> sessions = sessionService.getCompletedSessions();
        return new ResponseEntity<List<Session>>(sessions, HttpStatus.OK);
    }

    //        --------- RETRIEVE NEXT SESSIONS (not completed)-------------
    @CrossOrigin
    @RequestMapping(value = "/api/nextsessions", method = RequestMethod.GET)
    public ResponseEntity<List<Session>> listNextSessions() {
        List<Session> sessions = sessionService.getNextSessions();
        return new ResponseEntity<List<Session>>(sessions, HttpStatus.OK);
    }

    //      -------------RETRIEVE SINGLE SESSION-------------
    @RequestMapping(value = "/api/session/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSession(@PathVariable("id") long id) {
        Optional<Session> session = sessionService.getSession(id);
        if (!session.isPresent()) {
//            return new ResponseEntity(new CustomErrorType("User with id " + id
//                    + " not found"), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Session>(session.get(), HttpStatus.OK);
    }

    //     ---------- EDIT SESSION

    @SuppressWarnings("unchecked")
    @CrossOrigin
    @RequestMapping(value = "/api/editsession/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSession(@PathVariable("id") long id, @RequestBody Session session) {
        logger.info("Updating User with id {}", id);
        System.out.println("Editing Session");

        Optional<Session> currentSession = sessionService.getSession(id);

        if (!currentSession.isPresent()) {
            logger.error("Unable to update. Session with id {} not found.", id);
            //return new ResponseEntity(new CustomErrorType("Unable to update. Session with id " + id + " not found."),
            //        HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentSession.get().setTitle(session.getTitle());
        currentSession.get().setDescription(session.getDescription());
        currentSession.get().setTime(session.getTime());
        currentSession.get().setDoctor(session.getDoctor());
        currentSession.get().setCompleted(session.isCompleted());

        //List<Session> sessions = sessionService.getAllSessions();
        sessionService.deleteSession(currentSession.get().getId());
        sessionService.insert(currentSession.get());
        //sessionService.sessionRepository.save(currentSession.get());

        return new ResponseEntity<Session>(currentSession.get(), HttpStatus.OK);
    }



    // ------------- CREATE A SESSION ------------------
    @CrossOrigin
    @RequestMapping(value = "/api/addsession", method = RequestMethod.POST)
    public ResponseEntity<?> createSession(@RequestBody Map<String,Object> body, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating session");

        Doctor d = null;
        if(body.get("doctor") != null) {
            Long doctorID = ((Number)body.get("doctor")).longValue();
            Optional<Doctor> doctor = doctorService.getDoctor(doctorID);
            if(doctor.isPresent())
                d = doctor.get();
        }

        body.remove("doctor");

        ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Session session = mapper.convertValue(body, Session.class);
        session.setDoctor(d);


        sessionService.saveSession(session);

        body.put("completed", false);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/session/{id}").buildAndExpand(session.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}

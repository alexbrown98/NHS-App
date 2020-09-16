package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
public class AppController {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private  StudentService studentService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private UserRepository userRepository;

    public static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @RequestMapping("/")
    public String greetings(){
        return "Welcome on Medical Teaching App!!!";
    }



    @CrossOrigin
    @RequestMapping(value = "/api/adddoctor", method = RequestMethod.POST)
    public void addDoctor(@RequestBody Doctor doctor){
        doctorService.insert(doctor);
        System.out.println("added a doctor");
        System.out.println("Doctor name : "+doctor.getFirstName());
    }

    @CrossOrigin
    @RequestMapping("/api/alldoctors")
    public List<Doctor> allDoctors(){
        return doctorService.getAllDoctors();
    }

    @CrossOrigin
    @RequestMapping(value = "/addstudent", method = RequestMethod.POST)
    public void addstudent(@RequestBody Student student) {
        studentService.insert(student);
        System.out.println("added a student");
        System.out.println("Student name : "+ student.getFirstName());
    }

    @CrossOrigin
    @RequestMapping("/allstudents")
    public List<Student> allStudents(){
        return studentService.getAllStudents();
    }

    @CrossOrigin
    @RequestMapping(value = "/api/addbooking", method = RequestMethod.POST)
    public ResponseEntity<Object> addBooking(@RequestParam("sessionID") Long sessionID, Principal principal){
        Booking booking = new Booking();
        Optional<Session> s = sessionService.getSession(sessionID);
        if(s.isPresent())
            booking.setSession(s.get());
        else
            System.out.println("Session doesn't exist yet!");
        booking.setTime(new Date());
        Optional<User> ou = userRepository.findByUsername(principal.getName());
        if (!ou.isPresent()) {return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
        booking.setUser(ou.get());
        bookingService.insert(booking);
        System.out.println("added a booking");
        System.out.println("Booking's session title : "+booking.getSession().getTitle());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/addfeedback", method = RequestMethod.POST)
    public ResponseEntity<Object> addFeedback(@RequestBody Map<String,Object> body, Principal principal)
    {
        if(body.get("sessionID") == null | body.get("content") == null | body.get("rating") == null) {
            System.out.println("Call is missing parameters");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long sessionID = ((Number)body.get("sessionID")).longValue();
        String content = (String)body.get("content");
        System.out.println(body.get("rating"));
        Double rating = (Double)body.get("rating");
        System.out.println(rating);

        Feedback feedback = new Feedback();
        Optional<Session> s = sessionService.getSession(sessionID);
        if(rating > 10 || rating < 0) {
            System.out.println("Ratings must be between 0 and 10 (5 stars)");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if(!s.isPresent()) {
            System.out.println("You can't leave feedback on a session which you weren't booked onto.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            feedback.setRating(rating);
            feedback.setContent(content);
            feedback.setSession(s.get());
            feedback.setUsername(principal.getName());
            feedbackService.insert(feedback);
            System.out.println("added feedback");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    // just for testing
    @CrossOrigin
    @RequestMapping("/api/allbookings")
    public List<Booking> allBookings(){
        return bookingService.getAllBookings();
    }

    @CrossOrigin
    @RequestMapping("/api/allfeedback")
    public List<Feedback> allFeedback(){
        return feedbackService.getAllFeedback();
    }

    @CrossOrigin
    @RequestMapping("/api/bookingsbysession/{id}")
    public List<Booking> bookingsBySession(@PathVariable("id") long id) {
        logger.info("Requesting bookings for session", id);
        Optional<Session> s = sessionService.getSession(id);
        if (s.isPresent())
            return bookingService.getAllSessionBookings(s.get());
        else
            return Collections.emptyList();
    }

    @CrossOrigin
    @RequestMapping("/api/feedbacksbysession/{id}")
    public List<Feedback> feedbacksBySession(@PathVariable("id") long id) {
        logger.info("Requesting feedback(s) for session", id);
        Optional<Session> s = sessionService.getSession(id);
        if (s.isPresent())
            return feedbackService.getAllSessionFeedback(s.get());
        else
            return Collections.emptyList();
    }
}

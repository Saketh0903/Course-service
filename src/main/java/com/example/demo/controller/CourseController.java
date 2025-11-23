package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Course;
import com.example.demo.repo.CourseRepository;
import com.example.demo.repo.CourseRepository;

@RestController
public class CourseController {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@GetMapping("/")
	public String home() {
	    return "API is running";
	}

	
	@GetMapping("/getAllCourses")
	public ResponseEntity<List<Course>> getAllCourses() {
		try {
			List<Course> courseList=new ArrayList<>();
            courseRepository.findAll().forEach(courseList::add);
			if(courseList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("getCourseById/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
		Optional<Course> courseData=courseRepository.findById(id);
		if(courseData.isPresent()) {
			return new ResponseEntity<>(courseData.get(),HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/addCourse")
	public ResponseEntity<Course> addCourse(@RequestBody Course course) {
		Course courseObj=courseRepository.save(course);
		return new ResponseEntity<>(courseObj, HttpStatus.CREATED);
	}
	
	@PostMapping("/updateCourseById/{id}")
	public ResponseEntity<Course> updateCourseById(@PathVariable Long id, @RequestBody Course newCourseData) {
		Optional<Course> oldCourseData=courseRepository.findById(id);
		if(oldCourseData.isPresent()) {
			Course updatedCourseData=oldCourseData.get();
            updatedCourseData.setName(newCourseData.getName());
			updatedCourseData.setDescription(newCourseData.getDescription());
			Course courseObj=courseRepository.save(updatedCourseData);
			return new ResponseEntity<>(courseObj, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/deleteCourseById/{id}")
	public ResponseEntity<HttpStatus> deleteBoookById(@PathVariable Long id) {
		courseRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

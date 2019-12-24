package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.component.Sender;
import com.example.demo.document.Course;
import com.example.demo.repository.CourseMongoRepository;

@RestController
@CrossOrigin
@RequestMapping("/course")
@RefreshScope
public class CourseServiceController {
	@Autowired
	CourseMongoRepository courseMongoRepository;
	@Autowired
	Sender sender;
/*
    A resource can have multiple representations
	* XML
	* HTML
	* JSON
	When a resource is requested, we provide the representation of the resource.
	When a consumer sends a request, it can specify two HTTP Headers related to Content Negotiation
	Accept and 	Content-Type
	Content-Type indicates the content type of the body of the request.
	Accept indicates the expected content type of the response.  , produces = { "application/json", "application/xml" }
 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	List<Course> getCourses() {
		return courseMongoRepository.findAllByActiveTrue();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void add(@RequestBody Course course) {

		if (Objects.nonNull(course)) {
			Course c = courseMongoRepository.findByTitle(course.getTitle());
			if (Objects.isNull(c))
				courseMongoRepository.save(course);
			// send to StudentAQ
			Map<String, Object> courseDetails = new HashMap<String, Object>();
			courseDetails.put("id", course.getId());
			courseDetails.put("courseId", course.getCourseId());
			courseDetails.put("description", course.getDescription());
			courseDetails.put("active", course.getActive());
			courseDetails.put("title", course.getTitle());
			courseDetails.put("fee", course.getFee());
			sender.sendAR(courseDetails);
		}

	}

	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	void remove(@RequestParam(value = "courseID") String courseID) {
		// courseMongoRepository.deleteByCourseId(Integer.parseInt((courseID)));
		Course doc = courseMongoRepository.findByCourseId(Integer.parseInt((courseID)));
		doc.setActive(false);
		courseMongoRepository.save(doc);
		// send to StudentRQ
		sender.sendRR(courseID);
	}
}

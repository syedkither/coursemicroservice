package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.component.Sender;
import com.example.demo.document.Course;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.repository.CourseMongoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping("/course")
@RefreshScope
//Path Variables/ Request Param Validation
@Validated
@Api(tags = "Course")
public class CourseServiceController {

	protected static Logger logger = LoggerFactory.getLogger(CourseServiceController.class.getName());

	@Autowired
	CourseMongoRepository courseMongoRepository;
	@Autowired
	Sender sender;

	/*
	 * A resource can have multiple representations XML HTML JSON When a
	 * resource is requested, we provide the representation of the resource.
	 * When a consumer sends a request, it can specify two HTTP Headers related
	 * to Content Negotiation Accept and Content-Type Content-Type indicates the
	 * content type of the body of the request. Accept indicates the expected
	 * content type of the response. , produces = { "application/json",
	 * "application/xml" }
	 */
	@ApiOperation(value = "This endpoint is used to get the all active course details")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = " Successfully retrieved list of courses"),
            @ApiResponse(code = 201, message = " New course was successfully created"),
            @ApiResponse(code = 401, message = " You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = " Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = " The resource you were trying to reach is not found"),
		    @ApiResponse(code = 400, message = " Bad request is received", response = Error.class),
		    @ApiResponse(code = 500, message = " Server error", response = Error.class)
		})
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	List<Course> getCourses() {
		logger.info("Fetching all courses by active");
		return courseMongoRepository.findAllByActiveTrue();
	}
	@ApiOperation(value = "This endpoint is used to add course details")
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void add(@Valid @RequestBody Course course) {

		if (Objects.nonNull(course)) {
			Course c = courseMongoRepository.findByTitle(course.getTitle());
			if (Objects.isNull(c)) {
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
				logger.info(String.format("Course : %s added", course));
			} else {
				logger.info(String.format("Course : %s already exists", course));
			}
		} else {
			logger.error("Course details are empty, please provide course details to add");
		}

	}
	@ApiOperation(value = "This endpoint is used to remove the course details")
	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	void remove(@RequestParam(value = "courseID") @Min(1) String courseID) {
		if (Objects.nonNull(courseID)) {
			Course doc = courseMongoRepository.findByCourseId(Integer.parseInt((courseID)));
			if (Objects.nonNull(doc)) {
				doc.setActive(false);
				courseMongoRepository.save(doc);
				// send to StudentRQ
				sender.sendRR(courseID);
				logger.info(String.format("Course ID : %s removed", courseID));
			} else {
				// logger.info(String.format("Course ID : %s doesn't exists",
				// courseID));
				throw new RecordNotFoundException(String.format("Course ID : %s doesn't exists", courseID));
			}
		} else {
			// logger.error("Course details are empty, please provide course
			// details to remove");
			throw new RecordNotFoundException("Course details are empty, please provide course details to remove");
		}

	}
}

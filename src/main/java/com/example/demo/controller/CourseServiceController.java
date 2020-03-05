package com.example.demo.controller;

import java.text.MessageFormat;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.component.Sender;
import com.example.demo.document.Course;
import com.example.demo.document.CourseResponse;
import com.example.demo.document.CourseVO;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.mapper.CourseEntityMapper;
import com.example.demo.mapper.CourseResponseMapper;
import com.example.demo.service.CourseService;

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
	private CourseService courseService;
	@Autowired
	private Sender sender;
	@Autowired
	private CourseEntityMapper courseEntityMapper;
	@Autowired
    private CourseResponseMapper courseResponseMapper;
	/*
	 * A resource can have multiple representations XML HTML JSON When a
	 * resource is requested, we provide the representation of the resource.
	 * When a consumer sends a request, it can specify two HTTP Headers related
	 * to Content Negotiation Accept and Content-Type Content-Type indicates the
	 * content type of the body of the request. Accept indicates the expected
	 * content type of the response. , produces =  "application/json", "application/xml" 
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
	@GetMapping(value = "/get")
	public List<CourseResponse> getCourses() {
		final List<CourseResponse> aoCourseResp = courseService.getCoursesService();
		logger.info("Fetching all courses by active");
		return aoCourseResp;
	}
	
	
	@ApiOperation(value = "This endpoint is used to add course details")
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void add(@Valid @RequestBody CourseVO coursevo) {

		if (Objects.nonNull(coursevo)) {
			Course course = courseService.findByTitle(coursevo.getTitle());
			if (Objects.isNull(course)) {
				final Course courseEntity = courseEntityMapper.from(coursevo);
				courseService.saveCourse(courseEntity);
				// send to StudentAQ
				Map<String, Object> courseDetails = new HashMap();
				courseDetails.put("id", coursevo.getId());
				courseDetails.put("courseId", coursevo.getCourseId());
				courseDetails.put("description", coursevo.getDescription());
				courseDetails.put("active", coursevo.getActive());
				courseDetails.put("title", coursevo.getTitle());
				courseDetails.put("fee", coursevo.getFee());
				sender.sendAR(courseDetails);
				if (logger.isInfoEnabled()){
					logger.info(MessageFormat.format("Course {0} added", coursevo));
				}
			} else {
				if (logger.isInfoEnabled()){
					logger.info(MessageFormat.format("Course {0} already exists", course));
				}
			}
		} else {
			if (logger.isErrorEnabled()){
				logger.error("Course details are empty, please provide course details to add");
			}
		}

	}
	@ApiOperation(value = "This endpoint is used to remove the course details")
	@DeleteMapping(value = "/remove")
	public void remove(@RequestParam(value = "courseID") @Min(1) String courseID) {
		if (Objects.nonNull(courseID)) {
			Course doc = courseService.findByCourseId(courseID);
			if (Objects.nonNull(doc)) {
				doc.setActive(false);
				courseService.saveCourse(doc);
				// send to StudentRQ
				sender.sendRR(courseID);
				if (logger.isInfoEnabled()){
					logger.info(MessageFormat.format("Course ID {0} removed", courseID));
				}
			} else {
				throw new RecordNotFoundException(String.format("Course ID : %s doesn't exists", courseID));
			}
		} else {
			throw new RecordNotFoundException("Course details are empty, please provide course details to remove");
		}

	}
}

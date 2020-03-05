package com.example.demo.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.document.Course;
@RunWith(SpringRunner.class)
@SpringBootTest

public class CourseMongoRepositoryTest {
	@Autowired
    private CourseMongoRepository courseMongoRepository;

	
    @Before
    public void setUp() {
        Course c1= new Course("Rest", "Spring Restful", true, 5, 5.0);       
        //save product, verify has ID value after save
        assertNull(c1.getId());//null before save       
        this.courseMongoRepository.save(c1);     
        assertNotNull(c1.getId());
       }

    @Test
    public void testFetchData(){
        /*Test data retrieval*/
    	Course c1 = courseMongoRepository.findByTitle("DS");
        assertNotNull(c1);
        assertEquals("Database Systems", c1.getDescription());
        /*Get all products, list should only have two*/
        Iterable<Course> c2 = courseMongoRepository.findAll();
    }

    @Test
    public void testDataUpdate(){
        /*Test update*/
    	Course c1 = courseMongoRepository.findByTitle("WS");
        c1.setActive(false);
        courseMongoRepository.save(c1);
        Course c2= courseMongoRepository.findByTitle("WS");
        assertNotNull(c2);
        assertEquals(false, c2.getActive());
    }

}

package com.empl.coding.exercise.coding.ex.httprequests;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void baseResourceShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject(createURLWithPort("/"), String.class)).contains("OK");
	}

	@Test	
	public void incrementSalaryOfGivenPlaceEmployeesTest() throws Exception {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/employee/place/Gurgaon/salary/2"),
				HttpMethod.PUT, entity, String.class);
		System.out.println(">>>>>>>>>>>>>>>>>>> \n\n" + response + "\n\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		String actual = response.getBody();
		
		System.out.printf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n %s \n\n >>>>>>>>>>>>>>>>>>> \n", actual);
		String expected="{\"isError\":false,\"data\":11,\"message\":\"Success\",\"status\":200}";
		JSONAssert.assertEquals(expected, actual, false);
	}

	@Test
	public void getRangeOfSalaryHavingCompetencyTest() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/employee/competency/COMP3/salary/range"), HttpMethod.GET, entity, String.class);
		System.out.println(">>>>>>>>>>>>>>>>>>> \n\n" + response + "\n\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		String actual = response.getBody();
		
		System.out.printf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n %s \n\n >>>>>>>>>>>>>>>>>>> \n", actual);
		
		String expected="{\"isError\":false,\"data\":{\"MINSALARY\":125000,\"MAXSALARY\":180000},\"message\":\"Success\",\"status\":200}";
		
		JSONAssert.assertEquals(expected, actual, false);
	}

	@Test
	public void getEmployeesHavingPlaceTest() throws Exception {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/employee/place/Gurgaon"),
				HttpMethod.GET, entity, String.class);

		System.out.println(">>>>>>>>>>>>>>>>>>> \n\n" + response + "\n\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		String actual = response.getBody();
		System.out.printf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n %s \n\n >>>>>>>>>>>>>>>>>>> \n", actual);		
		
		String expected="{\"isError\":false,\"data\":[{\"employeeID\":4,\"employeeName\":\"Emp1\",\"title\":\"Mr.\",\"businessUnit\":\"BU1\",\"place\":\"Gurgaon\",\"supervisorID\":1,\"competencies\":\"COMP1\",\"salary\":160000},{\"employeeID\":11,\"employeeName\":\"Emp8\",\"title\":\"Mr.\",\"businessUnit\":\"BU2\",\"place\":\"Gurgaon\",\"supervisorID\":2,\"competencies\":\"COMP2\",\"salary\":100000},{\"employeeID\":12,\"employeeName\":\"Emp9\",\"title\":\"Mr.\",\"businessUnit\":\"BU2\",\"place\":\"Gurgaon\",\"supervisorID\":2,\"competencies\":\"COMP2\",\"salary\":100000},{\"employeeID\":13,\"employeeName\":\"Emp10\",\"title\":\"Mr.\",\"businessUnit\":\"BU2\",\"place\":\"Gurgaon\",\"supervisorID\":2,\"competencies\":\"COMP2\",\"salary\":110000},{\"employeeID\":14,\"employeeName\":\"Emp11\",\"title\":\"Mr.\",\"businessUnit\":\"BU2\",\"place\":\"Gurgaon\",\"supervisorID\":2,\"competencies\":\"COMP2\",\"salary\":110000}],\"message\":\"Success\",\"status\":200}";
//		ObjectMapper
//		assertTrue(actual.equals(res));
		JSONAssert.assertEquals(expected, actual, false);
	}

	private String createURLWithPort(String uri) {
		
//		return "http://localhost:" + port + "/";
		return "http://localhost:" + port + uri;
	}
}
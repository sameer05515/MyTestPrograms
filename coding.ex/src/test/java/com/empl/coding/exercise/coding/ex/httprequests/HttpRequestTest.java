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

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/employee/place/Chennai"),
				HttpMethod.GET, entity, String.class);

		System.out.println(">>>>>>>>>>>>>>>>>>> \n\n" + response + "\n\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		String actual = response.getBody();
		System.out.printf(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n\n %s \n\n >>>>>>>>>>>>>>>>>>> \n", actual);		
		
		String expected="{\"isError\":false,\"data\":[{\"employeeID\":8,\"employeeName\":\"Emp5\",\"title\":\"Mrs.\",\"businessUnit\":\"BU2\",\"place\":\"Chennai\",\"supervisorID\":2,\"competencies\":\"COMP2\",\"salary\":140000},{\"employeeID\":21,\"employeeName\":\"Emp18\",\"title\":\"Mrs.\",\"businessUnit\":\"BU3\",\"place\":\"Chennai\",\"supervisorID\":2,\"competencies\":\"COMP3\",\"salary\":180000},{\"employeeID\":22,\"employeeName\":\"Emp19\",\"title\":\"Mrs.\",\"businessUnit\":\"BU3\",\"place\":\"Chennai\",\"supervisorID\":2,\"competencies\":\"COMP3\",\"salary\":135000},{\"employeeID\":23,\"employeeName\":\"Emp20\",\"title\":\"Mrs.\",\"businessUnit\":\"BU3\",\"place\":\"Chennai\",\"supervisorID\":2,\"competencies\":\"COMP3\",\"salary\":125000},{\"employeeID\":24,\"employeeName\":\"Emp21\",\"title\":\"Mr.\",\"businessUnit\":\"BU3\",\"place\":\"Chennai\",\"supervisorID\":2,\"competencies\":\"COMP3\",\"salary\":125000}],\"message\":\"Success\",\"status\":200}";
//		ObjectMapper
//		assertTrue(actual.equals(res));
		JSONAssert.assertEquals(expected, actual, false);
	}

	private String createURLWithPort(String uri) {
		
//		return "http://localhost:" + port + "/";
		return "http://localhost:" + port + uri;
	}
}
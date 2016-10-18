package dev.rest.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class RestClientTest {

	// http://www.groupkt.com/post/f2129b88/free-restful-web-services-to-consume-and-test.htm
	// http://gturnquist-quoters.cfapps.io/api/random
	// http://api.openweathermap.org/data/2.5/forecast/daily
	// http://openweathermap.org/faq#error401

	@Test
	public void testGetRandomQuote() throws Exception {
		RestClient tc = new RestClient();
		Quote quote = tc.getRandomQuote("http://gturnquist-quoters.cfapps.io/api/random", "");
		System.out.println(quote);
	}

	@Test
	public void test2() throws Exception {
		RestClient tc = new RestClient();
		ResponseEntity<Country> result = tc.getCountries("http://services.groupkt.com/country/get/all");
		Country country = result.getBody();
		System.out.println(country);
	}

	@Test
	public void testStringToInputStream() throws Exception {

		String testStr = "Hello World 123! ሁ";
		// InputStream is = new ByteArrayInputStream(testStr.getBytes("UTF-8"));
		InputStream stream = new ByteArrayInputStream(testStr.getBytes(StandardCharsets.UTF_8));

		String result = IOUtils.toString(stream, StandardCharsets.UTF_8);

		Assert.assertEquals(testStr, result);
		System.out.println(result);
	}

//	@Test
//	public void testWeatherData() throws Exception {
//		String url = "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=f6c289bb63e910823aae6dad3e3becc1";
//		Map<String, String> params = new HashMap<>();
//		
//		RestClient restClient = new RestClient();
//		ResponseEntity<String> response = restClient.getWeatherData(url, String.class, params);
//		System.out.println(response);
//	}

	@Test
	public void testWeatherData() throws Exception {
		String url = "http://api.openweathermap.org/data/2.5/weather?";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("q", "London");
		params.add("APPID", "f6c289bb63e910823aae6dad3e3becc1");
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParams(params).build();
		System.out.println("The url created with the query parameters = \n" + uriComponents.toUriString());
		RestClient restClient = new RestClient();
		ResponseEntity<String> response = restClient.getWeatherData(uriComponents.toUriString(), String.class);
		System.out.println(response);
		System.out.println("------> Status: " + response.getStatusCodeValue());
		System.out.println("------> Headers: " + response.getHeaders());
		System.out.println("------> Status Code: " + response.getStatusCode());
		System.out.println("==============>> Body of the response: \n" + response.getBody());
	}

	@Test
	public void testWeatherInfo() throws Exception {
		String url = "http://api.openweathermap.org/data/2.5/weather?";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("q", "London");
		params.add("APPID", "f6c289bb63e910823aae6dad3e3becc1");
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParams(params).build();
		System.out.println("The url created with the query parameters = \n" + uriComponents.toUriString());
		RestClient restClient = new RestClient();
		WeatherInfo response = restClient.getWeatherInfo(uriComponents.toUriString(), WeatherInfo.class);
		System.out.println(response);
	}

}

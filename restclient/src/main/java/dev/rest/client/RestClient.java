package dev.rest.client;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class RestClient {

	// http://www.groupkt.com/post/f2129b88/free-restful-web-services-to-consume-and-test.htm
	//
	// http://gturnquist-quoters.cfapps.io/api/random
	//
	// http://api.openweathermap.org/data/2.5/forecast/daily
	//
	// http://openweathermap.org/faq#error401

	private static final Logger log = LoggerFactory.getLogger(RestClient.class);

	public Quote getRandomQuote(String url, String pathParam) {

		RestTemplate restTemplate = createRestTemplate();
		Quote quote = restTemplate.getForObject(url + pathParam, Quote.class);
		log.info(quote.toString());

		return quote;
	}

	public ResponseEntity<Country> getCountries(String url) {

		RestTemplate restTemplate = createRestTemplate();
		ResponseEntity<Country> result = restTemplate.getForEntity(url, Country.class);
		log.info(result.toString());

		return result;
	}

	public ResponseEntity<String> getWeatherData(String url, Class responseType) {
		RestTemplate restTemplate = createRestTemplate();
	    long start = System.currentTimeMillis();
		ResponseEntity<String> result  = restTemplate.getForEntity(url, String.class);
		long timeTaken = System.currentTimeMillis() - start;
		log.info("----> Total time taken: {}ms", timeTaken);
		log.info(result.toString());

		return result;
	}

	public WeatherInfo getWeatherInfo(String url, Class<WeatherInfo> responseType) {
		RestTemplate restTemplate = createRestTemplate();
	    long start = System.currentTimeMillis();
	    WeatherInfo result  = (WeatherInfo) restTemplate.getForObject(url, responseType);
		long timeTaken = System.currentTimeMillis() - start;
		log.info("----> Total time taken: {}ms", timeTaken);
		log.info(result.toString());

		return result;
	}


	private RestTemplate createRestTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		Proxy proxy= new Proxy(Type.HTTP, new InetSocketAddress("firewall", 80));
	    requestFactory.setProxy(proxy);
		requestFactory.setReadTimeout(10*1000);
		requestFactory.setConnectTimeout(15*1000);		

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
	}


}

package ru.mipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Controller {
	public static void main(String[] args) throws IOException, InterruptedException {

//		LoadConfiguration lc = new LoadConfiguration();
//		lc.setDuration(60);
//		lc.setExpirationTimeout(60);
//		lc.setRate(10);
//		ObjectMapper objectMapper = new ObjectMapper();
//		String fileName = "src/main/resources/loadConfig.json";
//		objectMapper.writeValue(new File(fileName), lc);
//		LoadConfiguration lc1 = objectMapper.readValue(new File(fileName), LoadConfiguration.class);
//		System.out.println(
//				lc.getDuration() == lc1.getDuration() &&
//				lc.getExpirationTimeout() == lc1.getExpirationTimeout() &&
//				lc.getRate() == lc1.getRate());
//
//		AppConfiguration conf = new AppConfiguration();
//		System.out.println(conf.getInQueueUrl());
		Generator g = new Generator(5, 10, "http://localhost:8081/putRequest");
		Receiver r = new Receiver("http://localhost:8082/getResponse");
		g.start();
		Thread.sleep(4000);
		r.start();
		g.join();
		r.join();

		Map<String, ResponseInfo> report = r.getReceivedResponses();
		System.out.println("Responses received: " + report.size());
		System.out.println("Request sent: " + g.getSentRequests().size());
	}
	
	private static LoadConfiguration readConfiguration(String fileName) {
		ObjectMapper objectMapper = new ObjectMapper();
		LoadConfiguration lc = null;
		try {
			lc = objectMapper.readValue(new File(fileName), LoadConfiguration.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lc;
	}

}

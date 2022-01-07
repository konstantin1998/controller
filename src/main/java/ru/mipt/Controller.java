package ru.mipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Controller {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) throws IOException, InterruptedException {
		String pathToAppConfig = "../" + args[0];
		AppConfig appConfig = readAppConfig(pathToAppConfig);

		String pathToLoadConfig = "../" + args[1];
		LoadConfiguration loadConfig = readLoadConfig(pathToLoadConfig);
		System.out.println("CONFIG");
		System.out.println("app config: " + objectMapper.writeValueAsString(appConfig));
		System.out.println("load config: " + objectMapper.writeValueAsString(loadConfig));

		Report report = generateReport(appConfig, loadConfig);

		writeReport(report);
	}

	private static void writeReport(Report report) throws IOException {
		System.out.println("Program finished".toUpperCase());
		System.out.println("Report:".toUpperCase());
		System.out.println("success: " + report.getSuccess());
		System.out.println("errors: " + report.getErrors());
		System.out.println("expired:" + report.getExpired());
		System.out.println("lost: " + report.getLost());
	}

	private static LoadConfiguration readLoadConfig(String pathToLoadConfig) throws IOException {
		return objectMapper.readValue(new File(pathToLoadConfig), LoadConfiguration.class);
	}

	private static AppConfig readAppConfig(String appConfigPath) throws IOException {
		return objectMapper.readValue(new File(appConfigPath), AppConfig.class);
	}

	private static Report generateReport(AppConfig appConfig, LoadConfiguration loadConfig) throws InterruptedException {
		Generator g = new Generator(
				loadConfig.getRate(),
				loadConfig.getDuration(),
				loadConfig.getTrafficThreshold(),
				appConfig.getRequestToInputQueue()
		);
		Receiver r = new Receiver(appConfig.getRequestToOutputQueue());
		g.start();
		r.start();

		g.join();
		r.join();

		ReportBuilder rb = new ReportBuilder(loadConfig.getExpirationTimeout() * 1000L, g.getSentRequests().size());
		return rb.buildReport(g.getSentRequests(), r.getReceivedResponses());
	}

}

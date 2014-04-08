package de.marrrschine.frontend;

import org.json.simple.parser.ParseException;

public class TestMain {

	public static void main(String[] args) throws ParseException {
		ServiceConsumer sc = new ServiceConsumer();
		sc.consumeServiceGet();
	}
}
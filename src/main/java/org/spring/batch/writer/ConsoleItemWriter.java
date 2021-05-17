package org.spring.batch.writer;

import java.util.List;

import org.springframework.batch.item.support.AbstractItemStreamItemWriter;

public class ConsoleItemWriter extends AbstractItemStreamItemWriter{

	@Override
	public void write(List items) throws Exception {
		items.stream().forEach(System.out::println);
		System.out.println("********** writing each chunk *************");
	}

}

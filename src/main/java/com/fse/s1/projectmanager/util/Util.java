package com.fse.s1.projectmanager.util;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class Util {
	
	public static <C> C convertObjectFieldsFromEmptyToNull(C c) {
		Field[] fields = c.getClass().getDeclaredFields();
		Stream<Field> stream = Stream.of(fields);
		stream.forEach(e -> {
			try {
				e.setAccessible(true);
				if(e.getType() == String.class && e.get(c).equals("")){
					e.set(c, null);
				}
			} catch (IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
		});
		return c;
	}
	
}

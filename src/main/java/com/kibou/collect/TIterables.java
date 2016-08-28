package com.kibou.collect;

public final class TIterables {
	
	
	
	
	
	
	
	
	public static interface Formatter<T>{
		String format(T t);
	}
	
	private static final Formatter<?> defaultFormatter = new Formatter<Object>() {
		@Override
		public String format(Object t) {
			return t== null? "null" : t.toString();
		}
	};

	public static <T> void toString(Iterable<T> iterable){
		toString(iterable,"");
	}
	
	@SuppressWarnings("unchecked")
	public static <T> void toString(Iterable<T> iterable,String forward){
		toString(iterable,forward,(Formatter<T>)defaultFormatter);
	}
	
	public static <T> void toString(Iterable<T> iterable,Formatter<T> formatter){
		toString(iterable, null, formatter, null);
	}
	
	public static <T> void toString(Iterable<T> iterable,String forward,Formatter<T> formatter){
		toString(iterable, forward, formatter, null);
	}
	
	public static <T> void toString(Iterable<T> iterable,String forward,Formatter<T> formatter,String latter){
		StringBuilder sb = new StringBuilder();
		if(forward != null && forward.length() > 0)
			sb.append(forward);
		sb.append("[");
		for(T t : iterable){
			sb.append(formatter.format(t)).append(", ");
		}
		sb.setCharAt(sb.length() - 2, ']');
		if(latter != null && latter.length() > 0)
			sb.append(latter);
		System.out.println(sb.toString());
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
	}
}

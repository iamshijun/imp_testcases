package com.kibou.collect;

public final class TIterables {

	public enum ToStringStyle{
		MULTILINE, ONELINE
	}
	
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
		toString(iterable, forward, formatter, latter, ToStringStyle.ONELINE);
	}
	
	public static <T> void toString(Iterable<T> iterable,String forward,Formatter<T> formatter,String latter,ToStringStyle toStringStyle){
		StringBuilder sb = new StringBuilder();
		if(forward != null && forward.length() > 0)
			sb.append(forward);
		
		sb.append("[");
		if(toStringStyle == ToStringStyle.MULTILINE){
			sb.append("\n");
		}
		for(T t : iterable){
			if(toStringStyle == ToStringStyle.MULTILINE){
				sb.append("  ").append(formatter.format(t)).append(",\n");
			}else{
				sb.append(formatter.format(t)).append(", ");
			}
		}
		if(toStringStyle == ToStringStyle.MULTILINE){
			sb.append("]").setCharAt(sb.length()-3, ' ');
		}else{
			sb.setCharAt(sb.length() - 2, ']');
		}
		if(latter != null && latter.length() > 0)
			sb.append(latter);
		System.out.println(sb.toString());
	}
	
	
	public static <T> void toMultilineString(Iterable<T> iterable,Formatter<T> formatter){
		toMultilineString(iterable, null, formatter, null);
	}
	
	public static <T> void toMultilineString(Iterable<T> iterable,String forward, Formatter<T> formatter){
		toMultilineString(iterable, forward, formatter, null);
	}
	
	public static <T> void toMultilineString(Iterable<T> iterable,String forward,Formatter<T> formatter,String latter){
		toString(iterable, forward, formatter, latter, ToStringStyle.MULTILINE);
	}
	
	
	
	public static void main(String[] args) {
	}
}

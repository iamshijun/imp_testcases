package com.kibou.exception;

import java.sql.SQLIntegrityConstraintViolationException;

public abstract class ExceptionUtils {

	public static boolean isSQLIntegrityConstraintViolationException(Throwable t){
		return t != null && t instanceof SQLIntegrityConstraintViolationException;
	}
	
	public static Exception replaceAsyncTraceWithInitiator(Exception cause,Exception trace){
		StackTraceElement[] causeSSEs = cause.getStackTrace();
		StackTraceElement[] traceSSEs = trace.getStackTrace();
		
		StackTraceElement[] newStackTraceElements = new StackTraceElement[traceSSEs.length];
		newStackTraceElements[0] = causeSSEs[0];
		System.arraycopy(traceSSEs, 1, newStackTraceElements, 1, traceSSEs.length - 1);
		
		//new StackTraceReadyException(newStackTraceElements).printStackTrace();;
		return new TraceException(newStackTraceElements);
	}
	
	private static class TraceException extends Exception{
		
		private static final long serialVersionUID = -8103274308041529580L;
		
		private final StackTraceElement[] stackTraceElements;
		
		public TraceException(StackTraceElement[] stackTraceElements){
			this.stackTraceElements = stackTraceElements;
			super.setStackTrace(stackTraceElements);
		}
		
		@Override
		public StackTraceElement[] getStackTrace() {
			return stackTraceElements;
		}
		
		@Override
		public String toString() {
			return super.toString();
			/*String s = getClass().getPackage().getName() + "." +  getClass().getSimpleName();
	        String message = getLocalizedMessage();
	        return (message != null) ? (s + ": " + message) : s;*/
		}
	}
}
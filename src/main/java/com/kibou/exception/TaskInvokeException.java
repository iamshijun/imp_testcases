package com.kibou.exception;

public class TaskInvokeException extends RuntimeException {

	public TaskInvokeException(String msg){
		super(msg);
	}
	
	public TaskInvokeException(String msg,Throwable cause){
		super(msg, cause);
	}
}

package com.netflix.hystrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class ObservableCommandTest {
	@Test
	public void testObservable() throws Exception {
	
	    Observable<String> fWorld = new ObservableCommandHelloWorld("World").observe();
	    Observable<String> fBob = new ObservableCommandHelloWorld("Bob").observe();
	
	    // blocking
	    assertEquals("Hello World!", fWorld.toBlocking().single());
	    assertEquals("Hello Bob!", fBob.toBlocking().single());
	
	    // non-blocking 
	    // - this is a verbose anonymous inner-class approach and doesn't do assertions
	    fWorld.subscribe(new Observer<String>() {
	
	        @Override
	        public void onCompleted() {
	            // nothing needed here
	        }
	
	        @Override
	        public void onError(Throwable e) {
	            e.printStackTrace();
	        }
	
	        @Override
	        public void onNext(String v) {
	            System.out.println("onNext: " + v);
	        }
	
	    });
	
	    // non-blocking
	    // - also verbose anonymous inner-class
	    // - ignore errors and onCompleted signal
	    fBob.subscribe(new Action1<String>() {
	
	        @Override
	        public void call(String v) {
	            System.out.println("onNext: " + v);
	        }
	
	    });
	}
}


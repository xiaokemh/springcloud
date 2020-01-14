package com.geo.core.exception;

public class CommonException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -2352684669007666429L;


	public CommonException(String message) {
		super(message);
	}

	public CommonException(String var1, Throwable var2) {
		super(var1, var2);
	}

	public CommonException(String message,Object ... args) {
		super(String.format(message.replace("{}", "%s"), args));
	}



}

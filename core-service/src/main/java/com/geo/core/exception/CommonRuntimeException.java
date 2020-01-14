package com.geo.core.exception;

public class CommonRuntimeException extends RuntimeException{
	/**
	 *
	 */
	private static final long serialVersionUID = 1069992206179137842L;

	public CommonRuntimeException(String message) {
		super(message);
	}

	public CommonRuntimeException(String var1, Throwable var2) {
		super(var1, var2);
	}

	public CommonRuntimeException(String message,Object ... args) {
		super(String.format(message.replace("{}", "%s"), args));
	}

}

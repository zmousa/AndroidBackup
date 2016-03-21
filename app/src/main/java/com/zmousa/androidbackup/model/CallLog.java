package com.zmousa.androidbackup.model;

public class CallLog {

	private String name;
	private String number;
	private String callType;
	private String callDuration;
	private String callDayTime;

	public CallLog(String name,String number, String callType, String callDuration, String callDayTime) {
		this.name=name;
		this.number = number;
		this.callType = callType;
		this.callDuration = callDuration;
		this.callDayTime = callDayTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}

	public String getCallDayTime() {
		return callDayTime;
	}

	public void setCallDayTime(String callDayTime) {
		this.callDayTime = callDayTime;
	}
}
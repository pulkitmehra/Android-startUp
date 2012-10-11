package com.myexample.dto;

public class TimeRecord {

	private Integer id;
	private String time;
	private String notes;

	public TimeRecord() {
		super();
	}

	public TimeRecord(String time, String notes) {
		super();
		this.time = time;
		this.notes = notes;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

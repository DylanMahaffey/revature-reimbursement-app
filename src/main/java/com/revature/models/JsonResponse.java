package com.revature.models;

import java.util.Map;

public class JsonResponse {
	
	private Map<String, Object> data;
	private Map<String, Object> error;
	
	public JsonResponse() {
		super();
	}
	
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public Map<String, Object> getError() {
		return error;
	}
	public void setError(Map<String, Object> error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "JsonResponse [data=" + data + ", error=" + error + "]";
	}

}

package com.study.usefulknowledge;

class Worker{
	private int workId;
	private String name;
	public Worker(int workId,String name){
		
		this.workId=workId;
		this.name=name;
	}
	public int getWorkId() {
		return workId;
	}
	public void setWorkId(int workId) {
		this.workId = workId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Worker worker=(Worker)obj;
		return this.workId==worker.getWorkId();
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return workId;
	}
	
	
	
}


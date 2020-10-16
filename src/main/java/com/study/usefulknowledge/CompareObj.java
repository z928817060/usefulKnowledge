package com.study.usefulknowledge;

class Student implements Comparable{

	
	private String name;
	private int score;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int compareTo(Object o) {
		if(!(o instanceof Student)){
			return -1;
		}
		Student st=(Student)o;
		int result=0;
		if(this.score>st.getScore()){
			result=1;
		}else if(this.score==st.getScore()){
			result=this.name.compareTo(st.getName());
			
		}else{
			result=-1;
		}
		return result;
	}
	
	
	
}


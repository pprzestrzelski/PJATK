// TODO: bad smell: data class, various format methods
//		 solution: include setters and getters, created universal Person's display method

//STILL TODO: support several last/first names (with arrays?!)
public class Person {
	private String last;

	private String first;

	private String middle;

	public Person(String last, String first, String middle) {
		this.setLast(last);
		this.setFirst(first);
		this.setMiddle(middle);
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}
}
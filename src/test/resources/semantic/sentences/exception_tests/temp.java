class Main {
	static void main() {
	
	}
}

class Test {
	public int x;
	
	static void f() {
		Object o;
		o = this;

		x = 1;

		g();
	}

	dynamic Object g() {
	}	
}
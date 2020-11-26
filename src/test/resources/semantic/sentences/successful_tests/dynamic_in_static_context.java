class Main {
	static void main() {
	
	}
}

class Test1 {
	public int x;
	
	dynamic void f() {
		Object o;
		o = this;

		x = 1;

		g();
	}

	dynamic Object g() {
	}	
}

class Test2 {
	public static int x;
	
	static void f() {
		Object o;

		x = 1;

		g();
	}

	static Object g() {
	}	
}
class Main {
	static void main() {
	
	}
}

class Test {
	dynamic void f() {
		int x;

		boolean b;
		b = true || false;

		if (true) {
			f();
		}

		if (false || false || false) {
		}

		if (b) {
			f();
		} else {
			f();
		}	

		while (true || false && b) {

		}

		while (b) {

		}	
	}
}
class B {
	dynamic String toString() {
		
	}
}

class A {
	static void f() {

	}

	dynamic int g(int x, String k) {
		return k + x;
	}

	dynamic B h() {

	}

	static void main() {
		int x = 3, y, z = x + y;

		f();

		h().toString();

		h().x = 3;

		h().toString() = 3;

		while (x) {
			x -= 1;
		}

		if (x) {
			g(x + 3, "a");
		} else {
			return;
		}

		return;		
	}
}
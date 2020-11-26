class Main {
	static void main() {
	
	}
}

class A {
	dynamic void a1(int x) {
		b1();
	}

	dynamic int a2() {
		a3();
	}
}

class B extends A {
	dynamic int a2() {
		int x;
	}

	dynamic int b1() {
		b3(b4());
	}

	dynamic void b2() {
		int x;
		x = a3();
	}
}
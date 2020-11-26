class Main {
	static void main() {
	
	}
}

class A {
	dynamic void a1(int x) {
		a1(1);
	}

	dynamic int a2() {
		a1(0);
	}
}

class B extends A {
	dynamic int a2() {
		int x;
	}

	dynamic int b1() {
		a1(a2());
	}

	dynamic int b2() {
		int x;
		x = b2();
	}
}
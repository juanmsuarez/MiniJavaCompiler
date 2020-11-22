class A {
	static void main() {}

	static String f(int x, char y) {}
	static void g(A a) {}
}

class B extends A {
	dynamic void main() {}
	static void g(B b) {}
}

class C extends B {
	static int main() {}
	static String f(int y, String x) {}
}

class D extends C {
	static void main(int x) {}
}

class E extends D {
	dynamic void main(int x) {}
}
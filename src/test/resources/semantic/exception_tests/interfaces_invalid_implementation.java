class Main { static void main() {} }

interface A {
	static void f(int x);
	static void g(int y);
}

class B implements A {
	static void f(int y) {}
	static void g() {}
}


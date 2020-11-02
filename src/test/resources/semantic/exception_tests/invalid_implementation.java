interface A {
	static void f(int x);
	static void g(int y);
}

class B implements A {
	static void f(int i) {}
	static void g() {}
}
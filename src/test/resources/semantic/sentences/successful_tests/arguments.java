class Main {
	static void main() {
	
	}
}

class A {
	
}
class B extends A {

}
class C {

}

class Test {
	static int f() {}

	static void g(int x, int y) {}

	static void h(A a, B b, C c) {}

	dynamic void test() {
		f();

		g(this.f(), 1 * 2 + 3);
		g(3 * 2 + 4 % 5 - 3, 3 + 5 * 2);

		h(new A(), new B(), new C());
		h(new B(), new B(), new C());
	}
}
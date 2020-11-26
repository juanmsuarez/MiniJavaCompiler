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
		f(1);

		g();		
		g(1);
		g("a", null);
		g(this.f(), 1 * 2 + 3);

		h();
		h(1 < 2 || false, 1 + 3 % 2, 1);
		h(new A(), new A(), new A());
		h(new B(), new B(), new B());
		h(new B(), new B(), new C());
	}
}
class Main {
	static void main() {
	
	}
}

class A {}
class B extends A {}

class C {}

class Test {
	dynamic A f() {
		int a;
		a = 3;

		char b;
		b = ' ';

		boolean c;
		c = true;
		c = false;

		String d;
		d = "asd";

		Object e;
		e = null;
		e = new Object();
		e = this;
		e = this.f();
	
		A f;
		f = new A();
		f = new B();

		B g;
		g = new B();

		C h = new C();
	}
}
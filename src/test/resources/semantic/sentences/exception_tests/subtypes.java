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
		a = 'a';

		char b;
		b = ' ';
		b = 3;

		boolean c;
		c = true;
		c = false;
		c = "asd";

		String d;
		d = "asd";
		d = null;

		Object e;
		e = null;
		e = new Object();
		e = true;
		e = "a";
		e = this;
		e = this.f();
	
		A f;
		f = new A();
		f = new B();
		f = new C();

		B g;
		g = new B();
		g = new A();
		g = new C();
		g = this.f();

		C h;
		h = new C();
		h = new A();
		h = new B();
	}
}
class Main {
	static void main() {
	
	}
}

class A {}
class B extends A {}

class C {}

class Test {
	dynamic A f() {
		int a = 3;
		a = 'a';

		char b = ' ';
		b = 3;

		boolean c = true;
		c = false;
		c = "asd";

		String d = "asd";
		d = null;

		Object e = null;
		e = new Object();
		e = true;
		e = "a";
		e = this;
		e = this.f();
	
		A f = new A();
		f = new B();
		f = new C();

		B g = new B();
		g = new A();
		g = new C();
		g = this.f();

		C h = new C();
		h = new A();
		h = new B();
	}
}
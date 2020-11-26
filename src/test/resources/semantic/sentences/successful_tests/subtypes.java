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

		char b = ' ';

		boolean c = true;
		c = false;

		String d = "asd";

		Object e = null;
		e = new Object();
		e = this;
		e = this.f();
	
		A f = new A();
		f = new B();

		B g = new B();

		C h = new C();
	}
}
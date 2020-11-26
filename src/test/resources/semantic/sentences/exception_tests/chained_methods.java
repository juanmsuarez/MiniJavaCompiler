class Main {
	static void main() {
	
	}
}

class A {
	dynamic void a1(int x) {
	}

	dynamic int a2() {
	}

	static A f() {}
}

class B extends A {
	public A atr1;

	static A b1() {}

	dynamic B b2() {}

	dynamic void b3(B par1) {
		int x;
		x = this.a1(3);

		b1().b2();

		b2().a1(3);

		b1().a2().f().f();

		b2().b1().a2();

		new A().b1();

		(1 + 2).b1();

		(atr1).b1();

		par1.a3();

		b1().f();

		B y;
		y = static A.f();

		B.b1().a2();
	}	
}
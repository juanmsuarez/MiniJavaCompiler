class Main {
	static void main() {
	
	}
}

class A {
	dynamic int a1(int x) {
	}

	dynamic int a2() {
	}

	static B f() {}
}

class B extends A {
	public A atr1;

	static A b1() {}

	dynamic B b2() {}

	dynamic void b3(B par1) {
		int x;
		x = this.a1(3);

		b1().a2();

		b2().a1(3);

		int z;
		z = b1().a2();

		b2().b1().a2();

		new A().a1(3);

		z = (1 + 2);

		(atr1).a2();

		par1.b2();

		b1().f();

		A y;
		y = static A.f();

		B.b1().a2();
	}	
}
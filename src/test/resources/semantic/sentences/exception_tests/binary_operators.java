class Main {
	static void main() {
	
	}
}

class A {}
class B extends A {}
class C {}

class Test {
	dynamic void f() {
		int x;

		x = 1 + 2;
		x = 'a' + 2;
	
		x = 1 - 2;
		x = null - 2;
	
		x = 1 * 2;
		x = "a" * 2;

		x = 1 / 2;
		x = this / 2;

		x = 1 % 2;
		x = new Object() % 2;

		boolean a;

		a = true && false;
		a = 1 && true;		

		a = true || false || a;
		a = 1 || true;

		a = (true && false) || ((a) && true);		
		
		boolean b;

		b = new Object() == this || new A() == new B() || new A() == new A();
		b = new A() == new C();
		b = this == new C();
		
		b = this != new Test();
		b = this != new C();
		
		boolean c;

		c = (1 <= 2 || 2 >= 1) && (1 < 2 || 2 > 1);
		c = "a" <= 1;
		c = true < false;

		String s;
		s = ((("asd")));
		s = (((1)));	
	}
}
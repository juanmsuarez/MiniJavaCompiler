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
	
		x = 1 - 2;
	
		x = 1 * 2;

		x = 1 / 2;

		x = 1 % 2;

		boolean a;

		a = true && false;	

		a = true || false || a;

		a = (true && false) || ((a) && true);		
		
		boolean b;

		b = new Object() == this || new A() == new B() || new A() == new A() || 1 == 2 || 'a' != 'b';
		
		b = this != new Test();
		
		boolean c;

		c = (1 <= 2 || 2 >= 1) && (1 < 2 || 2 > 1);

		String s = ((("asd")));
	}
}
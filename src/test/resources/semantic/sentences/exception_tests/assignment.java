class Main {
	static void main() {
	
	}
}

class A {
	private static int x;	
	public static int y;
}
class B extends A {
	public static char y;

	static void f() {
		x = 1;
	
		A.y = 1;
	}
}
class C {

}

class Test {
	public static int x;
		
	dynamic void f(char x, B y) {
		int y;
		x = 1;
		x = 'a';

		;;
	
		y.y = 'a';
		this.x = 1;
		y.f() = 1;
			
		;;

		y = new B();
		y = new A();
		
		A z;
		z = new B();
		boolean b = 1 < 2;
		
		;; 
	
		x += 1;
		y -= 1;

		int k;
		k += 'a';
		k -= 1;
		k += 3 * 2 + k;	
	}
}
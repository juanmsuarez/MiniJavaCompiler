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
		A.y = 1;
	}
}
class C {

}

class Test {
	public static int x;
		
	dynamic void f(char x, B y) {
		x = 'a';

		;;
	
		y.y = 'a';
		this.x = 1;
			
		;;

		y = new B();
		
		A z;
		z = new B();
		boolean b = 1 < 2;
		
		;; 

		int k;
		k -= 1;
		k += 3 * 2 + k;	
	}
}
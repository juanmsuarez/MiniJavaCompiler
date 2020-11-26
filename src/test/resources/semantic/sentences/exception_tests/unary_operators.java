class Main {
	static void main() {
	
	}
}

class Test {
	dynamic void f() {
		int x;
		x = +(1 - 2);
		x = -3;
		x = -'a';
		x = -this;
		
		char c;
		c = +3;
		
		boolean y;
		y = !true;
		y = !x;
		y = !0;
	}
}
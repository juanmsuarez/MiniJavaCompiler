class Main {
	static void main() {}
}

class C {
	static String h() {}
}

class B extends C {
	static void f(int x, int y) {}

	dynamic int g(char x) {}

	
}

class A extends B {
	static void f(int y, int x) {}

	static String h() {}
}
class Main { static void main() {} }

interface A {
	static void f(int x);
	static void g(int y);
	static void h(int z);
}

interface B extends A {
	static void f(int i);
	static void g();
}
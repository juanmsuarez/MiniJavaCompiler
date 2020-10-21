interface A {
	static void main();
	dynamic int f(int a, char c);
	static String x(BigInteger x);
	dynamic Clase k(char c, boolean x, String k);
}

interface B extends A {}

interface C extends A, B {
	static void f();	
}

class D implements A, B {}

class E extends D implements C {}
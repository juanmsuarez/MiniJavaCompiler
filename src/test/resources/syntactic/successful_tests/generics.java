interface A<K> {
	dynamic Clase<K> k(BigInteger<K> c, boolean x);
}

interface B extends A<K> {}

interface C extends A, B {
	static void f();	
}

class D<K> implements A<K>, B {
	private static Clase<K> k;
}

class E extends D<K> implements C {
	E() {

	}

	static int main() {
		Clase<K> c = new Clase<K>();
		Clase<K> c = new Clase<>();

		new Clase<K>().x;

		Clase.x();
	}
}
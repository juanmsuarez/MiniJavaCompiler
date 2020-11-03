class G0<T> {
	public static T x; // invalid
	static T f() {} // invalid
	static void g(T x) {} // invalid
}

class G1<T> {
	dynamic void f(T x) {}
	dynamic void g(T x) {}
	dynamic void h(Y x) {}
	dynamic void i(T x) {}
}
	
class G2<K> extends G1<K> {
	dynamic void f(K x) {} // valid
	dynamic void g(X x) {} // not valid
	dynamic void h(K x) {} // not valid
	// inherits i
}

class G3<T> {
	dynamic void f(G1<T> x) {}
	dynamic void g(G1<T> x) {}
}

class G4<K> extends G3<K> {
	dynamic void f(G1<K> x) {} // valid
	dynamic void g(G1<X> x) {} // not valid
}

class A {
	dynamic void f(G1<X> x) {}
	dynamic void g(G1<X> x) {}
	dynamic void h(G1<X> x) {}
	dynamic void i(X x) {}
	dynamic void j(G1<X> x) {}
	dynamic void k(G1<X> x) {}
}

class B extends A {
	dynamic void f(G1<X> y) {} // valid
	dynamic void g(G1<Y> y) {} // not valid
	dynamic void h(G2<X> x) {} // not valid
	dynamic void i(G1<X> x) {} // not valid
	dynamic void j(X x) {} // not valid
	// inherits k
}

class X {}
class Y {}

////////////////////////////


class Integer {}

class Q<T> {
	dynamic void f(T x) {}
}

class W<K> extends Q<K> {
	// inherits f(K...
}

class E extends W<Integer> {
	dynamic void f(Integer x) {} // valid
}
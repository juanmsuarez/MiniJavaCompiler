class Main { static void main() {} }

class A {

}
class Integer {}


class B<K> {
	
}

class C extends A {

}

class H1 extends B<A> {

}

class H2 extends B<Object> {

}

class H3 extends B<System> {
	private B<H3> z;
}

class I<K> extends A {
	public K x;

	dynamic void f1(K p1, B<K> p2) {}
	dynamic void f3(B<Integer> p2) {}

	dynamic K g1() {}
	dynamic B<K> g4() {}
	dynamic B<Integer> g7() {} 	
}

class L<K> extends B<K> {

}

class M<K> extends B<A> {

}


//////////////////////////////
class G1<T> {
	dynamic void f(T x) {}
	dynamic void g(T x) {}
	dynamic void h(Y x) {}
	dynamic void i(T x) {}
}
	
class G2<K> extends G1<K> {
	dynamic void f(K x) {} // valid
	// inherits
}

class G3<T> {
	dynamic void f(G1<T> x) {}
	dynamic void g(G1<T> x) {}
}

class G4<K> extends G3<K> {
	dynamic void f(G1<K> x) {} // valid
	// inherits
}

class A1 {
	dynamic void f(G1<X> x) {}
	dynamic void g(G1<X> x) {}
	dynamic void h(G1<X> x) {}
	dynamic void i(X x) {}
	dynamic void j(G1<X> x) {}
	dynamic void k(G1<X> x) {}
}

class B1 extends A1 {
	dynamic void f(G1<X> y) {} // valid
	// inherits 
}

class X {}
class Y {}

////////////////////////////

class Q<T> {
	dynamic void f(T x) {}
}

class W<K> extends Q<K> {
	// inherits f(K...
}

class E extends W<Integer> {
	dynamic void f(Integer x) {} // valid
}

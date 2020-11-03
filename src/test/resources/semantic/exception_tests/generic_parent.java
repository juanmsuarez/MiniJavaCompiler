class A {}
class B<K> {}

class C extends A {}
class D extends A<T> {}
class E extends A<K> {}

class F extends B {}
class G extends B<T> {}
class H1 extends B<A> {}
class H2 extends B<B> {}
class H3 extends B<X> {}

class I<K> extends A {}
class J<K> extends A<T> {}
class K<K> extends A<K> {}

class L<K> extends B {}
class M<K> extends B<K> {}
class N<K> extends B<T> {}

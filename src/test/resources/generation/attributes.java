class A {
    public String a_pub1, a_pub2;
    private String a_priv1, a_priv2;

    A() {
        this.startA();
    }

    dynamic void startA() {
        this.a_pub1 = "a_pub1";
        this.a_pub2 = "a_pub2";
        a_priv1 = "a_priv1";
        a_priv2 = "a_priv2";
    }

    dynamic void fa() {
        System.printSln(this.a_pub1);
        System.printSln(this.a_pub2);
        System.printSln(a_priv1);
        System.printSln(a_priv2);
        System.println();
    }
}

class B extends A {
    public String b_pub1, b_pub2, a_pub1;
    private String b_priv1, b_priv2, a_priv1;

    B() {
        this.startB();
    }

    dynamic void startB() {
        this.startA();

        this.a_pub1 = "b_a_pub1";
        this.a_pub2 = "a_pub2_b";
        a_priv1 = "b_a_priv1";
        this.b_pub1 = "b_pub1";
        this.b_pub2 = "b_pub2";
        b_priv1 = "b_priv1";
        b_priv2 = "b_priv2";
    }

    dynamic void fb() {
        this.fa();

        System.printSln(this.a_pub1);
        System.printSln(a_priv1);
        System.printSln(this.b_pub1);
        System.printSln(this.b_pub2);
        System.printSln(b_priv1);
        System.printSln(b_priv2);
        System.println();
    }
}

class C extends B {
    public String c_pub1, b_pub1, a_pub2;
    private String c_priv1, b_priv1, a_priv2;

    C() {
        this.startC();
    }

    dynamic void startC() {
        this.startB();

        this.a_pub1 = "a_pub1_c";
        this.a_pub2 = "c_a_pub2";
        a_priv2 = "c_a_priv2";
        this.b_pub1 = "c_b_pub1";
        this.b_pub2 = "b_pub2_c";
        b_priv1 = "c_b_priv1";
        this.c_pub1 = "c_pub1";
        c_priv1 = "c_priv1";
    }

    dynamic void fc() {
        this.fb();

        System.printSln(this.a_pub2);
        System.printSln(a_priv2);
        System.printSln(this.b_pub1);
        System.printSln(b_priv1);
        System.printSln(this.c_pub1);
        System.printSln(c_priv1);
        System.println();
    }
}

class D {
    public String a_pub1, a_pub2;
    private String a_priv1, a_priv2;

    D() {
        this.startD();
    }

    dynamic void startD() {
        this.a_pub1 = "d_pub1";
        this.a_pub2 = "d_pub2";
        a_priv1 = "d_priv1";
        a_priv2 = "d_priv2";
    }

    dynamic void fd() {
        System.printSln(this.a_pub1);
        System.printSln(this.a_pub2);
        System.printSln(a_priv1);
        System.printSln(a_priv2);
        System.println();
    }
}

class Main {
    static void main() {
        new A().fa();
        System.println();

        new B().fb();
        System.println();

        new C().fc();
        System.println();

        new D().fd();
        System.println();
    }
}

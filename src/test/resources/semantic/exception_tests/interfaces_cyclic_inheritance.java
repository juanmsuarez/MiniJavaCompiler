class Main { static void main() {} }

interface A extends B, C {}
interface B extends D {}
interface C extends D, E {}
interface E extends D {}
interface D {}

interface F extends G, H, M {}
interface G extends I {}
interface H extends K {}
interface I extends I, J {}
interface J extends L, K, G {}
interface K {}
interface L {}
interface M extends M, N {}
interface N {}
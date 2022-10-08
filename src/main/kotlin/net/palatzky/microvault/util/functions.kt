package net.palatzky.microvault.util

fun <A : Any, B: Any> A.toPair(value: B) : Pair<A, B> {
	return this to value
}

fun <A : Any> A.toPair() : Pair<A, A> {
	return this to this
}
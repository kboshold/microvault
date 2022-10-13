package net.palatzky.microvault.util

/**
 * Uses this as key of a pair and the value as value.
 *
 * @param A
 * @param B
 * @param value
 * @return
 */
fun <A : Any, B: Any> A.toPair(value: B) : Pair<A, B> {
	return this to value
}

/**
 * Uses this as key and value of a pair
 *
 * @param A
 * @return
 */
fun <A : Any> A.toPair() : Pair<A, A> {
	return this to this
}
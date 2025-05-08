package xyz.axie.nbt.util

/**
 * A primitive specialization of [java.util.function.BiConsumer] for
 * an object and a short.
 */
fun interface ObjShortConsumer<T> {
    /**
     * Performs this operation on the given arguments.
     *
     * @param t the input object
     * @param s the input short
     */
    fun accept(t: T, s: Short)
}
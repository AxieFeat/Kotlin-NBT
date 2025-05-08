package xyz.axie.nbt.util

/**
 * A primitive specialization of [java.util.function.BiConsumer] for
 * an object and a short.
 */
fun interface ObjShortConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t The input object.
     * @param s The input short.
     */
    fun accept(t: T, s: Short)

}
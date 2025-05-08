package xyz.axie.nbt.util

/**
 * A primitive specialization of [java.util.function.BiConsumer] for
 * an object and a byte.
 */
fun interface ObjByteConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t The input object.
     * @param b The input byte.
     */
    fun accept(t: T, b: Byte)

}
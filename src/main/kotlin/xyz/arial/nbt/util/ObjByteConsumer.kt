package xyz.arial.nbt.util

/**
 * A primitive specialization of [java.util.function.BiConsumer] for
 * an object and a byte.
 */
fun interface ObjByteConsumer<T> {
    /**
     * Performs this operation on the given arguments.
     *
     * @param t the input object
     * @param b the input byte
     */
    fun accept(t: T, b: Byte)
}
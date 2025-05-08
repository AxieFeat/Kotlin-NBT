package xyz.axie.nbt.util

/**
 * A primitive specialization of [java.util.function.BiConsumer] for
 * an object and a float.
 */
fun interface ObjFloatConsumer<T> {
    /**
     * Performs this operation on the given arguments.
     *
     * @param t the input object
     * @param f the input float
     */
    fun accept(t: T, f: Float)
}
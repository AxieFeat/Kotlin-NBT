package xyz.axie.nbt.util

/**
 * A primitive specialization of [java.util.function.BiConsumer] for
 * an object and a float.
 */
fun interface ObjFloatConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t The input object.
     * @param f The input float.
     */
    fun accept(t: T, f: Float)

}
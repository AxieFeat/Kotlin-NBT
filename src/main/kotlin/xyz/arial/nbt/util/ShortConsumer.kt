package xyz.arial.nbt.util

/**
 * A primitive specialization of [java.util.function.Consumer] for
 * shorts.
 */
fun interface ShortConsumer {
    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    fun accept(value: Short)
}
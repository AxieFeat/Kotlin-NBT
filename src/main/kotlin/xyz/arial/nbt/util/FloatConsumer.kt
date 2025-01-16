package xyz.arial.nbt.util

/**
 * A primitive specialization of [java.util.function.Consumer] for
 * floats.
 */
fun interface FloatConsumer {
    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    fun accept(value: Float)
}

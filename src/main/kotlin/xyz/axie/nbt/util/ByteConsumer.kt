package xyz.axie.nbt.util

/**
 * A primitive specialization of [java.util.function.Consumer] for bytes.
 */
fun interface ByteConsumer {

    /**
     * Performs this operation on the given argument.
     *
     * @param value The input argument.
     */
    fun accept(value: Byte)

}

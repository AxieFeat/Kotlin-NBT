package xyz.axie.nbt

/**
 * Utilities for adding to and removing from arrays.
 */
internal object ArrayUtil {

    @JvmStatic
    fun add(original: ByteArray, index: Int, value: Byte): ByteArray {
        val length = original.size
        if (index > length || index < 0) throw ArrayIndexOutOfBoundsException("Index $index out of bounds for length $length!")
        val result = ByteArray(length + 1)
        System.arraycopy(original, 0, result, 0, index)
        result[index] = value
        if (index < length) System.arraycopy(original, index, result, index + 1, length - index)
        return result
    }

    @JvmStatic
    fun add(original: IntArray, index: Int, value: Int): IntArray {
        val length = original.size
        if (index > length || index < 0) throw ArrayIndexOutOfBoundsException("Index $index out of bounds for length $length!")
        val result = IntArray(length + 1)
        System.arraycopy(original, 0, result, 0, index)
        result[index] = value
        if (index < length) System.arraycopy(original, index, result, index + 1, length - index)
        return result
    }

    @JvmStatic
    fun add(original: LongArray, index: Int, value: Long): LongArray {
        val length = original.size
        if (index > length || index < 0) throw ArrayIndexOutOfBoundsException("Index $index out of bounds for length $length!")
        val result = LongArray(length + 1)
        System.arraycopy(original, 0, result, 0, index)
        result[index] = value
        if (index < length) System.arraycopy(original, index, result, index + 1, length - index)
        return result
    }

    @JvmStatic
    fun remove(original: ByteArray, index: Int): ByteArray {
        val length = original.size
        if (index < 0 || index >= length) throw ArrayIndexOutOfBoundsException("Index $index out of bounds for length $length!")
        val result = ByteArray(length - 1)
        System.arraycopy(original, 0, result, 0, index)
        if (index < length - 1) System.arraycopy(original, index + 1, result, index, length - index - 1)
        return result
    }

    @JvmStatic
    fun remove(original: IntArray, index: Int): IntArray {
        val length = original.size
        if (index < 0 || index >= length) throw ArrayIndexOutOfBoundsException("Index $index out of bounds for length $length!")
        val result = IntArray(length - 1)
        System.arraycopy(original, 0, result, 0, index)
        if (index < length - 1) System.arraycopy(original, index + 1, result, index, length - index - 1)
        return result
    }

    @JvmStatic
    fun remove(original: LongArray, index: Int): LongArray {
        val length = original.size
        if (index < 0 || index >= length) throw ArrayIndexOutOfBoundsException("Index $index out of bounds for length $length!")
        val result = LongArray(length - 1)
        System.arraycopy(original, 0, result, 0, index)
        if (index < length - 1) System.arraycopy(original, index + 1, result, index, length - index - 1)
        return result
    }

}
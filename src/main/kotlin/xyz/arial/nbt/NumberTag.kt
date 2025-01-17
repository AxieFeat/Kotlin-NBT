package xyz.arial.nbt

/**
 * A tag holding a number value.
 */
// The reason why this is an interface, and there's an asNumber function, is so we can avoid unnecessary boxing in implementations.
// This is the same reason why the default implementations for the to{NumberType} functions were removed, as they introduce unnecessary
// boxing by having to create a boxed version of the value to have it be able to be cast to a Number.
interface NumberTag : Tag {
    /**
     * Gets the value of this number tag as a [Number].
     *
     * @return the number value
     */
    fun asNumber(): Number

    /**
     * Gets the value of this number tag as a `double`.
     *
     * @return the double value
     */
    fun toDouble(): Double

    /**
     * Gets the value of this number tag as a `float`.
     *
     * @return the float value
     */
    fun toFloat(): Float

    /**
     * Gets the value of this number tag as a `long`.
     *
     * @return the long value
     */
    fun toLong(): Long

    /**
     * Gets the value of this number tag as an `int`.
     *
     * @return the int value
     */
    fun toInt(): Int

    /**
     * Gets the value of this number tag as a `short`.
     *
     * @return the short value
     */
    fun toShort(): Short

    /**
     * Gets the value of this number tag as a `byte`.
     *
     * @return the byte value
     */
    fun toByte(): Byte
}
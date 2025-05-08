package xyz.axie.nbt

/**
 * A tag holding a number value.
 */
interface NumberTag : Tag {

    /**
     * Gets the value of this number tag as a [Number].
     *
     * @return The number value.
     */
    fun asNumber(): Number

    /**
     * Gets the value of this number tag as a `double`.
     *
     * @return The double value.
     */
    fun toDouble(): Double

    /**
     * Gets the value of this number tag as a `float`.
     *
     * @return The float value.
     */
    fun toFloat(): Float

    /**
     * Gets the value of this number tag as a `long`.
     *
     * @return The long value.
     */
    fun toLong(): Long

    /**
     * Gets the value of this number tag as an `int`.
     *
     * @return The int value.
     */
    fun toInt(): Int

    /**
     * Gets the value of this number tag as a `short`.
     *
     * @return The short value.
     */
    fun toShort(): Short

    /**
     * Gets the value of this number tag as a `byte`.
     *
     * @return The byte value.
     */
    fun toByte(): Byte

}
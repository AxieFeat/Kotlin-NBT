package xyz.axie.nbt

import xyz.axie.nbt.impl.DoubleTagImpl

/**
 * A tag that holds a byte value.
 */
interface DoubleTag : NumberTag, ScopedTag<DoubleTag> {

    /**
     * Gets the underlying value of this double tag.
     *
     * @return The value.
     */
    val value: Double

    companion object {

        /**
         * Gets a double tag representing the given value.
         *
         * @param value The backing value.
         *
         * @return A double tag representing the value.
         */
        @JvmStatic
        fun of(value: Double): DoubleTag {
            return if (value == 0.0) ZERO else DoubleTagImpl(value)
        }

        /**
         * The double tag representing the constant zero.
         */
        @JvmStatic
        val ZERO: DoubleTag = DoubleTagImpl(0.0)

        /**
         * The ID of this type of tag.
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 6

        /**
         * The tag type for this tag.
         */
        @JvmStatic
        val TYPE: TagType<DoubleTag> = DoubleTagImpl.createType()

    }

}
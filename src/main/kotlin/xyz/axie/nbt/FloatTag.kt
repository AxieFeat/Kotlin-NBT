package xyz.axie.nbt

import xyz.axie.nbt.impl.FloatTagImpl

/**
 * A tag that holds a byte value.
 */
interface FloatTag : NumberTag, ScopedTag<FloatTag> {

    /**
     * Gets the underlying value of this float tag.
     *
     * @return The value.
     */
    val value: Float

    companion object {

        /**
         * Gets a float tag representing the given value.
         *
         * @param value The backing value.
         *
         * @return A float tag representing the value.
         */
        @JvmStatic
        fun of(value: Float): FloatTag {
            return if (value == 0.0f) ZERO else FloatTagImpl(value)
        }

        /**
         * The float tag representing the constant zero.
         */
        @JvmStatic
        val ZERO: FloatTag = FloatTagImpl(0.0f)

        /**
         * The ID of this type of tag.
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 5

        /**
         * The tag type for this tag.
         */
        @JvmStatic
        val TYPE: TagType<FloatTag> = FloatTagImpl.createType()

    }

}
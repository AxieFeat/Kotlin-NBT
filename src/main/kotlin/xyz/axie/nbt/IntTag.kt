package xyz.axie.nbt

import xyz.axie.nbt.impl.IntTagImpl

/**
 * A tag that holds a byte value.
 */
interface IntTag : NumberTag, ScopedTag<IntTag> {

    /**
     * Gets the underlying value of this int tag.
     *
     * @return The value.
     */
    val value: Int

    companion object {

        /**
         * Gets an int tag representing the given value.
         *
         * @param value The backing value.
         *
         * @return An int tag representing the value.
         */
        @JvmStatic
        fun of(value: Int): IntTag {
            return IntTagImpl.of(value)
        }

        /**
         * The int tag representing the constant zero.
         */
        @JvmStatic
        val ZERO: IntTag = of(0)

        /**
         * The ID of this type of tag.
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 3

        /**
         * The tag type for this tag.
         */
        @JvmStatic
        val TYPE: TagType<IntTag> = IntTagImpl.createType()

    }

}
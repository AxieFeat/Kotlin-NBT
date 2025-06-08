package xyz.axie.nbt

import xyz.axie.nbt.impl.ByteTagImpl

/**
 * A tag that holds a byte value.
 */
interface ByteTag : NumberTag, ScopedTag<ByteTag> {

    /**
     * Gets the underlying value of this byte tag.
     *
     * @return The value.
     */
    val value: Byte

    companion object {

        /**
         * The byte tag representing the constant zero.
         */
        @JvmStatic
        val ZERO: ByteTag = of(0.toByte())

        /**
         * The byte tag representing the constant one.
         */
        @JvmStatic
        val ONE: ByteTag = of(1.toByte())

        /**
         * The ID of this type of tag.
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 1

        /**
         * The tag type for this tag.
         */
        @JvmStatic
        val TYPE: TagType<ByteTag> = ByteTagImpl.createType()

        /**
         * Gets a byte tag representing the given value.
         *
         * @param value The backing value.
         *
         * @return A byte tag representing the value.
         */
        @JvmStatic
        fun of(value: Byte): ByteTag {
            return ByteTagImpl.of(value)
        }

        /**
         * Gets the byte tag representing the given value.
         *
         * The boolean here will be converted to its byte representation, i.e.,
         * ONE for a true value, and ZERO for a false one.
         *
         * @param value The value.
         *
         * @return The byte tag representing the value.
         */
        @JvmStatic
        fun of(value: Boolean): ByteTag {
            return if (value) ONE else ZERO
        }

    }
}
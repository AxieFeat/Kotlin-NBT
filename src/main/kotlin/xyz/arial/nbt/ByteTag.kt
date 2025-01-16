package xyz.arial.nbt

import xyz.arial.nbt.impl.ByteTagImpl

/**
 * A tag that holds a byte value.
 */
interface ByteTag : NumberTag, ScopedTag<ByteTag> {
    /**
     * Gets the underlying value of this byte tag.
     *
     * @return the value
     */
    val value: Byte

    companion object {
        /**
         * Gets a byte tag representing the given value.
         *
         * @param value the backing value
         * @return a byte tag representing the value
         */
        fun of(value: Byte): ByteTag {
            return ByteTagImpl.of(value)
        }

        /**
         * Gets the byte tag representing the given value.
         *
         *
         * The boolean here will be converted to its byte representation, i.e.
         * ONE for a true value, and ZERO for a false one.
         *
         * @param value the value
         * @return the byte tag representing the value
         */
        fun of(value: Boolean): ByteTag {
            return if (value) ONE else ZERO
        }

        /**
         * The byte tag representing the constant zero.
         */
        val ZERO: ByteTag = of(0.toByte())

        /**
         * The byte tag representing the constant one.
         */
        val ONE: ByteTag = of(1.toByte())

        /**
         * The ID of this type of tag.
         *
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 1

        /**
         * The tag type for this tag.
         */
        val TYPE: TagType<ByteTag> = ByteTagImpl.createType()
    }
}
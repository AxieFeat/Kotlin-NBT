package xyz.axie.nbt

import xyz.axie.nbt.impl.ShortTagImpl

/**
 * A tag that holds a byte value.
 */
interface ShortTag : NumberTag, ScopedTag<ShortTag> {
    /**
     * Gets the underlying value of this short tag.
     *
     * @return the value
     */
    val value: Short

    companion object {
        /**
         * Gets a short tag representing the given value.
         *
         * @param value the backing value
         * @return a short tag representing the value
         */
        fun of(value: Short): ShortTag {
            return ShortTagImpl.of(value)
        }

        /**
         * The short tag representing the constant zero.
         */
        val ZERO: ShortTag = of(0.toShort())

        /**
         * The ID of this type of tag.
         *
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 2

        /**
         * The tag type for this tag.
         */
        val TYPE: TagType<ShortTag> = ShortTagImpl.createType()
    }
}

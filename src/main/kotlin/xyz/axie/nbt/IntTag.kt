package xyz.axie.nbt

/**
 * A tag that holds a byte value.
 */
interface IntTag : NumberTag, ScopedTag<IntTag> {

    /**
     * Gets the underlying value of this int tag.
     *
     * @return the value
     */
    val value: Int

    companion object {
        /**
         * Gets an int tag representing the given value.
         *
         * @param value the backing value
         * @return an int tag representing the value
         */
        fun of(value: Int): IntTag {
            return xyz.axie.nbt.impl.IntTagImpl.of(value)
        }

        /**
         * The int tag representing the constant zero.
         */
        val ZERO: IntTag = of(0)

        /**
         * The ID of this type of tag.
         *
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 3

        /**
         * The tag type for this tag.
         */
        val TYPE: TagType<IntTag> = xyz.axie.nbt.impl.IntTagImpl.createType()
    }
}
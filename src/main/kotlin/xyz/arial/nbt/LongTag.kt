package xyz.arial.nbt

import xyz.arial.nbt.impl.LongTagImpl

/**
 * A tag that holds a byte value.
 */
interface LongTag : NumberTag, ScopedTag<LongTag> {
    /**
     * Gets the underlying value of this long tag.
     *
     * @return the value
     */
    val value: Long

    companion object {
        /**
         * Gets a long tag representing the given value.
         *
         * @param value the backing value
         * @return a long tag representing the value
         */
        fun of(value: Long): LongTag {
            return LongTagImpl.of(value)
        }

        /**
         * The long tag representing the constant zero.
         */
        val ZERO: LongTag = of(0)

        /**
         * The ID of this type of tag.
         *
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 4

        /**
         * The tag type for this tag.
         */
        val TYPE: TagType<LongTag> = LongTagImpl.createType()
    }
}
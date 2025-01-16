package xyz.arial.nbt

import xyz.arial.nbt.impl.StringTagImpl

/**
 * A tag that holds a string value.
 */
interface StringTag : Tag {
    /**
     * Gets the underlying value of this string tag.
     *
     * @return the value
     */
    val value: String

    override fun copy(): StringTag

    companion object {
        /**
         * Gets a string tag that represents the given value.
         *
         * @param value the backing value
         * @return a string tag representing the value
         */
        fun of(value: String): StringTag {
            return if (value.isEmpty()) EMPTY else StringTagImpl(value)
        }

        /**
         * The string tag representing the empty string.
         */
        val EMPTY: StringTag = StringTagImpl("")

        /**
         * The ID of this type of tag.
         *
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 8

        /**
         * The tag type for this tag.
         */
        val TYPE: TagType<StringTag> = StringTagImpl.createType()
    }
}
package xyz.axie.nbt

import xyz.axie.nbt.impl.StringTagImpl

/**
 * A tag that holds a string value.
 */
interface StringTag : Tag {

    /**
     * Gets the underlying value of this string tag.
     *
     * @return The value.
     */
    val value: String

    override fun copy(): StringTag

    companion object {

        /**
         * Gets a string tag that represents the given value.
         *
         * @param value The backing value.
         *
         * @return A string tag representing the value.
         */
        @JvmStatic
        fun of(value: String): StringTag {
            return if (value.isEmpty()) EMPTY else StringTagImpl(value)
        }

        /**
         * The string tag representing the empty string.
         */
        @JvmStatic
        val EMPTY: StringTag = StringTagImpl("")

        /**
         * The ID of this type of tag.
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 8

        /**
         * The tag type for this tag.
         */
        @JvmStatic
        val TYPE: TagType<StringTag> = StringTagImpl.createType()

    }

}
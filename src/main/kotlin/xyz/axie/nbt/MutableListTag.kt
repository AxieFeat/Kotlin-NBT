package xyz.axie.nbt

import xyz.axie.nbt.impl.MutableListTagImpl

/**
 * A mutable list tag.
 */
interface MutableListTag : ScopedListTag<MutableListTag> {

    /**
     * Clears this mutable list tag, erasing all elements stored in it.
     */
    fun clear()

    override fun toBuilder(): Builder

    /**
     * A builder for building mutable list tags.
     */
    interface Builder : ScopedListTag.Builder<Builder>

    companion object {

        /**
         * Creates a new mutable list tag from the given data with the given
         * element type.
         *
         * @param data The data.
         * @param elementType The element type.
         *
         * @return A new mutable list tag.
         */
        @JvmStatic
        fun of(data: List<Tag>, elementType: Int): MutableListTag {
            return MutableListTagImpl(data.toMutableList(), elementType)
        }

        /**
         * Creates a new empty mutable list tag.
         *
         * @return A new empty mutable list tag.
         */
        @JvmStatic
        fun empty(): MutableListTag {
            return MutableListTagImpl(mutableListOf(), EndTag.ID)
        }

        /**
         * Creates a new builder for building a mutable list tag.
         *
         * The element type will be determined as elements are added.
         *
         * @return A new builder.
         */
        @JvmStatic
        @JvmOverloads
        fun builder(elementType: Int = EndTag.ID): Builder {
            return MutableListTagImpl.Builder(elementType)
        }

    }

}
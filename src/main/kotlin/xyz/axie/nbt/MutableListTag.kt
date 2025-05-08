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
         * @param data the data
         * @param elementType the element type
         * @return a new mutable list tag
         */
        fun of(data: List<Tag>, elementType: Int): MutableListTag {
            return MutableListTagImpl(data.toMutableList(), elementType)
        }

        /**
         * Creates a new empty mutable list tag.
         *
         * @return a new empty mutable list tag
         */
        fun empty(): MutableListTag {
            return MutableListTagImpl(mutableListOf(), EndTag.ID)
        }
        /**
         * Creates a new builder for building a mutable list tag.
         *
         * The element type will be determined as elements are added.
         *
         * @return a new builder
         */
        @JvmOverloads
        fun builder(elementType: Int = EndTag.ID): Builder {
            return MutableListTagImpl.Builder(elementType)
        }
    }
}
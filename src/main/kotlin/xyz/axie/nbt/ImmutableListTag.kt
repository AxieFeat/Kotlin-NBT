package xyz.axie.nbt

import xyz.axie.nbt.ListTag.Companion.EMPTY
import xyz.axie.nbt.impl.ImmutableListTagImpl
import org.pcollections.TreePVector

/**
 * An immutable list tag.
 */
interface ImmutableListTag : ScopedListTag<ImmutableListTag> {

    override fun toBuilder(): Builder

    /**
     * A builder for building immutable list tags.
     */
    interface Builder : ScopedListTag.Builder<Builder>

    companion object {
        /**
         * Creates a new immutable list tag from the given data with the given
         * element type.
         *
         * @param data the data
         * @param elementType the element type
         * @return a new immutable list tag
         */
        fun of(data: List<Tag?>, elementType: Int): ImmutableListTag {
            // Optimization: For empty data, the element type is always EndTag, so we can just return the empty list.
            if (data.isEmpty()) return EMPTY
            return ImmutableListTagImpl(TreePVector.from(data), elementType)
        }

        /**
         * Creates a new builder for building an immutable list tag.
         *
         * The element type will be determined as elements are added.
         *
         * @return a new builder
         */
        @JvmOverloads
        fun builder(elementType: Int = EndTag.ID): Builder {
            return ImmutableListTagImpl.Builder(elementType)
        }
    }
}
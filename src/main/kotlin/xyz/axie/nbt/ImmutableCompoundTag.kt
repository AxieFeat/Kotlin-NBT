package xyz.axie.nbt

import xyz.axie.nbt.CompoundTag.Companion.EMPTY
import xyz.axie.nbt.impl.ImmutableCompoundTagImpl
import org.pcollections.OrderedPMap


/**
 * An immutable compound tag.
 */
interface ImmutableCompoundTag : ScopedCompoundTag<ImmutableCompoundTag> {

    override fun toBuilder(): Builder

    /**
     * A builder for building immutable compound tags.
     */
    interface Builder : ScopedCompoundTag.Builder<Builder>

    companion object {
        /**
         * Creates a new immutable compound tag from the given data.
         *
         * @param data the data
         * @return a new immutable compound tag
         */
        fun of(data: Map<out String, Tag>): ImmutableCompoundTag {
            // Optimization: If the data is empty, we can just return the empty compound.
            if (data.isEmpty()) return EMPTY
            return ImmutableCompoundTagImpl(OrderedPMap.from(data))
        }

        /**
         * Creates a new builder for building an immutable compound tag.
         *
         * @return a new builder
         */
        fun builder(): Builder {
            return ImmutableCompoundTagImpl.Builder()
        }
    }
}
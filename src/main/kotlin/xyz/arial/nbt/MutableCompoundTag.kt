package xyz.arial.nbt

import xyz.arial.nbt.impl.MutableCompoundTagImpl
import org.jetbrains.annotations.Contract

/**
 * A mutable compound tag.
 */
interface MutableCompoundTag : ScopedCompoundTag<MutableCompoundTag> {
    /**
     * Clears this mutable compound tag, erasing all elements stored in it.
     */
    fun clear()

    override fun toBuilder(): Builder

    /**
     * A builder for building mutable compound tags.
     */
    interface Builder : ScopedCompoundTag.Builder<Builder>

    companion object {
        /**
         * Creates a new mutable compound tag from the given data.
         *
         * @param data the data
         * @return a new mutable compound tag
         */
        @Contract(value = "_ -> new", pure = true)
        fun of(data: Map<out String, Tag>): MutableCompoundTag {
            return MutableCompoundTagImpl(data.toMutableMap())
        }

        /**
         * Creates a new empty mutable compound tag.
         *
         * @return a new empty mutable compound tag
         */
        @Contract(value = "-> new", pure = true)
        fun empty(): MutableCompoundTag {
            return MutableCompoundTagImpl(LinkedHashMap())
        }

        /**
         * Creates a new builder for building a mutable compound tag.
         *
         * @return a new builder
         */
        @Contract(value = "-> new", pure = true)
        fun builder(): Builder {
            return MutableCompoundTagImpl.Builder()
        }
    }
}
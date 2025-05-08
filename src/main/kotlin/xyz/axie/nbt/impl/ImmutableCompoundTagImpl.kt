package xyz.axie.nbt.impl

import org.pcollections.OrderedPMap
import org.pcollections.PMap
import xyz.axie.nbt.*
import xyz.axie.nbt.AbstractCompoundTag

@Suppress("DIFFERENT_NAMES_FOR_THE_SAME_PARAMETER_IN_SUPERTYPES")
internal data class ImmutableCompoundTagImpl(
    override val data: PMap<String, Tag>
) : AbstractCompoundTag<ImmutableCompoundTag>(), ImmutableCompoundTag {

    override fun put(name: String, value: Tag): ImmutableCompoundTag {
        return ImmutableCompoundTagImpl(data.plus(name, value))
    }

    override fun remove(name: String): ImmutableCompoundTag {
        // Optimization: If we have no entries, we can't remove anything, so we return the empty compound. If we have one entry, and that
        // one entry's key is the name, we know we would end up with an empty map, so we just return the empty compound.
        if (data.isEmpty() || (data.size == 1 && data[name] != null)) return CompoundTag.EMPTY
        return ImmutableCompoundTagImpl(data.minus(name))
    }

    override fun copy(): ImmutableCompoundTag {
        return this
    }

    override fun toBuilder(): Builder {
        return Builder(LinkedHashMap<String, Tag>(data))
    }

    override fun asMutable(): MutableCompoundTag {
        return MutableCompoundTagImpl(LinkedHashMap(data))
    }

    override fun asImmutable(): ImmutableCompoundTag {
        return this
    }

    override fun toString(): String {
        return "ImmutableCompoundTagImpl{data=$data}"
    }

    internal class Builder(
        data: Map<String, Tag>
    ) : AbstractCompoundTag.Builder<Builder, ImmutableCompoundTag>(data.toMutableMap()), ImmutableCompoundTag.Builder {

        constructor() : this(LinkedHashMap<String, Tag>())

        override fun build(): ImmutableCompoundTag {
            return ImmutableCompoundTagImpl(OrderedPMap.from(data))
        }
    }
}
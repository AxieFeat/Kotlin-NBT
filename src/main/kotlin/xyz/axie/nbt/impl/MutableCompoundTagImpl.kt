package xyz.axie.nbt.impl

import xyz.axie.nbt.AbstractCompoundTag
import xyz.axie.nbt.ImmutableCompoundTag
import xyz.axie.nbt.MutableCompoundTag
import xyz.axie.nbt.Tag
import org.pcollections.OrderedPMap

@Suppress("DIFFERENT_NAMES_FOR_THE_SAME_PARAMETER_IN_SUPERTYPES")
internal data class MutableCompoundTagImpl(
    override val data: MutableMap<String, Tag>
) : AbstractCompoundTag<MutableCompoundTag>(), MutableCompoundTag {

    override fun clear() {
        data.clear()
    }

    override fun put(name: String, value: Tag): MutableCompoundTag {
        data[name] = value
        return this
    }

    override fun remove(name: String): MutableCompoundTag {
        data.remove(name)
        return this
    }

    override fun copy(): MutableCompoundTag {
        return MutableCompoundTagImpl(deepCopy(data))
    }

    override fun toBuilder(): Builder {
        return Builder(LinkedHashMap(data))
    }

    override fun asMutable(): MutableCompoundTag {
        return this
    }

    override fun asImmutable(): ImmutableCompoundTag {
        return ImmutableCompoundTagImpl(OrderedPMap.from(data))
    }

    override fun toString(): String {
        return "MutableCompoundTagImpl{data=$data}"
    }

    internal class Builder(
        data: Map<String, Tag>
    ) : AbstractCompoundTag.Builder<Builder, MutableCompoundTag>(data.toMutableMap()), MutableCompoundTag.Builder {

        constructor() : this(LinkedHashMap<String, Tag>())

        override fun build(): MutableCompoundTag {
            return MutableCompoundTagImpl(data)
        }
    }

    companion object {
        private fun deepCopy(data: Map<String, Tag>): MutableMap<String, Tag> {
            val result = LinkedHashMap<String, Tag>()
            for ((key, value) in data) {
                result[key] = value.copy()
            }
            return result
        }
    }
}
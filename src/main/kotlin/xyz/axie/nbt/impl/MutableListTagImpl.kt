package xyz.axie.nbt.impl

import xyz.axie.nbt.util.Types
import org.pcollections.TreePVector
import xyz.axie.nbt.*
import xyz.axie.nbt.AbstractListTag
import java.util.function.Predicate

internal data class MutableListTagImpl(
    override val data: MutableList<Tag>,
    override var elementType: Int
) : AbstractListTag<MutableListTag>(), MutableListTag {

    override fun elementType(): Int {
        return elementType
    }

    override fun add(tag: Tag): MutableListTag {
        if (tag.id == EndTag.ID) throw UnsupportedOperationException("Cannot add end tag to list!")

        checkUpdateType(tag, elementType)
        data.add(tag)
        return this
    }

    override fun addAll(tags: Collection<Tag?>): MutableListTag {
        for (tag in tags) {
            if (!canAdd(tag!!)) addUnsupported(tag.id, elementType())
            data.add(tag)
        }
        return this
    }

    override fun tryAdd(index: Int, tag: Tag): Boolean {
        if (tag.id == EndTag.ID || !updateType(tag)) return false
        data.add(index, tag)
        return true
    }

    override fun tryAdd(tag: Tag): Boolean {
        if (tag.id == EndTag.ID || !updateType(tag)) return false
        return data.add(tag)
    }

    override fun remove(index: Int): MutableListTag {
        data.removeAt(index)
        if (data.isEmpty()) elementType = EndTag.ID
        return this
    }

    override fun remove(tag: Tag): MutableListTag {
        data.remove(tag)
        if (data.isEmpty()) elementType = EndTag.ID
        return this
    }

    override fun removeAll(tags: Collection<Tag?>): MutableListTag {
        data.removeAll(tags)
        return this
    }

    override fun removeIf(predicate: Predicate<in Tag?>): MutableListTag {
        data.removeIf(predicate)
        return this
    }

    override fun set(index: Int, tag: Tag): MutableListTag {
        checkUpdateType(tag, elementType)
        data[index] = tag
        return this
    }

    private fun updateType(tag: Tag): Boolean {
        if (elementType == 0) {
            elementType = tag.id
            return true
        }
        return elementType == tag.id
    }

    private fun checkUpdateType(tag: Tag, elementType: Int) {
        if (!updateType(tag)) throw UnsupportedOperationException("Cannot add tag of type ${tag.id} to list of type $elementType!")
    }

    override fun clear() {
        data.clear()
        elementType = EndTag.ID
    }

    override fun copy(): MutableListTag {
        return MutableListTagImpl(deepCopy(data, elementType), elementType)
    }

    override fun toBuilder(): Builder {
        return Builder(data, elementType)
    }

    override fun asMutable(): MutableListTag {
        return this
    }

    override fun asImmutable(): ImmutableListTag {
        return ImmutableListTagImpl(TreePVector.from(data), elementType)
    }

    override fun toString(): String {
        return "MutableListTagImpl{data=$data, elementType=$elementType}"
    }

    internal class Builder(
        data: List<Tag>,
        elementType: Int
    ) : AbstractListTag.Builder<Builder, MutableListTag>(data.toMutableList(), elementType), MutableListTag.Builder {

        constructor(elementType: Int) : this(ArrayList<Tag>(), elementType)

        override fun build(): MutableListTag {
            return MutableListTagImpl(data, elementType)
        }
    }

    companion object {
        private fun deepCopy(data: List<Tag>, elementType: Int): MutableList<Tag> {
            if (Types.of(elementType).isValue) return data.toMutableList()

            val result = mutableListOf<Tag>()

            for (tag in data) {
                result.add(tag.copy())
            }

            return result
        }
    }
}
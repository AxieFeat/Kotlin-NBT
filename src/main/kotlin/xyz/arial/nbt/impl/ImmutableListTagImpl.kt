package xyz.arial.nbt.impl

import org.pcollections.PSequence
import org.pcollections.TreePVector
import xyz.arial.nbt.*
import xyz.arial.nbt.AbstractListTag
import java.util.function.Predicate

internal data class ImmutableListTagImpl(
    override val data: PSequence<Tag>,
    override val elementType: Int
) : AbstractListTag<ImmutableListTag>(), ImmutableListTag {

    override fun elementType(): Int {
        return elementType
    }

    fun getData(): List<Tag> {
        return data
    }

    override fun add(tag: Tag): ImmutableListTag {
        if (!canAdd(tag)) addUnsupported(tag.id, elementType())
        return ImmutableListTagImpl(data.plus(tag), elementType())
    }

    override fun addAll(tags: Collection<Tag?>): ImmutableListTag {
        for (tag in tags) {
            if (!canAdd(tag!!)) addUnsupported(tag.id, elementType())
        }
        return ImmutableListTagImpl(data.plusAll(tags), elementType())
    }

    override fun tryAdd(index: Int, tag: Tag): Boolean {
        // This tag is immutable, so this always returns false.
        return false
    }

    override fun tryAdd(tag: Tag): Boolean {
        // This tag is immutable, so this always returns false.
        return false
    }

    override fun remove(index: Int): ImmutableListTag {
        // Optimization: If we only have one element, we can only remove that element, which will result in an empty list,
        // therefore we return the empty list.
        if (data.isEmpty() || (data.size == 1 && index == 0)) return ListTag.EMPTY
        return ImmutableListTagImpl(data.minus(index), elementType())
    }

    override fun remove(tag: Tag): ImmutableListTag {
        // Optimization: If we only have one element, we can only remove that element, which will result in an empty list,
        // therefore we return the empty list.
        if (data.isEmpty() || (data.size == 1 && tag == data[0])) return ListTag.EMPTY
        return ImmutableListTagImpl(data.minus(tag), elementType())
    }

    override fun removeAll(tags: Collection<Tag?>): ImmutableListTag {
        // Optimization: We can't remove any data from an empty list.
        if (data.isEmpty()) return ListTag.EMPTY
        return ImmutableListTagImpl(data.minusAll(tags), elementType())
    }

    override fun removeIf(predicate: Predicate<in Tag?>): ImmutableListTag {
        if (data.isEmpty()) return ListTag.EMPTY
        var result: PSequence<Tag> = data
        for (i in 0 until data.size) {
            if (predicate.test(data[i])) result = result.minus(i)
        }
        // Optimization: If the sizes match, we removed nothing, so just return this tag.
        if (result.size == data.size) return this
        // Optimization: If the result is empty, we removed everything, so just return the empty list tag.
        if (result.isEmpty()) return ListTag.EMPTY
        return ImmutableListTagImpl(result, elementType())
    }

    override fun set(index: Int, tag: Tag): ImmutableListTag {
        return ImmutableListTagImpl(data.with(index, tag), elementType())
    }

    override fun copy(): ImmutableListTag {
        return this
    }

    override fun toBuilder(): ImmutableListTag.Builder {
        return Builder(ArrayList<Tag>(data), elementType())
    }

    override fun asMutable(): MutableListTag {
        return MutableListTagImpl(ArrayList(data), elementType())
    }

    override fun asImmutable(): ImmutableListTag {
        return this
    }

    override fun toString(): String {
        return "ImmutableListTagImpl{data=$data, elementType=$elementType}"
    }

    internal class Builder(
        data: List<Tag>,
        elementType: Int
    ) : AbstractListTag.Builder<Builder, ImmutableListTag>(data.toMutableList(), elementType), ImmutableListTag.Builder {

        constructor(elementType: Int) : this(ArrayList<Tag>(), elementType)

        override fun build(): ImmutableListTag {
            return ImmutableListTagImpl(TreePVector.from(data), elementType)
        }
    }
}
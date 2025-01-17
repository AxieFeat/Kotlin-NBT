package xyz.arial.nbt.impl

import xyz.arial.nbt.AbstractListCollectionTag
import xyz.arial.nbt.ArrayUtil
import xyz.arial.nbt.LongArrayTag
import xyz.arial.nbt.LongTag
import xyz.arial.nbt.TagType
import xyz.arial.nbt.visitor.StreamingTagVisitor
import xyz.arial.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException
import java.util.*
import java.util.function.LongConsumer
import java.util.stream.Stream

internal data class LongArrayTagImpl(
    override var data: LongArray
) : AbstractListCollectionTag<LongTag>(), LongArrayTag {

    override val id: Int = LongArrayTag.ID

    override fun type(): TagType<LongArrayTag> = LongArrayTag.TYPE

    override val elementType: Int = LongTag.ID

    override val size: Int
        get() = data.size

    override val empty: Boolean
        get() = data.isEmpty()

    override fun get(index: Int): LongTag {
        return LongTag.of(data[index])
    }

    override fun set(index: Int, value: Long) {
        data[index] = value
    }

    override fun set(index: Int, element: LongTag): LongTag {
        val old: LongTag = get(index)
        set(index, element.value)
        return old
    }

    override fun add(value: Long) {
        data = data.copyOf(data.size + 1)
        data[data.size - 1] = value
    }

    override fun add(index: Int, value: Long) {
        data = ArrayUtil.add(data, index, value)
    }

    override fun add(index: Int, element: LongTag) {
        add(index, element.value)
    }

    override fun removeAt(index: Int): LongTag {
        val old: LongTag = get(index)
        data = ArrayUtil.remove(data, index)
        return old
    }

    override fun forEachLong(action: LongConsumer) {
        for (element in data) {
            action.accept(element)
        }
    }

    override fun clear() {
        data = EMPTY_DATA
    }

    override fun stream(): Stream<LongTag> {
        return Arrays.stream(data).mapToObj<LongTag>(LongTag.Companion::of)
    }

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        output.writeInt(data.size)
        for (element in data) {
            output.writeLong(element)
        }
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitLongArray(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visit(data)
    }

    override fun copy(): LongArrayTag {
        return LongArrayTagImpl(data.copyOf(data.size))
    }

    override fun equals(other: Any?): Boolean {
        return this === other || (other is LongArrayTagImpl && data.contentEquals(other.data))
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }

    override fun toString(): String {
        return "LongArrayTagImpl{data=" + data.contentToString() + '}'
    }

    companion object {
        val EMPTY_DATA: LongArray = LongArray(0)

        fun createType(): TagType<LongArrayTag> {
            return object : TagType.VariableSize<LongArrayTag> {

                override val name: String = "LONG[]"

                override val prettyName: String = "TAG_Long_Array"

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): LongArrayTag {
                    val length = input.readInt()
                    val data = LongArray(length)
                    for (i in 0 until length) {
                        data[i] = input.readLong()
                    }
                    return LongArrayTagImpl(data)
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    val length = input.readInt()
                    val data = LongArray(length)
                    for (i in 0 until length) {
                        data[i] = input.readLong()
                    }
                    return visitor.visit(data)
                }


                @Throws(IOException::class)
                override fun skip(input: DataInput) {
                    input.skipBytes(input.readInt() * 8)
                }
            }
        }
    }
}
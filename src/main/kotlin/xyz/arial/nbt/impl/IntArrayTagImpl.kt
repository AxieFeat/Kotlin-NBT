package xyz.arial.nbt.impl

import net.aquamine.nbt.*
import xyz.arial.nbt.AbstractListCollectionTag
import xyz.arial.nbt.ArrayUtil
import xyz.arial.nbt.IntArrayTag
import xyz.arial.nbt.IntTag
import xyz.arial.nbt.TagType
import xyz.arial.nbt.visitor.StreamingTagVisitor
import xyz.arial.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException
import java.util.*
import java.util.function.IntConsumer
import java.util.stream.Stream

internal data class IntArrayTagImpl(
    override var data: IntArray
) : AbstractListCollectionTag<IntTag>(), IntArrayTag {

    override val id: Int = IntArrayTag.ID

    override val type: TagType<IntArrayTag> = IntArrayTag.TYPE

    override val size: Int
        get() = data.size

    override val isEmpty: Boolean
        get() = data.isEmpty()

    override val elementType: Int = IntTag.ID

    override fun get(index: Int): IntTag {
        return IntTag.of(data[index])
    }

    override fun set(index: Int, value: Int) {
        data[index] = value
    }

    override fun set(index: Int, element: IntTag): IntTag {
        val old: IntTag = get(index)
        set(index, element.value)
        return old
    }

    override fun add(value: Int) {
        data = data.copyOf(data.size + 1)
        data[data.size - 1] = value
    }

    override fun add(index: Int, value: Int) {
        data = ArrayUtil.add(data, index, value)
    }

    override fun add(index: Int, element: IntTag) {
        add(index, element.value)
    }

    override fun remove(index: Int): IntTag {
        val old: IntTag = get(index)
        data = ArrayUtil.remove(data, index)
        return old
    }

    override fun forEachInt(action: IntConsumer) {
        for (element in data) {
            action.accept(element)
        }
    }

    override fun clear() {
        data = EMPTY_DATA
    }

    override fun stream(): Stream<IntTag> {
        return Arrays.stream(data).mapToObj(IntTag.Companion::of)
    }

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        output.writeInt(data.size)
        for (element in data) {
            output.writeInt(element)
        }
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitIntArray(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visit(data)
    }

    override fun copy(): IntArrayTag {
        return IntArrayTagImpl(data.copyOf(data.size))
    }

    override fun equals(other: Any?): Boolean {
        return this === other || (other is IntArrayTagImpl && data.contentEquals(other.data))
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }

    override fun toString(): String {
        return "IntArrayTagImpl{data=" + data.contentToString() + '}'
    }

    companion object {
        val EMPTY_DATA: IntArray = IntArray(0)

        fun createType(): TagType<IntArrayTag> {
            return object : TagType.VariableSize<IntArrayTag> {

                override val name: String = "INT[]"

                override val prettyName: String = "TAG_Int_Array"

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): IntArrayTag {
                    val length = input.readInt()
                    val data = IntArray(length)
                    for (i in 0 until length) {
                        data[i] = input.readInt()
                    }
                    return IntArrayTagImpl(data)
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    val length = input.readInt()
                    val data = IntArray(length)
                    for (i in 0 until length) {
                        data[i] = input.readInt()
                    }
                    return visitor.visit(data)
                }

                @Throws(IOException::class)
                override fun skip(input: DataInput) {
                    input.skipBytes(input.readInt() * 4)
                }
            }
        }
    }
}
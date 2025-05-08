package xyz.axie.nbt.impl

import xyz.axie.nbt.AbstractListCollectionTag
import xyz.axie.nbt.ArrayUtil
import xyz.axie.nbt.ByteArrayTag
import xyz.axie.nbt.ByteTag
import xyz.axie.nbt.TagType
import xyz.axie.nbt.util.ByteConsumer
import xyz.axie.nbt.visitor.StreamingTagVisitor
import xyz.axie.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException
import java.util.stream.IntStream
import java.util.stream.Stream

internal data class ByteArrayTagImpl(
    override var data: ByteArray
) : AbstractListCollectionTag<ByteTag>(), ByteArrayTag {

    override val id: Int = ByteArrayTag.ID

    override fun type(): TagType<ByteArrayTag> = ByteArrayTag.TYPE

    override val size: Int
        get() = data.size

    override val elementType: Int = ByteTag.ID

    override fun isEmpty(): Boolean = data.isEmpty()

    override fun get(index: Int): ByteTag {
        return ByteTag.of(data[index])
    }

    override fun set(index: Int, value: Byte) {
        data[index] = value
    }

    override fun set(index: Int, element: ByteTag): ByteTag {
        val old: ByteTag = get(index)
        set(index, element.value)
        return old
    }

    override fun add(value: Byte) {
        data = data.copyOf(data.size + 1)
        data[data.size - 1] = value
    }

    override fun add(index: Int, value: Byte) {
        data = ArrayUtil.add(data, index, value)
    }

    override fun add(index: Int, element: ByteTag) {
        add(index, element.value)
    }

    override fun removeAt(index: Int): ByteTag {
        val old: ByteTag = get(index)
        data = ArrayUtil.remove(data, index)
        return old
    }

    override fun forEachByte(action: ByteConsumer) {
        for (element in data) {
            action.accept(element)
        }
    }

    override fun clear() {
        data = EMPTY_DATA
    }

    override fun stream(): Stream<ByteTag> {
        return IntStream.range(0, data.size).mapToObj { index: Int ->
            ByteTag.of(
                data[index]
            )
        }
    }

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        output.writeInt(data.size)
        output.write(data)
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitByteArray(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visit(data)
    }

    override fun copy(): ByteArrayTag {
        return ByteArrayTagImpl(data.copyOf(data.size))
    }

    override fun equals(other: Any?): Boolean {
        return this === other || (other is ByteArrayTagImpl && data.contentEquals(other.data))
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }

    override fun toString(): String {
        return "ByteArrayTagImpl{data=" + data.contentToString() + '}'
    }

    companion object {
        val EMPTY_DATA: ByteArray = ByteArray(0)

        fun createType(): TagType<ByteArrayTag> {
            return object : TagType.VariableSize<ByteArrayTag> {

                override val name: String = "BYTE[]"

                override val prettyName: String = "TAG_Byte_Array"

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): ByteArrayTag {
                    val length = input.readInt()
                    val data = ByteArray(length)
                    input.readFully(data)
                    return ByteArrayTagImpl(data)
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    val length = input.readInt()
                    val data = ByteArray(length)
                    input.readFully(data)
                    return visitor.visit(data)
                }

                @Throws(IOException::class)
                override fun skip(input: DataInput) {
                    input.skipBytes(input.readInt())
                }
            }
        }
    }
}
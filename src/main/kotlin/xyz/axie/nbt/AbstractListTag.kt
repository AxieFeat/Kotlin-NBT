package xyz.axie.nbt

import org.pcollections.TreePVector
import xyz.axie.nbt.ListTag.Companion.ID
import xyz.axie.nbt.ListTag.Companion.TYPE
import xyz.axie.nbt.impl.ImmutableListTagImpl
import xyz.axie.nbt.util.ByteConsumer
import xyz.axie.nbt.util.FloatConsumer
import xyz.axie.nbt.util.ShortConsumer
import xyz.axie.nbt.util.Types
import xyz.axie.nbt.visitor.StreamingTagVisitor
import xyz.axie.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException
import java.util.function.Consumer
import java.util.function.DoubleConsumer
import java.util.function.IntConsumer
import java.util.function.LongConsumer
import java.util.stream.Stream

@Suppress("UNCHECKED_CAST")
internal abstract class AbstractListTag<T : ListTag> : ListTag {

    override val id: Int = ID

    override fun type(): TagType<ListTag> = TYPE

    override val size: Int
        get() = data.size

    override fun isEmpty(): Boolean = data.isEmpty()

    override fun contains(element: Tag): Boolean {
        return data.contains(element)
    }

    // It's a delegate method. Performance should match that of the delegate.
    override fun containsAll(elements: Collection<Tag?>): Boolean {
        return data.containsAll(elements)
    }

    override fun get(index: Int): Tag {
        return data[index]
    }

    override fun getBoolean(index: Int, defaultValue: Boolean): Boolean {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is ByteTag) tag.value.toInt() != 0 else defaultValue
    }

    override fun getByte(index: Int, defaultValue: Byte): Byte {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is ByteTag) tag.value else defaultValue
    }

    override fun getShort(index: Int, defaultValue: Short): Short {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is ShortTag) tag.value else defaultValue
    }

    override fun getInt(index: Int, defaultValue: Int): Int {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is IntTag) tag.value else defaultValue
    }

    override fun getLong(index: Int, defaultValue: Long): Long {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is LongTag) tag.value else defaultValue
    }

    override fun getFloat(index: Int, defaultValue: Float): Float {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is FloatTag) tag.value else defaultValue
    }

    override fun getDouble(index: Int, defaultValue: Double): Double {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is DoubleTag) tag.value else defaultValue
    }

    override fun getString(index: Int, defaultValue: String): String {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is StringTag) tag.value else defaultValue
    }

    override fun getByteArray(index: Int, defaultValue: ByteArray): ByteArray {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is ByteArrayTag) tag.data else defaultValue
    }

    override fun getIntArray(index: Int, defaultValue: IntArray): IntArray {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is IntArrayTag) tag.data else defaultValue
    }

    override fun getLongArray(index: Int, defaultValue: LongArray): LongArray {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return if (tag is LongArrayTag) tag.data else defaultValue
    }

    override fun getList(index: Int, defaultValue: ListTag): ListTag {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return tag as? ListTag ?: defaultValue
    }

    override fun getCompound(index: Int, defaultValue: CompoundTag): CompoundTag {
        if (index < 0 || index >= size) return defaultValue
        val tag = get(index)
        return tag as? CompoundTag ?: defaultValue
    }

    abstract override fun set(index: Int, tag: Tag): T

    override fun setBoolean(index: Int, value: Boolean): T {
        return set(index, ByteTag.of(value))
    }

    override fun setByte(index: Int, value: Byte): T {
        return set(index, ByteTag.of(value))
    }

    override fun setShort(index: Int, value: Short): T {
        return set(index, ShortTag.of(value))
    }

    override fun setInt(index: Int, value: Int): T {
        return set(index, IntTag.of(value))
    }

    override fun setLong(index: Int, value: Long): T {
        return set(index, LongTag.of(value))
    }

    override fun setFloat(index: Int, value: Float): T {
        return set(index, FloatTag.of(value))
    }

    override fun setDouble(index: Int, value: Double): T {
        return set(index, DoubleTag.of(value))
    }

    override fun setString(index: Int, value: String): T {
        return set(index, StringTag.of(value))
    }

    override fun setByteArray(index: Int, value: ByteArray): T {
        return set(index, ByteArrayTag.of(value))
    }

    override fun setBytes(index: Int, vararg values: Byte): T {
        return set(index, ByteArrayTag.of(values))
    }

    override fun setIntArray(index: Int, value: IntArray): T {
        return set(index, IntArrayTag.of(value))
    }

    override fun setInts(index: Int, vararg values: Int): T {
        return set(index, IntArrayTag.of(values))
    }

    override fun setLongArray(index: Int, value: LongArray): T {
        return set(index, LongArrayTag.of(value))
    }

    override fun setLongs(index: Int, vararg values: Long): T {
        return set(index, LongArrayTag.of(values))
    }

    override fun forEachByte(action: ByteConsumer) {
        for (i in 0 until size) {
            action.accept(getByte(i))
        }
    }

    override fun forEachShort(action: ShortConsumer) {
        for (i in 0 until size) {
            action.accept(getShort(i))
        }
    }

    override fun forEachInt(action: IntConsumer) {
        for (i in 0 until size) {
            action.accept(getInt(i))
        }
    }

    override fun forEachLong(action: LongConsumer) {
        for (i in 0 until size) {
            action.accept(getLong(i))
        }
    }

    override fun forEachFloat(action: FloatConsumer) {
        for (i in 0 until size) {
            action.accept(getFloat(i))
        }
    }

    override fun forEachDouble(action: DoubleConsumer) {
        for (i in 0 until size) {
            action.accept(getDouble(i))
        }
    }

    override fun forEachString(action: Consumer<String>) {
        for (i in 0 until size) {
            action.accept(getString(i))
        }
    }

    override fun forEachByteArray(action: Consumer<ByteArray>) {
        for (i in 0 until size) {
            action.accept(getByteArray(i))
        }
    }

    override fun forEachIntArray(action: Consumer<IntArray>) {
        for (i in 0 until size) {
            action.accept(getIntArray(i))
        }
    }

    override fun forEachLongArray(action: Consumer<LongArray>) {
        for (i in 0 until size) {
            action.accept(getLongArray(i))
        }
    }

    override fun forEachList(action: Consumer<ListTag>) {
        for (i in 0 until size) {
            action.accept(getList(i))
        }
    }

    override fun forEachCompound(action: Consumer<CompoundTag>) {
        for (i in 0 until size) {
            action.accept(getCompound(i))
        }
    }

    override fun iterator(): Iterator<Tag> {
        return data.iterator()
    }

    override fun stream(): Stream<Tag> {
        return data.stream()
    }

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        val data: List<Tag> = data
        output.writeByte(elementType)
        output.writeInt(data.size)
        for (element in data) {
            element.write(output)
        }
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitList(this)
    }

    override  fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        val size: Int = data.size
        when (visitor.visitList(Types.of(id), size)) {
            StreamingTagVisitor.ValueResult.HALT -> {
                return StreamingTagVisitor.ValueResult.HALT
            }

            StreamingTagVisitor.ValueResult.BREAK -> {
                return visitor.visitContainerEnd()
            }

            else -> {
                var i = 0
                while (i < size) {
                    val value = get(i)
                    when (visitor.visitElement(value.type(), i)) {
                        StreamingTagVisitor.EntryResult.HALT -> return StreamingTagVisitor.ValueResult.HALT
                        StreamingTagVisitor.EntryResult.BREAK -> return visitor.visitContainerEnd()
                        StreamingTagVisitor.EntryResult.SKIP -> i++
                        else -> {
                            when (value.visit(visitor)) {
                                StreamingTagVisitor.ValueResult.HALT -> {
                                    return StreamingTagVisitor.ValueResult.HALT
                                }

                                StreamingTagVisitor.ValueResult.BREAK -> {
                                    return visitor.visitContainerEnd()
                                }

                                StreamingTagVisitor.ValueResult.CONTINUE -> i++
                            }
                        }
                    }
                }
                return visitor.visitContainerEnd()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return this === other || (other is AbstractListTag<*> && data == other.data)
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

    protected fun canAdd(tag: Tag): Boolean {
        if (tag.id == EndTag.ID) return false
        if (elementType == EndTag.ID) return true
        return elementType == tag.id
    }

    internal abstract class Builder<B : Builder<B, T>, T : ListTag> protected constructor(
        protected val data: MutableList<Tag>,
        protected var elementType: Int
    ) : ListTag.Builder {

        override fun add(tag: Tag): B {
            require(!(data.isNotEmpty() && elementType != tag.id)) { ("Cannot append element of type ${tag.id} to a builder for type $elementType!") }
            data.add(tag)
            elementType = tag.id
            return this as B
        }

        override fun remove(element: Tag): B {
            data.remove(element)
            return this as B
        }

        override fun from(other: ListTag.Builder): B {
            data.addAll((other as Builder<*, *>).data)
            return this as B
        }

        override fun from(other: ListTag): B {
            data.addAll(other.data)
            return this as B
        }

        abstract override fun build(): T
    }

    companion object {
        fun createType(): TagType<ListTag> {
            return object : TagType.VariableSize<ListTag> {
                override val name: String = "LIST"

                override val prettyName: String = "TAG_List"

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): ListTag {
                    if (depth > 512) throw RuntimeException("Depth too high! Given depth $depth is higher than maximum depth 512!")
                    val id = input.readByte().toInt()
                    val size = input.readInt()
                    if (id == EndTag.ID && size > 0) throw RuntimeException("Missing required type for list tag!")
                    val type: TagType<*> = Types.of(id)
                    val data: MutableList<Tag> = ArrayList(size)
                    repeat(size) {
                        data.add(type.load(input, depth + 1))
                    }
                    return ImmutableListTagImpl(TreePVector.from(data), id)
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    val type: TagType<*> = Types.of(input.readInt())
                    val size = input.readInt()
                    when (visitor.visitList(type, size)) {
                        StreamingTagVisitor.ValueResult.HALT -> {
                            return StreamingTagVisitor.ValueResult.HALT
                        }

                        StreamingTagVisitor.ValueResult.BREAK -> {
                            type.skip(input, size)
                            return visitor.visitContainerEnd()
                        }

                        else -> {
                            var i = 0
                            while (true) {
                                if (i >= size) {
                                    val remaining = size - 1 - i
                                    if (remaining > 0) type.skip(input, remaining)
                                    return visitor.visitContainerEnd()
                                }
                                when (visitor.visitElement(type, i)) {
                                    StreamingTagVisitor.EntryResult.HALT -> {
                                        return StreamingTagVisitor.ValueResult.HALT
                                    }

                                    StreamingTagVisitor.EntryResult.BREAK -> {
                                        type.skip(input)
                                        val remaining = size - 1 - i
                                        if (remaining > 0) type.skip(input, remaining)
                                        return visitor.visitContainerEnd()
                                    }

                                    StreamingTagVisitor.EntryResult.SKIP -> {
                                        type.skip(input)
                                        i++
                                    }

                                    else -> {
                                        when (type.parse(input, visitor)) {
                                            StreamingTagVisitor.ValueResult.HALT -> {
                                                return StreamingTagVisitor.ValueResult.HALT
                                            }

                                            StreamingTagVisitor.ValueResult.BREAK -> {
                                                val remaining = size - 1 - i
                                                if (remaining > 0) type.skip(input, remaining)
                                                return visitor.visitContainerEnd()
                                            }

                                            else -> i++
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                @Throws(IOException::class)
                override fun skip(input: DataInput) {
                    val type: TagType<*> = Types.of(input.readInt())
                    val size = input.readInt()
                    type.skip(input, size)
                }
            }
        }

        @JvmStatic
        protected fun addUnsupported(tagType: Int, elementType: Int) {
            throw UnsupportedOperationException("Cannot add tag of type $tagType to list of type $elementType!")
        }
    }
}
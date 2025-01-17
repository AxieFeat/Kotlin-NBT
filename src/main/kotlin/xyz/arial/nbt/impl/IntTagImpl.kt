package xyz.arial.nbt.impl

import xyz.arial.nbt.IntTag
import xyz.arial.nbt.TagType
import xyz.arial.nbt.visitor.StreamingTagVisitor
import xyz.arial.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException

internal data class IntTagImpl(
    override val value: Int
) : IntTag {

    override val id: Int = IntTag.ID

    override fun type(): TagType<IntTag> = IntTag.TYPE

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        output.writeInt(value)
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitInt(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visit(value)
    }

    override fun copy(): IntTag {
        return this
    }

    override fun asNumber(): Number {
        return value
    }

    override fun toDouble(): Double {
        return value.toDouble()
    }

    override fun toFloat(): Float {
        return value.toFloat()
    }

    override fun toLong(): Long {
        return value.toLong()
    }

    override fun toInt(): Int {
        return value
    }

    override fun toShort(): Short {
        return (value and 0xFFFF).toShort()
    }

    override fun toByte(): Byte {
        return (value and 0xFF).toByte()
    }

    private object Cache {
        const val LOWER_LIMIT: Int = -128
        const val UPPER_LIMIT: Int = 1024
        val CACHE: Array<IntTag?> = arrayOfNulls(UPPER_LIMIT - LOWER_LIMIT + 1)

        init {
            for (i in CACHE.indices) {
                CACHE[i] = IntTagImpl(i + LOWER_LIMIT)
            }
        }
    }

    companion object {
        fun of(value: Int): IntTag {
            if (value >= Cache.LOWER_LIMIT && value <= Cache.UPPER_LIMIT) return Cache.CACHE[value - Cache.LOWER_LIMIT]!!
            return IntTagImpl(value)
        }

        fun createType(): TagType<IntTag> {
            return object : TagType.StaticSize<IntTag> {

                override val name: String = "INT"

                override val prettyName: String = "TAG_Int"

                override val isValue: Boolean
                    get() = true

                override val size: Int = 4

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): IntTag {
                    return of(input.readInt())
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    return visitor.visit(input.readInt())
                }
            }
        }
    }
}
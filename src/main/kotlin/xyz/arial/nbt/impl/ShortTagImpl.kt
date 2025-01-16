package xyz.arial.nbt.impl

import xyz.arial.nbt.ShortTag
import xyz.arial.nbt.TagType
import xyz.arial.nbt.visitor.StreamingTagVisitor
import xyz.arial.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException

internal data class ShortTagImpl(
    override val value: Short
) : ShortTag {

    override val id: Int = ShortTag.ID

    override val type: TagType<ShortTag> = ShortTag.TYPE

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        output.writeShort(value.toInt())
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitShort(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visit(value)
    }

    override fun copy(): ShortTag {
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
        return value.toInt()
    }

    override fun toShort(): Short {
        return value
    }

    override fun toByte(): Byte {
        return (value.toInt() and 0xFF).toByte()
    }

    private object Cache {
        const val LOWER_LIMIT: Int = -128
        const val UPPER_LIMIT: Int = 1024
        val CACHE: Array<ShortTag?> = arrayOfNulls(UPPER_LIMIT - LOWER_LIMIT + 1)

        init {
            for (i in CACHE.indices) {
                CACHE[i] = ShortTagImpl((i + LOWER_LIMIT).toShort())
            }
        }
    }

    companion object {
        fun of(value: Short): ShortTag {
            if (value >= Cache.LOWER_LIMIT && value <= Cache.UPPER_LIMIT) return Cache.CACHE[value - Cache.LOWER_LIMIT]!!
            return ShortTagImpl(value)
        }

        fun createType(): TagType<ShortTag> {
            return object : TagType.StaticSize<ShortTag> {

                override val name: String = "SHORT"

                override val prettyName: String = "TAG_Short"

                override val isValue: Boolean
                    get() = true

                override val size: Int = 2

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): ShortTag {
                    return of(input.readShort())
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    return visitor.visit(input.readShort())
                }
            }
        }
    }
}
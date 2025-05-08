package xyz.axie.nbt.impl

import xyz.axie.nbt.DoubleTag
import xyz.axie.nbt.FloorMath
import xyz.axie.nbt.TagType
import xyz.axie.nbt.visitor.StreamingTagVisitor
import xyz.axie.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException
import kotlin.math.floor

internal data class DoubleTagImpl(
    override val value: Double
) : DoubleTag {

    override val id: Int = DoubleTag.ID

    override fun type(): TagType<DoubleTag> = DoubleTag.TYPE

    @Throws(IOException::class)
    override fun write(output: DataOutput) {
        output.writeDouble(value)
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitDouble(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visit(value)
    }

    override fun copy(): DoubleTag {
        return this
    }

    override fun asNumber(): Number {
        return value
    }

    override fun toDouble(): Double {
        return value
    }

    override fun toFloat(): Float {
        return value.toFloat()
    }

    override fun toLong(): Long {
        return floor(value).toLong()
    }

    override fun toInt(): Int {
        return FloorMath.floor(value)
    }

    override fun toShort(): Short {
        return (FloorMath.floor(value) and 0xFFFF).toShort()
    }

    override fun toByte(): Byte {
        return (FloorMath.floor(value) and 0xFF).toByte()
    }

    companion object {
        fun createType(): TagType<DoubleTag> {
            return object : TagType.StaticSize<DoubleTag> {

                override val name: String = "DOUBLE"

                override val prettyName: String = "TAG_Double"

                override val isValue: Boolean
                    get() = true

                override val size: Int = 8

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): DoubleTag {
                    return DoubleTag.of(input.readDouble())
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    return visitor.visit(input.readDouble())
                }
            }
        }
    }
}
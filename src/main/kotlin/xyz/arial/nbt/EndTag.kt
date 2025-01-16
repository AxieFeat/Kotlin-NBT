package xyz.arial.nbt

import xyz.arial.nbt.visitor.StreamingTagVisitor
import xyz.arial.nbt.visitor.TagVisitor
import java.io.DataInput
import java.io.DataOutput

/**
 * The tag representing the end of a compound tag.
 */
class EndTag private constructor() : ScopedTag<EndTag> {

    override val id: Int = ID

    override val type: TagType<EndTag> = TYPE

    override fun write(output: DataOutput) {
        // Nothing to write for end tags
    }

    override fun visit(visitor: TagVisitor) {
        visitor.visitEnd(this)
    }

    override fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
        return visitor.visitEnd()
    }

    override fun copy(): EndTag {
        return this
    }

    override fun toString(): String {
        return "EndTag"
    }

    companion object {
        /**
         * The singleton instance that represents an end tag.
         */
        val INSTANCE: EndTag = EndTag()

        /**
         * The ID of this type of tag.
         *
         *
         * Used for [CollectionTag.elementType] and in the serialized
         * binary form.
         */
        const val ID: Int = 0

        /**
         * The tag type for this tag.
         */
        val TYPE: TagType<EndTag> = object : TagType<EndTag> {

            override val name: String = "END"

            override val prettyName: String = "TAG_End"

            override val isValue: Boolean
                get() = true

            override fun load(input: DataInput, depth: Int): EndTag {
                return INSTANCE
            }

            override fun parse(input: DataInput, visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult {
                return visitor.visitEnd()
            }

            override fun skip(input: DataInput, bytes: Int) {
                // Will never be anything to skip.
            }

            override fun skip(input: DataInput) {
                // Will never be anything to skip.
            }
        }
    }
}
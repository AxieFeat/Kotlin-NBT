package xyz.arial.nbt

import xyz.arial.nbt.visitor.StreamingTagVisitor
import xyz.arial.nbt.visitor.StringTagVisitor
import xyz.arial.nbt.visitor.TagVisitor
import java.io.DataOutput
import java.io.IOException

/**
 * The base supertype for all NBT tags.
 */
interface Tag {

    /**
     * Gets the ID of this tag.
     *
     * @return the ID of this tag
     */
   val id: Int

    /**
     * Gets the type of this tag.
     *
     * @return the type of this tag
     */
    val type: TagType<*>

    /**
     * Writes this tag's contents to the given output.
     *
     * @param output the output to write to
     * @throws IOException if an IO error occurs
     */
    @Throws(IOException::class)
    fun write(output: DataOutput)

    /**
     * Visits this tag's contents using the given visitor.
     *
     * @param visitor the visitor
     */
    fun visit(visitor: TagVisitor)

    /**
     * Visits this tag's contents using the given visitor.
     *
     * @param visitor the visitor
     * @return the result
     */
    fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult

    /**
     * Converts this tag in to its SNBT form.
     *
     * @return the SNBT form of this tag
     */
    fun asString(): String {
        return StringTagVisitor().visit(this)
    }

    /**
     * Creates a copy of this tag and returns the result.
     *
     * @return the resulting copy
     * @implSpec If this tag is immutable, it will return itself, as there is
     * no need to make a copy of it. Otherwise, it will copy/clone all relevant
     * fields and construct a new object with the results.
     */
    fun copy(): Tag
}

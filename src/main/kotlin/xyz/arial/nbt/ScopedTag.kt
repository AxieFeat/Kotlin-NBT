package xyz.arial.nbt

interface ScopedTag<T : ScopedTag<T>> : Tag {
    override val type: TagType<T>

    override fun copy(): T
}

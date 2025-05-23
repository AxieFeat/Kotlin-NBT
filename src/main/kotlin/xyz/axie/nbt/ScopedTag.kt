package xyz.axie.nbt

interface ScopedTag<T : ScopedTag<T>> : Tag {

    override fun type(): TagType<T>

    override fun copy(): T

}

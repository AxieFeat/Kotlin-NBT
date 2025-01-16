package xyz.arial.nbt

import java.util.*

internal abstract class AbstractListCollectionTag<T : Tag> : AbstractList<T>(), ListCollectionTag<T> {
    override fun tryAdd(index: Int, tag: T): Boolean {
        add(index, tag)
        return true
    }

    override fun tryAdd(tag: T): Boolean {
        return add(tag)
    }
}
package xyz.arial.nbt

import java.util.stream.Stream

interface ListCollectionTag<T : Tag> : CollectionTag<T>, MutableList<T> {

    override fun isEmpty(): Boolean

    override fun stream(): Stream<T>

}
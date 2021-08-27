package timelinejs.rendering.compound

import timelinejs.rendering.Renderable

open class RenderParent : Renderable {
    private val children = mutableListOf<Renderable>()

    protected fun addChildren(vararg children: Renderable) {
        this.children += children
    }

    override fun render() {
        renderChildren()
    }

    protected fun renderChildren() {
        for (child in children) {
            child.render()
        }
    }
}
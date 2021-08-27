package timelinejs.rendering.compound

import timelinejs.rendering.Renderable

open class RenderParent : Renderable {
    protected var children = mutableListOf<Renderable>()

    override fun render() {
        renderChildren()
    }

    protected fun renderChildren() {
        for (child in children) {
            child.render()
        }
    }
}
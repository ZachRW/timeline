package timelinejs.rendering.compound

import timelinejs.rendering.Renderable
import timelinejs.rendering.StaticRenderable

open class StaticRenderParent : StaticRenderable {
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
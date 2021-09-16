package timelinejs.rendering.compound

import timelinejs.View
import timelinejs.rendering.DynamicRenderable
import timelinejs.rendering.Renderable

open class DynamicRenderParent(view: View) : DynamicRenderable(view) {
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
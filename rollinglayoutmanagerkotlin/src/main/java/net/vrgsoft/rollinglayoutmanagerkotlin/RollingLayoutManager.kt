package net.vrgsoft.rollinglayoutmanagerkotlin


import android.content.Context
import android.graphics.PointF
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.Log


class RollingLayoutManager(context: Context) : LinearLayoutManager(context) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        updateViewScale()
        super.onLayoutChildren(recycler, state)
    }

    private fun updateViewScale() {

        val height = height.toFloat()
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val childHeight = view.height
            val rotationTresholdPersent = 1f - childHeight / height
            val thresholdPx = (height * rotationTresholdPersent).toInt()
            var scale: Float
            val viewTop = getDecoratedTop(view)
            if (viewTop >= thresholdPx) {
                val delta = viewTop - thresholdPx
                scale = (childHeight - delta) / childHeight.toFloat()
                scale = Math.max(scale, 0f)
                view.alpha = 0.1f + 0.9f * scale
                view.pivotX = (view.width / 2).toFloat()
                view.pivotY = (-view.height / 12).toFloat()
                view.rotationX = -90 * (1 - scale)
            } else {
                view.rotationX = 0f
                view.alpha = 1f
            }
        }
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        if (position >= itemCount) {
            Log.e(TAG, "Cannot scroll to $position, item count is $itemCount")
            return
        }

        val scroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@RollingLayoutManager.computeScrollVectorForPosition(targetPosition)
            }

            override fun getVerticalSnapPreference(): Int {
                return LinearSmoothScroller.SNAP_TO_START
            }
        }
        scroller.targetPosition = position
        startSmoothScroll(scroller)
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
        if (childCount == 0) {
            return null
        }
        val firstChildPos = getPosition(getChildAt(0))
        val direction = if (targetPosition < firstChildPos) -1 else 1
        return PointF(0f, direction.toFloat())
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        updateViewScale()
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    companion object {
        private val TAG = "RollingLayoutManager"
    }
}

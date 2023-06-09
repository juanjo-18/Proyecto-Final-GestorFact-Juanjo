package clases

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Esta clase se encarga de añadir tanto un espacio arriba, derecha y izquierda para los recycer View
 * @author Juanjo medina
 */
class ItemSpacingDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = spacing
        outRect.right=spacing/2
        outRect.left=spacing/2
    }
}
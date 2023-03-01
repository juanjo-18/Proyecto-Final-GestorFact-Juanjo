package recyclers.albaranes

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import clases.Albaran

class AlbaranesAdapter  (val actividadMadre: Activity, val datos:ArrayList<Albaran>) : RecyclerView.Adapter<AlbaranesViewHolder>()

}
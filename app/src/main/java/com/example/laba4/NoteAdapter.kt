import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.laba4.Note
import com.example.laba4.R

class NoteAdapter(context: Context, notes: List<Note>) :
    ArrayAdapter<Note>(context, 0, notes) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                R.layout.list_item_note, parent, false
            )
        }

        val currentNote = getItem(position)

        val titleTextView = listItemView!!.findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = "ID: ${currentNote?.id} - ${currentNote?.title}"

        val textTextView = listItemView.findViewById<TextView>(R.id.textTextView)
        textTextView.text = currentNote?.text

        return listItemView
    }
}

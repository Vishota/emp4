package com.example.laba4

import NotesDBHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var noteIdEditText: EditText
    private lateinit var deleteButton: Button
    private lateinit var dbHelper: NotesDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_third, container, false)

        noteIdEditText = rootView.findViewById(R.id.noteIdEditText)
        deleteButton = rootView.findViewById(R.id.deleteButton)

        dbHelper = NotesDBHelper(requireContext())

        deleteButton.setOnClickListener {
            val noteId = noteIdEditText.text.toString().toLongOrNull()
            if (noteId != null) {
                val rowsAffected = dbHelper.deleteNoteById(noteId)
                if (rowsAffected > 0) {
                    Toast.makeText(context, "Заметка с ID $noteId была удалена", Toast.LENGTH_SHORT).show()
                    noteIdEditText.setText("")
                } else {
                    Toast.makeText(context, "Заметка c ID $noteId не была найдена", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Введите в поле ID заметки", Toast.LENGTH_LONG).show()
            }
        }

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
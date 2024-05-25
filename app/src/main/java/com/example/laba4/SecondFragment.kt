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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SecondFragment : Fragment() {


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

    private lateinit var titleEditText: EditText
    private lateinit var textEditText: EditText
    private lateinit var addButton: Button
    private lateinit var dbHelper: NotesDBHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_second, container, false)

        titleEditText = rootView.findViewById(R.id.titleEditText)
        textEditText = rootView.findViewById(R.id.textEditText)
        addButton = rootView.findViewById(R.id.addButton)

        dbHelper = NotesDBHelper(requireContext())

        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val text = textEditText.text.toString()
            if (title.isNotEmpty() && text.isNotEmpty()) {
                val id = dbHelper.addNote(title, text)
                if (id != -1L) {
                    Toast.makeText(context, "Заметка $title была добавлена!", Toast.LENGTH_SHORT).show()
                    titleEditText.setText("")
                    textEditText.setText("")
                }
            } else {

                Toast.makeText(context, "Необходимо заполнить поля заголовка и текста", Toast.LENGTH_LONG).show()
            }
        }

        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
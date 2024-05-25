import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.example.laba4.Note

class NotesDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "notes.db"
        private const val TABLE_NAME = "notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_TEXT = "text"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_NOTES_TABLE = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_TITLE TEXT,"
                + "$COLUMN_TEXT TEXT)")
        db.execSQL(CREATE_NOTES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addNote(title: String, text: String): Long {
        val values = ContentValues()
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_TEXT, text)

        val db = this.writableDatabase
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllNotes(): List<Note> {
        val notes = ArrayList<Note>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getLongOrNull(cursor.getColumnIndex(COLUMN_ID))
                val title = cursor.getStringOrNull(cursor.getColumnIndex(COLUMN_TITLE))
                val text = cursor.getStringOrNull(cursor.getColumnIndex(COLUMN_TEXT))
                if (id != null && title != null && text != null) {
                    val note = Note(id, title, text)
                    notes.add(note)
                }

            } while (cursor.moveToNext())
        }
        cursor?.close()
        db.close()
        return notes
    }


    fun updateNoteById(id: Long, newTitle: String, newText: String): Int {
        val values = ContentValues().apply {
            put(COLUMN_TITLE, newTitle)
            put(COLUMN_TEXT, newText)
        }
        val db = this.writableDatabase
        val rowsAffected = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteNoteById(id: Long): Int {
        val db = this.writableDatabase
        val rowsAffected = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

}


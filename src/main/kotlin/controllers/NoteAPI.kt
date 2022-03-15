package controllers

import models.Note
import persistence.Serializer

class NoteAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun listAllNotes(): String =
        if  (notes.isEmpty()) "No notes stored"
        else notes.joinToString (separator = "\n") { note ->
            notes.indexOf(note).toString() + ": " + note.toString() }

    fun numberOfNotes(): Int {
        return notes.size
    }

    fun deleteNote(indexToDelete: Int) : Note? {
        return if(isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null
    }

    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        val foundNote = findNote(indexToUpdate)

        if((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }

        return false
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun isValidIndex(index: Int) : Boolean {
        return isValidListIndex(index, notes)
    }


    fun listActiveNotes(): String {
        return if(numberOfActiveNotes() == 0) "No active notes stored"
        else notes.filter { !it.isNoteArchived }
            .joinToString(separator = "\n") { note ->
                notes.indexOf(note).toString() + ": " + note.toString()
            }
    }

    fun archiveNote(noteIndex: Int) : Boolean {
        if(isValidIndex(noteIndex)) {
            var note = notes[noteIndex]
            if(!note.isNoteArchived) {
                note.isNoteArchived = true
                return true
            }
        }
        return false
    }



    fun listArchivedNotes(): String {
      return if (numberOfArchivedNotes() == 0) "No Archived Notes Stored"
        else notes.filter { it.isNoteArchived }
            .joinToString(separator = "\n") { note ->
                notes.indexOf(note).toString() + ": " + note.toString()
            }
    }

    fun numberOfArchivedNotes(): Int {
        return notes.stream()
            .filter{note: Note -> note.isNoteArchived}
            .count()
            .toInt()
    }

    fun numberOfActiveNotes(): Int {
        return notes.stream()
            .filter{note: Note -> !note.isNoteArchived}
            .count()
            .toInt()
    }

//    fun listNotesBySelectedPriority(priority: Int): String {
//
//        var notesByPriority = ""
//        for(Note in notes) {
//            if(Note.notePriority == priority) {
//                notesByPriority += "${Note}\n"
//            }
//        }
//        if(notesByPriority.isEmpty()) {
//            return "No Note at that priority level"
//        }
//        return notesByPriority
//    }

    fun listNotesBySelectedPriority(priority: Int): String {
        return if(numberOfNotesByPriority(priority) == 0) "No note at that priority level"
        else notes.filter { it.notePriority == priority }
            .joinToString(separator = "\n") { note ->
                notes.indexOf(note).toString() + ": " + note.toString()
            }
    }


    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }




    fun numberOfNotesByPriority(priority: Int): Int {
        return notes.stream()
            .filter{note: Note -> note.notePriority == priority}
            .count()
            .toInt()
    }

}

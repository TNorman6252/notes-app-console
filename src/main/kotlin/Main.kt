import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.Integer.parseInt
import java.lang.System.exit


private val logger = KotlinLogging.logger {}
private val noteAPI = NoteAPI()


fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add a note                |
         > |   2) List all notes            |
         > |   3) List Active Notes             |
         > |   4) List Archived Note             |
         > |   5) Number Of Archived Notes
         > |   6) Number Of Active Notes
         > |   7) List Notes by Selected Priority
         > |   8) Update Note
         > |   9) Delete Note
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        val option = mainMenu()
        when(option) {
            1 -> addNote()
            2 -> listNotes()
            3 -> listActiveNotes()
            4 -> listArchivedNotes()
            5 -> numberOfArchivedNotes()
            6 -> numberOfActiveNotes()
            7 -> listNotesBySelectedPriority()
            8 -> updateNote()
            9 -> deleteNote()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while(true)
}

fun addNote() {
    //logger.info { "addNote() function invoked" }
    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = readNextLine("Enter a category for the note: ")
    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, false))

    if(isAdded) {
        println("Added successfully")
    } else {
        println("Add failed")
    }
}

fun listNotes() {
   // logger.info { "listNotes() function invoked" }
    println(noteAPI.listAllNotes())
}

fun listActiveNotes() {
    println(noteAPI.listActiveNotes())
}

fun listArchivedNotes() {
    println(noteAPI.listArchivedNotes())
}

fun numberOfArchivedNotes() {
    println(noteAPI.numberOfArchivedNotes())
}

fun numberOfActiveNotes() {
    println(noteAPI.numberOfActiveNotes())
}

fun listNotesBySelectedPriority() {
    var notePriority = parseInt(readNextLine("Enter Note Priority Level: "))
    println(noteAPI.listNotesBySelectedPriority(notePriority))
}

fun updateNote() {
    logger.info { "updateNote() function invoked" }
}

fun deleteNote() {
    logger.info { "deleteNote() function invoked" }
}

fun exitApp() {
    println("Exiting...bye")
    exit(0)
}
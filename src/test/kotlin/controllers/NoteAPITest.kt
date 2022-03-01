package controllers

import models.Note
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NoteAPITest {

    private var learnKotlin: Note? = null
    private var summerHoliday: Note? = null
    private var codeApp: Note? = null
    private var testApp: Note? = null
    private var swim: Note? = null
    private var populatedNotes: NoteAPI? = NoteAPI()
    private var emptyNotes: NoteAPI? = NoteAPI()
    private var notesArchived: NoteAPI? = NoteAPI()
    private var notesActive: NoteAPI? = NoteAPI()
    private var notesPriority: NoteAPI? = NoteAPI()

    @BeforeEach
    fun setup(){
        learnKotlin = Note("Learning Kotlin", 5, "College", false)
        summerHoliday = Note("Summer Holiday to France", 1, "Holiday", false)
        codeApp = Note("Code App", 4, "Work", false)
        testApp = Note("Test App", 4, "Work", false)
        swim = Note("Swim - Pool", 3, "Hobby", false)

        //adding 5 Note to the notes api
        populatedNotes!!.add(learnKotlin!!)
        populatedNotes!!.add(summerHoliday!!)
        populatedNotes!!.add(codeApp!!)
        populatedNotes!!.add(testApp!!)
        populatedNotes!!.add(swim!!)

    }

    @AfterEach
    fun tearDown(){
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        populatedNotes = null
        emptyNotes = null
    }

    @Nested
    inner class AddNotes {
        @Test
        fun `adding a Note to a populated list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(5, populatedNotes!!.numberOfNotes())
            assertTrue(populatedNotes!!.add(newNote))
            assertEquals(6, populatedNotes!!.numberOfNotes())
            assertEquals(newNote, populatedNotes!!.findNote(populatedNotes!!.numberOfNotes() - 1))
        }

        @Test
        fun `adding a Note to an empty list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.add(newNote))
            assertEquals(1, emptyNotes!!.numberOfNotes())
            assertEquals(newNote, emptyNotes!!.findNote(emptyNotes!!.numberOfNotes() - 1))
        }
    }

    @Nested
    inner class ListNotes {

        @Test
        fun `listAllNotes returns No Notes Stored message when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.listAllNotes().lowercase().contains("no notes"))
        }

        @Test
        fun `listAllNotes returns Notes when ArrayList has notes stored`() {
            assertEquals(5, populatedNotes!!.numberOfNotes())
            val notesString = populatedNotes!!.listAllNotes().lowercase()
            assertTrue(notesString.contains("learning kotlin"))
            assertTrue(notesString.contains("code app"))
            assertTrue(notesString.contains("test app"))
            assertTrue(notesString.contains("swim"))
            assertTrue(notesString.contains("summer holiday"))
        }
    }

    @Nested
    inner class ActiveNotes {

        @Test
        fun `listActiveNotes returns No Active Notes message when all arraylist objects have isNoteArchived set to true`() {
            val noteArchivedTest = Note("Test title", 2, "Test category", true)
            assertEquals(0, notesArchived!!.numberOfNotes())
            assertTrue( notesArchived!!.add(noteArchivedTest))
            assertEquals(1, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.listActiveNotes().lowercase().contains("no active notes"))
        }

        @Test
        fun `listAllNotes returns Active Notes when all arrayList objects have isNoteArchived set to false`() {
            val noteNotArchivedTest = Note("Test title", 2, "Test category", false)
            assertEquals(0, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.add(noteNotArchivedTest))
            assertEquals(1, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.listActiveNotes().contains(noteNotArchivedTest.toString()))
        }
    }

    @Nested
    inner class ArchivedNotes {

        @Test
        fun `listArchivedNotes returns No Archived Notes message when all arraylist objects have isNoteArchived set to false`() {
            val noteNotArchivedTest = Note("Test title", 2, "Test category", false)
            assertEquals(0, notesArchived!!.numberOfNotes())
            assertTrue( notesArchived!!.add(noteNotArchivedTest))
            assertEquals(1, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.listArchivedNotes().lowercase().contains("no archived notes"))
        }

        @Test
        fun `listArchivedNotes returns Archived Notes when all arrayList objects have isNoteArchived set to true`() {
            val noteArchivedTest = Note("Test title", 2, "Test category", true)
            assertEquals(0, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.add(noteArchivedTest))
            assertEquals(1, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.listArchivedNotes().contains(noteArchivedTest.toString()))
        }
    }

    @Nested
    inner class NumberOfArchivedNotes {

        @Test
        fun `prints number of Archived Notes`() {
            val noteNotArchivedTest = Note("Test title", 2, "Test category", false)
            val noteArchivedTest = Note("Test title", 2, "Test category", true)

            assertEquals(0, notesArchived!!.numberOfArchivedNotes())
            assertTrue( notesArchived!!.add(noteNotArchivedTest))
            assertTrue( notesArchived!!.add(noteArchivedTest))
            assertEquals(1, notesArchived!!.numberOfArchivedNotes())
        }
    }

    @Nested
    inner class NumberOfActiveNotes {

        @Test
        fun `prints number of Active Notes`() {
            val noteNotArchivedTest = Note("Test title", 2, "Test category", false)
            val noteNotArchivedTest2 = Note("Another Test title", 3, "Test category", false)
            val noteArchivedTest = Note("Test title", 2, "Test category", true)

            assertEquals(0, notesActive!!.numberOfActiveNotes())
            assertTrue( notesActive!!.add(noteNotArchivedTest))
            assertTrue( notesActive!!.add(noteNotArchivedTest2))
            assertTrue( notesActive!!.add(noteArchivedTest))
            assertEquals(2, notesActive!!.numberOfActiveNotes())
        }
    }

    @Nested
    inner class ListNotesByPriority {

        @Test
        fun `prints Notes by selected priority`() {
            val note1 = Note("Test title", 2, "Test category", false)
            val note2 = Note("Another Test title", 5, "Test category", false)
            val note3 = Note("Test title", 2, "Test category", true)

            assertEquals(0, notesPriority!!.numberOfNotes())
            assertTrue( notesPriority!!.add(note1))
            assertTrue( notesPriority!!.add(note2))
            assertTrue( notesPriority!!.add(note3))
            val answer = notesPriority!!.listNotesBySelectedPriority(2)
            assertTrue(answer.contains(note1.toString()))
            assertTrue(!answer.contains(note2.toString()))
            assertTrue(answer.contains(note3.toString()))
        }
    }


}
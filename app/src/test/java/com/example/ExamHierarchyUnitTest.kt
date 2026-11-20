package com.example

import com.example.data.seed.SeedExamData
import com.example.data.seed.StudyNotesSeedData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExamHierarchyUnitTest {

    @Test
    fun examSections_areInExactRequestedOrder() {
        val sectionIds = SeedExamData.getExamSections().map { it.id }
        assertEquals(
            listOf(
                "Navodaya",
                "Sainik School",
                "Simultala",
                "Olympiad",
                "Railway",
                "SSC",
                "All"
            ),
            sectionIds
        )
    }

    @Test
    fun legacyTestIds_arePreservedAndNormalized() {
        val tests = SeedExamData.getAllTests()
        val rail = tests.find { it.id == "test_railway_math_01" }!!
        val ssc = tests.find { it.id == "test_ssc_cgl_chsl_02" }!!
        val jnv = tests.find { it.id == "test_jnv_sainik_03" }!!

        assertEquals("Railway", rail.category)
        assertEquals("SSC", ssc.category)
        assertEquals("Navodaya", jnv.category)
        assertEquals("Class 6", jnv.examClass)
        assertEquals("FULL_LENGTH", jnv.testType)
        assertFalse(rail.allowRetakes)
        assertFalse(ssc.allowRetakes)
        assertFalse(jnv.allowRetakes)
    }

    @Test
    fun allSeededTests_haveUniqueIdsAndMatchingQuestionsAndAnswerKeys() {
        val tests = SeedExamData.getAllTests()
        val uniqueIds = tests.map { it.id }.toSet()
        assertEquals("Test IDs must be unique", tests.size, uniqueIds.size)

        tests.forEach { test ->
            val (questions, answerKey) = SeedExamData.getQuestionsForTest(test.id)
            assertTrue("Missing questions for ${test.id}", questions.isNotEmpty())
            assertEquals("Question count mismatch for ${test.id}", test.totalQuestions, questions.size)
            questions.forEach { q ->
                assertTrue("Missing answer key for ${q.id}", answerKey.keys.containsKey(q.id))
                assertTrue("Missing English prompt for ${q.id}", q.questionEn.isNotBlank())
                assertTrue("Missing Hindi prompt for ${q.id}", q.questionHi.isNotBlank())
                assertEquals("Must have 4 options for ${q.id}", 4, q.optionsEn.size)
            }
        }
    }

    @Test
    fun examsAndClasses_areStrictlySeparated() {
        val tests = SeedExamData.getAllTests()

        // Navodaya has both Class 6 and Class 9 separated
        val nav6 = tests.filter { it.category == "Navodaya" && it.examClass == "Class 6" }
        val nav9 = tests.filter { it.category == "Navodaya" && it.examClass == "Class 9" }
        assertTrue(nav6.isNotEmpty() && nav9.isNotEmpty())
        assertTrue(nav6.map { it.id }.intersect(nav9.map { it.id }.toSet()).isEmpty())

        // Sainik School has both Class 6 and Class 9 separated
        val sai6 = tests.filter { it.category == "Sainik School" && it.examClass == "Class 6" }
        val sai9 = tests.filter { it.category == "Sainik School" && it.examClass == "Class 9" }
        assertTrue(sai6.isNotEmpty() && sai9.isNotEmpty())
        assertTrue(sai6.map { it.id }.intersect(sai9.map { it.id }.toSet()).isEmpty())

        // Simultala strictly has Class 6 only (no unsupported classes invented)
        val simClasses = tests.filter { it.category == "Simultala" }.map { it.examClass }.distinct()
        assertEquals(listOf("Class 6"), simClasses)

        // Olympiad has Class 6 and Class 9 backed by real tests
        val olyClasses = tests.filter { it.category == "Olympiad" }.map { it.examClass }.distinct().sorted()
        assertEquals(listOf("Class 6", "Class 9"), olyClasses)

        // Each exam covers Full Length, Chapter Wise, Sectional, Subject Wise, Advanced, and PYQ
        val requiredTypes = listOf("FULL_LENGTH", "CHAPTER_WISE", "SECTIONAL", "SUBJECT_WISE", "ADVANCED", "PYQ")
        listOf("Navodaya", "Sainik School", "Simultala", "Olympiad", "Railway", "SSC").forEach { cat ->
            val types = tests.filter { it.category == cat }.map { it.testType }.toSet()
            requiredTypes.forEach { req ->
                assertTrue("$cat missing $req", req in types)
            }
        }

        // Class-based exams cover all required categories per supported class as well
        listOf(
            "Navodaya" to "Class 6",
            "Navodaya" to "Class 9",
            "Sainik School" to "Class 6",
            "Sainik School" to "Class 9",
            "Simultala" to "Class 6",
            "Olympiad" to "Class 6",
            "Olympiad" to "Class 9"
        ).forEach { (cat, cls) ->
            val types = tests.filter { it.category == cat && it.examClass == cls }.map { it.testType }.toSet()
            requiredTypes.forEach { req ->
                assertTrue("$cat ($cls) missing $req", req in types)
            }
        }
    }

    @Test
    fun studyNotes_haveUniqueIdsFormulasAndValidLinkedTests() {
        val allTestsById = SeedExamData.getAllTests().associateBy { it.id }
        val notes = StudyNotesSeedData.getAllNotes()
        assertTrue("Study notes should not be empty", notes.isNotEmpty())

        val uniqueIds = notes.map { it.id }.toSet()
        assertEquals("Study note IDs must be unique", notes.size, uniqueIds.size)

        notes.forEach { note ->
            assertTrue("Missing English summary for ${note.id}", note.summaryEn.isNotBlank())
            assertTrue("Missing Hindi summary for ${note.id}", note.summaryHi.isNotBlank())
            assertTrue("Missing key formulas for ${note.id}", note.keyFormulas.isNotEmpty())
            assertTrue("Missing linkedTestIds for ${note.id}", note.linkedTestIds.isNotEmpty())
            note.linkedTestIds.forEach { tid ->
                val linkedTest = allTestsById[tid]
                assertTrue("Linked test $tid in ${note.id} does not exist in SeedExamData", linkedTest != null)
                assertEquals(
                    "Linked test $tid category mismatch for ${note.id}",
                    note.examCategory,
                    linkedTest!!.category
                )
                if (note.examClass.isNotBlank()) {
                    assertEquals(
                        "Linked test $tid class mismatch for ${note.id}",
                        note.examClass,
                        linkedTest.examClass
                    )
                }
            }
        }

        // Verify every exam + class context has study notes and subjects
        listOf(
            "Navodaya" to "Class 6",
            "Navodaya" to "Class 9",
            "Sainik School" to "Class 6",
            "Sainik School" to "Class 9",
            "Simultala" to "Class 6",
            "Olympiad" to "Class 6",
            "Olympiad" to "Class 9",
            "Railway" to null,
            "SSC" to null
        ).forEach { (cat, cls) ->
            val subjects = StudyNotesSeedData.getSubjectsForNotes(cat, cls)
            assertTrue("Missing study note subjects for $cat $cls", subjects.isNotEmpty())
            val contextNotes = StudyNotesSeedData.getNotesForContext(cat, cls)
            assertTrue("Missing study notes for $cat $cls", contextNotes.isNotEmpty())
        }
    }
}

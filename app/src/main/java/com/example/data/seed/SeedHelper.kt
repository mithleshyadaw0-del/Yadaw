package com.example.data.seed

import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamTest

data class SeedQ(
    val qEn: String,
    val qHi: String,
    val a: String,
    val b: String,
    val c: String,
    val d: String,
    val ans: String,
    val expEn: String,
    val expHi: String
)

fun buildQuestionPack(
    testId: String,
    marks: Double,
    items: List<SeedQ>
): Pair<List<ExamQuestion>, ExamAnswerKey> {
    val questions = items.mapIndexed { idx, item ->
        ExamQuestion(
            id = "${testId}_q${idx + 1}",
            testId = testId,
            questionIndex = idx,
            questionEn = item.qEn,
            questionHi = item.qHi,
            optionsEn = listOf("A) ${item.a}", "B) ${item.b}", "C) ${item.c}", "D) ${item.d}"),
            optionsHi = listOf("A) ${item.a}", "B) ${item.b}", "C) ${item.c}", "D) ${item.d}"),
            marks = marks
        )
    }
    val keys = mutableMapOf<String, String>()
    val expEn = mutableMapOf<String, String>()
    val expHi = mutableMapOf<String, String>()
    items.forEachIndexed { idx, item ->
        val qId = "${testId}_q${idx + 1}"
        keys[qId] = item.ans
        expEn[qId] = item.expEn
        expHi[qId] = item.expHi
    }
    val answerKey = ExamAnswerKey(
        id = "${testId}_key",
        testId = testId,
        keys = keys,
        explanationsEn = expEn,
        explanationsHi = expHi
    )
    return Pair(questions, answerKey)
}

fun makeTest(
    id: String,
    title: String,
    titleHindi: String,
    category: String,
    examClass: String,
    testType: String,
    subType: String = "Mock Test",
    subject: String = "Mathematics",
    chapter: String = "",
    examYear: String = "",
    isFree: Boolean = true,
    totalQuestions: Int,
    durationMinutes: Int,
    marksPerQuestion: Double = 1.0,
    negativeMarking: Double = 0.0,
    instructionsEn: String = "",
    instructionsHi: String = ""
): ExamTest {
    return ExamTest(
        id = id,
        title = title,
        titleHindi = titleHindi,
        subject = subject,
        category = category,
        examClass = examClass,
        testType = testType,
        subType = subType,
        chapter = chapter,
        examYear = examYear,
        isFree = isFree,
        totalQuestions = totalQuestions,
        durationMinutes = durationMinutes,
        marksPerQuestion = marksPerQuestion,
        negativeMarking = negativeMarking,
        instructionsEn = instructionsEn.ifBlank {
            "1. Total questions: $totalQuestions. Duration: $durationMinutes minutes.\n2. Marks per correct answer: +$marksPerQuestion, Negative marking: -$negativeMarking.\n3. You can attempt each test only ONCE. Review carefully before submitting."
        },
        instructionsHi = instructionsHi.ifBlank {
            "1. कुल प्रश्न: $totalQuestions, कुल समय: $durationMinutes मिनट।\n2. प्रत्येक सही उत्तर पर +$marksPerQuestion अंक व गलत उत्तर पर -$negativeMarking अंक।\n3. प्रत्येक छात्र एक टेस्ट केवल 1 बार ही दे सकता है।"
        },
        allowRetakes = false,
        showExplanations = true,
        isPublished = true,
        createdBy = "mithlesh_sir"
    )
}

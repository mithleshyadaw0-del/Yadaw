package com.example.data.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp

enum class UserRole {
    STUDENT,
    ADMIN
}

enum class CbtStatus {
    NOT_VISITED,                  // Grey
    NOT_ANSWERED,                 // Red (Visited but no answer)
    ANSWERED,                     // Green
    MARKED_FOR_REVIEW,            // Purple
    ANSWERED_AND_MARKED_FOR_REVIEW // Purple with green badge
}

enum class ExamTestType(val key: String, val labelEn: String, val labelHi: String) {
    FULL_LENGTH("FULL_LENGTH", "Full Length Test", "पूर्ण लंबाई मॉक टेस्ट"),
    CHAPTER_WISE("CHAPTER_WISE", "Chapter Wise Test", "अध्यायवार अभ्यास टेस्ट"),
    SECTIONAL("SECTIONAL", "Sectional Test", "विषयवार सेक्शनल टेस्ट"),
    SUBJECT_WISE("SUBJECT_WISE", "Subject Wise Test", "संपूर्ण विषयवार टेस्ट"),
    ADVANCED("ADVANCED", "Advanced Test", "एडवांस्ड रैंक बूस्टर टेस्ट"),
    PYQ("PYQ", "Previous Year Paper", "विगत वर्ष प्रश्न पत्र")
}

data class ExamSectionInfo(
    val id: String,               // "Navodaya", "Sainik School", "Simultala", "Olympiad", "Railway", "SSC", "All"
    val titleEn: String,
    val titleHi: String,
    val subtitle: String,
    val supportsClassSelection: Boolean,
    val defaultClasses: List<String> = emptyList()
)

data class StudyNoteChapter(
    val id: String,
    val examCategory: String,
    val examClass: String,
    val subject: String,
    val chapterTitleEn: String,
    val chapterTitleHi: String,
    val summaryEn: String,
    val summaryHi: String,
    val keyFormulas: List<String>,
    val solvedExampleEn: String,
    val solvedExampleHi: String,
    val linkedTestIds: List<String> = emptyList()
)

@Keep
@IgnoreExtraProperties
data class UserProfile(
    @DocumentId val id: String = "",
    val userId: String = "",
    val role: String = "student", // "student" or "admin"
    val name: String = "",
    val studentId: String = "",
    val studentClass: String = "Railway / SSC",
    val email: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class ExamTest(
    val id: String = "",
    val title: String = "",
    val titleHindi: String = "",
    val subject: String = "Mathematics",
    val category: String = "Railway", // Navodaya, Sainik School, Simultala, Olympiad, Railway, SSC, All
    val examClass: String = "",       // "Class 6", "Class 9", or "" for general competitive exams
    val testType: String = "FULL_LENGTH", // "FULL_LENGTH", "CHAPTER_WISE", "SECTIONAL", "SUBJECT_WISE", "ADVANCED", "PYQ"
    val subType: String = "Mock Test",    // "Mock Test", "Previous Year", "Model Paper", "Practice Test", "Marathon"
    val chapter: String = "",             // Chapter name when testType == "CHAPTER_WISE"
    val examYear: String = "",            // e.g. "2025", "2024", "2023" for PYQs
    @get:PropertyName("isFree")
    @PropertyName("isFree")
    val isFree: Boolean = true,
    val totalQuestions: Int = 20,
    val durationMinutes: Int = 30,
    val marksPerQuestion: Double = 1.0,
    val negativeMarking: Double = 0.25,
    val instructionsEn: String = "",
    val instructionsHi: String = "",
    val allowRetakes: Boolean = false,
    val showExplanations: Boolean = true,
    @get:PropertyName("isPublished")
    @PropertyName("isPublished")
    val isPublished: Boolean = true,
    val createdBy: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class ExamQuestion(
    val id: String = "",
    val testId: String = "",
    val questionIndex: Int = 0,
    val questionEn: String = "",
    val questionHi: String = "",
    val optionsEn: List<String> = emptyList(),
    val optionsHi: List<String> = emptyList(),
    val marks: Double = 1.0,
    val imageUrl: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class ExamAnswerKey(
    val id: String = "",
    val testId: String = "",
    val keys: Map<String, String> = emptyMap(), // questionId -> "A" | "B" | "C" | "D"
    val explanationsEn: Map<String, String> = emptyMap(),
    val explanationsHi: Map<String, String> = emptyMap(),
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class ExamAttempt(
    val id: String = "",
    val testId: String = "",
    val studentUid: String = "",
    val studentName: String = "",
    val studentId: String = "",
    val startedAt: Timestamp? = null,
    val submittedAt: Timestamp? = null,
    val status: String = "in_progress", // "in_progress" | "submitted"
    val answers: Map<String, String> = emptyMap(), // questionId -> option ("A", "B", "C", "D")
    val reviewFlags: List<String> = emptyList(),  // list of questionId
    val remainingSeconds: Int = 0,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class ExamResult(
    val id: String = "",
    val testId: String = "",
    val testTitle: String = "",
    val examCategory: String = "",
    val examClass: String = "",
    val testType: String = "",
    val subject: String = "",
    val studentUid: String = "",
    val studentName: String = "",
    val studentId: String = "",
    val studentClass: String = "",
    val totalQuestions: Int = 0,
    val correctAnswers: Int = 0,
    val incorrectAnswers: Int = 0,
    val unattempted: Int = 0,
    val totalMarks: Double = 0.0,
    val maxMarks: Double = 0.0,
    val percentage: Double = 0.0,
    val accuracy: Double = 0.0,
    val timeTakenSeconds: Int = 0,
    val rank: Int = 1,
    val userAnswers: Map<String, String> = emptyMap(),
    val correctKeys: Map<String, String> = emptyMap(),
    val explanationsEn: Map<String, String> = emptyMap(),
    val explanationsHi: Map<String, String> = emptyMap(),
    @ServerTimestamp val submittedAt: Timestamp? = null,
    @ServerTimestamp val createdAt: Timestamp? = null
)

@Keep
@IgnoreExtraProperties
data class LeaderboardEntry(
    @DocumentId val id: String = "",
    val testId: String = "",
    val studentUid: String = "",
    val studentName: String = "",
    val studentId: String = "",
    val studentClass: String = "",
    val score: Double = 0.0,
    val percentage: Double = 0.0,
    val accuracy: Double = 0.0,
    val timeTakenSeconds: Int = 0,
    val rank: Int = 1,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

data class VideoRecommendation(
    val title: String,
    val titleHindi: String,
    val topic: String,
    val duration: String,
    val description: String,
    val targetExam: String
)

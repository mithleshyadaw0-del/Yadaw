package com.example.data.repository

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Ignore
import org.junit.Test

@Ignore("Requires pre-cached Robolectric android-all jar")
class ExamRepositoryRuleTest : FirestoreEmulatorTestBase() {

  @Test
  fun authenticatedStudent_canSaveAndObserveProfile() = runBlocking {
    val uid = signInTestUser("student_rule_test@example.com")
    val repo = ExamRepository(firestore)

    val profile = UserProfile(
      userId = uid,
      role = "student",
      name = "Amit Sharma",
      studentId = "STU-2026-99",
      studentClass = "Railway NTPC",
      email = "student_rule_test@example.com"
    )

    repo.saveUserProfile(profile)

    val fetched = repo.observeUserProfile(uid).first()
    assertNotNull(fetched)
    assertEquals("Amit Sharma", fetched?.name)
    assertEquals("student", fetched?.role)
  }

  @Test
  fun unauthenticatedUser_cannotAccessProfile() = runBlocking<Unit> {
    val uid = signInTestUser("student_temp@example.com")
    val repo = ExamRepository(firestore)
    val profile = UserProfile(
      userId = uid,
      role = "student",
      name = "Temp Student",
      studentId = "STU-TEMP-01",
      email = "student_temp@example.com"
    )
    repo.saveUserProfile(profile)

    // Sign out to test unauthenticated rejection
    auth.signOut()

    val unauthRepo = ExamRepository(firestore)
    val fetched = unauthRepo.observeUserProfile(uid).first()
    // Unauthenticated user is rejected and emits null safely
    assertNull(fetched)
  }
}

const { initializeTestEnvironment, assertFails, assertSucceeds } = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

const PROJECT_ID = "demo-no-project";
let testEnv;

before(async () => {
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules: fs.readFileSync("firestore.rules", "utf8"),
      host: "127.0.0.1",
      port: 8085
    }
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Unauthenticated user cannot read any user profile", async () => {
  const unauthCtx = testEnv.unauthenticatedContext();
  const db = unauthCtx.firestore();
  await assertFails(db.collection("users").doc("student123").get());
});

test("Authenticated student can create their own profile with student role", async () => {
  const studentCtx = testEnv.authenticatedContext("student123", { email: "student@example.com" });
  const db = studentCtx.firestore();
  await assertSucceeds(
    db.collection("users").doc("student123").set({
      userId: "student123",
      role: "student",
      name: "Rohan Kumar",
      studentId: "STU-2026-001",
      studentClass: "Railway Group D",
      email: "student@example.com",
      createdAt: new Date(),
      updatedAt: new Date()
    })
  );
});

test("Student cannot elevate their role to admin", async () => {
  const studentCtx = testEnv.authenticatedContext("student123", { email: "student@example.com" });
  const db = studentCtx.firestore();
  await assertFails(
    db.collection("users").doc("student123").set({
      userId: "student123",
      role: "admin",
      name: "Rohan Kumar",
      studentId: "STU-2026-001",
      createdAt: new Date()
    })
  );
});

test("Teacher Admin can create an exam test", async () => {
  const adminCtx = testEnv.authenticatedContext("admin123", {
    email: "mithleshyadaw0@gmail.com",
    email_verified: true
  });
  const db = adminCtx.firestore();
  await assertSucceeds(
    db.collection("tests").doc("test_math_01").set({
      id: "test_math_01",
      title: "Mathematics Full Mock Test - 01",
      titleHindi: "गणित पूर्ण मॉक टेस्ट - 01",
      subject: "Mathematics",
      category: "Railway / SSC",
      totalQuestions: 25,
      durationMinutes: 30,
      marksPerQuestion: 1,
      negativeMarking: 0.25,
      isPublished: true,
      createdAt: new Date()
    })
  );
});

test("Student cannot create or delete an exam test", async () => {
  const studentCtx = testEnv.authenticatedContext("student123", { email: "student@example.com" });
  const db = studentCtx.firestore();
  await assertFails(
    db.collection("tests").doc("test_math_02").set({
      id: "test_math_02",
      title: "Hacked Test",
      subject: "Math",
      totalQuestions: 10,
      durationMinutes: 10,
      marksPerQuestion: 1,
      negativeMarking: 0,
      isPublished: true,
      createdAt: new Date(Date.now() - 1000)
    })
  );
  await assertFails(db.collection("tests").doc("test_math_01").delete());
});

test("Teacher Admin can update and delete an exam test", async () => {
  const adminCtx = testEnv.authenticatedContext("admin123", {
    email: "mithleshyadaw0@gmail.com",
    email_verified: true
  });
  const db = adminCtx.firestore();
  const createdAt = new Date(Date.now() - 5000);
  await assertSucceeds(
    db.collection("tests").doc("test_math_01").set({
      id: "test_math_01",
      title: "Mathematics Full Mock Test - 01",
      subject: "Mathematics",
      totalQuestions: 25,
      durationMinutes: 30,
      marksPerQuestion: 1,
      negativeMarking: 0.25,
      isPublished: true,
      createdAt
    })
  );
  await assertSucceeds(
    db.collection("tests").doc("test_math_01").update({
      isPublished: false,
      updatedAt: new Date(Date.now() - 1000)
    })
  );
  await assertSucceeds(db.collection("tests").doc("test_math_01").delete());
});

test("Unauthenticated user cannot list tests or read/list attempts", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("tests").limit(1).get());
  await assertFails(
    unauthDb.collection("attempts")
      .where("studentUid", "==", "student123")
      .where("testId", "==", "test_railway_math_01")
      .where("status", "==", "in_progress")
      .limit(1)
      .get()
  );
});

test("Student can create, query own active attempt, and submit result; cross-user and unfiltered queries fail", async () => {
  const studentDb = testEnv.authenticatedContext("student123", { email: "student@example.com" }).firestore();
  const otherDb = testEnv.authenticatedContext("other456", { email: "other@example.com" }).firestore();
  const ts = new Date(Date.now() - 2000);

  await assertSucceeds(
    studentDb.collection("attempts").doc("att_1").set({
      id: "att_1",
      testId: "test_railway_math_01",
      studentUid: "student123",
      studentName: "Rohan",
      studentId: "MS-101",
      status: "in_progress",
      answers: { q1: "A" },
      reviewFlags: [],
      remainingSeconds: 1500,
      createdAt: ts,
      updatedAt: ts
    })
  );

  // Matches ExamRepository.getActiveAttempt
  await assertSucceeds(
    studentDb.collection("attempts")
      .where("studentUid", "==", "student123")
      .where("testId", "==", "test_railway_math_01")
      .where("status", "==", "in_progress")
      .limit(1)
      .get()
  );

  // Unfiltered or cross-user attempt queries fail
  await assertFails(studentDb.collection("attempts").get());
  await assertFails(
    otherDb.collection("attempts")
      .where("studentUid", "==", "student123")
      .where("testId", "==", "test_railway_math_01")
      .where("status", "==", "in_progress")
      .limit(1)
      .get()
  );

  // Submit result & leaderboard entry
  await assertSucceeds(
    studentDb.collection("results").doc("res_1").set({
      id: "res_1",
      testId: "test_railway_math_01",
      testTitle: "Railway CBT Math",
      studentUid: "student123",
      studentName: "Rohan",
      totalQuestions: 20,
      totalMarks: 18,
      createdAt: ts
    })
  );
  await assertSucceeds(
    studentDb.collection("results").where("studentUid", "==", "student123").get()
  );
  await assertFails(studentDb.collection("results").get());

  await assertSucceeds(
    studentDb.collection("leaderboards").doc("test_railway_math_01")
      .collection("entries").doc("student123").set({
        testId: "test_railway_math_01",
        studentUid: "student123",
        studentName: "Rohan",
        score: 18,
        timeTakenSeconds: 420,
        updatedAt: ts
      })
  );
});

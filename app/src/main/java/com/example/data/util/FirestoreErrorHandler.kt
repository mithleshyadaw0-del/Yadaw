package com.example.data.util

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException

enum class OperationType {
    GET,
    LIST,
    CREATE,
    UPDATE,
    DELETE
}

fun handleFirestoreError(
    exception: Exception,
    operationType: OperationType,
    collectionPath: String
) {
    val firestoreException = exception as? FirebaseFirestoreException
    val errorCode = firestoreException?.code?.name ?: "UNKNOWN"
    val errorMessage = exception.localizedMessage ?: "Unknown error"

    val logPayload = """
        {
            "event": "firestore_error",
            "operation": "${operationType.name}",
            "path": "$collectionPath",
            "code": "$errorCode",
            "message": "$errorMessage"
        }
    """.trimIndent()

    Log.e("FirestoreError", logPayload, exception)
}

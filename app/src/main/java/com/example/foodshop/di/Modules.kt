package com.example.foodshop.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.core.module.Module
import org.koin.dsl.module
val FirebaseFirestoreDatabase = module {
    single { Firebase.firestore }
}

val FirebaseFirestoreCommon = module {
    single { Firebase.firestore }
}
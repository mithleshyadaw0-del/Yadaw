package com.example.data.seed

import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamTest

object ExtendedCompetitiveSeedData {

    fun getTests(): List<ExamTest> = listOf(
        // ==================== RAILWAY CBT ====================
        makeTest(
            id = "rail_sw_math",
            title = "Railway CBT - Complete Mathematics Subject Test",
            titleHindi = "रेलवे CBT - सम्पूर्ण गणित विषय टेस्ट",
            category = "Railway", examClass = "", testType = "SUBJECT_WISE",
            subject = "Mathematics", totalQuestions = 5, durationMinutes = 12,
            marksPerQuestion = 1.0, negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_sw_sci",
            title = "Railway CBT - Complete Science Subject Test",
            titleHindi = "रेलवे CBT - सम्पूर्ण सामान्य विज्ञान टेस्ट",
            category = "Railway", examClass = "", testType = "SUBJECT_WISE",
            subject = "Science", totalQuestions = 5, durationMinutes = 10,
            marksPerQuestion = 1.0, negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_adv_cbt2",
            title = "Railway CBT-2 High Level Rank Booster (Advanced)",
            titleHindi = "रेलवे CBT-2 उच्च स्तरीय एडवांस्ड टेस्ट",
            category = "Railway", examClass = "", testType = "ADVANCED",
            subType = "Advanced", subject = "All Subjects", totalQuestions = 5, durationMinutes = 15,
            marksPerQuestion = 1.0, negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_pyq_2025",
            title = "RRB ALP & Technician PYQ Paper (2025)",
            titleHindi = "रेलवे ALP व तकनीशियन विगत वर्ष पेपर (2025)",
            category = "Railway", examClass = "", testType = "PYQ",
            subType = "Previous Year", subject = "All Subjects", examYear = "2025",
            totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 1.0, negativeMarking = 0.33
        ),
        makeTest(
            id = "rail_pyq_2024_mar",
            title = "Railway NTPC Maths PYQ Marathon Live (2024)",
            titleHindi = "रेलवे NTPC गणित PYQ मैराथन लाइव (2024)",
            category = "Railway", examClass = "", testType = "PYQ",
            subType = "Marathon", subject = "Mathematics", examYear = "2024",
            totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 1.0, negativeMarking = 0.33
        ),

        // ==================== SSC MATHS BOOSTER ====================
        makeTest(
            id = "ssc_sw_math",
            title = "SSC CGL/CHSL - Complete Quantitative Subject Test",
            titleHindi = "एसएससी CGL/CHSL - सम्पूर्ण गणित विषय टेस्ट",
            category = "SSC", examClass = "", testType = "SUBJECT_WISE",
            subject = "Mathematics", totalQuestions = 5, durationMinutes = 12,
            marksPerQuestion = 2.0, negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_sw_eng",
            title = "SSC CGL/CHSL - Complete English Subject Test",
            titleHindi = "एसएससी CGL/CHSL - सम्पूर्ण अंग्रेजी विषय टेस्ट",
            category = "SSC", examClass = "", testType = "SUBJECT_WISE",
            subject = "English", totalQuestions = 5, durationMinutes = 10,
            marksPerQuestion = 2.0, negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_adv_tier2",
            title = "SSC CGL Tier-2 Mains Level Booster (Advanced)",
            titleHindi = "एसएससी CGL टियर-2 एडवांस्ड मेन्स टेस्ट",
            category = "SSC", examClass = "", testType = "ADVANCED",
            subType = "Advanced", subject = "Mathematics", totalQuestions = 5, durationMinutes = 15,
            marksPerQuestion = 2.0, negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_pyq_2025",
            title = "SSC CGL Tier-1 Official PYQ Paper (2025)",
            titleHindi = "एसएससी CGL टियर-1 विगत वर्ष पेपर (2025)",
            category = "SSC", examClass = "", testType = "PYQ",
            subType = "Previous Year", subject = "All Subjects", examYear = "2025",
            totalQuestions = 5, durationMinutes = 15, marksPerQuestion = 2.0, negativeMarking = 0.50
        ),
        makeTest(
            id = "ssc_pyq_2024_mar",
            title = "SSC Maths Booster PYQ Marathon Live (2024)",
            titleHindi = "एसएससी गणित PYQ मैराथन लाइव टेस्ट (2024)",
            category = "SSC", examClass = "", testType = "PYQ",
            subType = "Marathon", subject = "Mathematics", examYear = "2024",
            totalQuestions = 5, durationMinutes = 12, marksPerQuestion = 2.0, negativeMarking = 0.50
        )
    )

    fun getQuestions(testId: String): Pair<List<ExamQuestion>, ExamAnswerKey>? {
        val items: List<SeedQ> = when (testId) {
            "rail_sw_math", "rail_pyq_2024_mar", "rail_adv_cbt2", "rail_pyq_2025" -> listOf(
                SeedQ("Compound interest on ₹8,000 at 5% for 2 years is:", "₹8,000 पर 5% वार्षिक दर से 2 वर्ष का चक्रवृद्धि ब्याज:", "₹800", "₹820", "₹840", "₹850", "B", "8000×(1.05)² - 8000 = ₹820.", "₹820।"),
                SeedQ("If sin A = 3/5, find cos A (acute angle):", "यदि sin A = 3/5 हो, तो cos A ज्ञात करें:", "3/4", "4/5", "5/4", "5/3", "B", "√(1 - 9/25) = 4/5.", "4/5।"),
                SeedQ("Ratio of speed of two trains is 5 : 7. If 2nd runs 350 km in 5 hr, speed of 1st:", "दो ट्रेनों की चाल का अनुपात 5:7 है। दूसरी 5 घंटे में 350 किमी चले, तो पहली की चाल:", "40 km/h", "50 km/h", "60 km/h", "70 km/h", "B", "2nd speed = 70 => 1st = 50 km/h.", "50 किमी/घंटा।"),
                SeedQ("Unit of Work and Energy in CGS system is:", "CGS पद्धति में कार्य व ऊर्जा का मात्रक क्या है?", "Joule", "Erg", "Dyne", "Newton", "B", "1 Joule = 10⁷ Erg.", "अर्ग (Erg)।"),
                SeedQ("Who invented the Steam Engine?", "भाप के इंजन का आविष्कार किसने किया था?", "James Watt", "Thomas Edison", "Isaac Newton", "Michael Faraday", "A", "James Watt.", "जेम्स वाट।")
            )
            "rail_sw_sci" -> listOf(
                SeedQ("Chemical name of Vitamin C is:", "विटामिन C का रासायनिक नाम क्या है?", "Retinol", "Ascorbic acid", "Calciferol", "Thiamine", "B", "Ascorbic acid.", "एस्कॉर्बिक अम्ल।"),
                SeedQ("Filament of electric bulb is made of:", "विद्युत बल्ब का तंतु (Filament) किस धातु का बना होता है?", "Copper", "Tungsten", "Nichrome", "Aluminium", "B", "Tungsten (high melting point).", "टंगस्टन।"),
                SeedQ("Normal blood pressure of a healthy human is:", "स्वस्थ मनुष्य का सामान्य रक्तचाप (BP) होता है:", "100/60 mmHg", "120/80 mmHg", "140/90 mmHg", "80/120 mmHg", "B", "120/80 mmHg.", "120/80 mmHg।"),
                SeedQ("Which mirror is used as rear-view mirror in vehicles?", "वाहनों में पीछे देखने (Rear-view) के लिए किस दर्पण का प्रयोग होता है?", "Concave", "Convex", "Plane", "Parabolic", "B", "Convex mirror gives wider field of view.", "उत्तल दर्पण (Convex mirror)।"),
                SeedQ("Formula of Heavy Water is:", "भारी जल (Heavy Water) का रासायनिक सूत्र है:", "H₂O", "D₂O", "H₂O₂", "CO₂", "B", "Deuterium oxide (D₂O).", "D₂O।")
            )
            "ssc_sw_math", "ssc_adv_tier2", "ssc_pyq_2024_mar", "ssc_pyq_2025" -> listOf(
                SeedQ("If x + 1/x = 3, find x⁴ + 1/x⁴:", "यदि x + 1/x = 3 हो, तो x⁴ + 1/x⁴ =", "47", "49", "79", "81", "A", "x²+1/x² = 7 => 7² - 2 = 47.", "47।"),
                SeedQ("In triangle ABC, O is circumcentre and ∠BAC = 55°. Find ∠BOC:", "त्रिभुज ABC का परिकेंद्र O है तथा ∠BAC = 55° है। ∠BOC ज्ञात करें:", "55°", "110°", "125°", "130°", "B", "Angle at centre = 2 × 55° = 110°.", "110°।"),
                SeedQ("If cos θ + sin θ = √2 cos θ, then cos θ - sin θ =", "यदि cos θ + sin θ = √2 cos θ हो, तो cos θ - sin θ =", "√2 sin θ", "√2 cos θ", "2 sin θ", "1", "A", "Square & simplify => √2 sin θ.", "√2 sin θ।"),
                SeedQ("A sells at 20% profit to B, B sells at 10% loss to C for ₹540. A's CP:", "A ने B को 20% लाभ पर व B ने C को ₹540 में 10% हानि पर बेचा। A का क्रय मूल्य:", "₹450", "₹500", "₹520", "₹600", "B", "CP × 1.2 × 0.9 = 540 => CP = ₹500.", "₹500।"),
                SeedQ("Curved surface area of cone with r=7 cm, slant height l=10 cm:", "7 सेमी त्रिज्या और 10 सेमी तिर्यक ऊंचाई वाले शंकु का वक्र पृष्ठीय क्षेत्रफल:", "210 cm²", "220 cm²", "240 cm²", "440 cm²", "B", "πrl = (22/7)×7×10 = 220 cm².", "220 सेमी²।")
            )
            "ssc_sw_eng" -> listOf(
                SeedQ("Antonym of 'Abundant' is:", "'Abundant' (प्रचुर) का विलोम शब्द है:", "Plentiful", "Scarce", "Rich", "Ample", "B", "Scarce means insufficient/rare.", "Scarce।"),
                SeedQ("Idiom 'At the eleventh hour' means:", "'At the eleventh hour' मुहावरे का अर्थ:", "At 11 PM", "At the last moment", "Very early", "On time", "B", "At the last possible moment.", "अंतिम क्षण में।"),
                SeedQ("Spot error / correct form: Each of the boys ___ given a prize.", "रिक्त स्थान भरें: Each of the boys ___ given a prize.", "were", "was", "are", "have", "B", "'Each of' takes singular verb 'was'.", "was सही है।"),
                SeedQ("One word for 'One who knows many languages':", "'बहुभाषी' के लिए अंग्रेजी एक शब्द:", "Polyglot", "Omniscient", "Linguist", "Bilingual", "A", "Polyglot.", "Polyglot।"),
                SeedQ("Synonym of 'Candid' is:", "'Candid' (स्पष्टवादी) का समानार्थी:", "Secretive", "Frank", "Dishonest", "Rude", "B", "Frank and honest.", "Frank।")
            )
            else -> return null
        }
        val marks = if (testId.startsWith("rail_")) 1.0 else 2.0
        return buildQuestionPack(testId, marks, items)
    }
}

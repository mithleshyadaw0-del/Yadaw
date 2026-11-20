package com.example.data.seed

import com.example.data.model.ExamAnswerKey
import com.example.data.model.ExamQuestion
import com.example.data.model.ExamTest
import com.example.data.model.VideoRecommendation

object SeedExamData {

    fun getStandardTests(): List<ExamTest> = listOf(
        ExamTest(
            id = "test_railway_math_01",
            title = "Railway NTPC & Group D - Mathematics Mock Test 01",
            titleHindi = "रेलवे NTPC एवं ग्रुप D - गणित विशेष मॉक टेस्ट 01",
            subject = "All Subjects",
            category = "Railway",
            examClass = "",
            testType = "FULL_LENGTH",
            subType = "Mock Test",
            totalQuestions = 15,
            durationMinutes = 20,
            marksPerQuestion = 1.0,
            negativeMarking = 0.33,
            instructionsEn = "1. The test contains 15 Multiple Choice Questions.\n2. Total duration is 20 minutes.\n3. Each correct response awards +1.0 mark.\n4. Negative marking of 0.33 mark for each incorrect response.\n5. You can toggle language (English/Hindi) anytime without losing answers.\n6. You can attempt each test only ONCE. Submit only after reviewing all questions.",
            instructionsHi = "1. इस परीक्षा में 15 बहुविकल्पीय प्रश्न (MCQ) हैं।\n2. परीक्षा की कुल अवधि 20 मिनट है।\n3. प्रत्येक सही उत्तर के लिए +1.0 अंक दिया जाएगा।\n4. प्रत्येक गलत उत्तर के लिए 0.33 अंक काटा जाएगा।\n5. आप परीक्षा के दौरान किसी भी समय अंग्रेजी/हिन्दी भाषा बदल सकते हैं।\n6. प्रत्येक छात्र एक टेस्ट केवल 1 बार ही दे सकता है।",
            allowRetakes = false,
            showExplanations = true,
            isPublished = true,
            createdBy = "mithlesh_sir"
        ),
        ExamTest(
            id = "test_ssc_cgl_chsl_02",
            title = "SSC CGL / CHSL / MTS - Quantitative Aptitude Booster 01",
            titleHindi = "एसएससी CGL / CHSL - पूर्ण मॉक टेस्ट 01",
            subject = "All Subjects",
            category = "SSC",
            examClass = "",
            testType = "FULL_LENGTH",
            subType = "Mock Test",
            totalQuestions = 12,
            durationMinutes = 15,
            marksPerQuestion = 2.0,
            negativeMarking = 0.50,
            instructionsEn = "1. High difficulty questions according to latest SSC pattern.\n2. Total questions: 12. Time: 15 minutes.\n3. Positive mark: +2.0, Negative: -0.50.\n4. Questions are provided with detailed step-by-step solutions.",
            instructionsHi = "1. नवीनतम एसएससी परीक्षा पैटर्न पर आधारित प्रश्न।\n2. कुल प्रश्न: 12, समय: 15 मिनट।\n3. सही उत्तर: +2.0 अंक, गलत उत्तर: -0.50 अंक।\n4. परीक्षा समाप्ति पर प्रत्येक प्रश्न का विस्तृत हल उपलब्ध होगा।",
            allowRetakes = false,
            showExplanations = true,
            isPublished = true,
            createdBy = "mithlesh_sir"
        ),
        ExamTest(
            id = "test_jnv_sainik_03",
            title = "JNVST Class 6 - Entrance Special Mock Test 01",
            titleHindi = "जवाहर नवोदय कक्षा 6 - प्रवेश परीक्षा विशेष मॉक टेस्ट 01",
            subject = "All Subjects",
            category = "Navodaya",
            examClass = "Class 6",
            testType = "FULL_LENGTH",
            subType = "Mock Test",
            totalQuestions = 10,
            durationMinutes = 15,
            marksPerQuestion = 1.25,
            negativeMarking = 0.0,
            instructionsEn = "1. Specially designed for Class 6 Navodaya Vidyalaya (JNVST) aspirants.\n2. Total questions: 10. Total marks: 12.5.\n3. No negative marking for this foundational test.\n4. Read questions carefully in English or Hindi.",
            instructionsHi = "1. कक्षा 6 जवाहर नवोदय विद्यालय प्रवेश परीक्षा के लिए विशेष मॉक टेस्ट।\n2. कुल प्रश्न: 10, पूर्णांक: 12.5 अंक।\n3. इस टेस्ट में कोई ऋणात्मक अंकन (Negative Marking) नहीं है।\n4. प्रत्येक छात्र एक टेस्ट केवल 1 बार दे सकता है।",
            allowRetakes = false,
            showExplanations = true,
            isPublished = true,
            createdBy = "mithlesh_sir"
        )
    )

    fun getAllTests(): List<ExamTest> = buildList {
        addAll(getStandardTests())
        addAll(NavodayaSeedData.getTests())
        addAll(SainikSeedData.getTests())
        addAll(SimultalaOlympiadSeedData.getTests())
        addAll(RailwaySscSeedData.getTests())
        addAll(ExtendedSchoolSeedData.getTests())
        addAll(ExtendedCompetitiveSeedData.getTests())
    }

    fun getExamSections(): List<com.example.data.model.ExamSectionInfo> = listOf(
        com.example.data.model.ExamSectionInfo(
            id = "Navodaya",
            titleEn = "Navodaya Entrance Exam",
            titleHi = "नवोदय प्रवेश परीक्षा (JNVST)",
            subtitle = "Class 6 & Class 9 • Complete Mock, Chapter & Sectional",
            supportsClassSelection = true,
            defaultClasses = listOf("Class 6", "Class 9")
        ),
        com.example.data.model.ExamSectionInfo(
            id = "Sainik School",
            titleEn = "Sainik School Entrance Exam",
            titleHi = "सैनिक स्कूल प्रवेश परीक्षा (AISSEE)",
            subtitle = "Class 6 & Class 9 • Maths, Intelligence, GK & English",
            supportsClassSelection = true,
            defaultClasses = listOf("Class 6", "Class 9")
        ),
        com.example.data.model.ExamSectionInfo(
            id = "Simultala",
            titleEn = "Simultala Entrance Exam",
            titleHi = "सिमुलतला आवासीय विद्यालय (SAV)",
            subtitle = "Class 6 Bihar Entrance • Prelims & Mains Practice",
            supportsClassSelection = true
        ),
        com.example.data.model.ExamSectionInfo(
            id = "Olympiad",
            titleEn = "Olympiad",
            titleHi = "गणित व विज्ञान ओलंपियाड (IMO / NSO)",
            subtitle = "Logical Reasoning, Maths & Science Achievers",
            supportsClassSelection = true
        ),
        com.example.data.model.ExamSectionInfo(
            id = "Railway",
            titleEn = "Railway CBT",
            titleHi = "रेलवे भर्ती बोर्ड (Railway CBT)",
            subtitle = "RRB NTPC, Group D, ALP & Technician",
            supportsClassSelection = false
        ),
        com.example.data.model.ExamSectionInfo(
            id = "SSC",
            titleEn = "SSC Maths Booster",
            titleHi = "एसएससी गणित (SSC Maths Booster)",
            subtitle = "SSC CGL, CHSL, MTS, GD Constable",
            supportsClassSelection = false
        ),
        com.example.data.model.ExamSectionInfo(
            id = "All",
            titleEn = "All Mixed Tests",
            titleHi = "सभी संयुक्त मॉक टेस्ट (All Mixed Tests)",
            subtitle = "सम्पूर्ण पाठ्यक्रम अभ्यास टेस्ट सीरीज",
            supportsClassSelection = false
        )
    )

    fun getQuestionsForRailway01(): Pair<List<ExamQuestion>, ExamAnswerKey> {
        val testId = "test_railway_math_01"
        val questions = listOf(
            ExamQuestion(
                id = "${testId}_q1",
                testId = testId,
                questionIndex = 0,
                questionEn = "If 25% of a number is 75, what is 40% of the same number?",
                questionHi = "यदि किसी संख्या का 25% भाग 75 है, तो उसी संख्या का 40% कितना होगा?",
                optionsEn = listOf("A) 100", "B) 120", "C) 150", "D) 160"),
                optionsHi = listOf("A) 100", "B) 120", "C) 150", "D) 160"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q2",
                testId = testId,
                questionIndex = 1,
                questionEn = "A shopkeeper sells an article for ₹840 at a profit of 20%. What was the cost price of the article?",
                questionHi = "एक दुकानदार किसी वस्तु को 20% लाभ पर ₹840 में बेचता है। वस्तु का क्रय मूल्य (Cost Price) क्या था?",
                optionsEn = listOf("A) ₹650", "B) ₹700", "C) ₹720", "D) ₹750"),
                optionsHi = listOf("A) ₹650", "B) ₹700", "C) ₹720", "D) ₹750"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q3",
                testId = testId,
                questionIndex = 2,
                questionEn = "A train 180 meters long is running at a speed of 54 km/h. How much time will it take to cross an electric pole?",
                questionHi = "180 मीटर लंबी एक रेलगाड़ी 54 किमी/घंटा की गति से चल रही है। एक बिजली के खंभे को पार करने में इसे कितना समय लगेगा?",
                optionsEn = listOf("A) 10 seconds", "B) 12 seconds", "C) 15 seconds", "D) 18 seconds"),
                optionsHi = listOf("A) 10 सेकंड", "B) 12 सेकंड", "C) 15 सेकंड", "D) 18 सेकंड"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q4",
                testId = testId,
                questionIndex = 3,
                questionEn = "If A : B = 3 : 4 and B : C = 8 : 9, find the ratio A : C.",
                questionHi = "यदि A : B = 3 : 4 तथा B : C = 8 : 9 हो, तो अनुपात A : C ज्ञात कीजिए।",
                optionsEn = listOf("A) 1 : 2", "B) 2 : 3", "C) 3 : 2", "D) 4 : 5"),
                optionsHi = listOf("A) 1 : 2", "B) 2 : 3", "C) 3 : 2", "D) 4 : 5"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q5",
                testId = testId,
                questionIndex = 4,
                questionEn = "What is the simple interest on ₹5,000 at 8% per annum for 3 years?",
                questionHi = "₹5,000 की राशि पर 8% वार्षिक दर से 3 वर्ष का साधारण ब्याज कितना होगा?",
                optionsEn = listOf("A) ₹1,000", "B) ₹1,200", "C) ₹1,400", "D) ₹1,500"),
                optionsHi = listOf("A) ₹1,000", "B) ₹1,200", "C) ₹1,400", "D) ₹1,500"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q6",
                testId = testId,
                questionIndex = 5,
                questionEn = "A can do a piece of work in 10 days and B can do it in 15 days. In how many days can both complete it working together?",
                questionHi = "A किसी कार्य को 10 दिन में तथा B उसे 15 दिन में पूरा कर सकता है। दोनों मिलकर उस कार्य को कितने दिनों में पूरा करेंगे?",
                optionsEn = listOf("A) 5 days", "B) 6 days", "C) 7.5 days", "D) 8 days"),
                optionsHi = listOf("A) 5 दिन", "B) 6 दिन", "C) 7.5 दिन", "D) 8 दिन"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q7",
                testId = testId,
                questionIndex = 6,
                questionEn = "Find the HCF of 36, 54, and 90.",
                questionHi = "36, 54 और 90 का महत्तम समापवर्तक (HCF / म.स.) ज्ञात कीजिए।",
                optionsEn = listOf("A) 6", "B) 9", "C) 18", "D) 27"),
                optionsHi = listOf("A) 6", "B) 9", "C) 18", "D) 27"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q8",
                testId = testId,
                questionIndex = 7,
                questionEn = "Simplify: 18 + [24 ÷ 6 × (4 - 2)]",
                questionHi = "सरल कीजिए: 18 + [24 ÷ 6 × (4 - 2)]",
                optionsEn = listOf("A) 24", "B) 26", "C) 28", "D) 32"),
                optionsHi = listOf("A) 24", "B) 26", "C) 28", "D) 32"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q9",
                testId = testId,
                questionIndex = 8,
                questionEn = "The average of 5 consecutive odd numbers is 27. What is the largest of these numbers?",
                questionHi = "5 क्रमागत विषम संख्याओं का औसत 27 है। इनमें से सबसे बड़ी संख्या कौन सी है?",
                optionsEn = listOf("A) 29", "B) 31", "C) 33", "D) 35"),
                optionsHi = listOf("A) 29", "B) 31", "C) 33", "D) 35"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q10",
                testId = testId,
                questionIndex = 9,
                questionEn = "Find the area of a circle whose circumference is 44 cm. (Take π = 22/7)",
                questionHi = "एक वृत्त का क्षेत्रफल ज्ञात कीजिए जिसकी परिधि 44 सेमी है। (π = 22/7 लें)",
                optionsEn = listOf("A) 144 cm²", "B) 154 cm²", "C) 176 cm²", "D) 196 cm²"),
                optionsHi = listOf("A) 144 सेमी²", "B) 154 सेमी²", "C) 176 सेमी²", "D) 196 सेमी²"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q11",
                testId = testId,
                questionIndex = 10,
                questionEn = "If x + 1/x = 4, then find the value of x² + 1/x².",
                questionHi = "यदि x + 1/x = 4 हो, तो x² + 1/x² का मान ज्ञात कीजिए।",
                optionsEn = listOf("A) 12", "B) 14", "C) 16", "D) 18"),
                optionsHi = listOf("A) 12", "B) 14", "C) 16", "D) 18"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q12",
                testId = testId,
                questionIndex = 11,
                questionEn = "If sin θ = 3/5, what is the value of tan θ?",
                questionHi = "यदि sin θ = 3/5 हो, तो tan θ का मान क्या होगा?",
                optionsEn = listOf("A) 3/4", "B) 4/3", "C) 4/5", "D) 5/3"),
                optionsHi = listOf("A) 3/4", "B) 4/3", "C) 4/5", "D) 5/3"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q13",
                testId = testId,
                questionIndex = 12,
                questionEn = "A person buys a bike for ₹60,000 and sells it at a loss of 15%. What is the selling price?",
                questionHi = "एक व्यक्ति ₹60,000 में एक बाइक खरीदता है और 15% की हानि पर बेच देता है। विक्रय मूल्य क्या है?",
                optionsEn = listOf("A) ₹50,000", "B) ₹51,000", "C) ₹52,000", "D) ₹54,000"),
                optionsHi = listOf("A) ₹50,000", "B) ₹51,000", "C) ₹52,000", "D) ₹54,000"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q14",
                testId = testId,
                questionIndex = 13,
                questionEn = "What is the compound interest on ₹10,000 for 2 years at 10% per annum compounded annually?",
                questionHi = "₹10,000 पर 10% वार्षिक ब्याज की दर से 2 वर्ष का चक्रवृद्धि ब्याज (Compound Interest) कितना होगा?",
                optionsEn = listOf("A) ₹2,000", "B) ₹2,100", "C) ₹2,200", "D) ₹2,300"),
                optionsHi = listOf("A) ₹2,000", "B) ₹2,100", "C) ₹2,200", "D) ₹2,300"),
                marks = 1.0
            ),
            ExamQuestion(
                id = "${testId}_q15",
                testId = testId,
                questionIndex = 14,
                questionEn = "The diagonal of a square is 10√2 cm. What is its perimeter?",
                questionHi = "एक वर्ग का विकर्ण 10√2 सेमी है। इसका परिमाप (Perimeter) क्या होगा?",
                optionsEn = listOf("A) 30 cm", "B) 40 cm", "C) 50 cm", "D) 60 cm"),
                optionsHi = listOf("A) 30 सेमी", "B) 40 सेमी", "C) 50 सेमी", "D) 60 सेमी"),
                marks = 1.0
            )
        )

        val keys = mapOf(
            "${testId}_q1" to "B",
            "${testId}_q2" to "B",
            "${testId}_q3" to "B",
            "${testId}_q4" to "B",
            "${testId}_q5" to "B",
            "${testId}_q6" to "B",
            "${testId}_q7" to "C",
            "${testId}_q8" to "B",
            "${testId}_q9" to "B",
            "${testId}_q10" to "B",
            "${testId}_q11" to "B",
            "${testId}_q12" to "A",
            "${testId}_q13" to "B",
            "${testId}_q14" to "B",
            "${testId}_q15" to "B"
        )

        val expEn = mapOf(
            "${testId}_q1" to "Let number be X. 0.25 * X = 75 => X = 300. Now 40% of 300 = 0.40 * 300 = 120. (Option B)",
            "${testId}_q2" to "SP = ₹840, Profit = 20%. CP = SP / (1 + Profit%) = 840 / 1.20 = ₹700. (Option B)",
            "${testId}_q3" to "Speed in m/s = 54 * (5/18) = 15 m/s. Time = Distance / Speed = 180 / 15 = 12 seconds. (Option B)",
            "${testId}_q4" to "A/C = (A/B) * (B/C) = (3/4) * (8/9) = 24/36 = 2/3 => 2 : 3. (Option B)",
            "${testId}_q5" to "SI = (P * R * T) / 100 = (5000 * 8 * 3) / 100 = ₹1,200. (Option B)",
            "${testId}_q6" to "Work per day = (1/10) + (1/15) = (3+2)/30 = 5/30 = 1/6. Hence together 6 days. (Option B)",
            "${testId}_q7" to "Factors: 36 = 18*2, 54 = 18*3, 90 = 18*5. Highest Common Factor = 18. (Option C)",
            "${testId}_q8" to "BODMAS: Inside brackets (4 - 2) = 2. Then division 24 ÷ 6 = 4. Then 4 * 2 = 8. Finally 18 + 8 = 26. (Option B)",
            "${testId}_q9" to "Consecutive odd numbers centered at 27: 23, 25, 27, 29, 31. Largest is 31. (Option B)",
            "${testId}_q10" to "Circumference = 2πr = 44 => 2*(22/7)*r = 44 => r = 7 cm. Area = πr² = (22/7)*7*7 = 154 cm². (Option B)",
            "${testId}_q11" to "(x + 1/x)² = x² + 2 + 1/x² = 4² = 16 => x² + 1/x² = 16 - 2 = 14. (Option B)",
            "${testId}_q12" to "sin θ = Perpendicular/Hypotenuse = 3/5. Base = √(5² - 3²) = 4. tan θ = Perpendicular/Base = 3/4. (Option A)",
            "${testId}_q13" to "SP = CP * (1 - Loss%) = 60000 * (1 - 0.15) = 60000 * 0.85 = ₹51,000. (Option B)",
            "${testId}_q14" to "Amount = P*(1 + R/100)² = 10000*(1.1)² = 10000*1.21 = ₹12,100. CI = Amount - P = ₹2,100. (Option B)",
            "${testId}_q15" to "Diagonal = Side * √2 = 10√2 => Side = 10 cm. Perimeter = 4 * Side = 4 * 10 = 40 cm. (Option B)"
        )

        val expHi = mapOf(
            "${testId}_q1" to "माना संख्या X है। X × 25/100 = 75 => X = 300। अब 300 का 40% = 300 × 40/100 = 120। (विकल्प B)",
            "${testId}_q2" to "विक्रय मूल्य = ₹840, लाभ = 20%। क्रय मूल्य = 840 / 1.20 = ₹700। (विकल्प B)",
            "${testId}_q3" to "चाल मीटर/सेकंड में = 54 × (5/18) = 15 मी/से। समय = दूरी / चाल = 180 / 15 = 12 सेकंड। (विकल्प B)",
            "${testId}_q4" to "A/C = (A/B) × (B/C) = (3/4) × (8/9) = 24/36 = 2/3 => 2 : 3। (विकल्प B)",
            "${testId}_q5" to "साधारण ब्याज = (मूलधन × दर × समय) / 100 = (5000 × 8 × 3) / 100 = ₹1,200। (विकल्प B)",
            "${testId}_q6" to "1 दिन का कार्य = 1/10 + 1/15 = 5/30 = 1/6 भाग। अतः पूरा काम 6 दिन में होगा। (विकल्प B)",
            "${testId}_q7" to "गुणनखंड: 36 = 18×2, 54 = 18×3, 90 = 18×5। सबसे बड़ा उभयनिष्ठ गुणनखंड = 18। (विकल्प C)",
            "${testId}_q8" to "BODMAS नियम: (4 - 2) = 2, 24 ÷ 6 = 4, 4 × 2 = 8, 18 + 8 = 26। (विकल्प B)",
            "${testId}_q9" to "मध्य संख्या औसत 27 है। 5 क्रमागत विषम संख्याएं: 23, 25, 27, 29, 31 हैं। सबसे बड़ी संख्या 31 है। (विकल्प B)",
            "${testId}_q10" to "परिधि = 2πr = 44 सेमी => r = 7 सेमी। क्षेत्रफल = πr² = (22/7) × 7 × 7 = 154 सेमी²। (विकल्प B)",
            "${testId}_q11" to "(x + 1/x)² = x² + 2 + 1/x² = 4² = 16 => x² + 1/x² = 16 - 2 = 14। (विकल्प B)",
            "${testId}_q12" to "लंब = 3, कर्ण = 5, आधार = √(25 - 9) = 4। tan θ = लंब/आधार = 3/4। (विकल्प A)",
            "${testId}_q13" to "विक्रय मूल्य = 60000 × (100 - 15)/100 = 60000 × 0.85 = ₹51,000। (विकल्प B)",
            "${testId}_q14" to "मिश्रधन = 10000 × (1 + 10/100)² = 10000 × 1.21 = ₹12,100। चक्रवृद्धि ब्याज = 12100 - 10000 = ₹2,100। (विकल्प B)",
            "${testId}_q15" to "विकर्ण = भुजा × √2 = 10√2 => भुजा = 10 सेमी। परिमाप = 4 × 10 = 40 सेमी। (विकल्प B)"
        )

        val answerKey = ExamAnswerKey(
            id = "${testId}_key",
            testId = testId,
            keys = keys,
            explanationsEn = expEn,
            explanationsHi = expHi
        )

        return Pair(questions, answerKey)
    }

    fun getQuestionsForSsc02(): Pair<List<ExamQuestion>, ExamAnswerKey> {
        val testId = "test_ssc_cgl_chsl_02"
        val questions = listOf(
            ExamQuestion(
                id = "${testId}_q1",
                testId = testId,
                questionIndex = 0,
                questionEn = "If x + y = 12 and xy = 32, find the value of x² + y².",
                questionHi = "यदि x + y = 12 तथा xy = 32 हो, तो x² + y² का मान ज्ञात कीजिए।",
                optionsEn = listOf("A) 80", "B) 96", "C) 112", "D) 124"),
                optionsHi = listOf("A) 80", "B) 96", "C) 112", "D) 124"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q2",
                testId = testId,
                questionIndex = 1,
                questionEn = "A shopkeeper allows a 10% discount on marked price and still gains 20%. If marked price is ₹600, what is the cost price?",
                questionHi = "एक दुकानदार अंकित मूल्य पर 10% छूट देकर भी 20% लाभ प्राप्त करता है। यदि अंकित मूल्य ₹600 हो, तो क्रय मूल्य क्या है?",
                optionsEn = listOf("A) ₹400", "B) ₹450", "C) ₹480", "D) ₹500"),
                optionsHi = listOf("A) ₹400", "B) ₹450", "C) ₹480", "D) ₹500"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q3",
                testId = testId,
                questionIndex = 2,
                questionEn = "Two pipes A and B can fill a tank in 12 hours and 18 hours respectively. In how many hours will both pipes fill it together?",
                questionHi = "दो नल A और B एक टंकी को क्रमशः 12 घंटे और 18 घंटे में भर सकते हैं। दोनों मिलकर उस टंकी को कितने समय में भरेंगे?",
                optionsEn = listOf("A) 6 hours", "B) 6.5 hours", "C) 7.2 hours", "D) 8 hours"),
                optionsHi = listOf("A) 6 घंटे", "B) 6.5 घंटे", "C) 7.2 घंटे", "D) 8 घंटे"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q4",
                testId = testId,
                questionIndex = 3,
                questionEn = "The average weight of 8 persons increases by 1.5 kg when a person of 65 kg is replaced by a new person. Weight of the new person is:",
                questionHi = "8 व्यक्तियों का औसत वजन 1.5 किग्रा बढ़ जाता है जब 65 किग्रा वाले व्यक्ति के स्थान पर नया व्यक्ति आता है। नए व्यक्ति का वजन कितना है?",
                optionsEn = listOf("A) 75 kg", "B) 77 kg", "C) 80 kg", "D) 82 kg"),
                optionsHi = listOf("A) 75 किग्रा", "B) 77 किग्रा", "C) 80 किग्रा", "D) 82 किग्रा"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q5",
                testId = testId,
                questionIndex = 4,
                questionEn = "If tan θ = 4/3, what is the value of (3 sin θ + 2 cos θ) / (3 sin θ - 2 cos θ)?",
                questionHi = "यदि tan θ = 4/3 हो, तो (3 sin θ + 2 cos θ) / (3 sin θ - 2 cos θ) का मान क्या होगा?",
                optionsEn = listOf("A) 3", "B) 2", "C) 4", "D) 5"),
                optionsHi = listOf("A) 3", "B) 2", "C) 4", "D) 5"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q6",
                testId = testId,
                questionIndex = 5,
                questionEn = "Find the compound interest on ₹8,000 at 15% per annum for 2 years compounded annually.",
                questionHi = "₹8,000 की राशि पर 15% वार्षिक दर से 2 वर्ष का चक्रवृद्धि ब्याज ज्ञात कीजिए।",
                optionsEn = listOf("A) ₹2,400", "B) ₹2,580", "C) ₹2,600", "D) ₹2,720"),
                optionsHi = listOf("A) ₹2,400", "B) ₹2,580", "C) ₹2,600", "D) ₹2,720"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q7",
                testId = testId,
                questionIndex = 6,
                questionEn = "A car covers 450 km. If speed was 15 km/h more, it would take 1 hour less. What is the original speed?",
                questionHi = "एक कार 450 किमी दूरी तय करती है। यदि चाल 15 किमी/घंटा अधिक होती तो 1 घंटा कम लगता। मूल चाल ज्ञात कीजिए।",
                optionsEn = listOf("A) 60 km/h", "B) 75 km/h", "C) 80 km/h", "D) 90 km/h"),
                optionsHi = listOf("A) 60 किमी/घंटा", "B) 75 किमी/घंटा", "C) 80 किमी/घंटा", "D) 90 किमी/घंटा"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q8",
                testId = testId,
                questionIndex = 7,
                questionEn = "Evaluate: (256)^0.16 × (256)^0.09",
                questionHi = "मान ज्ञात कीजिए: (256)^0.16 × (256)^0.09",
                optionsEn = listOf("A) 2", "B) 3", "C) 4", "D) 16"),
                optionsHi = listOf("A) 2", "B) 3", "C) 4", "D) 16"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q9",
                testId = testId,
                questionIndex = 8,
                questionEn = "Ratio of ages of Father and Son is 5 : 2. After 4 years, sum of their ages will be 64. Father's present age is:",
                questionHi = "पिता और पुत्र की वर्तमान आयु का अनुपात 5 : 2 है। 4 वर्ष बाद दोनों की आयु का योग 64 होगा। पिता की वर्तमान आयु क्या है?",
                optionsEn = listOf("A) 40 years", "B) 42 years", "C) 45 years", "D) 50 years"),
                optionsHi = listOf("A) 40 वर्ष", "B) 42 वर्ष", "C) 45 वर्ष", "D) 50 वर्ष"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q10",
                testId = testId,
                questionIndex = 9,
                questionEn = "A sphere of radius 6 cm is melted and recast into small spheres of radius 2 cm each. Find the number of small spheres.",
                questionHi = "6 सेमी त्रिज्या के ठोस गोले को पिघलाकर 2 सेमी त्रिज्या की कितनी छोटी गोलियां बनाई जा सकती हैं?",
                optionsEn = listOf("A) 18", "B) 24", "C) 27", "D) 36"),
                optionsHi = listOf("A) 18", "B) 24", "C) 27", "D) 36"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q11",
                testId = testId,
                questionIndex = 10,
                questionEn = "If simple interest on a sum for 3 years at 5% per annum is ₹1,200, find the principal sum.",
                questionHi = "यदि किसी मूलधन पर 5% वार्षिक दर से 3 वर्ष का साधारण ब्याज ₹1,200 है, तो वह मूलधन ज्ञात कीजिए।",
                optionsEn = listOf("A) ₹7,500", "B) ₹8,000", "C) ₹8,500", "D) ₹9,000"),
                optionsHi = listOf("A) ₹7,500", "B) ₹8,000", "C) ₹8,500", "D) ₹9,000"),
                marks = 2.0
            ),
            ExamQuestion(
                id = "${testId}_q12",
                testId = testId,
                questionIndex = 11,
                questionEn = "Simplify: (a³ - b³) / (a² + ab + b²) when a = 15, b = 7.",
                questionHi = "सरल कीजिए: (a³ - b³) / (a² + ab + b²) जहाँ a = 15, b = 7.",
                optionsEn = listOf("A) 7", "B) 8", "C) 15", "D) 22"),
                optionsHi = listOf("A) 7", "B) 8", "C) 15", "D) 22"),
                marks = 2.0
            )
        )

        val keys = mapOf(
            "${testId}_q1" to "A",
            "${testId}_q2" to "B",
            "${testId}_q3" to "C",
            "${testId}_q4" to "B",
            "${testId}_q5" to "A",
            "${testId}_q6" to "B",
            "${testId}_q7" to "B",
            "${testId}_q8" to "C",
            "${testId}_q9" to "A",
            "${testId}_q10" to "C",
            "${testId}_q11" to "B",
            "${testId}_q12" to "B"
        )

        val expEn = mapOf(
            "${testId}_q1" to "x² + y² = (x + y)² - 2xy = 12² - 2(32) = 144 - 64 = 80. (Option A)",
            "${testId}_q2" to "SP = 600 * 0.90 = ₹540. CP = 540 / 1.20 = ₹450. (Option B)",
            "${testId}_q3" to "1/12 + 1/18 = (3 + 2)/36 = 5/36 => Time = 36/5 = 7.2 hours. (Option C)",
            "${testId}_q4" to "Increase = 8 * 1.5 = 12 kg. New weight = 65 + 12 = 77 kg. (Option B)",
            "${testId}_q5" to "Divide numerator & denominator by cos θ: (3 tan θ + 2)/(3 tan θ - 2) = (3*(4/3) + 2)/(3*(4/3) - 2) = 6/2 = 3. (Option A)",
            "${testId}_q6" to "Amount = 8000*(1.15)² = 8000*1.3225 = ₹10,580. CI = ₹2,580. (Option B)",
            "${testId}_q7" to "450/S - 450/(S+15) = 1 => 450*15 = S(S+15) = 6750 = 75*90 => S = 75 km/h. (Option B)",
            "${testId}_q8" to "256^(0.16 + 0.09) = 256^0.25 = 256^(1/4) = 4. (Option C)",
            "${testId}_q9" to "Let ages be 5x, 2x. (5x + 4) + (2x + 4) = 64 => 7x + 8 = 64 => 7x = 56 => x = 8. Father = 5*8 = 40. (Option A)",
            "${testId}_q10" to "Number = (R/r)³ = (6/2)³ = 3³ = 27. (Option C)",
            "${testId}_q11" to "P = (SI * 100)/(R * T) = (1200 * 100)/(5 * 3) = ₹8,000. (Option B)",
            "${testId}_q12" to "Formula: (a³ - b³)/(a² + ab + b²) = a - b = 15 - 7 = 8. (Option B)"
        )

        val expHi = mapOf(
            "${testId}_q1" to "x² + y² = (x + y)² - 2xy = 12² - 2(32) = 144 - 64 = 80। (विकल्प A)",
            "${testId}_q2" to "विक्रय मूल्य = 600 का 90% = ₹540। क्रय मूल्य = 540 / 1.20 = ₹450। (विकल्प B)",
            "${testId}_q3" to "कुल समय = (12 × 18) / (12 + 18) = 216 / 30 = 7.2 घंटे। (विकल्प C)",
            "${testId}_q4" to "कुल वृद्धि = 8 × 1.5 = 12 किग्रा। नए व्यक्ति का भार = 65 + 12 = 77 किग्रा। (विकल्प B)",
            "${testId}_q5" to "cos θ से भाग देने पर: (3 tan θ + 2)/(3 tan θ - 2) = (4 + 2)/(4 - 2) = 6/2 = 3। (विकल्प A)",
            "${testId}_q6" to "मिश्रधन = 8000 × (1.15)² = ₹10,580। चक्रवृद्धि ब्याज = 10,580 - 8,000 = ₹2,580। (विकल्प B)",
            "${testId}_q7" to "मूल चाल 75 किमी/घंटा: 450/75 = 6 घंटे, 450/90 = 5 घंटे (अंतर 1 घंटा)। (विकल्प B)",
            "${testId}_q8" to "256^(0.16 + 0.09) = 256^0.25 = 256^(1/4) = 4। (विकल्प C)",
            "${testId}_q9" to "7x + 8 = 64 => 7x = 56 => x = 8। पिता की आयु = 5 × 8 = 40 वर्ष। (विकल्प A)",
            "${testId}_q10" to "संख्या = (बड़ी त्रिज्या / छोटी त्रिज्या)³ = (6/2)³ = 3³ = 27। (विकल्प C)",
            "${testId}_q11" to "मूलधन = (ब्याज × 100) / (दर × समय) = (1200 × 100)/(5 × 3) = ₹8,000। (विकल्प B)",
            "${testId}_q12" to "सर्वसमिका: (a³ - b³)/(a² + ab + b²) = a - b = 15 - 7 = 8। (विकल्प B)"
        )

        val answerKey = ExamAnswerKey(
            id = "${testId}_key",
            testId = testId,
            keys = keys,
            explanationsEn = expEn,
            explanationsHi = expHi
        )

        return Pair(questions, answerKey)
    }

    fun getQuestionsForJnv03(): Pair<List<ExamQuestion>, ExamAnswerKey> {
        val testId = "test_jnv_sainik_03"
        val questions = listOf(
            ExamQuestion(
                id = "${testId}_q1",
                testId = testId,
                questionIndex = 0,
                questionEn = "What is the successor of 99,999?",
                questionHi = "संख्या 99,999 की अनुवर्ती (Successor) संख्या क्या है?",
                optionsEn = listOf("A) 1,00,000", "B) 99,998", "C) 10,000", "D) 1,00,001"),
                optionsHi = listOf("A) 1,00,000", "B) 99,998", "C) 10,000", "D) 1,00,001"),
                marks = 1.25
            ),
            ExamQuestion(
                id = "${testId}_q2",
                testId = testId,
                questionIndex = 1,
                questionEn = "What is the LCM of 12, 18, and 24?",
                questionHi = "12, 18 और 24 का लघुत्तम समापवर्त्य (LCM / ल.स.) क्या होगा?",
                optionsEn = listOf("A) 36", "B) 72", "C) 96", "D) 144"),
                optionsHi = listOf("A) 36", "B) 72", "C) 96", "D) 144"),
                marks = 1.25
            ),
            ExamQuestion(
                id = "${testId}_q3",
                testId = testId,
                questionIndex = 2,
                questionEn = "Convert 3/5 into percentage.",
                questionHi = "भिन्न 3/5 को प्रतिशत में बदलिए।",
                optionsEn = listOf("A) 30%", "B) 50%", "C) 60%", "D) 75%"),
                optionsHi = listOf("A) 30%", "B) 50%", "C) 60%", "D) 75%"),
                marks = 1.25
            ),
            ExamQuestion(
                id = "${testId}_q4",
                testId = testId,
                questionIndex = 3,
                questionEn = "A rectangular park is 50 m long and 30 m wide. Find its perimeter.",
                questionHi = "एक आयताकार पार्क की लंबाई 50 मीटर तथा चौड़ाई 30 मीटर है। इसका परिमाप ज्ञात कीजिए।",
                optionsEn = listOf("A) 150 m", "B) 160 m", "C) 180 m", "D) 1500 m"),
                optionsHi = listOf("A) 150 मीटर", "B) 160 मीटर", "C) 180 मीटर", "D) 1500 मीटर"),
                marks = 1.25
            ),
            ExamQuestion(
                id = "${testId}_q5",
                testId = testId,
                questionIndex = 4,
                questionEn = "If 15 pens cost ₹180, what is the cost of 8 pens?",
                questionHi = "यदि 15 पेनों का मूल्य ₹180 है, तो 8 पेनों का मूल्य क्या होगा?",
                optionsEn = listOf("A) ₹80", "B) ₹96", "C) ₹104", "D) ₹120"),
                optionsHi = listOf("A) ₹80", "B) ₹96", "C) ₹104", "D) ₹120"),
                marks = 1.25
            ),
            ExamQuestion(
                id = "${testId}_q6",
                testId = testId,
                questionIndex = 5,
                questionEn = "Find the smallest 5-digit number formed by using digits 4, 0, 7, 2, 9 only once.",
                questionHi = "अंकों 4, 0, 7, 2, 9 का केवल एक बार प्रयोग करके 5 अंकों की सबसे छोटी संख्या क्या होगी?",
                optionsEn = listOf("A) 20,479", "B) 02,479", "C) 24,079", "D) 20,497"),
                optionsHi = listOf("A) 20,479", "B) 02,479", "C) 24,079", "D) 20,497"),
                marks = 1.25
            ),
            ExamQuestion(
                id = "${testId}_q7",
                testId = testId,
                questionIndex = 6,
                questionEn = "Solve: 45 - [38 - {60 ÷ 3 - (6 - 9 ÷ 3)}]",
                questionHi = "सरल कीजिए: 45 - [38 - {60 ÷ 3 - (6 - 9 ÷ 3)}]",
                optionsEn = listOf("A) 20", "B) 24", "C) 28", "D) 32"),
                optionsHi = listOf("A) 20", "B) 24", "C) 28", "D) 32"),
                marks = 1.25
            ),
            ExamQuestion(
                id = "${testId}_q8",
                testId = testId,
                questionIndex = 7,
                questionEn = "What fraction of an hour is 20 minutes?",
                questionHi = "20 मिनट, 1 घंटे का कौन सा भिन्न भाग है?",
                optionsEn = listOf("A) 1/3", "B) 1/4", "C) 1/2", "D) 2/5"),
                optionsHi = listOf("A) 1/3", "B) 1/4", "C) 1/2", "D) 2/5"),
                marks = 1.25
            ),
            ExamQuestion(
                id = "${testId}_q9",
                testId = testId,
                questionIndex = 8,
                questionEn = "Find the average of 12, 16, 20, 24, and 28.",
                questionHi = "संख्याओं 12, 16, 20, 24 और 28 का औसत ज्ञात कीजिए।",
                optionsEn = listOf("A) 18", "B) 20", "C) 22", "D) 24"),
                optionsHi = listOf("A) 18", "B) 20", "C) 22", "D) 24"),
                marks = 1.25
            ),
            ExamQuestion(
                id = "${testId}_q10",
                testId = testId,
                questionIndex = 9,
                questionEn = "A boy sleeps at 9:30 PM and wakes up at 6:00 AM. How long did he sleep?",
                questionHi = "एक बालक रात 9:30 बजे सोता है तथा सुबह 6:00 बजे जागता है। वह कुल कितने समय सोया?",
                optionsEn = listOf("A) 8 hours", "B) 8 hours 15 mins", "C) 8 hours 30 mins", "D) 9 hours"),
                optionsHi = listOf("A) 8 घंटे", "B) 8 घंटे 15 मिनट", "C) 8 घंटे 30 मिनट", "D) 9 घंटे"),
                marks = 1.25
            )
        )

        val keys = mapOf(
            "${testId}_q1" to "A",
            "${testId}_q2" to "B",
            "${testId}_q3" to "C",
            "${testId}_q4" to "B",
            "${testId}_q5" to "B",
            "${testId}_q6" to "A",
            "${testId}_q7" to "B",
            "${testId}_q8" to "A",
            "${testId}_q9" to "B",
            "${testId}_q10" to "C"
        )

        val expEn = mapOf(
            "${testId}_q1" to "Successor = 99999 + 1 = 1,00,000. (Option A)",
            "${testId}_q2" to "Multiples of 24: 24, 48, 72. 72 is divisible by 12, 18, 24. LCM = 72. (Option B)",
            "${testId}_q3" to "(3/5) * 100 = 60%. (Option C)",
            "${testId}_q4" to "Perimeter = 2 * (L + W) = 2 * (50 + 30) = 2 * 80 = 160 m. (Option B)",
            "${testId}_q5" to "Cost of 1 pen = 180 / 15 = ₹12. Cost of 8 pens = 12 * 8 = ₹96. (Option B)",
            "${testId}_q6" to "Smallest non-zero digit first: 2, then 0, 4, 7, 9 => 20,479. (Option A)",
            "${testId}_q7" to "Inside innermost: (6 - 3) = 3. Then {20 - 3} = 17. Then [38 - 17] = 21. 45 - 21 = 24. (Option B)",
            "${testId}_q8" to "20 mins / 60 mins = 1/3. (Option A)",
            "${testId}_q9" to "Equidistant AP series, middle term is average = 20. (Option B)",
            "${testId}_q10" to "9:30 PM to 12:00 midnight = 2 hr 30 min. Midnight to 6:00 AM = 6 hr. Total = 8 hr 30 min. (Option C)"
        )

        val expHi = mapOf(
            "${testId}_q1" to "अनुवर्ती संख्या = 99,999 + 1 = 1,00,000। (विकल्प A)",
            "${testId}_q2" to "12, 18 और 24 का ल.स. = 72। (विकल्प B)",
            "${testId}_q3" to "(3/5) × 100 = 60%। (विकल्प C)",
            "${testId}_q4" to "परिमाप = 2 × (लंबाई + चौड़ाई) = 2 × (50 + 30) = 160 मीटर। (विकल्प B)",
            "${testId}_q5" to "1 पेन = ₹180 / 15 = ₹12। 8 पेन = 12 × 8 = ₹96। (विकल्प B)",
            "${testId}_q6" to "शून्य से बड़े न्यूनतम अंक से शुरू करें: 20,479। (विकल्प A)",
            "${testId}_q7" to "BODMAS हल: (6 - 3) = 3, {20 - 3} = 17, [38 - 17] = 21, 45 - 21 = 24। (विकल्प B)",
            "${testId}_q8" to "20 मिनट / 60 मिनट = 1/3 भाग। (विकल्प A)",
            "${testId}_q9" to "मध्य पद ही औसत होता है = 20। (विकल्प B)",
            "${testId}_q10" to "9:30 से 12:00 = 2 घंटे 30 मिनट + 6 घंटे = 8 घंटे 30 मिनट। (विकल्प C)"
        )

        val answerKey = ExamAnswerKey(
            id = "${testId}_key",
            testId = testId,
            keys = keys,
            explanationsEn = expEn,
            explanationsHi = expHi
        )

        return Pair(questions, answerKey)
    }

    fun getQuestionsForTest(testId: String): Pair<List<ExamQuestion>, ExamAnswerKey> {
        return when (testId) {
            "test_railway_math_01" -> getQuestionsForRailway01()
            "test_ssc_cgl_chsl_02" -> getQuestionsForSsc02()
            "test_jnv_sainik_03" -> getQuestionsForJnv03()
            else -> {
                NavodayaSeedData.getQuestions(testId)
                    ?: SainikSeedData.getQuestions(testId)
                    ?: SimultalaOlympiadSeedData.getQuestions(testId)
                    ?: RailwaySscSeedData.getQuestions(testId)
                    ?: ExtendedSchoolSeedData.getQuestions(testId)
                    ?: ExtendedCompetitiveSeedData.getQuestions(testId)
                    ?: Pair(emptyList(), ExamAnswerKey(testId = testId))
            }
        }
    }

    fun getRecommendedVideos(): List<VideoRecommendation> = listOf(
        VideoRecommendation(
            title = "Percentage Shortcut Tricks (प्रतिशत जादुई ट्रिक)",
            titleHindi = "प्रतिशत शॉर्टकट ट्रिक्स - बिना पेन उठाए सवाल हल करें",
            topic = "Percentage",
            duration = "45 mins",
            description = "Complete concept of Percentage for Railway, SSC and Sainik School exams with Mithlesh Sir.",
            targetExam = "Railway / SSC"
        ),
        VideoRecommendation(
            title = "Time & Work Masterclass (समय और कार्य)",
            titleHindi = "समय और कार्य - LCM मेथड से सबसे तेज हल",
            topic = "Time & Work",
            duration = "52 mins",
            description = "Learn how to solve complex Time and Work questions in 10 seconds using LCM method.",
            targetExam = "All Exams"
        ),
        VideoRecommendation(
            title = "Profit & Loss Formula Free Approach (लाभ एवं हानि)",
            titleHindi = "लाभ और हानि - बिना किसी सूत्र के आसान तरीका",
            topic = "Profit & Loss",
            duration = "38 mins",
            description = "Master Cost Price, Selling Price and Marked Price discount concepts step-by-step.",
            targetExam = "SSC / Railway"
        ),
        VideoRecommendation(
            title = "Navodaya & Sainik School Number System (संख्या पद्धति)",
            titleHindi = "नवोदय एवं सैनिक स्कूल प्रवेश परीक्षा - संख्या पद्धति",
            topic = "Number System",
            duration = "60 mins",
            description = "Basic to advanced questions of Divisibility, Prime numbers, LCM & HCF for young aspirants.",
            targetExam = "JNVST / AISSEE"
        ),
        VideoRecommendation(
            title = "Speed, Time & Train Problems (चाल, समय और रेलगाड़ी)",
            titleHindi = "ट्रेन और प्लेटफॉर्म वाले सवाल - चुटकियों में हल करें",
            topic = "Speed & Distance",
            duration = "40 mins",
            description = "Relative speed, pole crossing and platform crossing questions explained by Mithlesh Sir.",
            targetExam = "Railway Special"
        )
    )

    fun getSampleCsvTemplate(): String {
        return """Question_EN,Question_HI,Option_A,Option_B,Option_C,Option_D,Correct_Option,Explanation_EN,Explanation_HI,Marks
"What is 20% of 250?","250 का 20% क्या है?","40","50","60","75","B","250 * 0.20 = 50","250 × 20/100 = 50","1.0"
"Find the square root of 625.","625 का वर्गमूल ज्ञात कीजिए।","15","20","25","35","C","sqrt(625) = 25","625 का वर्गमूल 25 है।","1.0"
"Sum of angles in a triangle is:","त्रिभुज के तीनों कोणों का योग होता है:","90°","180°","270°","360°","B","Sum of triangle interior angles is 180°","त्रिभुज के अंतःकोणों का योग 180° होता है।","1.0"
"What is the LCM of 12 and 18?","12 और 18 का ल.स.प. (LCM) क्या है?","24","36","48","72","B","LCM(12, 18) = 36","12 और 18 का LCM 36 है।","1.0"
"If CP is ₹500 and SP is ₹600, Profit% is:","यदि क्रय मूल्य ₹500 तथा विक्रय मूल्य ₹600 हो, तो लाभ प्रतिशत है:","10%","15%","20%","25%","C","Profit = 100/500 * 100 = 20%","लाभ = (100 / 500) × 100 = 20%","1.0"
"""
    }

    fun getSampleExcelTsvTemplate(): String {
        return "Question_EN\tQuestion_HI\tOption_A\tOption_B\tOption_C\tOption_D\tCorrect_Option\tExplanation_EN\tExplanation_HI\tMarks\n" +
                "What is 20% of 250?\t250 का 20% क्या है?\t40\t50\t60\t75\tB\t250 * 0.20 = 50\t250 × 20/100 = 50\t1.0\n" +
                "Find the square root of 625.\t625 का वर्गमूल ज्ञात कीजिए।\t15\t20\t25\t35\tC\tsqrt(625) = 25\t625 का वर्गमूल 25 है।\t1.0\n" +
                "Sum of angles in a triangle is:\tत्रिभुज के तीनों कोणों का योग होता है:\t90°\t180°\t270°\t360°\tB\tSum is 180°\tत्रिभुज के कोणों का योग 180° होता है।\t1.0\n" +
                "What is the LCM of 12 and 18?\t12 और 18 का ल.स.प. (LCM) क्या है?\t24\t36\t48\t72\tB\tLCM(12, 18) = 36\t12 और 18 का LCM 36 है।\t1.0\n" +
                "If CP is ₹500 and SP is ₹600, Profit% is:\tयदि क्रय मूल्य ₹500 तथा विक्रय मूल्य ₹600 हो, तो लाभ प्रतिशत है:\t10%\t15%\t20%\t25%\tC\tProfit = 20%\tलाभ = 20%\t1.0"
    }
}
